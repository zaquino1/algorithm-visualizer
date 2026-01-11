public class InOrderTraversal {
    public void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeft());
        // Process node (e.g., print node.getValue())
        inOrder(node.getRight());
    }
}