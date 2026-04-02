package com.baldree.mod2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility methods for writing and reading binary random data records.
 *
 * Each record contains:
 * - five random integers
 * - five random double values
 */
public final class RandomDataFileUtil {

    public static final String FILE_NAME = "Baldree datafile.dat";
    public static final int VALUE_COUNT = 5;

    private RandomDataFileUtil() {
        // Prevent instantiation.
    }

    /**
     * Writes one record of five random integers and five random doubles.
     * If the file does not exist, it is created.
     * If the file exists, the new record is appended.
     *
     * @param filePath the path to the data file
     * @param random   the random number generator
     * @return the record that was written
     * @throws IOException if a file error occurs
     */
    public static RandomDataRecord writeRandomData(Path filePath, Random random) throws IOException {
        Files.createDirectories(filePath.toAbsolutePath().getParent());

        int[] integers = new int[VALUE_COUNT];
        double[] doubles = new double[VALUE_COUNT];

        for (int i = 0; i < VALUE_COUNT; i++) {
            integers[i] = random.nextInt(1000);
        }

        for (int i = 0; i < VALUE_COUNT; i++) {
            doubles[i] = Math.round(random.nextDouble() * 10000.0) / 100.0;
        }

        try (DataOutputStream output = new DataOutputStream(
                new BufferedOutputStream(Files.newOutputStream(filePath,
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.APPEND)))) {

            for (int value : integers) {
                output.writeInt(value);
            }

            for (double value : doubles) {
                output.writeDouble(value);
            }
        }

        return new RandomDataRecord(integers, doubles);
    }

    /**
     * Reads all records from the data file until the end of the file is reached.
     *
     * @param filePath the path to the data file
     * @return a list of records found in the file
     * @throws IOException if a file error occurs
     */
    public static List<RandomDataRecord> readAllRecords(Path filePath) throws IOException {
        List<RandomDataRecord> records = new ArrayList<>();

        if (!Files.exists(filePath) || Files.size(filePath) == 0) {
            return records;
        }

        try (DataInputStream input = new DataInputStream(
                new BufferedInputStream(Files.newInputStream(filePath)))) {

            while (true) {
                try {
                    int[] integers = new int[VALUE_COUNT];
                    double[] doubles = new double[VALUE_COUNT];

                    for (int i = 0; i < VALUE_COUNT; i++) {
                        integers[i] = input.readInt();
                    }

                    for (int i = 0; i < VALUE_COUNT; i++) {
                        doubles[i] = input.readDouble();
                    }

                    records.add(new RandomDataRecord(integers, doubles));
                } catch (EOFException endOfFile) {
                    break;
                }
            }
        }

        return records;
    }
}
