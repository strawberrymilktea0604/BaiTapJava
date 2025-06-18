package GeigerCounterAnalysis;

public class RadiationSample {

	private String dateTime;
	private int countsPerMinute;
	
	public RadiationSample(String dt, int counts) {
		dateTime = dt;
		countsPerMinute = counts;
	}
	
	public String getDateTime() {
		return dateTime;
	}
	
	public int getCountsPerMinute() {
		return countsPerMinute;
	}
	
	public static void main(String[] args) {
		RadiationSample sample = new RadiationSample("4/6/2018 17:15", 17);
		System.out.println("Date and Time : " + sample.getDateTime() +
			"\nCounts Per Minute: " + sample.getCountsPerMinute());
	}
}