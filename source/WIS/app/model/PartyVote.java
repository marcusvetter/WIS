package model;

public class PartyVote {

	private String party;
    private int votes2009Absolute;
    private float votes2009Percent;
    private int votes2005Absolute;
    private float votes2005Percent;

	public PartyVote(String party, int votes2009Absolute, float votes2009Percent, int votes2005Absolute, float votes2005Percent) {
		this.party = party;
	    this.votes2009Absolute = votes2009Absolute;
        this.votes2009Percent = votes2009Percent;
        this.votes2005Absolute = votes2005Absolute;
        this.votes2005Percent = votes2005Percent;
    }

	public String getParty() {
		return party;
	}

	public int getVotes2009Absolute() {
		return votes2009Absolute;
	}

	public int getVotes2005Absolute() {
		return votes2005Absolute;
	}

	public float getVotes2005Percent() {
		return votes2005Percent;
	}

	public float getVotes2009Percent() {
		return votes2009Percent;
	}
    
}
