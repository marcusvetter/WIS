package de.tum.in.dbs.project.wis.model;

public class AggregatedVote {
	
	private int constituencyId;
	private String party;
	private int firstvote2009;
	private int secondvote2009;
	private int firstvote2005;
	private int secondvote2005;

	/**
	 * @param constituencyId
	 * @param party
	 * @param firstvote2009
	 * @param secondvote2009
	 * @param firstvote2005
	 * @param secondvote2005
	 */
	public AggregatedVote(int constituencyId, String party, int firstvote2009,
			int secondvote2009, int firstvote2005, int secondvote2005) {
		super();
		this.constituencyId = constituencyId;
		this.party = party;
		this.firstvote2009 = firstvote2009;
		this.secondvote2009 = secondvote2009;
		this.firstvote2005 = firstvote2005;
		this.secondvote2005 = secondvote2005;
	}

	/**
	 * @return the constituencyId
	 */
	public int getConstituencyId() {
		return constituencyId;
	}

	/**
	 * @param constituencyId
	 *            the constituencyId to set
	 */
	public void setConstituencyId(int constituencyId) {
		this.constituencyId = constituencyId;
	}

	/**
	 * @return the party
	 */
	public String getParty() {
		return party;
	}

	/**
	 * @param party
	 *            the party to set
	 */
	public void setParty(String party) {
		this.party = party;
	}

	/**
	 * @return the firstvote2009
	 */
	public int getFirstvote2009() {
		return firstvote2009;
	}

	/**
	 * @param firstvote2009
	 *            the firstvote2009 to set
	 */
	public void setFirstvote2009(int firstvote2009) {
		this.firstvote2009 = firstvote2009;
	}

	/**
	 * @return the secondvote2009
	 */
	public int getSecondvote2009() {
		return secondvote2009;
	}

	/**
	 * @param secondvote2009
	 *            the secondvote2009 to set
	 */
	public void setSecondvote2009(int secondvote2009) {
		this.secondvote2009 = secondvote2009;
	}

	/**
	 * @return the firstvote2005
	 */
	public int getFirstvote2005() {
		return firstvote2005;
	}

	/**
	 * @param firstvote2005
	 *            the firstvote2005 to set
	 */
	public void setFirstvote2005(int firstvote2005) {
		this.firstvote2005 = firstvote2005;
	}

	/**
	 * @return the secondvote2005
	 */
	public int getSecondvote2005() {
		return secondvote2005;
	}

	/**
	 * @param secondvote2005
	 *            the secondvote2005 to set
	 */
	public void setSecondvote2005(int secondvote2005) {
		this.secondvote2005 = secondvote2005;
	}

}
