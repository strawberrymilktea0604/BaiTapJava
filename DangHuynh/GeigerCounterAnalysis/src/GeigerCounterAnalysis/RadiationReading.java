package GeigerCounterAnalysis;

import java.util.Objects;

public class RadiationReading {
    
    private final String timestamp;
    private final int countsPerMinute;
    
    /**
     * Creates a new radiation reading with specified timestamp and CPM value
     * 
     * @param timestamp Date and time when measurement was recorded
     * @param countsPerMinute Number of radiation events detected per minute
     */
    public RadiationReading(String timestamp, int countsPerMinute) {
        this.timestamp = timestamp;
        this.countsPerMinute = countsPerMinute;
    }
    
    /**
     * Retrieves the timestamp for this measurement
     * @return Formatted date/time string
     */
    public String getTimestamp() {
        return timestamp;
    }
    
    /**
     * Retrieves the radiation count for this measurement
     * @return Counts per minute value
     */
    public int getCountsPerMinute() {
        return countsPerMinute;
    }
    
    /**
     * Provides string representation of this radiation reading
     * @return Formatted string showing timestamp and CPM
     */
    @Override
    public String toString() {
        return String.format("%s: %d CPM", timestamp, countsPerMinute);
    }
    
    /**
     * Compares this reading with another for equality
     * @param other Object to compare against
     * @return true if timestamps and CPM values match
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        
        RadiationReading that = (RadiationReading) other;
        return countsPerMinute == that.countsPerMinute && 
               timestamp.equals(that.timestamp);
    }
    
    /**
     * Generates hash code for this reading
     * @return Hash code based on timestamp and CPM
     */
    @Override
    public int hashCode() {
        return Objects.hash(timestamp, countsPerMinute);
    }
    
    /**
     * Compares radiation levels between readings
     * @param other Another RadiationReading to compare with
     * @return true if this reading has higher CPM than the other
     */
    public boolean hasHigherRadiationThan(RadiationReading other) {
        return this.countsPerMinute > other.countsPerMinute;
    }
    
    /**
     * Determines if this reading qualifies as "high" relative to a threshold
     * @param threshold Minimum CPM value to be considered high
     * @return true if this reading meets or exceeds the threshold
     */
    public boolean isHighReading(int threshold) {
        return this.countsPerMinute >= threshold;
    }
}
