package com.freshremix.common.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import au.com.bytecode.opencsv.CSVReader;

/**
 *
 * Utility class for handling CSV files.
 *
 */
public final class CSVTestUtil {

	/**
	 * Private constructor.
	 */
    private CSVTestUtil() {
    }

	/**
	 * Generates a file name with the format: temp directory path + UUI random
	 * value + extension ".csv".
	 * 
	 * @return Full file path
	 */
	public static String generateFileName() {
		String fullFilePath = FileUtils.getTempDirectoryPath()
				+ UUID.randomUUID().toString() + ".csv";
		System.out.println("FullFilePath:" + fullFilePath);
		return fullFilePath;
	}

	/**
	 * Quietly deletes a file. Any errors are suppressed.
	 * 
	 * @param file File representation of the csv
	 */
	public static void deleteFile(final File file) {
		System.out.println("Deleting file:" + file.getAbsolutePath());
		FileUtils.deleteQuietly(file);
	}

	/**
	 * Counts the number of lines in a csv file. This does not treat a CR\LF as
	 * a line if enclosed in double quotes (escaping for csv file)
	 * 
	 * @param file input file
	 * @return int  number of lines in the csv file
	 * @throws IOException when file reading fails
	 */
	public static int countLines(final File file) throws IOException {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(file));
			List<String[]> lines = reader.readAll();
			if (lines != null) {
				return lines.size();
			}
			return 0;
		} finally {
			if (reader != null) {
				reader.close();
			}

		}
	}

}
