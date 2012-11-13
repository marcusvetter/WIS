package de.tum.in.dbs.project.wis.model;

/**
 * This class represents one cell in the csv files
 * 
 * @author Marcus Vetter
 * 
 */
public class Cell {

	/**
	 * The row number
	 */
	private int row;

	/**
	 * The col number
	 */
	private int col;

	/**
	 * @param row
	 * @param col
	 */
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @param col
	 *            the col to set
	 */
	public void setCol(int col) {
		this.col = col;
	}

}
