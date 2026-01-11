public class PostOrderTraversal {
    public void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.getLeft());
        postOrder(node.getRight());
        // Process node (e.g., print node.getValue())
    }
}