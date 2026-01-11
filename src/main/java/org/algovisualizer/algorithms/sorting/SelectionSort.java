package org.algovisualizer.algorithms.sorting;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import java.util.*;

/**
 * Selection Sort - finds minimum and places it at beginning.
 * Time: O(n²), Space: O(1)
 */
public class SelectionSort implements Algorithm {
    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        List<AlgorithmState> states = new ArrayList<>();
        int[] arr = array.clone();
        int n = arr.length;
        int comparisons = 0, swaps = 0, step = 0;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;

            for (int j = i + 1; j < n; j++) {
                AlgorithmState state = createState(arr, comparisons, swaps, step++);
                state.setComparingIndices(Set.of(minIdx, j));
                state.setDescription(String.format("Finding minimum in unsorted portion"));
                states.add(state);

                comparisons++;
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }

            if (minIdx != i) {
                AlgorithmState swapState = createState(arr, comparisons, swaps, step++);
                swapState.setSwappingIndices(Set.of(i, minIdx));
                swapState.setDescription(String.format("Moving minimum to position %d", i));
                states.add(swapState);

                int temp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = temp;
                swaps++;
            }

            AlgorithmState sortedState = createState(arr, comparisons, swaps, step++);
            Set<Integer> sorted = new HashSet<>();
            for (int k = 0; k <= i; k++) sorted.add(k);
            sortedState.setSortedIndices(sorted);
            states.add(sortedState);
        }

        AlgorithmState finalState = createState(arr, comparisons, swaps, step);
        Set<Integer> allSorted = new HashSet<>();
        for (int i = 0; i < n; i++) allSorted.add(i);
        finalState.setSortedIndices(allSorted);
        finalState.setDescription("Selection sort complete!");
        states.add(finalState);

        return states.iterator();
    }

    private AlgorithmState createState(int[] arr, int comp, int swaps, int step) {
        AlgorithmState state = new AlgorithmState();
        state.setArray(arr.clone());
        state.setComparisons(comp);
        state.setSwaps(swaps);
        state.setStepCount(step);
        state.setTimeComplexity(getTimeComplexity());
        return state;
    }

    @Override
    public String getTimeComplexity() { return "O(n²)"; }

    @Override
    public String getSpaceComplexity() { return "O(1)"; }
}