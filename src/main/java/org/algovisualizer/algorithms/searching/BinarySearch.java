package org.algovisualizer.algorithms.searching;

import org.
algovisualizer.algorithms.Algorithm;
import org.
algovisualizer.model.AlgorithmState;
import java.util.*;

/**
 * Binary Search - efficient search in sorted array.
 * Time: O(log n), Space: O(1)
 */
public class BinarySearch implements Algorithm {
    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        List<AlgorithmState> states = new ArrayList<>();
        int[] arr = array.clone();
        Arrays.sort(arr); // Ensure array is sorted
        int target = arr[arr.length / 2];
        int comparisons = 0, step = 0;

        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            AlgorithmState state = new AlgorithmState();
            state.setArray(arr.clone());
            state.setComparingIndices(Set.of(mid));
            state.setComparisons(++comparisons);
            state.setStepCount(step++);
            state.setTimeComplexity(getTimeComplexity());
            state.setDescription(String.format("Checking middle element at index %d", mid));
            states.add(state);

            if (arr[mid] == target) {
                AlgorithmState foundState = new AlgorithmState();
                foundState.setArray(arr.clone());
                foundState.setSortedIndices(Set.of(mid));
                foundState.setComparisons(comparisons);
                foundState.setStepCount(step);
                foundState.setTimeComplexity(getTimeComplexity());
                foundState.setDescription(String.format("Found %d at index %d!", target, mid));
                states.add(foundState);
                break;
            }

            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return states.iterator();
    }

    @Override
    public String getTimeComplexity() { return "O(log n)"; }

    @Override
    public String getSpaceComplexity() { return "O(1)"; }
}
