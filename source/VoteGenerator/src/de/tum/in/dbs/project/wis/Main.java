package de.tum.in.dbs.project.wis;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class Main {
	
	public static void main(String args[]) {
		System.out.println("Welcome to the vote generator");
		
		
	    CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("C:\\Users\\Marcus\\Desktop\\wahldaten.csv"), ';');
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	    String [] nextLine;
	    try {
			while ((nextLine = reader.readNext()) != null) {
			    // nextLine[] is an array of values from the line
				for (String cell : nextLine) {
					System.out.print(cell + " ; ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
