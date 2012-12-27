package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

import model.DataCache;
import model.Party;
import model.Constituency;

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
        DataCache.setDataProvider(dataProvider);
		this.intervalSeconds = intervalSeconds;
		this.semaphore = semaphore;
	}
   

    public static void printLogLine(String line) {
	    String date = new SimpleDateFormat("dd MMM yyyy HH:mm:ss")
		    .format(new Date(System.currentTimeMillis()));
		System.out.println(date + " - " + line);
    }

	@Override
	public void run() {
        if (!DataCache.USE_CACHE) { 
			if (this.semaphore != null) {
				this.semaphore.release();
				this.semaphore = null;
			}
            return;
        }
		while (!this.isInterrupted()) {
			// Update data cache
			try {
				DataCache.setSeatAggregation(dataProvider.getSeatAggregation());
                //printLogLine("SeatAggration updated");
				DataCache.setVoteAggregation(dataProvider.getVoteAggregation());
                //printLogLine("VoteAggration updated");
				DataCache.setBundestagMembers(dataProvider
						.getBundestagMembers());
                //printLogLine("BundestagMembers updated");
				DataCache.setConstituencyWinners(dataProvider
						.getConstituencyWinners());
                //printLogLine("ConstituencyWinners updated");
                DataCache.setParties(dataProvider.getParties());
                for (Party p : DataCache.getParties(true)) {
                    DataCache.setNarrowWinners(p.getID(), dataProvider.getNarrowWinners(p.getID()));
                    DataCache.setNarrowLosers(p.getID(), dataProvider.getNarrowLosers(p.getID()));
                    //printLogLine("Updated NarrowWinners for party "+p.getID());
                }
				DataCache.setExcessMandates(dataProvider.getExcessMandates());
                //printLogLine("ExcessMandates updated");
                DataCache.setConstituencies(dataProvider.getConstituencies());
                //printLogLine("Constituencies updated");
                for (Constituency c : DataCache.getConstituencies(true)) {
                    DataCache.setPartyFirstVotes(c.getID(), dataProvider.getPartyFirstVotes(c.getID()));
                    //printLogLine("PartyFirstVotes updated for "+c.getID());
                    DataCache.setPartySecondVotes(c.getID(), dataProvider.getPartySecondVotes(c.getID()));
                    //printLogLine("PartySecondVotes updated for "+c.getID());
                    DataCache.setConstituencyInfo(c.getID(), dataProvider.getConstituencyInfo(c.getID()));
                    //printLogLine("ConstituencyInfo updated for "+c.getID());
                }
				
                // Log
				printLogLine("The data manager updated the data cache.");
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
