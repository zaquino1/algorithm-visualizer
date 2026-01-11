package org.algovisualizer.model.tree;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private TreeNode root;

    public Tree() {
        this.root = null;
    }

    public Tree(TreeNode root) {
        this.root = root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            root = new TreeNode(value);
            return root;
        }
        if (value < root.getValue()) {
            root.setLeft(insertRec(root.getLeft(), value));
        } else if (value > root.getValue()) {
            root.setRight(insertRec(root.getRight(), value));
        }
        return root;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void positionNodes() {
        if (root != null) {
            positionNodes(root, 800 / 2, 50, 800 / 4);
        }
    }

    private void positionNodes(TreeNode node, int x, int y, int xOffset) {
        if (node == null) return;
        node.setPosition(x, y);
        positionNodes(node.getLeft(), x - xOffset, y + 70, xOffset / 2);
        positionNodes(node.getRight(), x + xOffset, y + 70, xOffset / 2);
    }

    public List<TreeNode> getAllNodes() {
        List<TreeNode> nodes = new ArrayList<>();
        collectNodes(root, nodes);
        return nodes;
    }

    private void collectNodes(TreeNode node, List<TreeNode> nodes) {
        if (node != null) {
            nodes.add(node);
            collectNodes(node.getLeft(), nodes);
            collectNodes(node.getRight(), nodes);
        }
    }
}