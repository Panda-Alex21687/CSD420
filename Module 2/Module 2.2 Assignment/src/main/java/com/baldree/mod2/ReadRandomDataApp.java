package com.baldree.mod2;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Name: Alex Baldree
 * Course: CSD420
 * Assignment: Module 2 Random Data File (Reader)
 *
 * This program reads the data stored in "Baldree datafile.dat" and displays
 * every record that has been written to the file.
 *
 * How the code functions:
 * - A Path object points to the assignment data file.
 * - DataInputStream reads values back in the same order they were written.
 * - The reader continues until the end of the file is reached.
 * - Each record is displayed as one set of five integers and five doubles.
 */
public class ReadRandomDataApp {

    public static void main(String[] args) {
        Path filePath = Path.of(RandomDataFileUtil.FILE_NAME);

        try {
            List<RandomDataRecord> records = RandomDataFileUtil.readAllRecords(filePath);

            if (records.isEmpty()) {
                System.out.println("No data found. Run WriteRandomDataApp first.");
                return;
            }

            System.out.println("Reading data from: " + filePath.toAbsolutePath());
            System.out.println();

            for (int i = 0; i < records.size(); i++) {
                System.out.println("Record " + (i + 1) + ":");
                System.out.println(records.get(i));
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
}
