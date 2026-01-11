package org.algovisualizer.controller;

import org.algovisualizer.model.*;
import org.algovisualizer.algorithms.*;
import org.algovisualizer.model.graph.Graph;
import org.algovisualizer.model.graph.Node;
import org.algovisualizer.model.tree.Tree;
import org.algovisualizer.observer.AlgorithmObserver;
import javax.swing.JOptionPane;
import java.util.*;
import java.util.function.Consumer;

public class AlgorithmController {
    private Algorithm currentAlgorithm;
    private AlgorithmState currentState;
    private final List<AlgorithmObserver> observers;
    private Timer executionTimer;
    private volatile int speed = 50;
    private List<AlgorithmState> allSteps;
    private int currentStepIndex = -1;
    private int[] originalArray;
    private Graph currentGraph;
    private Tree currentTree;
    private Consumer<String> visualizerSwitcher;

    public AlgorithmController() {
        this.observers = new ArrayList<>();
        this.currentState = new AlgorithmState();
        this.allSteps = new ArrayList<>();
    }

    public void addObserver(AlgorithmObserver observer) { observers.add(observer); }
    public void addVisualizerSwitcher(Consumer<String> switcher) { this.visualizerSwitcher = switcher; }

    public void setAlgorithm(AlgorithmType type) {
        stopPlayback();
        currentAlgorithm = AlgorithmFactory.createAlgorithm(type);
        currentState = new AlgorithmState(); 
        currentState.setAlgorithmType(type);
        if (currentAlgorithm != null) {
            currentState.setTimeComplexity(currentAlgorithm.getTimeComplexity());
        }
        
        if (visualizerSwitcher != null) {
            visualizerSwitcher.accept(type.getCategory());
        }
        
        generateRandomData();
    }

    public void generateRandomData() {
        stopPlayback();
        String category = currentState.getAlgorithmType().getCategory();
        switch (category) {
            case "Graph":
                createSampleGraph();
                setGraph(this.currentGraph);
                break;
            case "Tree":
                createSampleTree();
                setTree(this.currentTree);
                break;
            default:
                generateRandomArray(20);
                break;
        }
    }

    public void setCustomData(String data) {
        stopPlayback();
        try {
            String category = currentState.getAlgorithmType().getCategory();
            switch (category) {
                case "Sorting":
                case "Searching":
                    int[] array = Arrays.stream(data.split(","))
                                        .map(String::trim)
                                        .mapToInt(Integer::parseInt)
                                        .toArray();
                    setArray(array);
                    break;
                case "Tree":
                    Tree tree = new Tree();
                    Arrays.stream(data.split(","))
                          .map(String::trim)
                          .mapToInt(Integer::parseInt)
                          .forEach(tree::insert);
                    tree.positionNodes();
                    setTree(tree);
                    break;
                case "Graph":
                    JOptionPane.showMessageDialog(null, "Custom graph input is not yet supported.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to parse custom data.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setArray(int[] array) {
        this.originalArray = array.clone();
        resetExecutionState();
        currentState.setArray(this.originalArray);
        currentState.setGraph(null);
        currentState.setTree(null);
        notifyObservers();
    }
    
    private void setTree(Tree tree) {
        this.currentTree = tree;
        resetExecutionState();
        currentState.setTree(this.currentTree);
        currentState.setArray(null);
        currentState.setGraph(null);
        notifyObservers();
    }

    private void setGraph(Graph graph) {
        this.currentGraph = graph;
        resetExecutionState();
        currentState.setGraph(this.currentGraph);
        currentState.setArray(null);
        currentState.setTree(null);
        notifyObservers();
    }

    private void generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(100) + 1;
        }
        setArray(array);
    }

    private void createSampleGraph() {
        this.currentGraph = new Graph();
        Node a = new Node(0, "A", 100, 150);
        Node b = new Node(1, "B", 300, 100);
        Node c = new Node(2, "C", 150, 300);
        Node d = new Node(3, "D", 400, 300);
        currentGraph.addNode(a);
        currentGraph.addNode(b);
        currentGraph.addNode(c);
        currentGraph.addNode(d);
        currentGraph.addEdge(a, b, 1);
        currentGraph.addEdge(a, c, 1);
        currentGraph.addEdge(b, d, 1);
        currentGraph.addEdge(c, d, 1);
    }

    private void createSampleTree() {
        this.currentTree = new Tree();
        currentTree.insert(50);
        currentTree.insert(30);
        currentTree.insert(20);
        currentTree.insert(40);
        currentTree.insert(70);
        currentTree.insert(60);
        currentTree.insert(80);
        currentTree.positionNodes();
    }

    private void precomputeSteps() {
        if (currentAlgorithm == null) return;
        allSteps.clear();
        String category = currentState.getAlgorithmType().getCategory();
        Iterator<AlgorithmState> iterator;
        switch (category) {
            case "Graph": iterator = currentAlgorithm.execute(currentGraph); break;
            case "Tree": iterator = currentAlgorithm.execute(currentTree); break;
            default: iterator = currentAlgorithm.execute(originalArray); break;
        }
        iterator.forEachRemaining(allSteps::add);
        currentStepIndex = 0;
        if (!allSteps.isEmpty()) {
            currentState = allSteps.get(0);
        }
    }

    public void play() {
        if (currentState.isPlaying()) return;
        
        if (allSteps.isEmpty() || currentStepIndex >= allSteps.size() - 1) {
            precomputeSteps();
        }
        
        currentState.setPlaying(true);
        notifyObservers();
        startPlaybackLoop();
    }

    private void startPlaybackLoop() {
        if (executionTimer != null) executionTimer.cancel();
        executionTimer = new Timer();
        executionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!currentState.isPlaying()) {
                    this.cancel();
                    return;
                }
                if (currentStepIndex < allSteps.size() - 1) {
                    currentStepIndex++;
                    currentState = allSteps.get(currentStepIndex);
                    currentState.setPlaying(true);
                    notifyObservers();
                } else {
                    stopPlayback();
                }
            }
        }, getDelay(), getDelay());
    }

    public void pause() {
        stopPlayback();
    }

    public void stepForward() {
        if (currentState.isPlaying()) return;
        if (allSteps.isEmpty() || currentStepIndex >= allSteps.size() - 1) {
            precomputeSteps();
        } else {
            currentStepIndex++;
            currentState = allSteps.get(currentStepIndex);
            notifyObservers();
        }
    }

    public void stepBack() {
        if (currentState.isPlaying()) return;
        if (currentStepIndex > 0) {
            currentStepIndex--;
            currentState = allSteps.get(currentStepIndex);
            notifyObservers();
        }
    }

    public void reset() {
        stopPlayback();
        generateRandomData();
    }

    private void stopPlayback() {
        if (executionTimer != null) {
            executionTimer.cancel();
            executionTimer = null;
        }
        if (currentState.isPlaying()) {
            currentState.setPlaying(false);
            notifyObservers();
        }
    }

    private void resetExecutionState() {
        allSteps.clear();
        currentStepIndex = -1;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        if (currentState.isPlaying()) {
            startPlaybackLoop();
        }
    }

    private int getDelay() {
        return Math.max(10, 1000 - (speed * 10));
    }

    private void notifyObservers() {
        for (AlgorithmObserver observer : observers) {
            if (observer != null) {
                observer.onStateUpdate(currentState);
            }
        }
    }
}