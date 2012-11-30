package model;

public class BundestagMember {

	private String state;
	private String party;
	private String firstname;
	private String surname;
	private int constituency;
	private int placement;

	/**
	 * @param state
	 * @param party
	 * @param firstname
	 * @param surname
	 * @param constituency
	 * @param placement
	 */
	public BundestagMember(String state, String party, String firstname,
			String surname, int constituency, int placement) {
		this.state = state;
		this.party = party;
		this.firstname = firstname;
		this.surname = surname;
		this.constituency = constituency;
		this.placement = placement;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
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
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the constituency
	 */
	public int getConstituency() {
		return constituency;
	}

	/**
	 * @param constituency
	 *            the constituency to set
	 */
	public void setConstituency(int constituency) {
		this.constituency = constituency;
	}

	/**
	 * @return the placement
	 */
	public int getPlacement() {
		return placement;
	}

	/**
	 * @param placement
	 *            the placement to set
	 */
	public void setPlacement(int placement) {
		this.placement = placement;
	}

}
