package de.tum.in.dbs.project.wis;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {

	private List<String> websites;
	private int amountClients;
	private int sleepTimeSec;

	public ClientManager(List<String> websites, int amountClients,
			int sleepTimeSec) {
		this.websites = websites;
		this.amountClients = amountClients;
		this.sleepTimeSec = sleepTimeSec;
	}

	public void run() {
		for (int i = 1; i <= amountClients; i++) {
			// Create a website list copy for the client
			List<String> websitesForClient = new ArrayList<String>();
			for (String website : websites) {
				websitesForClient.add(website);
			}

			// Start the client
			Client client = new Client(i, websitesForClient, sleepTimeSec);
			client.start();
		}
	}

}
