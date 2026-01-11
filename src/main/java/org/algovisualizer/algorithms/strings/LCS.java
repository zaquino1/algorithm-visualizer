package org.algovisualizer.algorithms.strings;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import java.util.Collections;
import java.util.Iterator;

public class LCS implements Algorithm {

    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        // This algorithm operates on strings, not arrays.
        return Collections.emptyIterator();
    }

    // We will need a new execute method for strings, e.g., execute(String s1, String s2)
    // For now, this is a placeholder.

    @Override
    public String getTimeComplexity() {
        return "O(m*n)";
    }

    @Override
    public String getSpaceComplexity() {
        return "O(m*n)";
    }
}