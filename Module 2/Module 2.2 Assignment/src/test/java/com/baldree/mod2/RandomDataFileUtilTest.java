package com.baldree.mod2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RandomDataFileUtilTest {

    @Test
    void writeCreatesFileAndReadReturnsOneRecord() throws IOException {
        Path tempFile = Files.createTempFile("random-data-test", ".dat");
        Files.deleteIfExists(tempFile);

        RandomDataFileUtil.writeRandomData(tempFile, new Random(123));

        assertTrue(Files.exists(tempFile));

        List<RandomDataRecord> records = RandomDataFileUtil.readAllRecords(tempFile);
        assertTrue(records.size() == 1);
        assertTrue(records.get(0).integers().length == 5);
        assertTrue(records.get(0).doubles().length == 5);
    }

    @Test
    void secondWriteAppendsAnotherRecord() throws IOException {
        Path tempFile = Files.createTempFile("random-data-test", ".dat");

        RandomDataFileUtil.writeRandomData(tempFile, new Random(1));
        RandomDataFileUtil.writeRandomData(tempFile, new Random(2));

        List<RandomDataRecord> records = RandomDataFileUtil.readAllRecords(tempFile);
        assertTrue(records.size() == 2);
    }
}