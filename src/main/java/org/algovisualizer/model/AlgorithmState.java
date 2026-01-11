package org.algovisualizer.model;

import org.algovisualizer.model.graph.Graph;
import org.algovisualizer.model.tree.Tree;
import java.util.HashSet;
import java.util.Set;

public class AlgorithmState {
    private int[] array;
    private Set<Integer> comparingIndices;
    private Set<Integer> swappingIndices;
    private Set<Integer> sortedIndices;
    private Graph graph;
    private Tree tree;
    private int comparisons;
    private int swaps;
    private int stepCount;
    private String description;
    private String timeComplexity;
    private long elapsedTime;
    private int highlightedLine;
    private AlgorithmType algorithmType;
    private boolean isPlaying; // New flag for playback status

    public AlgorithmState() {
        this.array = new int[0];
        this.comparingIndices = new HashSet<>();
        this.swappingIndices = new HashSet<>();
        this.sortedIndices = new HashSet<>();
        this.description = "Ready. Generate data to begin.";
        this.timeComplexity = "N/A";
        this.highlightedLine = -1;
        this.isPlaying = false;
    }

    // Getters
    public int[] getArray() { return array; }
    public Set<Integer> getComparingIndices() { return comparingIndices; }
    public Set<Integer> getSwappingIndices() { return swappingIndices; }
    public Set<Integer> getSortedIndices() { return sortedIndices; }
    public Graph getGraph() { return graph; }
    public Tree getTree() { return tree; }
    public int getComparisons() { return comparisons; }
    public int getSwaps() { return swaps; }
    public int getStepCount() { return stepCount; }
    public String getDescription() { return description; }
    public String getTimeComplexity() { return timeComplexity; }
    public long getElapsedTime() { return elapsedTime; }
    public int getHighlightedLine() { return highlightedLine; }
    public AlgorithmType getAlgorithmType() { return algorithmType; }
    public boolean isPlaying() { return isPlaying; }

    // Setters
    public void setArray(int[] array) { this.array = array; }
    public void setComparingIndices(Set<Integer> indices) { this.comparingIndices = indices; }
    public void setSwappingIndices(Set<Integer> indices) { this.swappingIndices = indices; }
    public void setSortedIndices(Set<Integer> indices) { this.sortedIndices = indices; }
    public void setGraph(Graph graph) { this.graph = graph; }
    public void setTree(Tree tree) { this.tree = tree; }
    public void setComparisons(int comparisons) { this.comparisons = comparisons; }
    public void setSwaps(int swaps) { this.swaps = swaps; }
    public void setStepCount(int stepCount) { this.stepCount = stepCount; }
    public void setDescription(String description) { this.description = description; }
    public void setTimeComplexity(String timeComplexity) { this.timeComplexity = timeComplexity; }
    public void setElapsedTime(long elapsedTime) { this.elapsedTime = elapsedTime; }
    public void setHighlightedLine(int highlightedLine) { this.highlightedLine = highlightedLine; }
    public void setAlgorithmType(AlgorithmType algorithmType) { this.algorithmType = algorithmType; }
    public void setPlaying(boolean playing) { isPlaying = playing; }
}