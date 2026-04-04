package com.baldree.mod2;

/**
 * Stores one record of data from the file:
 * - five integers
 * - five doubles
 */
public record RandomDataRecord(int[] integers, double[] doubles) {
}