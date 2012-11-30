package common;

import java.util.List;

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

}
