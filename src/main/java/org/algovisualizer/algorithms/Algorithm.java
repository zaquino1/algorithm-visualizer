package org.algovisualizer.algorithms;

import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.model.graph.Graph;
import org.algovisualizer.model.tree.Tree;
import java.util.Collections;
import java.util.Iterator;

public interface Algorithm {
    default Iterator<AlgorithmState> execute(int[] array) {
        return Collections.emptyIterator();
    }
    
    default Iterator<AlgorithmState> execute(Graph graph) {
        return Collections.emptyIterator();
    }

    default Iterator<AlgorithmState> execute(Tree tree) {
        return Collections.emptyIterator();
    }

    String getTimeComplexity();
    String getSpaceComplexity();
}