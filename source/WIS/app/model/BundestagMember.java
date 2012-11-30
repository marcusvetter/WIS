package model;

public class BundestagMember {

	private String state;
	private String party;
	private String firstname;
	private String surname;
	private String constituency;
	private String placement;

	/**
	 * @param state
	 * @param party
	 * @param firstname
	 * @param surname
	 * @param constituency
	 * @param placement
	 */
	public BundestagMember(String state, String party, String firstname,
			String surname, String constituency, String placement) {
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
	 * @return the party
	 */
	public String getParty() {
		return party;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the constituency
	 */
	public String getConstituency() {
		return constituency;
	}

	/**
	 * @return the placement
	 */
	public String getPlacement() {
		return placement;
	}

}
