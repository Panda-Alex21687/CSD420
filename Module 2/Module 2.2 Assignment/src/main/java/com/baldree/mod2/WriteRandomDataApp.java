package com.baldree.mod2;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

/**
 * Name: Alex Baldree
 * Course: CSD420
 * Assignment: Module 2 Random Data File
 *
 * This program creates an array of five random integers and an array of five
 * random double values, then writes them to a file named "Baldree datafile.dat".
 * If the file already exists, the program appends the new data to the end.
 */

public class WriteRandomDataApp {

    public static void main(String[] args) {
        Path filePath = Path.of(RandomDataFileUtil.FILE_NAME);

        try {
            RandomDataRecord record = RandomDataFileUtil.writeRandomData(filePath, new Random());

            System.out.println("Data written successfully to: " + filePath.toAbsolutePath());
            System.out.println(record);
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file: " + e.getMessage());
        }
    }
}
