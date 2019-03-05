/**
 * 
 */
package com.diversey.dm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Joydeep
 *
 */

public class TxtToJson {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO set the file locations
		/*
		 * 
		 * set the infile as input text file location and set the outfile as output json
		 * file location
		 * 
		 * 
		 */
		String infile = "C:/Users/jo20052314/Desktop/TestConversion/MTR_4G_A3_ini.txt", // input text file location
				outfile = "C:/Users/jo20052314/Desktop/TestConversionOUT/MTR_4G_A3_ini.json"; // output json file
																								// location
		txt_to_json(infile, outfile);

	}

	private static void txt_to_json(String infile, String outfile) {

		System.out.println("file convertion started...");
		int countLineNumber = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(infile));
				FileWriter fw = new FileWriter(outfile, false)) {

			String line = reader.readLine();
			System.out.println("Line number== " + ++countLineNumber);
			// first line
			fw.write("{ \n");
			fw.flush();

			// For single values
			while (line != null) {
				if (line.contains("=")) {

					String[] arrOfStr = line.split("=");

					if (arrOfStr.length > 1) { // for no value to null
						// loop splitted string
						if (!arrOfStr[1].trim().equals("null")) {
							fw.write("\"" + arrOfStr[0].trim() + "\" : [ \"" + arrOfStr[1].trim() + "\" ], \n");
							fw.flush();
						} else { // value as Null
							fw.write("\"" + arrOfStr[0].trim() + "\" : [ " + arrOfStr[1].trim() + " ], \n");
							fw.flush();
						}
					} else {
						fw.write("\"" + arrOfStr[0].trim() + "\" : [ \"\" ], \n");
						fw.flush();
					}

				}
				// read next line
				line = reader.readLine();
				System.out.println("Line number== " + ++countLineNumber);
				System.out.println("Line==" + line);
				if (line.contains("["))
					break;

			}

			System.out.println("==============================================================");

			// for objects
			// while (line != null) {
			while (line != null) {

				if (line.contains("[")) {
					// extracting []
					String temp = line.trim();
					temp = temp.replace("[", "");
					temp = temp.replace("]", "");

					fw.write("\"" + temp.trim() + "\" : [ {\n"); // writing pattern
					fw.flush();
					line = reader.readLine();
					System.out.println("Line number== " + ++countLineNumber);
					System.out.println("Line==" + line);

					while (line != null && (line.contains("="))) {
						String[] arrOfStr = line.split("=");// for no value to null
						int len = arrOfStr.length;
						if (len > 1) {
							if (len == 3) {
								fw.write("\"" + arrOfStr[0].trim() + "\" : \"" + arrOfStr[1].trim() + "="
										+ arrOfStr[2].trim() + "\"");
								fw.flush();
							} else {
								fw.write("\"" + arrOfStr[0].trim() + "\" : \"" + arrOfStr[1].trim() + "\"");
								fw.flush();
							}
						} else {
							fw.write("\"" + arrOfStr[0].trim() + "\" : [  null  ]");
							fw.flush();
						}

						line = reader.readLine();
						System.out.println("Line number== " + ++countLineNumber);
						System.out.println("Line==" + line);
						if (line != null && (line.contains("="))) { // for comma(,) in all line but last line
							fw.write(",\n");
							fw.flush();
						} else {
							fw.write("\n"); // last line no comma
							fw.flush();
						}

					}
					System.out.println("**line==" + line);
					if (line != null) { // for comma(,) in all objects but last object
						if (!line.trim().equals("") || !line.trim().contains("[")) { // checking for end of file or not
							fw.write("} ],\n");
							fw.flush();
						} else { // last object no comma
							fw.write("} ]\n");
							fw.flush();
						}
					} else { // last object no comma
						fw.write("} ]\n");
						fw.flush();
					}

				}

				else {
					// reached to end of file
					line = reader.readLine();
					System.out.println("Line number== " + ++countLineNumber);
					System.out.println("Line==" + line);

				}

			}

			fw.write("}");
			fw.flush();
			System.out.println("total line executed:: " + --countLineNumber);
			System.out.println("file generated at :: " + outfile);

		} catch (IOException e) {
			System.out.println("IO Exception:::: File not found : Please select the correct infile and outfile");
			// Files.deleteIfExists(Paths.get(outfile));
			e.printStackTrace();

		} catch (Exception ex) {
			System.out.println("Exception:::: ");
			// Files.deleteIfExists(Paths.get(outfile));
			ex.printStackTrace();
		}

	}

}