public class Main {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums) {
            bst.add(num);
        }

        // 测试前序遍历
        bst.preOrder(); // 5 3 2 4 6 8
        // 测试非递归前序遍历
        bst.preOrderNotR(); // 5 3 2 4 6 8
        // 测试中序遍历
        bst.inOrder(); // 2 3 4 5 6 8 可以注意到中序遍历就是排序
        // 测试后序遍历
        bst.postOrder(); // 2 4 3 8 6 5

        // 测试层序遍历广度优先
        bst.levelOrder(); // 5 3 6 2 4 8

        // System.out.println(bst);
    }
}
