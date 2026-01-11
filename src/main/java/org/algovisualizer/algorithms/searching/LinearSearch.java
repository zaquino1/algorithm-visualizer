package org.algovisualizer.algorithms.searching;

import org.
algovisualizer.algorithms.Algorithm;
import org.
algovisualizer.model.AlgorithmState;
import java.util.*;

/**
 * Linear Search - sequential search through array.
 * Time: O(n), Space: O(1)
 */
public class LinearSearch implements Algorithm {
    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        List<AlgorithmState> states = new ArrayList<>();
        int[] arr = array.clone();
        int target = arr[arr.length / 2]; // Search for middle element
        int comparisons = 0, step = 0;

        for (int i = 0; i < arr.length; i++) {
            AlgorithmState state = new AlgorithmState();
            state.setArray(arr.clone());
            state.setComparingIndices(Set.of(i));
            state.setComparisons(++comparisons);
            state.setStepCount(step++);
            state.setTimeComplexity(getTimeComplexity());
            state.setDescription(String.format("Searching for %d at index %d", target, i));
            states.add(state);

            if (arr[i] == target) {
                AlgorithmState foundState = new AlgorithmState();
                foundState.setArray(arr.clone());
                foundState.setSortedIndices(Set.of(i));
                foundState.setComparisons(comparisons);
                foundState.setStepCount(step);
                foundState.setTimeComplexity(getTimeComplexity());
                foundState.setDescription(String.format("Found %d at index %d!", target, i));
                states.add(foundState);
                break;
            }
        }

        return states.iterator();
    }

    @Override
    public String getTimeComplexity() { return "O(n)"; }

    @Override
    public String getSpaceComplexity() { return "O(1)"; }
}
