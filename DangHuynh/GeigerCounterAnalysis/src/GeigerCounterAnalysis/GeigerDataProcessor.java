package GeigerCounterAnalysis;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class GeigerDataProcessor {
    
    private static final String DATA_FILE = "7_14_2019.txt";
    private static final int PROXIMITY_THRESHOLD = 5;
    
    private List<RadiationReading> radiationData;
    private int maximumCPM;
    
    public GeigerDataProcessor() {
        this.radiationData = new ArrayList<>();
        this.maximumCPM = 0;
    }
    
    public static void main(String[] args) {
        GeigerDataProcessor processor = new GeigerDataProcessor();
        processor.executeAnalysis();
    }
    
    /**
     * Main analysis workflow execution
     */
    public void executeAnalysis() {
        System.out.println("=== GEIGER COUNTER DATA ANALYSIS ===");
        System.out.println("Processing file: " + DATA_FILE);
        System.out.println();
        
        if (!loadRadiationData()) {
            System.err.println("Failed to load radiation data. Exiting.");
            return;
        }
        
        calculateMaximumCPM();
        displayAnalysisResults();
        performCampingTripDetection();
    }
    
    /**
     * Loads and parses radiation data from CSV file
     * @return true if data loaded successfully, false otherwise
     */
    private boolean loadRadiationData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String currentLine;
            int validEntries = 0;
            
            while ((currentLine = reader.readLine()) != null) {
                RadiationReading reading = parseRadiationEntry(currentLine);
                if (reading != null) {
                    radiationData.add(reading);
                    validEntries++;
                }
            }
            
            System.out.println("Loaded " + validEntries + " valid radiation measurements");
            return validEntries > 0;
            
        } catch (FileNotFoundException e) {
            System.err.println("Error: Cannot locate file " + DATA_FILE);
            System.err.println("Please ensure the file exists in your project directory");
            return false;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Parses individual CSV line into RadiationReading object
     * @param csvLine Raw CSV line from file
     * @return RadiationReading object or null if line is invalid
     */
    private RadiationReading parseRadiationEntry(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            return null;
        }
        
        // Skip header lines and location markers
        if (csvLine.contains("GQ Electronics") || 
            csvLine.contains("Date Time,uSv") ||
            csvLine.contains("FEDERAL-WAY") ||
            !csvLine.contains(",")) {
            return null;
        }
        
        String[] fields = csvLine.split(",");
        
        // Validate CSV structure - need at least timestamp and CPM
        if (fields.length < 3) {
            return null;
        }
        
        String timestamp = fields[0].trim();
        String cpmValue = fields[2].trim();
        
        // Ensure we have actual data values
        if (timestamp.isEmpty() || cpmValue.isEmpty()) {
            return null;
        }
        
        // Validate this is a measurement line (contains "Every Minute")
        if (fields.length > 1 && !fields[1].trim().equals("Every Minute")) {
            return null;
        }
        
        try {
            int countsPerMinute = Integer.parseInt(cpmValue);
            return new RadiationReading(timestamp, countsPerMinute);
        } catch (NumberFormatException e) {
            // Skip malformed numeric data
            return null;
        }
    }
    
    /**
     * Determines the maximum CPM value in the dataset
     */
    private void calculateMaximumCPM() {
        maximumCPM = radiationData.stream()
                                 .mapToInt(RadiationReading::getCountsPerMinute)
                                 .max()
                                 .orElse(0);
    }
    
    /**
     * Displays analysis results including high radiation readings
     */
    private void displayAnalysisResults() {
        System.out.println("Maximum radiation detected: " + maximumCPM + " CPM");
        System.out.println();
        
        int thresholdValue = maximumCPM - PROXIMITY_THRESHOLD;
        System.out.println("Displaying all readings >= " + thresholdValue + " CPM:");
        System.out.println("Date Time                CPM");
        System.out.println("=======================  ===");
        
        radiationData.stream()
                    .filter(reading -> reading.getCountsPerMinute() >= thresholdValue)
                    .forEach(reading -> System.out.printf("%-23s  %d%n", 
                           reading.getTimestamp(), 
                           reading.getCountsPerMinute()));
        
        System.out.println();
    }
    
    /**
     * Analyzes high radiation patterns to identify potential camping trips
     */
    private void performCampingTripDetection() {
        System.out.println("=== CAMPING TRIP ANALYSIS ===");
        
        int thresholdValue = maximumCPM - PROXIMITY_THRESHOLD;
        Map<String, Integer> dailyHighCounts = new TreeMap<>();
        
        // Count high readings per day
        for (RadiationReading reading : radiationData) {
            if (reading.getCountsPerMinute() >= thresholdValue) {
                String dateOnly = extractDateFromTimestamp(reading.getTimestamp());
                dailyHighCounts.merge(dateOnly, 1, Integer::sum);
            }
        }
        
        // Display daily analysis
        System.out.println("Days with elevated radiation readings:");
        System.out.println("Date          High Readings Count");
        System.out.println("==========    ==================");
        
        for (Map.Entry<String, Integer> entry : dailyHighCounts.entrySet()) {
            System.out.printf("%-12s  %d%n", entry.getKey(), entry.getValue());
            
            // Flag potential camping indicators
            if (entry.getValue() >= 8) {
                System.out.println("    ^^^ POTENTIAL CAMPING DAY - Sustained high readings!");
            }
        }
        
        System.out.println();
        System.out.println("ANALYSIS CONCLUSION:");
        System.out.println("Camping trip dates are identified by consecutive days with");
        System.out.println("numerous high radiation readings (8+ per day), indicating");
        System.out.println("extended time at 4900ft elevation where cosmic radiation");
        System.out.println("exposure is approximately double that at sea level.");
        
        identifyConsecutiveCampingDays(dailyHighCounts);
    }
    
    /**
     * Identifies consecutive days with high readings that suggest camping trips
     */
    private void identifyConsecutiveCampingDays(Map<String, Integer> dailyHighCounts) {
        System.out.println();
        System.out.println("PROBABLE CAMPING TRIP PERIODS:");
        
        List<String> campingDays = dailyHighCounts.entrySet().stream()
                                                 .filter(entry -> entry.getValue() >= 8)
                                                 .map(Map.Entry::getKey)
                                                 .sorted()
                                                 .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        if (campingDays.isEmpty()) {
            System.out.println("No clear camping trip patterns detected.");
            return;
        }
        
        // Group consecutive camping days
        List<String> currentTrip = new ArrayList<>();
        String previousDate = "";
        
        for (String date : campingDays) {
            if (isConsecutiveDay(previousDate, date) && !currentTrip.isEmpty()) {
                currentTrip.add(date);
            } else {
                if (!currentTrip.isEmpty()) {
                    displayCampingTrip(currentTrip);
                }
                currentTrip = new ArrayList<>();
                currentTrip.add(date);
            }
            previousDate = date;
        }
        
        // Handle final trip group
        if (!currentTrip.isEmpty()) {
            displayCampingTrip(currentTrip);
        }
    }
    
    /**
     * Displays information about a detected camping trip
     */
    private void displayCampingTrip(List<String> tripDays) {
        if (tripDays.size() == 1) {
            System.out.println("Single day elevation event: " + tripDays.get(0));
        } else {
            System.out.println("Multi-day camping trip detected:");
            System.out.println("  Start: " + tripDays.get(0));
            System.out.println("  End:   " + tripDays.get(tripDays.size() - 1));
            System.out.println("  Duration: " + tripDays.size() + " days");
        }
    }
    
    /**
     * Extracts date portion from timestamp string
     */
    private String extractDateFromTimestamp(String timestamp) {
        if (timestamp.length() >= 10) {
            return timestamp.substring(0, 10);
        }
        return timestamp;
    }
    
    /**
     * Checks if two dates are consecutive
     */
    private boolean isConsecutiveDay(String date1, String date2) {
        if (date1.isEmpty() || date2.isEmpty()) {
            return false;
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            
            long diffInMillis = d2.getTime() - d1.getTime();
            long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
            
            return diffInDays == 1;
        } catch (Exception e) {
            return false;
        }
    }
}
