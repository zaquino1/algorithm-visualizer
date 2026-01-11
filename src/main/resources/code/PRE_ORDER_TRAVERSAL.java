public class PreOrderTraversal {
    public void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        // Process node (e.g., print node.getValue())
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }
}