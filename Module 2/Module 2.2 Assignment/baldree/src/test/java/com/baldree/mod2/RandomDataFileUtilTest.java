package com.baldree.mod2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

/**
 * Tests the file utility methods to ensure the assignment works correctly.
 * This version runs without JUnit by providing a main method and local assertions.
 */
public class RandomDataFileUtilTest {

    public static void main(String[] args) {
        try {
            writeCreatesFileAndReadReturnsOneRecord();
            secondWriteAppendsAnotherRecord();
            System.out.println("All tests passed.");
        } catch (IOException t) {
            java.util.logging.Logger.getLogger(RandomDataFileUtilTest.class.getName())
                    .log(java.util.logging.Level.SEVERE, "Uncaught test failure", t);
            System.exit(1);
        }
    }

    static void writeCreatesFileAndReadReturnsOneRecord() throws IOException {
        Path tempFile = Files.createTempFile("random-data-test", ".dat");
        Files.deleteIfExists(tempFile);

        RandomDataFileUtil.writeRandomData(tempFile, new Random(123));

        assertTrue(Files.exists(tempFile));

        List<RandomDataRecord> records = RandomDataFileUtil.readAllRecords(tempFile);
        assertEquals(1, records.size());
        assertEquals(5, records.get(0).integers().length);
        assertEquals(5, records.get(0).doubles().length);
    }

    static void secondWriteAppendsAnotherRecord() throws IOException {
        Path tempFile = Files.createTempFile("random-data-test", ".dat");

        RandomDataFileUtil.writeRandomData(tempFile, new Random(1));
        RandomDataFileUtil.writeRandomData(tempFile, new Random(2));

        List<RandomDataRecord> records = RandomDataFileUtil.readAllRecords(tempFile);
        assertEquals(2, records.size());
    }

    private static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }

    private static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected condition to be true");
        }
    }
}