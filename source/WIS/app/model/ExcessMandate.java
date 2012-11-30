package model;

public class ExcessMandate {

	private String state;
	private String party;
	private String amount;

	/**
	 * @param state
	 * @param party
	 * @param amount
	 */
	public ExcessMandate(String state, String party, String amount) {
		this.state = state;
		this.party = party;
		this.amount = amount;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the party
	 */
	public String getParty() {
		return party;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

}
