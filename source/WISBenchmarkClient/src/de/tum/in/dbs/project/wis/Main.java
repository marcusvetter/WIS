package de.tum.in.dbs.project.wis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class to start the benchmark client
 */
public class Main {

	/**
	 * @param 1st param: CSV file with urls and amount 2nd param: Sleep time (in
	 *        seconds) 3rd param: Amount of simulated clients 4th param: Amount
	 *        of website calls per client
	 * 
	 */
	public static void main(String[] args) {
		// Check the params
		if (args.length != 4) {
			printUsage();
			return;
		}
		String fileName = "";
		int sleepTimeSec = 0;
		int amountClients = 0;
		int amountCalls = 0;
		try {
			fileName = args[0];
			sleepTimeSec = Integer.valueOf(args[1]);
			amountClients = Integer.valueOf(args[2]);
			amountCalls = Integer.valueOf(args[3]);
		} catch (NumberFormatException nfe) {
			printUsage();
			return;
		}

		// Log welcome
		System.out.println("Welcome to the WIS Benchmark Client.");

		// Read the configuration file
		File f = new File(fileName);
		if (!f.canRead()) {
			System.out.println("Can not read the configuration file.");
			System.out.println("Aborted.");
			return;
		}

		// Parse the configuration file
		List<String> websites = ConfigurationParser.parseConfiguration(f);

		// Start the clients
		startClients(websites, amountClients, sleepTimeSec, amountCalls);

		// Log finished
		System.out.println("Finished the WIS Benchmark Client.");
	}

	/**
	 * Print usage to the console
	 */
	private static void printUsage() {
		System.out
				.println("Please start the WIS Benchmark Client with the following parameters:");
		System.out.println("1st parameter: CSV file with urls and amount");
		System.out.println("2nd parameter: Sleep time (in seconds)");
		System.out.println("3rd parameter: Amount of simulated clients");
		System.out.println("4th parameter: Amount of website calls per client");
	}

	/**
	 * Start the clients. For each client one thread will be started.
	 * 
	 * @param websites
	 *            list with websites
	 * @param amountClients
	 *            amount of clients
	 * @param sleepTimeSec
	 *            sleeptime in seconds
	 * @param amountCalls
	 *            amount of website calls per client
	 */
	private static void startClients(List<String> websites, int amountClients,
			int sleepTimeSec, int amountCalls) {
		for (int i = 1; i <= amountClients; i++) {
			// Create a copy of the website list for the client
			List<String> websitesForClient = new ArrayList<String>();
			for (String website : websites) {
				websitesForClient.add(website);
			}

			// Start the client
			Client client = new Client(i, websitesForClient, sleepTimeSec,
					amountCalls);
			client.start();
		}
	}

}
