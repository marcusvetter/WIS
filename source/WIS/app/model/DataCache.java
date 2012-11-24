package model;

import java.util.ArrayList;
import java.util.List;

import play.Configuration;

import common.db.*;

public class DataCache {

	/**
	 * Cached list of seats
	 */
	private static List<SeatAggregate> seats = new ArrayList<SeatAggregate>();

	/**
	 * Cached aggregated votes
	 */
	private static List<VoteAggregate> votes = new ArrayList<VoteAggregate>();

	/**
	 * Get cached seat aggregates
	 * 
	 * @return seat aggregates
	 */
	public static List<SeatAggregate> getSeatAggregates() {
		return DataCache.seats;
	}

	/**
	 * Get cached vote aggregates
	 * 
	 * @param view
	 *            View (deutschland, bundesland or wahlkreis)
	 * @return vote aggregates
	 */
	public static List<VoteAggregate> getVoteAggregates(String view) {
		DataCache.votes.add(new VoteAggregate("SPD", 25461, 12345, 2009));
		DataCache.votes.add(new VoteAggregate("CDU", 12345, 25461, 2009));
		DataCache.votes.add(new VoteAggregate("FDP", 25461, 25461, 2009));
		return DataCache.votes;
	}

	/**
	 * Update the cache
	 * 
	 * @return false, if a database error occurred.
	 */
	public static boolean updateCache() {
		try {
			Configuration conf = play.Play.application().configuration();
			DBConnect db = new DBConnect(conf.getString("wisdb.connectstring"),
					conf.getString("wisdb.username"),
					conf.getString("wisdb.password"));
			DataCache.seats = db.getSeatAggregation();
			return true;
		} catch (DatabaseException e) {
			return false;
		}
	}

}
