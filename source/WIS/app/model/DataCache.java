package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.IDataProvider;

public class DataCache {
    public static boolean USE_CACHE = false; 

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
	public static List<SeatAggregate> getSeatAggregates(boolean cache) {
        if (cache && USE_CACHE)
		    return DataCache.seats;
        else
            return dataprovider.getSeatAggregation();


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
	public static List<VoteAggregate> getVoteAggregates(boolean cache) {
        if (cache && USE_CACHE)
		    return DataCache.votes;
        else
            return dataprovider.getVoteAggregation();
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
	public static List<BundestagMember> getBundestagMembers(boolean cache) {
        if (cache && USE_CACHE)
		    return DataCache.bundestagMembers;
        else
            return dataprovider.getBundestagMembers();
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
	public static List<ConstituencyWinner> getConstituencyWinners(boolean cache) {
        if (cache && USE_CACHE)
		    return DataCache.constituencyWinners;
        else
            return dataprovider.getConstituencyWinners();
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
    public static List<Party> getParties(boolean cache) {
        if (cache && USE_CACHE)
            return DataCache.parties;
        else
            return dataprovider.getParties();
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
    public static List<NarrowWinner> getNarrowWinners(int party, boolean cache) {
        if (cache && USE_CACHE)
            return DataCache.narrowwinners.get(party);
        else
            return dataprovider.getNarrowWinners(party);
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
    public static List<NarrowWinner> getNarrowLosers(int party, boolean cache) {
        if (cache && USE_CACHE)
            return DataCache.narrowlosers.get(party);
        else
            return dataprovider.getNarrowLosers(party);
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
	public static List<ExcessMandate> getExcessMandates(boolean cache) {
        if (cache && USE_CACHE)
		    return DataCache.excessmandates;
        else
            return dataprovider.getExcessMandates();
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
    public static List<Constituency> getConstituencies(boolean cache) {
        if (cache && USE_CACHE)
            return DataCache.constituencies;
        else
            return dataprovider.getConstituencies();
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
    public static List<PartyVote> getPartySecondVotes(int constituency, boolean cache) {
        if (!(cache && USE_CACHE) || DataCache.partysecondvotes.get(constituency) == null) {
            List<PartyVote> l = DataCache.dataprovider.getPartySecondVotes(constituency);
            DataCache.partysecondvotes.put(constituency, l);
            return l;
        } else {
            return DataCache.partysecondvotes.get(constituency);
        }
    }
    
    public static List<PartyVote> getPartyFirstVotes(int constituency, boolean cache) {
        if (!(cache && USE_CACHE) || DataCache.partyfirstvotes.get(constituency) == null) {
            List<PartyVote> l = dataprovider.getPartyFirstVotes(constituency);
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
    public static ConstituencyInfo getConstituencyInfo(int constituency, boolean cache) {
        if (cache && USE_CACHE)
            return DataCache.constituencyinfo.get(constituency);
        else
            return dataprovider.getConstituencyInfo(constituency);
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
