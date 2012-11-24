package model;

public class VoteAggregate {

	private String party;
	private int firstVotes;
	private int secondVotes;
	private int year;

	public VoteAggregate(String party, int firstVotes, int secondVotes, int year) {
		this.party = party;
		this.firstVotes = firstVotes;
		this.secondVotes = secondVotes;
		this.year = year;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public int getFirstVotes() {
		return firstVotes;
	}

	public void setFirstVotes(int firstVotes) {
		this.firstVotes = firstVotes;
	}

	public int getSecondVotes() {
		return secondVotes;
	}

	public void setSecondVotes(int secondVotes) {
		this.secondVotes = secondVotes;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
