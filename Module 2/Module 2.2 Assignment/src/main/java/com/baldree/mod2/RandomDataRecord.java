package com.baldree.mod2;

import java.util.Arrays;

/**
 * Stores one record made of five integers and five doubles.
 */
public record RandomDataRecord(int[] integers, double[] doubles) {

    @Override
    public String toString() {
        return "Integers: " + Arrays.toString(integers) + System.lineSeparator()
                + "Doubles:  " + Arrays.toString(doubles);
    }
}
