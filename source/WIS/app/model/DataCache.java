package model;

import java.util.ArrayList;
import java.util.HashMap;
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
     * Cached list of parties
     */
    private static List<Party> parties = new ArrayList<Party>();

    /**
     * Cached list of narrow winners
     */
    private static HashMap<Integer, List<NarrowWinner>> narrowwinners = new HashMap<Integer, List<NarrowWinner>>();
    
    /**
     * Cached list of narrow losers
     */
    private static HashMap<Integer, List<NarrowWinner>> narrowlosers = new HashMap<Integer, List<NarrowWinner>>();


	/**
	 * Cached excess mandates
	 */
	private static List<ExcessMandate> excessmandates = new ArrayList<ExcessMandate>();

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

	/**
	 * Get all parties 
	 * 
	 * @return list of parties
	 */
    public static List<Party> getParties() {
        return DataCache.parties;
    }

	/**
	 * Set the parties 
	 * 
	 * @param parties
	 *            list of parties
	 */
    public static void setParties(List<Party> parties) {
        DataCache.parties = parties;
    }

	/**
	 * Get narrow winners for a party 
	 * 
     * @param party ID of party
     *
	 * @return list of narrow winners
	 */
    public static List<NarrowWinner> getNarrowWinners(int party) {
        return DataCache.narrowwinners.get(party);
    }
	
    /**
	 * Set narrow winners for a party 
	 * 
	 * @param party ID of party
	 * @param winners list of narrow winners
	 */
    public static void setNarrowWinners(int party, List<NarrowWinner> winners) {
        DataCache.narrowwinners.put(party, winners);
    }

	/**
	 * Get narrow losers for a party 
	 * 
     * @param party ID of party
     *
	 * @return list of narrow losers
	 */
    public static List<NarrowWinner> getNarrowLosers(int party) {
        return DataCache.narrowlosers.get(party);
    }
	
    /**
	 * Set narrow losers for a party 
	 * 
	 * @param party ID of party
	 * @param winners list of narrow losers
	 */
    public static void setNarrowLosers(int party, List<NarrowWinner> losers) {
        DataCache.narrowlosers.put(party, losers);
    }
	 
    /** Get the excess mandates
	 * 
	 * @return list of excess mandates
	 */
	public static List<ExcessMandate> getExcessMandates() {
		return DataCache.excessmandates;
	}

	/**
	 * Set the excess mandates
	 * 
	 * @param excessmandates
	 *            to set
	 */
	public static void setExcessMandates(List<ExcessMandate> excessmandates) {
		DataCache.excessmandates = excessmandates;
	}

}
