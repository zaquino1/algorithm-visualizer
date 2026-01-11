package org.algovisualizer.algorithms.sorting;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import java.util.*;

/**
 * Quick Sort - divide and conquer with partitioning.
 * Time: O(n log n) average, O(n²) worst, Space: O(log n)
 */
public class QuickSort implements Algorithm {
    private List<AlgorithmState> states;
    private int[] arr;
    private int comparisons, swaps, step;

    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        states = new ArrayList<>();
        arr = array.clone();
        comparisons = swaps = step = 0;

        quickSort(0, arr.length - 1);

        AlgorithmState finalState = createState();
        Set<Integer> allSorted = new HashSet<>();
        for (int i = 0; i < arr.length; i++) allSorted.add(i);
        finalState.setSortedIndices(allSorted);
        finalState.setDescription("Quick sort complete!");
        states.add(finalState);

        return states.iterator();
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        AlgorithmState pivotState = createState();
        pivotState.setComparingIndices(Set.of(high));
        pivotState.setDescription(String.format("Pivot: %d", pivot));
        states.add(pivotState);

        for (int j = low; j < high; j++) {
            AlgorithmState compareState = createState();
            compareState.setComparingIndices(Set.of(j, high));
            compareState.setDescription(String.format("Comparing with pivot"));
            states.add(compareState);
            comparisons++;

            if (arr[j] < pivot) {
                i++;
                if (i != j) {
                    AlgorithmState swapState = createState();
                    swapState.setSwappingIndices(Set.of(i, j));
                    states.add(swapState);

                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    swaps++;
                }
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        swaps++;

        return i + 1;
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
    public String getSpaceComplexity() { return "O(log n)"; }
}
