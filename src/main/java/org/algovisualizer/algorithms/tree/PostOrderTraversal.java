package org.algovisualizer.algorithms.tree;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.model.tree.Tree;
import org.algovisualizer.model.tree.TreeNode;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PostOrderTraversal implements Algorithm {

    private List<AlgorithmState> states;
    private int step;

    @Override
    public Iterator<AlgorithmState> execute(Tree tree) {
        states = new ArrayList<>();
        step = 0;
        if (tree.getRoot() != null) {
            postOrderRecursive(tree.getRoot());
        }
        states.add(createState(tree, "Post-Order Traversal Complete", ++step));
        return states.iterator();
    }

    private void postOrderRecursive(TreeNode node) {
        if (node == null) return;

        postOrderRecursive(node.getLeft());
        postOrderRecursive(node.getRight());

        node.setColor(Color.RED); // Visiting color
        states.add(createState(new Tree(node), "Visiting node " + node.getValue(), ++step));
        
        node.setColor(Color.GREEN); // Visited color
    }

    private AlgorithmState createState(Tree tree, String description, int step) {
        AlgorithmState state = new AlgorithmState();
        state.setTree(copyTree(tree));
        state.setDescription(description);
        state.setStepCount(step);
        state.setTimeComplexity(getTimeComplexity());
        return state;
    }

    private Tree copyTree(Tree original) {
        Tree copy = new Tree();
        if (original.getRoot() != null) {
            copy.setRoot(copyNode(original.getRoot()));
        }
        return copy;
    }

    private TreeNode copyNode(TreeNode original) {
        if (original == null) return null;
        TreeNode copy = new TreeNode(original.getValue());
        copy.setColor(original.getColor());
        copy.setPosition(original.getX(), original.getY());
        copy.setLeft(copyNode(original.getLeft()));
        copy.setRight(copyNode(original.getRight()));
        return copy;
    }

    @Override
    public String getTimeComplexity() {
        return "O(n)";
    }

    @Override
    public String getSpaceComplexity() {
        return "O(h)";
    }
}