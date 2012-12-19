package de.tum.in.dbs.project.wis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVReader;

/**
 * This class is used to parse a csv file and create a list of urls
 */
public class ConfigurationParser {

	/**
	 * Parse a csv file and create a list with urls. The list contains the url
	 * as often as determined in the csv file, where as the amount will be
	 * reduced by the greatest common divisor of the amounts.
	 * 
	 * @param file
	 *            csv file to parse
	 * @param delimiter
	 *            delimiter of the csv file
	 * @return list with urls
	 */
	public static List<String> parseConfiguration(File file, char delimiter) {

		// List of websites
		Map<String, Integer> websiteMap = new HashMap<String, Integer>();

		try {
			// Instantiate a csv reader
			CSVReader reader = null;
			reader = new CSVReader(new FileReader(file), delimiter);

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

		// Calculate the gcd
		List<Integer> amountPerUrl = new ArrayList<Integer>();
		for (Entry<String, Integer> entry : websiteMap.entrySet()) {
			amountPerUrl.add(entry.getValue());
		}
		int gcd = calculateGCD(amountPerUrl);

		// The string list with urls
		List<String> urlList = new ArrayList<String>();

		// Calculate the amounts div gcd and create the string list
		for (Entry<String, Integer> entry : websiteMap.entrySet()) {
			for (int itr = 0; itr < entry.getValue() / gcd; itr++) {
				urlList.add(entry.getKey());
			}
		}

		return urlList;
	}

	/**
	 * Calculate the greatest common divisor out of n numbers
	 * 
	 * @param list
	 *            list with integers
	 * @return the gcd
	 */
	private static int calculateGCD(List<Integer> list) {
		int gcd = list.get(0);
		for (int itr = 1; itr < list.size(); itr++) {
			gcd = gcd(gcd, list.get(itr));
		}
		return gcd;
	}

	/**
	 * Internal method to calculate the gcd
	 * 
	 * @param i
	 * @param j
	 * @return gcd of i and j
	 */
	private static int gcd(int i, int j) {
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