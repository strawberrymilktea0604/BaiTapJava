package GeigerCounterAnalysis;

import java.io.*;
import java.util.*;

public class GeigerCounterAnalysis {
    
    public static void main(String[] args) {
        String filename = "7_14_2019.txt";
        List<RadiationSample> samples = new ArrayList<>();
        
        // Read and parse the data file
        try {
            samples = readRadiationData(filename);
            System.out.println("Successfully read " + samples.size() + " radiation samples.");
            
            // Find maximum counts per minute
            int maxCounts = findMaxCounts(samples);
            System.out.println("Maximum counts per minute found: " + maxCounts);
            System.out.println();
            
            // Display all samples within 5 counts of maximum
            System.out.println("Radiation samples within 5 counts of maximum (" + maxCounts + "):");
            System.out.println("Date\t\t\tCounts per minute");
            System.out.println("----------------------------------------");
            
            displayHighReadings(samples, maxCounts);
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.err.println("Make sure the file '7_14_2019.txt' is in your project directory.");
        }
    }
    
    /**
     * Reads radiation data from CSV file and creates RadiationSample objects
     * @param filename the name of the CSV file to read
     * @return List of RadiationSample objects
     * @throws IOException if file cannot be read
     */
    public static List<RadiationSample> readRadiationData(String filename) throws IOException {
        List<RadiationSample> samples = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        
        while ((line = reader.readLine()) != null) {
            // Skip header lines and location lines
            if (line.contains("GQ Electronics") || 
                line.contains("Date Time") || 
                line.contains("FEDERAL-WAY") ||
                line.trim().isEmpty()) {
                continue;
            }
            
            // Parse data lines
            String[] fields = line.split(",");
            if (fields.length >= 3) {
                try {
                    String dateTime = fields[0].trim();
                    String cpmString = fields[2].trim();
                    
                    // Skip lines that don't have valid CPM data
                    if (dateTime.isEmpty() || cpmString.isEmpty() || 
                        cpmString.equals("Every Minute")) {
                        continue;
                    }
                    
                    int countsPerMinute = Integer.parseInt(cpmString);
                    samples.add(new RadiationSample(dateTime, countsPerMinute));
                    
                } catch (NumberFormatException e) {
                    // Skip lines with invalid number format
                    continue;
                }
            }
        }
        reader.close();
        return samples;
    }
    
    /**
     * Finds the maximum counts per minute in the sample data
     * @param samples List of RadiationSample objects
     * @return maximum counts per minute value
     */
    public static int findMaxCounts(List<RadiationSample> samples) {
        int max = 0;
        for (RadiationSample sample : samples) {
            if (sample.getCountsPerMinute() > max) {
                max = sample.getCountsPerMinute();
            }
        }
        return max;
    }
    
    /**
     * Displays all radiation samples within 5 counts of the maximum
     * @param samples List of RadiationSample objects
     * @param maxCounts the maximum counts per minute value
     */
    public static void displayHighReadings(List<RadiationSample> samples, int maxCounts) {
        int threshold = maxCounts - 5;
        
        for (RadiationSample sample : samples) {
            if (sample.getCountsPerMinute() >= threshold) {
                System.out.println(sample.getDateTime() + "\t\t" + 
                                 sample.getCountsPerMinute());
            }
        }
    }
}