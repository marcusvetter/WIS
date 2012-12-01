package model;

public class Constituency {

    private int id;
	private String name;

	public Constituency(int id, String name) {
        this.id = id;
	    this.name = name;
    }

	public String getName() {
		return name;
	}

    public int getID() {
        return id;
    }

}
