package model;

public class Party {

    private int id;
	private String name;

	public Party(int id, String name) {
        this.id = id;
        this.name = name;
    }

	public int getID() {
		return id;
	}

    public String getName() {
        return name;
    }
}
