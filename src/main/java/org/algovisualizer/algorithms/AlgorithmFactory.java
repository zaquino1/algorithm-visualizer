package org.algovisualizer.algorithms;

import org.algovisualizer.algorithms.graph.BFS;
import org.algovisualizer.algorithms.graph.DFS;
import org.algovisualizer.algorithms.graph.Dijkstra;
import org.algovisualizer.algorithms.searching.BinarySearch;
import org.algovisualizer.algorithms.searching.LinearSearch;
import org.algovisualizer.algorithms.sorting.*;
import org.algovisualizer.algorithms.strings.LCS;
import org.algovisualizer.algorithms.tree.InOrderTraversal;
import org.algovisualizer.algorithms.tree.PostOrderTraversal;
import org.algovisualizer.algorithms.tree.PreOrderTraversal;
import org.algovisualizer.model.AlgorithmType;

public class AlgorithmFactory {
    public static Algorithm createAlgorithm(AlgorithmType type) {
        return switch (type) {
            // Sorting
            case BUBBLE_SORT -> new BubbleSort();
            case SELECTION_SORT -> new SelectionSort();
            case INSERTION_SORT -> new InsertionSort();
            case MERGE_SORT -> new MergeSort();
            case QUICK_SORT -> new QuickSort();
            case HEAP_SORT -> new HeapSort();
            // Searching
            case LINEAR_SEARCH -> new LinearSearch();
            case BINARY_SEARCH -> new BinarySearch();
            // Graph
            case BFS -> new BFS();
            case DFS -> new DFS();
            case DIJKSTRA -> new Dijkstra();
            // Tree
            case IN_ORDER_TRAVERSAL -> new InOrderTraversal();
            case PRE_ORDER_TRAVERSAL -> new PreOrderTraversal();
            case POST_ORDER_TRAVERSAL -> new PostOrderTraversal();
            // Strings
            case LCS -> new LCS();
        };
    }
}