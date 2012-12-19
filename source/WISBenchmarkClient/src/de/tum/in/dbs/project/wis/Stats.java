package de.tum.in.dbs.project.wis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class is used to log the duration of each clients website calls
 */
public class Stats {

	/**
	 * Total amount of website calls
	 */
	private static int totalWebsiteCalls;

	/**
	 * Amount of clients
	 */
	private static int amountClients;

	/**
	 * Amount of calls per client
	 */
	private static int amountCalls;

	/**
	 * Time of benchmark start
	 */
	private static long startTime;

	/**
	 * HashMap with all clients, all websites and all durations
	 */
	private static Map<Integer, Map<String, List<Long>>> stats = new HashMap<Integer, Map<String, List<Long>>>();

	/**
	 * Initialize the statistics
	 * 
	 * @param websites
	 *            the list of websites
	 * @param amountClients
	 *            the amount of clients
	 * @param amountCalls
	 *            the amount of calls per client
	 */
	public static void initialize(List<String> websites, int amountClients,
			int amountCalls) {
		Stats.amountClients = amountClients;
		Stats.amountCalls = amountCalls;
		Stats.totalWebsiteCalls = amountClients * amountCalls;

		// Initialize the hash maps
		for (int clientId = 1; clientId <= amountClients; clientId++) {
			Map<String, List<Long>> websiteDurations = new HashMap<String, List<Long>>();
			for (String website : websites) {
				if (!websiteDurations.containsKey(website)) {
					websiteDurations.put(website, new ArrayList<Long>());
				}
			}
			stats.put(clientId, websiteDurations);
		}

		// Set the start time
		Stats.startTime = System.currentTimeMillis();
	}

	/**
	 * Log the duration of calling a website for each client
	 * 
	 * @param clientId
	 *            id of the client
	 * @param website
	 *            website which was called
	 * @param duration
	 *            duration in milliseconds of the call
	 */
	public static void logDuration(int clientId, String website, long duration) {
		stats.get(clientId).get(website).add(duration);

		totalWebsiteCalls--;
		if (totalWebsiteCalls == 0) {
			printStatistics();
		}
	}

	/**
	 * Print the statistics
	 */
	private static void printStatistics() {
		System.out
				.println("---------------------------------------------------------------");
		System.out
				.println("----------------- Statistics of the Benchmark -----------------");
		System.out
				.println("---------------------------------------------------------------");
		System.out.println("Simulated Clients: " + Stats.amountClients);
		System.out.println("Website calls per client: " + Stats.amountCalls);
		System.out.println("Total website calls: " + Stats.amountCalls
				* Stats.amountClients);
		System.out
				.println("---------------------------------------------------------------");
		Map<String, Integer> websiteCalls = getWebsiteCalls();
		for (Entry<String, Long> entry : durationAverageWebsite().entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue()
					+ " ms (average), "
					+ websiteCalls.get(entry.getKey()) + " calls");
		}
		System.out
				.println("---------------------------------------------------------------");
		System.out
				.println("Total time needed for executing the benchmark client: "
						+ convertMillisecondsToString(System
								.currentTimeMillis() - Stats.startTime));
		System.out
				.println("---------------------------------------------------------------");
	}

	/**
	 * Calculate the average duration for each website
	 * 
	 * @return map
	 */
	private static Map<String, Long> durationAverageWebsite() {
		Map<String, Long> durationWebsiteAverage = new HashMap<String, Long>();
		Map<String, List<Long>> durationWebsite = getWebsiteDurations();

		// Calculate the average
		for (Entry<String, List<Long>> durations : durationWebsite.entrySet()) {
			long sum = 0;
			for (int itr = 0; itr < durations.getValue().size(); itr++) {
				sum += durations.getValue().get(itr);
			}
			durationWebsiteAverage.put(durations.getKey(), sum
					/ durations.getValue().size());
		}

		return durationWebsiteAverage;
	}

	/**
	 * Calculate a map with the durations of all websites
	 * 
	 * @return map
	 */
	private static Map<String, List<Long>> getWebsiteDurations() {
		Map<String, List<Long>> durationWebsite = new HashMap<String, List<Long>>();

		// Concatenate the durations of all clients
		for (Entry<Integer, Map<String, List<Long>>> clientEntry : stats
				.entrySet()) {
			for (Entry<String, List<Long>> websiteEntry : clientEntry
					.getValue().entrySet()) {
				if (!durationWebsite.containsKey(websiteEntry.getKey())) {
					durationWebsite.put(websiteEntry.getKey(),
							new ArrayList<Long>());
				}
				// Concatenate the durations
				List<Long> newDurations = new ArrayList<Long>(
						durationWebsite.get(websiteEntry.getKey()));
				newDurations.addAll(websiteEntry.getValue());

				// Put the concatenated durations
				durationWebsite.put(websiteEntry.getKey(), newDurations);
			}
		}

		return durationWebsite;
	}

	/**
	 * Get the amount of website calls
	 * 
	 * @return map
	 */
	private static Map<String, Integer> getWebsiteCalls() {
		Map<String, Integer> calls = new HashMap<String, Integer>();
		Map<String, List<Long>> durationWebsite = getWebsiteDurations();

		for (Entry<String, List<Long>> durations : durationWebsite.entrySet()) {
			calls.put(durations.getKey(), durations.getValue().size());
		}

		return calls;
	}

	/**
	 * Convert a time in milliseconds to a string in days, hours, minutes,
	 * seconds and milliseconds
	 * 
	 * @param ms
	 *            the time in milliseconds
	 * @return a pretty string
	 */
	private static String convertMillisecondsToString(long ms) {
		long givenMS = ms;
		long days = givenMS / 1000 / 60 / 60 / 24;
		long daysInMS = days * 1000 * 60 * 60 * 24;
		long hours = ((givenMS - daysInMS) / 1000 / 60 / 60);
		long hoursInMS = hours * 1000 * 60 * 60;
		long mins = ((givenMS - daysInMS - hoursInMS) / 1000 / 60);
		long minsInMS = mins * 1000 * 60;
		long secs = (givenMS - daysInMS - hoursInMS - minsInMS) / 1000;
		long secsInMS = secs * 1000;
		long milliseconds = givenMS - daysInMS - hoursInMS - minsInMS
				- secsInMS;

		StringBuilder sb = new StringBuilder();
		if (days > 0) {
			sb.append(String.valueOf(days) + "d ");
		}
		if (hours > 0) {
			sb.append(String.valueOf(hours) + "h ");
		}
		if (mins > 0) {
			sb.append(String.valueOf(mins) + "m ");
		}
		if (secs > 0) {
			sb.append(String.valueOf(secs) + "s ");
		}
		sb.append(String.valueOf(milliseconds) + "ms");
		return sb.toString();
	}

}
