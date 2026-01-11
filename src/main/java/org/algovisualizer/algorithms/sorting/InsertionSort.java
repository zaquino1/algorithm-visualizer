package org.algovisualizer.algorithms.sorting;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import java.util.*;

/**
 * Insertion Sort - builds sorted array one item at a time.
 * Time: O(n²), Space: O(1)
 */
public class InsertionSort implements Algorithm {
    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        List<AlgorithmState> states = new ArrayList<>();
        int[] arr = array.clone();
        int n = arr.length;
        int comparisons = 0, swaps = 0, step = 0;

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                AlgorithmState compareState = createState(arr, comparisons, swaps, step++);
                compareState.setComparingIndices(Set.of(j, j + 1));
                compareState.setDescription(String.format("Inserting %d into sorted portion", key));
                states.add(compareState);

                comparisons++;
                arr[j + 1] = arr[j];
                j--;
                swaps++;
            }

            arr[j + 1] = key;

            AlgorithmState insertState = createState(arr, comparisons, swaps, step++);
            Set<Integer> sorted = new HashSet<>();
            for (int k = 0; k <= i; k++) sorted.add(k);
            insertState.setSortedIndices(sorted);
            states.add(insertState);
        }

        AlgorithmState finalState = createState(arr, comparisons, swaps, step);
        Set<Integer> allSorted = new HashSet<>();
        for (int i = 0; i < n; i++) allSorted.add(i);
        finalState.setSortedIndices(allSorted);
        finalState.setDescription("Insertion sort complete!");
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
