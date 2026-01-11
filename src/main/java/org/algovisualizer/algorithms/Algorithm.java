package org.algovisualizer.algorithms;

import org.algovisualizer.model.AlgorithmState;
import java.util.Iterator;

/**
 * Strategy interface for all algorithms.
 * Each algorithm returns an iterator of states for step-by-step visualization.
 */
public interface Algorithm {
    Iterator<AlgorithmState> execute(int[] array);
    String getTimeComplexity();
    String getSpaceComplexity();
}