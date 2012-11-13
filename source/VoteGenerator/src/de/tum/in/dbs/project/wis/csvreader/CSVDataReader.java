package de.tum.in.dbs.project.wis.csvreader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import de.tum.in.dbs.project.wis.model.AggregatedVote;

/**
 * The class is used to parse a csv file with aggregated votes
 * 
 * @author Marcus Vetter
 * 
 */
public class CSVDataReader {

	/**
	 * Read the csv file and parse it
	 * 
	 * @param fileName
	 *            name/path of the file
	 * @param delimiter
	 *            the delimiter used in the csv file
	 * @return list with aggregated vote objects
	 */
	public static List<AggregatedVote> readCSV(String fileName, char delimiter) {

		// Instantiate a csv reader
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(fileName), delimiter);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		// Result list
		List<AggregatedVote> resultList = new ArrayList<AggregatedVote>();

		// current line
		String[] line;
		try {
			// Attributes of the aggregated vote object
			int rowItr = 0;
			int currentConstituencyId = 0;
			int firstVote2009 = 0;
			int secondVote2009 = 0;
			int firstVote2005 = 0;
			int secondVote2005 = 0;

			// List of parties
			List<String> partyList = new ArrayList<String>();

			// Parse the csv file
			lineloop: while ((line = reader.readNext()) != null) {
				rowItr++;

				// Ignore useless data
				if (rowItr < CSVDataReaderConstants.PARTY_CELL.getRow())
					continue lineloop;

				int colItr = 0;
				int partyItr = -1;
				for (String cell : line) {
					colItr++;

					// New party (modulo party interval)
					if ((colItr >= CSVDataReaderConstants.PARTY_CELL.getCol())
							&& ((colItr - CSVDataReaderConstants.PARTY_CELL
									.getCol()) % CSVDataReaderConstants.PARTY_INTERVAL) == 0) {

						partyItr++;

						// Store the party
						if (rowItr == CSVDataReaderConstants.PARTY_CELL
								.getRow()) {
							partyList.add(cell);
						}

					}

					// Get the constituency identifier
					if (rowItr >= CSVDataReaderConstants.CONSTITUENCY_CELL
							.getRow()
							&& colItr == CSVDataReaderConstants.CONSTITUENCY_CELL
									.getCol()) {
						currentConstituencyId = Integer.valueOf(cell);
					}

					// Store the vote
					if (colItr >= CSVDataReaderConstants.VOTE_CELL.getCol()
							&& rowItr >= CSVDataReaderConstants.VOTE_CELL
									.getRow()) {

						// Get the votes
						switch ((colItr - CSVDataReaderConstants.PARTY_CELL
								.getCol())
								% CSVDataReaderConstants.PARTY_INTERVAL) {
						case 0:
							if (cell.isEmpty()) {
								firstVote2009 = 0;
							} else {
								firstVote2009 = Integer.valueOf(cell);
							}
							break;
						case 1:
							if (cell.isEmpty()) {
								firstVote2005 = 0;
							} else {
								firstVote2005 = Integer.valueOf(cell);
							}
							break;
						case 2:
							if (cell.isEmpty()) {
								secondVote2009 = 0;
							} else {
								secondVote2009 = Integer.valueOf(cell);
							}
							break;
						case 3:
							if (cell.isEmpty()) {
								secondVote2005 = 0;
							} else {
								secondVote2005 = Integer.valueOf(cell);
							}

							// Create the aggregated vote object
							AggregatedVote aggVote = new AggregatedVote(
									currentConstituencyId,
									partyList.get(partyItr), firstVote2009,
									secondVote2009, firstVote2005,
									secondVote2005);
							resultList.add(aggVote);

							// Clear the attributes
							firstVote2009 = 0;
							secondVote2009 = 0;
							firstVote2005 = 0;
							secondVote2005 = 0;
							break;

						}
					}

				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultList;
	}

}
