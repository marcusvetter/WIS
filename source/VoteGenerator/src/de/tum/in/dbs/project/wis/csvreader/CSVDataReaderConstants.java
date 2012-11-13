package de.tum.in.dbs.project.wis.csvreader;

import de.tum.in.dbs.project.wis.model.Cell;

/**
 * This class is used to specify constants used for parsing the csv file
 * 
 * @author Marcus Vetter
 * 
 */
public class CSVDataReaderConstants {

	/**
	 * First cell of an occurrence of a constituency, vote resp. party
	 */
	public static final Cell CONSTITUENCY_CELL = new Cell(6, 1);
	public static final Cell VOTE_CELL = new Cell(6, 14);
	public static final Cell PARTY_CELL = new Cell(3, 14);

	/**
	 * Interval of parties
	 */
	public static final int PARTY_INTERVAL = 4;

}
