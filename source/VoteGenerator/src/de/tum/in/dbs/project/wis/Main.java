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
		if (args.length != 2) {
			System.out.println("Usage:");
			System.out.println("First param: Input file (separator: \\\\)");
			System.out.println("Second param: Output file (separator: \\\\)");
			return;
		}
		
		String inputFileName = args[0];
		String outputFileName = args[1];
		
		// Check files
		if (!new File(inputFileName).canRead()) {
			System.out.println("Can not read the input file.");
			System.out.println("Aborted.");
			return;
		} else if (new File(outputFileName).exists()) {
			System.out.println("Output file already exists.");
			System.out.println("Aborted.");
			return;
		}

		System.out.println("Started the vote generator ...");
		System.out.println("0 %");

		CSVDataReader reader = new CSVDataReader(inputFileName, ';');
		List<AggregatedVote> aggregatedVoteList = reader.readCSV();

		BulkDataWriter datawriter = new BulkDataWriter();
		datawriter.generateWriteFirstVotes2009(aggregatedVoteList,
				outputFileName);

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

}
