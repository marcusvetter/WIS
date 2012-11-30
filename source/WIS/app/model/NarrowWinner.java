package model;

public class NarrowWinner {

    private int constituencyID;
	private String constituency;
	private String firstname;
    private String lastname;
    private String party;
    private int difference;

	public NarrowWinner(int id, String con, String fn, String ln, String p, int diff) {
        constituencyID = id;
	    constituency = con;
        firstname = fn;
        lastname = ln;
        party = p;
        difference = diff;
    }

	public String getConstituency() {
		return constituency;
	}

    public int getConstituencyID() {
        return constituencyID;
    }

	public String getFirstname() {
		return firstname;
    }

	public String getLastname() {
		return lastname;
    }

    public String getParty() {
        return party;
    }

    public int getDifference() {
        return difference;
    }
}
