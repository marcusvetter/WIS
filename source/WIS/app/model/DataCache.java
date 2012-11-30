package model;

import java.util.ArrayList;
import java.util.List;

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
	 * Set the seat aggregation list
	 * 
	 * @param seatAggregation
	 */
	public static void setSeatAggregation(List<SeatAggregate> seatAggregation) {
		DataCache.seats = seatAggregation;
	}

	/**
	 * Get cached vote aggregates
	 * 
	 * @param view
	 *            View (deutschland, bundesland or wahlkreis)
	 * @return vote aggregates
	 */
	public static List<VoteAggregate> getVoteAggregates(String view) {
		// TODO: Filter by view
		return DataCache.votes;
	}

	/**
	 * Set the vote aggregation list
	 * 
	 * @param voteAggregation
	 */
	public static void setVoteAggregation(List<VoteAggregate> voteAggregation) {
		DataCache.votes = voteAggregation;
	}

}
