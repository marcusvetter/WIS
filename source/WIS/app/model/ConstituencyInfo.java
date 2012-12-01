package model;

public class ConstituencyInfo {

    private int constituencyID;
	private String constituency;
    private float voterturnout;
    private String winnerFirstname, winnerLastname, winnerParty;

	public ConstituencyInfo(int id, String ed, float voterturnout, String winnerFirstname, String winnerLastname, String winnerParty) {
        constituencyID = id;
	    constituency = ed;
        this.voterturnout = voterturnout;
        this.winnerFirstname = winnerFirstname;
        this.winnerLastname = winnerLastname;
        this.winnerParty = winnerParty;
    }

	public String getConstituency() {
		return constituency;
	}

    public int getConstituencyID() {
        return constituencyID;
    }

    public float getVoterturnout() {
        return voterturnout;
    }

	public String getWinnerFirstname() {
		return winnerFirstname;
    }

	public String getWinnerLastname() {
		return winnerLastname;
    }

	public String getWinnerParty() {
		return winnerParty;
    }
}
