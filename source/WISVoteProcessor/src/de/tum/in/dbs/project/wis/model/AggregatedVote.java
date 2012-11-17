package de.tum.in.dbs.project.wis.model;

import de.tum.in.dbs.project.wis.Mode;

/**
 * Data class for an aggregated vote
 * 
 * @author Marcus Vetter
 * 
 */
public class AggregatedVote {

	/**
	 * Id of the constituency
	 */
	private int constituencyId;

	/**
	 * Name of the party
	 */
	private String party;

	/**
	 * The votes [first, second, firstprior, secondprior]
	 */
	private int[] votes = new int[4];

	/**
	 * Constructor to create an object with all fields
	 * 
	 * @param constituencyId
	 * @param party
	 * @param firstvotes
	 * @param secondvotes
	 * @param firstvotesprior
	 * @param secondvotesprior
	 */
	public AggregatedVote(int constituencyId, String party, int firstvotes,
			int secondvotes, int firstvotesprior, int secondvotesprior) {
		super();
		this.constituencyId = constituencyId;
		this.party = party;
		
		this.votes[0] = firstvotes;
		this.votes[1] = secondvotes;
		this.votes[2] = firstvotesprior;
		this.votes[3] = secondvotesprior;
	}

	/**
	 * @return the constituencyId
	 */
	public int getConstituencyId() {
		return constituencyId;
	}

	/**
	 * @return the party
	 */
	public String getParty() {
		return party;
	}

	/**
	 * 
	 * @param mode
	 *            {@link Mode}
	 * @return the votes
	 */
	public int getVotes(Mode mode) {
		switch (mode) {
		case first:
			return this.votes[0];
		case second:
			return this.votes[1];
		case firstprior:
			return this.votes[2];
		case secondprior:
			return this.votes[3];
		}
		return -1;
	}
}
