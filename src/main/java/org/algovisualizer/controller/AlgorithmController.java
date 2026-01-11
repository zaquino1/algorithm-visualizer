package org.algovisualizer.controller;

import org.algovisualizer.model.*;
import org.algovisualizer.algorithms.*;
import org.algovisualizer.observer.AlgorithmObserver;
import java.util.*;

public class AlgorithmController {
    private Algorithm currentAlgorithm;
    private AlgorithmState state;
    private final List<AlgorithmObserver> observers;
    private Timer executionTimer;
    private int speed = 50;
    private Iterator<AlgorithmState> currentSteps;
    private int[] originalArray;

    public AlgorithmController() {
        this.observers = new ArrayList<>();
        this.state = new AlgorithmState();
        this.originalArray = new int[0];
    }

    public void setAlgorithm(AlgorithmType type) {
        currentAlgorithm = AlgorithmFactory.createAlgorithm(type);
        currentSteps = null;
        notifyObservers();
    }

    public void setArray(int[] array) {
        this.originalArray = array.clone();
        state = new AlgorithmState();
        state.setArray(array.clone());
        if (currentAlgorithm != null) {
            state.setTimeComplexity(currentAlgorithm.getTimeComplexity());
        }
        currentSteps = null;
        notifyObservers();
    }

    public void generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(100) + 1;
        }
        setArray(array);
    }

    public void run() {
        if (currentAlgorithm == null || state.getArray().length == 0) {
            return;
        }

        if (executionTimer != null) {
            executionTimer.cancel();
        }

        if (currentSteps == null || !currentSteps.hasNext()) {
            currentSteps = currentAlgorithm.execute(state.getArray());
        }

        executionTimer = new Timer();
        long startTime = System.currentTimeMillis();

        executionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (currentSteps.hasNext()) {
                    state = currentSteps.next();
                    state.setElapsedTime(System.currentTimeMillis() - startTime);
                    notifyObservers();
                } else {
                    executionTimer.cancel();
                }
            }
        }, 0, getDelay());
    }

    public void step() {
        if (currentAlgorithm == null || state.getArray().length == 0) {
            return;
        }

        if (currentSteps == null || !currentSteps.hasNext()) {
            currentSteps = currentAlgorithm.execute(state.getArray());
        }

        if (currentSteps.hasNext()) {
            state = currentSteps.next();
            notifyObservers();
        }
    }

    public void reset() {
        if (executionTimer != null) {
            executionTimer.cancel();
        }
        currentSteps = null;
        if (originalArray.length > 0) {
            setArray(originalArray);
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private int getDelay() {
        return Math.max(10, 1000 - (speed * 10));
    }

    public void addObserver(AlgorithmObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (AlgorithmObserver observer : observers) {
            observer.onStateUpdate(state);
        }
    }
}
