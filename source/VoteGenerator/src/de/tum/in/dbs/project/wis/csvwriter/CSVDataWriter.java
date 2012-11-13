package de.tum.in.dbs.project.wis.csvwriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import de.tum.in.dbs.project.wis.DBConstants;
import de.tum.in.dbs.project.wis.Main;
import de.tum.in.dbs.project.wis.Mode;
import de.tum.in.dbs.project.wis.model.AggregatedVote;

/**
 * Class for writing (and poss. generate) votes to a csv file
 * 
 * @author Marcus Vetter
 * 
 */
public class CSVDataWriter {

	/**
	 * The connection to the database
	 */
	private static Connection connection = null;

	/**
	 * Write the csv data
	 * 
	 * @param mode
	 * @param aggregatedVoteList
	 * @param outputFileName
	 * @return
	 */
	public static int writeCSVData(Mode mode,
			List<AggregatedVote> aggregatedVoteList, String outputFileName) {
		int voteId = 1;
		try {

			FileWriter writer = initFileWriter(outputFileName);

			for (AggregatedVote aggregatedVote : aggregatedVoteList) {
				// Get the attributes of the aggregated vote
				int constituencyId = aggregatedVote.getConstituencyId();
				String party = aggregatedVote.getParty();
				int votes = aggregatedVote.getVotes(mode);

				// Get the list of referenced ids (candidate or list)
				List<Integer> referencedIdList = getReferencedIdList(mode,
						constituencyId, party);

				// Check, if the list is empty
				if (referencedIdList.isEmpty()) {

					// There are no votes, so no references are expected
					if (votes == 0) {
						Main.printProgress();
						continue;
					} else {
						// Error, if there are votes but no references
						System.err
								.println("Referenced id list is empty, but there are aggregated votes!");
						System.err.println("Party: " + party);
						System.err.println("ConstituencyId: " + constituencyId);
						System.err.println("Votes: " + votes);
					}
				}

				// Process corresponding to the mode
				if (mode.equals(Mode.first2009) || mode.equals(Mode.second2009)) {

					// Iterate the votes
					for (int voteItr = 0; voteItr < votes; voteItr++) {
						int refId = -1;

						// Take the reference, if there is only one reference
						if (referencedIdList.size() == 1) {
							refId = referencedIdList.get(0);
						} else {
							// Take a random reference, if there is more than
							// one reference
							refId = referencedIdList.get(new Random()
									.nextInt(referencedIdList.size()));
						}

						// Write
						writer.append(voteId + ";" + refId + ";"
								+ constituencyId + "\n");

						voteId++;
					}
				} else if (mode.equals(Mode.first2005)
						|| mode.equals(Mode.second2005)) {
					int refAggregatedId;

					// Take the reference, if there is only one reference
					if (referencedIdList.size() == 1) {
						refAggregatedId = referencedIdList.get(0);

						writer.append(voteId + ";" + refAggregatedId + ";"
								+ constituencyId + ";" + votes + ";"
								+ System.currentTimeMillis() + "true" + "\n");
						voteId++;

					} else if (referencedIdList.size() > 1) {
						// Take a random reference, if there is more than
						// one reference. Therefore get all possible candidates.
						Map<Integer, Integer> votesForReference = new HashMap<Integer, Integer>();
						for (int i = 0; i < referencedIdList.size(); i++) {
							votesForReference.put(i, 0);
						}
						for (int voteItr = 0; voteItr < votes; voteItr++) {
							int randomCandidate = new Random()
									.nextInt(referencedIdList.size());

							int oldVotes = votesForReference
									.get(randomCandidate);
							votesForReference
									.put(randomCandidate, oldVotes + 1);
						}
						// Write the votes for the references
						for (Entry<Integer, Integer> entry : votesForReference
								.entrySet()) {
							writer.append(voteId + ";"
									+ referencedIdList.get(entry.getKey())
									+ ";" + constituencyId + ";"
									+ entry.getValue() + ";"
									+ System.currentTimeMillis() + "true"
									+ "\n");
							voteId++;
						}
					}
				}

				// Flush the writer
				writer.flush();

				// Log the progress
				Main.printProgress();
			}

			// Finalize
			writer.close();
			connection.close();

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}

		return voteId;

	}

	/**
	 * Initialize a file writer
	 * 
	 * @param fileName
	 *            name/path of the file to write
	 * @return an initialized file writer
	 * @throws IOException
	 */
	private static FileWriter initFileWriter(String fileName)
			throws IOException {
		File file = new File(fileName);
		FileWriter writer = null;
		if (!file.exists()) {
			file.createNewFile();
		}
		writer = new FileWriter(file, true);
		return writer;
	}

	/**
	 * Get the referenced id list (candidate ids or list ids, corresponding to
	 * the selected mode
	 * 
	 * @param mode
	 *            the mode
	 * @param constituencyId
	 *            the id of the constituency
	 * @param party
	 *            the name of the party
	 * @return list of referenced ids
	 */
	private static List<Integer> getReferencedIdList(Mode mode,
			int constituencyId, String party) {

		// Get the year
		int year = -1;
		if (mode.equals(Mode.first2005) || mode.equals(Mode.second2005)) {
			year = 2005;
		} else {
			year = 2009;
		}

		// The result list
		List<Integer> resultList = new ArrayList<Integer>();

		// Create the query for first, resp. second votes (candidate/listid)
		String query = "";
		if (mode.equals(Mode.first2005) || mode.equals(Mode.first2009)) {
			String select = "ktur.id";
			String from = "wis_partei p, wis_kandidat k, wis_kandidatur ktur, wis_bundestagswahl btwahl";
			String where1 = "p.name = \'" + party + "\'";
			String where2 = "p.id = k.partei";
			String where3 = "k.id = ktur.kandidat";
			String where4 = "ktur.wahlkreis = \'" + constituencyId + "\'";
			String where5 = "ktur.wahl = btwahl.id";
			String where6 = "btwahl.jahr = " + year;

			query = "select " + select + " from " + from + " where " + where1
					+ " and " + where2 + " and " + where3 + " and " + where4
					+ " and " + where5 + " and " + where6;
		} else if (mode.equals(Mode.second2005) || mode.equals(Mode.second2009)) {
			String select = "ll.id";
			String from = "wis_partei p, wis_bundestagswahl btwahl, wis_landesliste ll, wis_wahlkreis wk, wis_bundesland bl";
			String where1 = "p.name = \'" + party + "\'";
			String where2 = "p.id = ll.partei";
			String where3 = "wk.bundesland = bl.id";
			String where4 = "ll.bundesland = bl.id";
			String where5 = "wk.id = \'" + constituencyId + "\'";
			String where6 = "ll.wahl = btwahl.id";
			String where7 = "btwahl.jahr = " + year;

			query = "select " + select + " from " + from + " where " + where1
					+ " and " + where2 + " and " + where3 + " and " + where4
					+ " and " + where5 + " and " + where6 + " and " + where7;
		}

		// Connect to database
		if (connection == null) {
			establishDBConnection();
		}

		try {
			// Execute the query
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);

			// Process the result set
			while (result.next()) {
				resultList.add(result.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	/**
	 * Establish the database connection
	 */
	private static void establishDBConnection() {
		// Connect to the database
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
					DBConstants.DB_CONNECTION_URL, DBConstants.DB_USERNAME,
					DBConstants.DB_PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}