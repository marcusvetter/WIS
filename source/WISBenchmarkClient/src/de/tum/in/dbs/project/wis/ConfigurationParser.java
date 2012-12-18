package de.tum.in.dbs.project.wis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

public class ConfigurationParser {

	public static List<String> parseConfiguration(File file) {
		

		// List of websites
		Map<String, Integer> websiteMap = new HashMap<String, Integer>();

		try {
			// Instantiate a csv reader
			CSVReader reader = null;
			reader = new CSVReader(new FileReader(file), ';');

			// Parse the csv file
			String[] line;
			while ((line = reader.readNext()) != null) {
				websiteMap.put(line[0], Integer.valueOf(line[1]));
			}

			// Close the reader

			reader.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		// Create a list with strings
		calculateGCD(websiteMap.entrySet());
		
		return null;
	}

	private int calculateGCD(Set<Entry<String, Integer>> set) {
		set.toArray(new ArrayList<Integer>());
		int gcd = set.get(0);
		for (int itr = 1; itr < set.size(); itr++) {
			gcd = gcd(gcd, set.get(itr));
		}
		return gcd;
	}

	private int gcd(int i, int j) {
		while (j != 0) {
			if (i > j) {
				i = i - j;
			} else {
				j = j - i;
			}
		}
		return i;
	}

}