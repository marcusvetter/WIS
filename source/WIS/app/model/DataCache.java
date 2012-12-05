package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.IDataProvider;

public class DataCache {
    
    private static IDataProvider dataprovider;
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
	 * Cached constituencies 
	 */
    private static List<Constituency> constituencies = new ArrayList<Constituency>();

    /**
     * Cached list of party votes 
     */
    private static HashMap<Integer, List<PartyVote>> partyfirstvotes = new HashMap<Integer, List<PartyVote>>();
    private static HashMap<Integer, List<PartyVote>> partysecondvotes = new HashMap<Integer, List<PartyVote>>();

    /**
     * Cached vote data for a constituency 
     */
    private static HashMap<Integer, ConstituencyInfo> constituencyinfo = new HashMap<Integer, ConstituencyInfo>();

    
    public static void setDataProvider(IDataProvider dp) {
        dataprovider = dp;
    }

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

	/**
	 * Get all parties 
	 * 
	 * @return list of parties
	 */
    public static List<Constituency> getConstituencies() {
        return DataCache.constituencies;
    }

	/**
	 * Set the constituencies
	 * 
	 * @param constituencies
	 *            list of constituencies
	 */
    public static void setConstituencies(List<Constituency> constituencies) {
        DataCache.constituencies = constituencies;
    }

	/**
	 * Get party votes for a constituency 
	 * 
     * @param constituency ID of constituency
     *
	 * @return list of party votes
	 */
    public static List<PartyVote> getPartySecondVotes(int constituency) {
        if (DataCache.partysecondvotes.get(constituency) == null) {
            List<PartyVote> l = DataCache.dataprovider.getPartySecondVotes(constituency);
            DataCache.partysecondvotes.put(constituency, l);
            return l;
        } else {
            return DataCache.partysecondvotes.get(constituency);
        }
    }
    
    public static List<PartyVote> getPartyFirstVotes(int constituency) {
        if (DataCache.partyfirstvotes.get(constituency) == null) {
            List<PartyVote> l = DataCache.dataprovider.getPartyFirstVotes(constituency);
            DataCache.partyfirstvotes.put(constituency, l);
            return l;
        } else {
            return DataCache.partyfirstvotes.get(constituency);
        }
    }
	
    /**
	 * Set party votes for a constituency 
	 * 
	 * @param constituency ID of constituency
	 * @param winners list of party votes
	 */
    public static void setPartySecondVotes(int constituency, List<PartyVote> partyvotes) {
        DataCache.partyfirstvotes.put(constituency, partyvotes);
    }
    
    public static void setPartyFirstVotes(int constituency, List<PartyVote> partyvotes) {
        DataCache.partyfirstvotes.put(constituency, partyvotes);
    }
	
    /**
	 * Get constituency info for a constituency 
	 * 
     * @param constituency ID of constituency
     *
	 * @return Constituency Info
	 */
    public static ConstituencyInfo getConstituencyInfo(int constituency) {
        return DataCache.constituencyinfo.get(constituency);
    }
	
    /**
	 * Set constituency info for a constituency 
	 * 
	 * @param constituency ID of constituency
	 * @param info ConstituencyInfo
	 */
    public static void setConstituencyInfo(int constituency, ConstituencyInfo info) {
        DataCache.constituencyinfo.put(constituency, info);
    }

}
