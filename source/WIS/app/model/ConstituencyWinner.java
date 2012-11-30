package model;

public class ConstituencyWinner {

    private int constituencyID;
	private String constituency;
	private String firstVoteWinner;
    private String secondVoteWinner;

	public ConstituencyWinner(int id, String ed, String fvw, String svw) {
        constituencyID = id;
	    constituency = ed;
	    firstVoteWinner = fvw;
        secondVoteWinner = svw;
    }

	public String getElectoralDistrict() {
		return constituency;
	}

    public int getElectoralDistrictID() {
        return constituencyID;
    }

	public String getFirstVoteWinner() {
		return firstVoteWinner;
    }

	public String getSecondVoteWinner() {
		return secondVoteWinner;
    }
}
