package org.algovisualizer.algorithms.sorting;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import java.util.*;

/**
 * Merge Sort - divide and conquer by merging sorted halves.
 * Time: O(n log n), Space: O(n)
 */
public class MergeSort implements Algorithm {
    private List<AlgorithmState> states;
    private int[] arr;
    private int comparisons, swaps, step;

    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        states = new ArrayList<>();
        arr = array.clone();
        comparisons = swaps = step = 0;

        mergeSort(0, arr.length - 1);

        AlgorithmState finalState = createState();
        Set<Integer> allSorted = new HashSet<>();
        for (int i = 0; i < arr.length; i++) allSorted.add(i);
        finalState.setSortedIndices(allSorted);
        finalState.setDescription("Merge sort complete!");
        states.add(finalState);

        return states.iterator();
    }

    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            AlgorithmState compareState = createState();
            Set<Integer> comparing = new HashSet<>();
            comparing.add(k);
            compareState.setComparingIndices(comparing);
            compareState.setDescription("Merging sorted subarrays");
            states.add(compareState);

            comparisons++;
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
            swaps++;
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];

        AlgorithmState mergedState = createState();
        Set<Integer> merged = new HashSet<>();
        for (int idx = left; idx <= right; idx++) merged.add(idx);
        mergedState.setSortedIndices(merged);
        states.add(mergedState);
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
    public String getSpaceComplexity() { return "O(n)"; }
}