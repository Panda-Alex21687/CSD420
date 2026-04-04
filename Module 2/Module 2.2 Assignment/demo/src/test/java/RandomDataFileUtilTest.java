package com.baldree.mod2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Tests the file utility methods to ensure the assignment works correctly.
 */
class RandomDataFileUtilTest {

    @Test
    void writeCreatesFileAndReadReturnsOneRecord() throws IOException {
        Path tempFile = Files.createTempFile("random-data-test", ".dat");
        Files.deleteIfExists(tempFile);

        RandomDataFileUtil.writeRandomData(tempFile, new Random(123));

        assertTrue(Files.exists(tempFile));

        List<RandomDataRecord> records = RandomDataFileUtil.readAllRecords(tempFile);
        assertEquals(1, records.size());
        assertEquals(5, records.get(0).integers().length);
        assertEquals(5, records.get(0).doubles().length);
    }

    @Test
    void secondWriteAppendsAnotherRecord() throws IOException {
        Path tempFile = Files.createTempFile("random-data-test", ".dat");

        RandomDataFileUtil.writeRandomData(tempFile, new Random(1));
        RandomDataFileUtil.writeRandomData(tempFile, new Random(2));

        List<RandomDataRecord> records = RandomDataFileUtil.readAllRecords(tempFile);
        assertEquals(2, records.size());
    }
}