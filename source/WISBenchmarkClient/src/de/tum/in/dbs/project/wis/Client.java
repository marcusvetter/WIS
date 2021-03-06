package de.tum.in.dbs.project.wis;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.http.client.fluent.Request;

/**
 * The client is implemented as thread an does the website calls
 */
public class Client extends Thread {

	/**
	 * The id of the client (only used for loggin)
	 */
	private int id;

	/**
	 * List of websites
	 */
	private List<String> websites;

	/**
	 * Sleep time in seconds
	 */
	private int sleepTimeSec;

	/**
	 * Amount of website calls
	 */
	private int amountCalls;

	/**
	 * Constructor to set the attributed
	 * 
	 * @param id
	 *            id of the client
	 * @param websites
	 *            list with websites
	 * @param sleepTimeSec
	 *            sleep time in seconds
	 * @param amountCalls
	 *            amount of website calls
	 */
	public Client(int id, List<String> websites, int sleepTimeSec,
			int amountCalls) {
		this.id = id;
		this.websites = websites;
		this.sleepTimeSec = sleepTimeSec;
		this.amountCalls = amountCalls;
	}

	/**
	 * Call websites
	 */
	@Override
	public void run() {

		int calls = 0;

		outerloop: while (!Thread.interrupted()) {
			// Shuffle the websites
			Collections.shuffle(websites);

			// Call the websites
			for (String website : websites) {
				try {
					long startTime = System.currentTimeMillis();
					Request.Get(website).execute().returnContent();
					long duration = System.currentTimeMillis() - startTime;

					calls++;

					// Log
					System.out.println("Client " + id + " called website "
							+ website + " in " + duration + " ms.");
					Stats.logDuration(this.id, website, duration);

					if (calls < this.amountCalls) {
						// Wait (except of the last call)
						double sleepMilliseconds = ((new Random()
								.nextInt(400001) / 1000000) + 0.8)
								* sleepTimeSec * 1000;
						Thread.sleep((long) sleepMilliseconds);
					} else {
						// Finished (last call)
						break outerloop;
					}

				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
