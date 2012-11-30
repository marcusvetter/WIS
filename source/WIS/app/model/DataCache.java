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
	 * Cached bundestag members
	 */
	private static List<BundestagMember> bundestagMembers = new ArrayList<BundestagMember>();

	/**
	 * Cached constituency winners
	 */
	private static List<ConstituencyWinner> constituencyWinners = new ArrayList<ConstituencyWinner>();

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
	 * @return vote aggregates
	 */
	public static List<VoteAggregate> getVoteAggregates() {
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

	/**
	 * Get the cached list of the bundestag members
	 * 
	 * @return list of cached bundestag members
	 */
	public static List<BundestagMember> getBundestagMembers() {
		return DataCache.bundestagMembers;
	}

	/**
	 * Set the list of the bundestag members
	 * 
	 * @param bundestagMembers
	 *            list of bundestag members
	 */
	public static void setBundestagMembers(
			List<BundestagMember> bundestagMembers) {
		DataCache.bundestagMembers = bundestagMembers;
	}

	/**
	 * Get the constituency winners
	 * 
	 * @return list of constituency winners
	 */
	public static List<ConstituencyWinner> getConstituencyWinners() {
		return DataCache.constituencyWinners;
	}

	/**
	 * Set the constituency winners
	 * 
	 * @param constituencyWinners
	 *            list of constituency winners
	 */
	public static void setConstituencyWinners(
			List<ConstituencyWinner> constituencyWinners) {
		DataCache.constituencyWinners = constituencyWinners;
	}

}
