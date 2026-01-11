package org.algovisualizer.algorithms;

import org.algovisualizer.algorithms.sorting.*;
import org.algovisualizer.algorithms.searching.*;
import org.algovisualizer.model.AlgorithmType;

/**
 * Factory class for creating algorithm instances.
 * Implements the Factory pattern for loose coupling.
 */
public class AlgorithmFactory {
    public static Algorithm createAlgorithm(AlgorithmType type) {
        return switch (type) {
            case BUBBLE_SORT -> new BubbleSort();
            case SELECTION_SORT -> new SelectionSort();
            case INSERTION_SORT -> new InsertionSort();
            case MERGE_SORT -> new MergeSort();
            case QUICK_SORT -> new QuickSort();
            case HEAP_SORT -> new HeapSort();
            case LINEAR_SEARCH -> new LinearSearch();
            case BINARY_SEARCH -> new BinarySearch();
        };
    }
}