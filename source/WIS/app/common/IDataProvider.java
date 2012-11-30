package common;

import java.util.List;

import model.BundestagMember;
import model.ConstituencyWinner;
import model.ExcessMandate;
import model.SeatAggregate;
import model.VoteAggregate;

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
	 * Get the list of excess mandates
	 * 
	 * @return list of excess mandates
	 */
	List<ExcessMandate> getExcessMandates();

}
