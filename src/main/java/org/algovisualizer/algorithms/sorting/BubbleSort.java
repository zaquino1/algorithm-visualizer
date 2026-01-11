package org.algovisualizer.algorithms.sorting;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import java.util.*;

/**
 * Bubble Sort implementation with step-by-step state generation.
 * Time: O(n²), Space: O(1)
 * Repeatedly swaps adjacent elements if they're in wrong order.
 */
public class BubbleSort implements Algorithm {

    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        List<AlgorithmState> states = new ArrayList<>();
        int[] arr = array.clone();
        int n = arr.length;
        int comparisons = 0;
        int swaps = 0;
        int step = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                // Comparing state
                AlgorithmState compareState = createState(arr, comparisons, swaps, step++);
                compareState.setComparingIndices(Set.of(j, j + 1));
                compareState.setDescription(String.format("Comparing elements at indices %d and %d", j, j + 1));
                states.add(compareState);

                comparisons++;

                if (arr[j] > arr[j + 1]) {
                    // Swapping state
                    AlgorithmState swapState = createState(arr, comparisons, swaps, step++);
                    swapState.setSwappingIndices(Set.of(j, j + 1));
                    swapState.setDescription(String.format("Swapping %d and %d", arr[j], arr[j + 1]));
                    states.add(swapState);

                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }

            // Mark last element as sorted
            AlgorithmState sortedState = createState(arr, comparisons, swaps, step++);
            Set<Integer> sorted = new HashSet<>();
            for (int k = n - 1; k >= n - i - 1; k--) {
                sorted.add(k);
            }
            sortedState.setSortedIndices(sorted);
            sortedState.setDescription(String.format("Element at index %d is now sorted", n - i - 1));
            states.add(sortedState);

            if (!swapped) break;
        }

        // Final sorted state
        AlgorithmState finalState = createState(arr, comparisons, swaps, step);
        Set<Integer> allSorted = new HashSet<>();
        for (int i = 0; i < n; i++) allSorted.add(i);
        finalState.setSortedIndices(allSorted);
        finalState.setDescription("Array is completely sorted!");
        states.add(finalState);

        return states.iterator();
    }

    private AlgorithmState createState(int[] arr, int comparisons, int swaps, int step) {
        AlgorithmState state = new AlgorithmState();
        state.setArray(arr.clone());
        state.setComparisons(comparisons);
        state.setSwaps(swaps);
        state.setStepCount(step);
        state.setTimeComplexity(getTimeComplexity());
        return state;
    }

    @Override
    public String getTimeComplexity() {
        return "O(n²)";
    }

    @Override
    public String getSpaceComplexity() {
        return "O(1)";
    }
}