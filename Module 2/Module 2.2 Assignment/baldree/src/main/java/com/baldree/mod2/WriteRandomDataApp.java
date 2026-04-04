package com.baldree.mod2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Writes one record of random data to "Baldree datafile.dat".
 * If the file does not exist, it is created.
 * If the file exists, the data is appended.
 */
public class WriteRandomDataApp {

    public static void main(String[] args) {
        Path filePath = Paths.get("Baldree datafile.dat");
        Random random = new Random();

        try {
            RandomDataFileUtil.writeRandomData(filePath, random);
            System.out.println("Data written successfully to: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing data: " + e.getMessage());
        }
    }
}