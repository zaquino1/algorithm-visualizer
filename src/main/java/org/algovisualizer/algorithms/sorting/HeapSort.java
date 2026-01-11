package org.algovisualizer.algorithms.sorting;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import java.util.*;

/**
 * Heap Sort - uses heap data structure for sorting.
 * Time: O(n log n), Space: O(1)
 */
public class HeapSort implements Algorithm {
    private List<AlgorithmState> states;
    private int[] arr;
    private int comparisons, swaps, step;

    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        states = new ArrayList<>();
        arr = array.clone();
        comparisons = swaps = step = 0;
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(n, i);
        }

        // Extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            AlgorithmState swapState = createState();
            swapState.setSwappingIndices(Set.of(0, i));
            swapState.setDescription("Moving max to end");
            states.add(swapState);

            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            swaps++;

            heapify(i, 0);

            AlgorithmState sortedState = createState();
            Set<Integer> sorted = new HashSet<>();
            for (int j = i; j < n; j++) sorted.add(j);
            sortedState.setSortedIndices(sorted);
            states.add(sortedState);
        }

        AlgorithmState finalState = createState();
        Set<Integer> allSorted = new HashSet<>();
        for (int i = 0; i < n; i++) allSorted.add(i);
        finalState.setSortedIndices(allSorted);
        finalState.setDescription("Heap sort complete!");
        states.add(finalState);

        return states.iterator();
    }

    private void heapify(int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n) {
            comparisons++;
            if (arr[left] > arr[largest]) largest = left;
        }

        if (right < n) {
            comparisons++;
            if (arr[right] > arr[largest]) largest = right;
        }

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            swaps++;

            heapify(n, largest);
        }
    }

    private AlgorithmState createState() {
        AlgorithmState state = new AlgorithmState();
        state.setArray(arr.clone());
        state.setComparisons(comparisons);
        state.setSwaps(swaps);
        state.setStepCount(step++);
        state.setTimeComplexity(getTimeComplexity());
        return state;
    }

    @Override
    public String getTimeComplexity() { return "O(n log n)"; }

    @Override
    public String getSpaceComplexity() { return "O(1)"; }
}
