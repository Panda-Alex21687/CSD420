package com.baldree.mod2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Reads all data from "Baldree datafile.dat" and displays it.
 */
public class ReadRandomDataApp {

    public static void main(String[] args) {
        Path filePath = Paths.get("Baldree datafile.dat");

        try {
            List<RandomDataRecord> records = RandomDataFileUtil.readAllRecords(filePath);

            if (records.isEmpty()) {
                System.out.println("No data found in file.");
                return;
            }

            for (int recordIndex = 0; recordIndex < records.size(); recordIndex++) {
                RandomDataRecord record = records.get(recordIndex);

                System.out.println("Record " + (recordIndex + 1) + ":");

                System.out.print("Integers: ");
                int[] integers = record.integers();
                for (int i = 0; i < integers.length; i++) {
                    System.out.print(integers[i]);
                    if (i < integers.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();

                System.out.print("Doubles: ");
                double[] doubles = record.doubles();
                for (int i = 0; i < doubles.length; i++) {
                    System.out.printf("%.2f", doubles[i]);
                    if (i < doubles.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading data: " + e.getMessage());
        }
    }
}