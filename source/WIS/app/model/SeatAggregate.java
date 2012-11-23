package model;

public class SeatAggregate {

	private String party;
	private int seats;
    private String color;

	public SeatAggregate(String party, int seats, String color) {
		this.party = party;
		this.seats = seats;
        this.color = color;
	}

	public String getParty() {
		return party;
	}

	public int getSeats() {
		return seats;
	}

    public String getColor() {
        return color;
    }

}
