public class Main {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums) {
            bst.add(num);
        }

        // 测试前序遍历
        bst.preOrder(); // 5 3 2 4 6 8
        System.out.println(bst);
    }
}
