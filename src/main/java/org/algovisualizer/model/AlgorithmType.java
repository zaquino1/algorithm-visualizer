package org.algovisualizer.model;

public enum AlgorithmType {
    // Sorting Algorithms
    BUBBLE_SORT("Bubble Sort", "Sorting"),
    SELECTION_SORT("Selection Sort", "Sorting"),
    INSERTION_SORT("Insertion Sort", "Sorting"),
    MERGE_SORT("Merge Sort", "Sorting"),
    QUICK_SORT("Quick Sort", "Sorting"),
    HEAP_SORT("Heap Sort", "Sorting"),

    // Searching Algorithms
    LINEAR_SEARCH("Linear Search", "Searching"),
    BINARY_SEARCH("Binary Search", "Searching"),

    // Graph Algorithms
    BFS("Breadth-First Search", "Graph"),
    DFS("Depth-First Search", "Graph"),
    DIJKSTRA("Dijkstra's Algorithm", "Graph"),

    // Tree Algorithms
    IN_ORDER_TRAVERSAL("In-Order Traversal", "Tree"),
    PRE_ORDER_TRAVERSAL("Pre-Order Traversal", "Tree"),
    POST_ORDER_TRAVERSAL("Post-Order Traversal", "Tree"),

    // String Algorithms
    LCS("Longest Common Subsequence", "Strings");


    private final String displayName;
    private final String category;

    AlgorithmType(String displayName, String category) {
        this.displayName = displayName;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return displayName;
    }
}