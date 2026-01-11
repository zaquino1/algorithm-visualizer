package org.algovisualizer.observer;

import org.algovisualizer.model.AlgorithmState;

/**
 * Observer interface for the Observer pattern.
 * UI components implement this to receive algorithm state updates.
 */
public interface AlgorithmObserver {
    void onStateUpdate(AlgorithmState state);
}