package com.baldree.mod2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility class for writing and reading random data records.
 * Each record contains:
 * - 5 random integers
 * - 5 random doubles
 */

 public final class RandomDataFileUtil {

    public static final int INTEGER_COUNT = 5;
    public static final int DOUBLE_COUNT = 5;

    private RandomDataFileUtil() {
    }

    /**
     * Writes one record to the file.
     * If the file does not exist, it is created.
     * If the file already exists, the new record is appended.
     *
     * @param filePath path to the data file
     * @param random   random number generator
     * @throws IOException if file writing fails
     */
    public static void writeRandomData(Path filePath, Random random) throws IOException {
        if (filePath.getParent() != null) {
            Files.createDirectories(filePath.getParent());
        }

        try (DataOutputStream output = new DataOutputStream(
                new BufferedOutputStream(
                        Files.newOutputStream(
                                filePath,
                                StandardOpenOption.CREATE,
                                StandardOpenOption.APPEND)))) {

            for (int i = 0; i < INTEGER_COUNT; i++) {
                output.writeInt(random.nextInt(100));
            }

            for (int i = 0; i < DOUBLE_COUNT; i++) {
                output.writeDouble(random.nextDouble() * 100);
            }
        }
    }

    /**
     * Reads all records from the file.
     *
     * @param filePath path to the data file
     * @return list of all records in the file
     * @throws IOException if file reading fails
     */
    public static List<RandomDataRecord> readAllRecords(Path filePath) throws IOException {
        List<RandomDataRecord> records = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return records;
        }

        try (DataInputStream input = new DataInputStream(
                new BufferedInputStream(Files.newInputStream(filePath)))) {

            while (true) {
                try {
                    int[] integers = new int[INTEGER_COUNT];
                    double[] doubles = new double[DOUBLE_COUNT];

                    for (int i = 0; i < INTEGER_COUNT; i++) {
                        integers[i] = input.readInt();
                    }

                    for (int i = 0; i < DOUBLE_COUNT; i++) {
                        doubles[i] = input.readDouble();
                    }

                    records.add(new RandomDataRecord(integers, doubles));
                } catch (EOFException eof) {
                    break;
                }
            }
        }

        return records;
    }
}