package org.algovisualizer.model;

/**
 * Enumeration of all supported algorithm types.
 * Organized by category for easy navigation.
 */
public enum AlgorithmType {
    // Sorting Algorithms
    BUBBLE_SORT("Bubble Sort"),
    SELECTION_SORT("Selection Sort"),
    INSERTION_SORT("Insertion Sort"),
    MERGE_SORT("Merge Sort"),
    QUICK_SORT("Quick Sort"),
    HEAP_SORT("Heap Sort"),

    // Searching Algorithms
    LINEAR_SEARCH("Linear Search"),
    BINARY_SEARCH("Binary Search");

    private final String displayName;

    AlgorithmType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}