package de.tum.in.dbs.project.wis;

import java.io.File;
import java.util.List;

import de.tum.in.dbs.project.wis.bulkdatainsert.BulkDataInserter;
import de.tum.in.dbs.project.wis.csvreader.CSVDataReader;
import de.tum.in.dbs.project.wis.csvwriter.CSVDataWriter;
import de.tum.in.dbs.project.wis.model.AggregatedVote;

/**
 * Main class to start the program, get the mode and start processing data
 * 
 * @author Marcus Vetter
 * 
 */
public class Main {

	/**
	 * Progress of processing data
	 */
	private static int progress = 0;
	private static int progressPercent = 0;

	/**
	 * Main method to start the program
	 * 
	 * @param args
	 * @see Main.printUsage()
	 * 
	 */
	public static void main(String args[]) {

		// Check length of arguments
		if (args.length != 5) {
			System.out.println("Invalid number of arguments.");
			System.out.println("Aborted.");
			System.out.println();
			printUsage();
			return;
		}

		// Check mode
		int mode = -1;
		try {
			mode = Integer.valueOf(args[0]);
			if (mode < 1 || mode > 5)
				throw new Exception();
		} catch (Exception e) {
			System.out.println("Invalid flag.");
			System.out.println("Aborted.");
			System.out.println();
			printUsage();
			return;
		}

		// Get the parameters (arguments)
		String inputFileName = args[1];
		String delimiters = "";
		String table = "";
		String outputFileName = "";
		int year = -1;
		boolean generate = false;
		String columNames = "";

		if (mode == 5) {
			delimiters = args[2];
			table = args[3];
			columNames = args[4];
		} else {
			outputFileName = args[2];
			try {
				year = Integer.valueOf(args[3]);
			} catch (Exception e) {
				System.out.println("Invalid year.");
				System.out.println("Aborted.");
				System.out.println();
				printUsage();
				return;
			}
			try {
				generate = Boolean.valueOf(args[4]);
			} catch (Exception e) {
				System.out.println("Invalid generate flag.");
				System.out.println("Aborted.");
				System.out.println();
				printUsage();
				return;
			}

		}

		// Check readability/writeability of files
		if (!new File(inputFileName).canRead()) {
			System.out.println("Can not read the input file.");
			System.out.println("Aborted.");
			System.out.println();
			printUsage();
			return;
		} else if (mode >= 1 && mode <= 4 && new File(outputFileName).exists()) {
			System.out.println("Output file already exists.");
			System.out.println("Aborted.");
			System.out.println();
			printUsage();
			return;
		}

		// Log
		System.out.println("Started the vote generator ...");

		// Star the related action
		int count = startAction(mode, inputFileName, outputFileName, table,
				delimiters, year, generate, columNames);

		// Log
		System.out.println("Finished generating/writing votes.");

		// Log the processed data
		if (mode >= 1 && mode <= 4) {
			System.out.println("Generated " + (count - 1) + " votes.");
		}

	}

	/**
	 * Start the action and process the data
	 * 
	 * @param mode
	 *            mode of the process
	 * @param inputFileName
	 *            name of the input file
	 * @param outputFileName
	 *            name of the output file
	 * @param table
	 *            name of the table (if inserting bulk data)
	 * @param delimiters
	 *            delimiters (if inserting bulk data)
	 * @param year
	 *            year of election
	 * @param generate
	 *            flag, if the data should be generated
	 * @param columNames
	 *            name of the columns (comma separated)
	 * @return number of processed entries
	 */
	private static int startAction(int mode, String inputFileName,
			String outputFileName, String table, String delimiters, int year,
			boolean generate, String columNames) {

		// List of aggregated votes
		List<AggregatedVote> aggregatedVoteList = null;

		if (mode != 5) {
			// Parse the csv file
			aggregatedVoteList = CSVDataReader.readCSV(inputFileName, ';');
			System.out.println("0 %");
		}

		// Variable for counting the created results
		int count = 0;

		// Call the processors

		switch (mode) {
		case 1:
			count = CSVDataWriter.writeCSVData(Mode.first, aggregatedVoteList,
					outputFileName, year, generate);
			break;
		case 2:
			count = CSVDataWriter.writeCSVData(Mode.second, aggregatedVoteList,
					outputFileName, year, generate);
			break;
		case 3:
			count = CSVDataWriter.writeCSVData(Mode.firstprior,
					aggregatedVoteList, outputFileName, year, generate);
			break;
		case 4:
			count = CSVDataWriter.writeCSVData(Mode.secondprior,
					aggregatedVoteList, outputFileName, year, generate);
			break;
		case 5:
			BulkDataInserter.insertBulkData(table, inputFileName, delimiters, columNames);
			break;
		}

		return count;
	}

	/**
	 * Print the progress. One percent per 299 constituencies and 29 party
	 */
	public static void printProgress() {
		progress++;
		int newpercent = progress * 100 / 299 / 29;

		if (newpercent > progressPercent) {
			progressPercent = newpercent;
			System.out.println(newpercent + " %");
		}
	}

	/**
	 * Print the usage of this tool
	 */
	private static void printUsage() {
		System.out.println("Usage:");
		System.out
				.println("1st param: '1' for first votes, '2' for second votes, '3' for first votes (prior period), '4' for second votes (prior period), '5' for inserting bulk data");
		System.out.println("2nd param: Input file (csv)");
		System.out
				.println("3rd param: If 1st param = 1, 2, 3 or 4: Output file. If 1st param = 5: Delimiters");
		System.out
				.println("4th param: If 1st param = 1, 2, 3 or 4: Year of election. If 1st param = 5: Name of table");
		System.out
				.println("5th param: if 1st param = 1, 2, 3 or 4: Generate (true/false). If 1st param = 5: List of columns (comma separated)");
	}

}
