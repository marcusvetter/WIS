package model;

public class ElectoralDistrictWinner {

    private int electoralDistrictID;
	private String electoralDistrict;
	private String firstVoteWinner;
    private String secondVoteWinner;

	public ElectoralDistrictWinner(int id, String ed, String fvw, String svw) {
        electoralDistrictID = id;
	    electoralDistrict = ed;
	    firstVoteWinner = fvw;
        secondVoteWinner = svw;
    }

	public String getElectoralDistrict() {
		return electoralDistrict;
	}

    public int getElectoralDistrictID() {
        return electoralDistrictID;
    }

	public String getFirstVoteWinner() {
		return firstVoteWinner;
    }

	public String getSecondVoteWinner() {
		return secondVoteWinner;
    }
}
