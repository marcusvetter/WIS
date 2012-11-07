package de.tum.in.dbs.project.wis;

import java.io.File;
import java.util.List;

import de.tum.in.dbs.project.wis.model.AggregatedVote;
import de.tum.in.dbs.project.wis.reader.CSVDataReader;
import de.tum.in.dbs.project.wis.writer.BulkDataWriter;

public class Main {

	private static int progress = 0;
	private static int progressPercent = 0;

	public static void main(String args[]) {
		// Check args
		if (args.length != 3) {
			printUsage();
			return;
		}

		// Check flag
		int flag = -1;
		try {
			flag = Integer.valueOf(args[0]);
			if (!(flag == 1) && !(flag == 2))
				throw new Exception();
		} catch (Exception e) {
			System.out.println("Invalid flag.");
			System.out.println("Aborted.");
			System.out.println();
			printUsage();
			return;
		}

		String inputFileName = args[1];
		String outputFileName = args[2];

		// Check files
		if (!new File(inputFileName).canRead()) {
			System.out.println("Can not read the input file.");
			System.out.println("Aborted.");
			System.out.println();
			printUsage();
			return;
		} else if (new File(outputFileName).exists()) {
			System.out.println("Output file already exists.");
			System.out.println("Aborted.");
			System.out.println();
			printUsage();
			return;
		}

		System.out.println("Started the vote generator ...");
		System.out.println("0 %");

		CSVDataReader reader = new CSVDataReader(inputFileName, ';');
		List<AggregatedVote> aggregatedVoteList = reader.readCSV();

		BulkDataWriter datawriter = new BulkDataWriter();

		if (flag == 1) {
			datawriter.generateWriteFirstVotes2009(aggregatedVoteList,
					outputFileName);
		} else {
			datawriter.generateWriteSecondVotes2009(aggregatedVoteList,
					outputFileName);
		}

		System.out.println("Finished generating votes.");

	}

	public static void logProgress() {
		progress++;
		int newpercent = progress * 100 / 299 / 29;

		if (newpercent > progressPercent) {
			progressPercent = newpercent;
			System.out.println(newpercent + " %");
		}
	}

	private static void printUsage() {
		System.out.println("Usage:");
		System.out
				.println("1th param: '1' for first votes, '2' for second votes");
		System.out.println("2nd param: Input file (separator: '\\\\')");
		System.out.println("3rd param: Output file (separator: '\\\\')");
	}

}
