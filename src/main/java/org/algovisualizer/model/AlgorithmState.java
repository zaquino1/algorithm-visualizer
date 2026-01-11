package org.algovisualizer.model;

import java.util.*;

/**
 * Immutable state object representing a single step in algorithm execution.
 * Contains array state, performance metrics, and visual indicators.
 */
public class AlgorithmState {
    private int[] array;
    private Set<Integer> comparingIndices;
    private Set<Integer> swappingIndices;
    private Set<Integer> sortedIndices;
    private int comparisons;
    private int swaps;
    private int stepCount;
    private String description;
    private String timeComplexity;
    private long elapsedTime;

    public AlgorithmState() {
        this.array = new int[0];
        this.comparingIndices = new HashSet<>();
        this.swappingIndices = new HashSet<>();
        this.sortedIndices = new HashSet<>();
        this.description = "";
        this.timeComplexity = "N/A";
    }

    // Getters
    public int[] getArray() { return array; }
    public Set<Integer> getComparingIndices() { return comparingIndices; }
    public Set<Integer> getSwappingIndices() { return swappingIndices; }
    public Set<Integer> getSortedIndices() { return sortedIndices; }
    public int getComparisons() { return comparisons; }
    public int getSwaps() { return swaps; }
    public int getStepCount() { return stepCount; }
    public String getDescription() { return description; }
    public String getTimeComplexity() { return timeComplexity; }
    public long getElapsedTime() { return elapsedTime; }

    // Setters
    public void setArray(int[] array) { this.array = array; }
    public void setComparingIndices(Set<Integer> indices) { this.comparingIndices = indices; }
    public void setSwappingIndices(Set<Integer> indices) { this.swappingIndices = indices; }
    public void setSortedIndices(Set<Integer> indices) { this.sortedIndices = indices; }
    public void setComparisons(int comparisons) { this.comparisons = comparisons; }
    public void setSwaps(int swaps) { this.swaps = swaps; }
    public void setStepCount(int stepCount) { this.stepCount = stepCount; }
    public void setDescription(String description) { this.description = description; }
    public void setTimeComplexity(String timeComplexity) { this.timeComplexity = timeComplexity; }
    public void setElapsedTime(long elapsedTime) { this.elapsedTime = elapsedTime; }
}
