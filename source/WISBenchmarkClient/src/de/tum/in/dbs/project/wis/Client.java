package de.tum.in.dbs.project.wis;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.http.client.fluent.Request;

public class Client extends Thread {

	private int id;
	private List<String> websites;
	private int sleepTimeSec;

	public Client(int id, List<String> websites, int sleepTimeSec) {
		this.id = id;
		this.websites = websites;
		this.sleepTimeSec = sleepTimeSec;
	}

	@Override
	public void run() {

		while (!Thread.interrupted()) {

			// Shuffle the websites
			Collections.shuffle(websites);

			// Call the websites
			for (String website : websites) {
				try {
					long startTime = System.currentTimeMillis();
					Request.Get(website).execute().returnContent();
					long duration = startTime - System.currentTimeMillis();

					// Log
					System.out.println("Client " + id + " called website "
							+ website + " in " + duration + " ms.");

					// Wait
					double sleepMilliseconds = ((new Random().nextInt(400001) / 1000000) + 0.8)
							* sleepTimeSec * 1000;
					Thread.sleep((long) sleepMilliseconds);

				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
