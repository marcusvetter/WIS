package de.tum.in.dbs.project.wis.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import de.tum.in.dbs.project.wis.Main;
import de.tum.in.dbs.project.wis.model.AggregatedVote;

public class BulkDataWriter {

	private Connection connection = null;

	public void generateWriteFirstVotes2009(
			List<AggregatedVote> aggregatedVoteList, String filename) {
		FileWriter writer = initFileWriter(filename);
		int voteId = 0;

		// Create the header
		try {
			writer.append("id,fuerkandidat,abgegebenin\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (AggregatedVote aggregatedVote : aggregatedVoteList) {
			int constituencyId = aggregatedVote.getConstituencyId();
			String party = aggregatedVote.getParty();
			int votes = aggregatedVote.getFirstvote2009();

			// Get the candidate
			int candidateId = this.getCandidateId(constituencyId, party);

			// Create the votes
			for (int voteItr = 0; voteItr < votes; voteItr++) {
				try {
					writer.append(voteId + "," + candidateId + ","
							+ constituencyId + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				voteId++;
			}

			try {
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Main.logProgress();
			
		}

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void generateWriteSecondVotes2009(
			List<AggregatedVote> aggregatedVoteList, String filename) {
		FileWriter writer = initFileWriter(filename);

		for (AggregatedVote aggregatedVote : aggregatedVoteList) {
			int constituencyId = aggregatedVote.getConstituencyId();
			String party = aggregatedVote.getParty();
			int votes = aggregatedVote.getSecondvote2009();
		}
	}

	private FileWriter initFileWriter(String filename) {
		File file = new File(filename);
		FileWriter writer = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new FileWriter(file, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}

	private int getCandidateId(int constituencyId, String party) {

		// Query
		String select = "ktur.id";
		String from = "wis_partei p, wis_kandidat k, wis_kandidatur ktur, wis_bundestagswahl btwahl";
		String where1 = "p.kurzname = \'" + party + "\'";
		String where2 = "p.id = k.partei";
		String where3 = "k.id = ktur.kandidat";
		String where4 = "ktur.wahlkreis = \'" + constituencyId + "\'";
		String where5 = "ktur.wahl = btwahl.id";
		String where6 = "btwahl.jahr = 2009";

		String query = "select " + select + " from " + from + " where "
				+ where1 + " and " + where2 + " and " + where3 + " and "
				+ where4 + " and " + where5 + " and " + where6;

		// Connect to database
		if (this.connection == null) {
			establishDBConnection();
		}

		try {
			Statement statement = this.connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			result.next();
			if (!result.isFirst() || !result.isLast())
				return -1;

			return result.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private void establishDBConnection() {
		// Connect to database
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
					"jdbc:postgresql://s3.benitum.de:5432/wis", "wis",
					"wis$pass4you");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}