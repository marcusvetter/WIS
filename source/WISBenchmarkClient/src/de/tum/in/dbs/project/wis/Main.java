package de.tum.in.dbs.project.wis;

import java.io.File;
import java.util.List;

/**
 * Main class to start the benchmark client
 */
public class Main {

	/**
	 * @param 1st param: CSV file with urls and amount 2nd param: Sleep time (in
	 *        seconds) 3rd param: Amount of simulated clients
	 * 
	 */
	public static void main(String[] args) {
		// Check the params
		if (args.length != 3) {
			showStartParams();
			return;
		}
		String fileName = "";
		int sleepTimeSec = 0;
		int amountClients = 0;
		try {
			fileName = args[0];
			sleepTimeSec = Integer.valueOf(args[1]);
			amountClients = Integer.valueOf(args[2]);
		} catch (NumberFormatException nfe) {
			showStartParams();
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

		// Start the clients using the client manager
		ClientManager clientManager = new ClientManager(websites,
				amountClients, sleepTimeSec);
		clientManager.run();

		// Log finished
		System.out.println("Finished the WIS Benchmark Client.");
	}

	private static void showStartParams() {
		System.out
				.println("Please start the WIS Benchmark Client with the following parameters:");
		System.out.println("1st parameter: CSV file with urls and amount");
		System.out.println("2nd parameter: Sleep time (in seconds)");
		System.out.println("3rd parameter: Amount of simulated clients");
	}

}
