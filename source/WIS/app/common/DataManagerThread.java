package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

import model.DataCache;

import common.db.DatabaseException;

public class DataManagerThread extends Thread {

	private IDataProvider dataProvider;
	private int intervalSeconds;
	private Semaphore semaphore;

	/**
	 * Create the data manager thread and set the {@link IDataProvider} and the
	 * interval of updating the data cache in seconds
	 * 
	 * @param dataProvider
	 *            the {@link IDataProvider}
	 * @param intervalSeconds
	 *            interval of updating the data cache in seconds
	 * @param semaphore
	 *            semaphore will be release after the first insertion of data
	 * 
	 */
	public DataManagerThread(IDataProvider dataProvider, int intervalSeconds,
			Semaphore semaphore) {
		this.dataProvider = dataProvider;
		this.intervalSeconds = intervalSeconds;
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		while (!this.isInterrupted()) {
			// Update data cache
			try {
				DataCache.setSeatAggregation(dataProvider.getSeatAggregation());
				DataCache.setVoteAggregation(dataProvider.getVoteAggregation());
				DataCache.setBundestagMembers(dataProvider
						.getBundestagMembers());
				DataCache.setConstituencyWinners(dataProvider
						.getConstituencyWinners());
				DataCache.setExcessMandates(dataProvider.getExcessMandates());

				// Log
				String date = new SimpleDateFormat("dd MMM yyyy HH:mm:ss")
						.format(new Date(System.currentTimeMillis()));
				System.out.println(date
						+ " - The data manager updated the data cache.");
			} catch (DatabaseException e) {
				e.printStackTrace();
			}

			// Release the semaphore, if its the first run
			if (this.semaphore != null) {
				this.semaphore.release();
				this.semaphore = null;
			}

			// Sleep
			try {
				Thread.sleep(intervalSeconds * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
