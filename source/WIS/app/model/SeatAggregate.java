package model;

public class SeatAggregate {

	private String party;
	private int seats;

	public SeatAggregate(String party, int seats) {
		this.party = party;
		this.seats = seats;
	}

	public String getParty() {
		return party;
	}

	public int getSeats() {
		return seats;
	}

}
