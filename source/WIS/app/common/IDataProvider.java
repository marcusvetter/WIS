package common;

import java.util.List;

import model.BundestagMember;
import model.Party;
import model.ConstituencyWinner;
import model.ConstituencyInfo;
import model.NarrowWinner;
import model.ExcessMandate;
import model.SeatAggregate;
import model.VoteAggregate;
import model.Constituency;
import model.PartyVote;
import model.BallotEntry;


public interface IDataProvider {

	/**
	 * Get a list of aggregated seats
	 * 
	 * @return list of aggregated seats
	 */
	List<SeatAggregate> getSeatAggregation();

	/**
	 * Get a list of aggregated votes
	 * 
	 * @return list of aggregated votes
	 */
	List<VoteAggregate> getVoteAggregation();

	/**
	 * Get the winner of the constituencies
	 * 
	 * @return list of winner of the constituencies
	 */
	List<ConstituencyWinner> getConstituencyWinners();

	/**
	 * Get the members of the bundestag
	 * 
	 * @return list of members of the bundestag
	 */
	List<BundestagMember> getBundestagMembers();

    /**
     * Get a list of all parties
     * 
     * @return list of parties
     */
    List<Party> getParties();

    /**
     * Get a list of narrow winners for a party 
     * 
     * @param party ID of party
     *
     * @return list of narrow winners
     */
    List<NarrowWinner> getNarrowWinners(int party);

    /**
     * Get a list of narrow losers for a party 
     * 
     * @param party ID of party
     *
     * @return list of narrow losers
     */
    List<NarrowWinner> getNarrowLosers(int party);
	
    /**
	 * Get the list of excess mandates
	 * 
	 * @return list of excess mandates
	 */
	List<ExcessMandate> getExcessMandates();

    /**
     * Get a list of all constituencies 
     * 
     * @return list of constituencies
     */
    List<Constituency> getConstituencies();
    
    /**
     * Get a list of party votes (first or second) for a constituency
     * 
     * @param constituency ID of constituency
     *
     * @return list of party votes
     */
    List<PartyVote> getPartySecondVotes(int constituency);
    List<PartyVote> getPartyFirstVotes(int constituency);

    /**
     * Get info on a constituency
     *
     * @param constituency ID of constituency
     *
     * @return ConstituencyInfo
     */
    ConstituencyInfo getConstituencyInfo(int constituency);

    /**
     * Get a ballot for a constituency
     * 
     * @param constituency ID of constituency
     *
     * @return list of BallotEntrys
     */
    List<BallotEntry> getBallot(int constituency);
}
