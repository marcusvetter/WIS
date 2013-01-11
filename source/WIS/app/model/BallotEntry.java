package model;

public class BallotEntry {
	
	private int candidateId;
	private String candidateName;
	private String candidateParty;
	private int partyId;
	private String partyShortName;
	private String partyFullName;
	private String listCandidateNames;
	
	/**
	 * @param candidateId
	 * @param candidateName
	 * @param partyId
	 * @param partyShortName
	 * @param partyFullName
	 * @param listCandidateNames
	 */
	public BallotEntry(int candidateId, String candidateName, int partyId,
			String partyShortName, String partyFullName,
			String listCandidateNames) {
		this.candidateId = candidateId;
		this.candidateName = candidateName;
		this.partyId = partyId;
		this.partyShortName = partyShortName;
		this.partyFullName = partyFullName;
		this.listCandidateNames = listCandidateNames;
	}
	/**
	 * @return the candidateId
	 */
	public int getCandidateId() {
		return candidateId;
	}

	/**
	 * @return the candidateName
	 */
	public String getCandidateName() {
		return candidateName;
	}
	
	/**
	 * @return the candidateParty
	 */
	public String getCandidateParty() {
		return candidateParty;
	}

	/**
	 * @return the partyId
	 */
	public int getPartyId() {
		return partyId;
	}

	/**
	 * @return the partyShortName
	 */
	public String getPartyShortName() {
		return partyShortName;
	}

	/**
	 * @return the partyFullName
	 */
	public String getPartyFullName() {
		return partyFullName;
	}

	/**
	 * @return the listCandidateNames
	 */
	public String getListCandidateNames() {
		return listCandidateNames;
	}

}