import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums) {
            bst.add(num);
        }
        // System.out.println(bst.contains(10));
        //
        // // 测试前序遍历
        // bst.preOrder(); // 5 3 2 4 6 8
        // // 测试非递归前序遍历
        // bst.preOrderNotR(); // 5 3 2 4 6 8
        // // 测试中序遍历
        // bst.inOrder(); // 2 3 4 5 6 8 可以注意到中序遍历就是排序
        // // 测试后序遍历
        // bst.postOrder(); // 2 4 3 8 6 5
        // // 测试层序遍历广度优先
        // bst.levelOrder(); // 5 3 6 2 4 8
        //
        // System.out.println(bst);
        // System.out.println("最小值: " + bst.minimum());
        // System.out.println("最大值: " + bst.maximum());

        // 测试删除最小值和最大值
        Random random = new Random();
        int n = 1000;
        for (int i = 0; i < n; i++) {
            bst.add(random.nextInt(1000));
        }
        ArrayList<Integer> nums2 = new ArrayList<>();
        while (!bst.isEmpty()) {
            // 因为每一次移除的都是最小值，所以最后的数字肯定是从小到大排序的
            nums2.add(bst.removeMin());
        }

        for (int i = 1; i < nums2.size(); i++) {
            if (nums2.get(i - 1) > nums2.get(i)) {
                throw new IllegalArgumentException("Error.");
            }
        }
        System.out.println("removeMin test completed.");

        for (int i = 0; i < n; i++) {
            bst.add(random.nextInt(1000));
        }
        ArrayList<Integer> nums3 = new ArrayList<>();
        while (!bst.isEmpty()) {
            // 因为每一次移除的都是最小值，所以最后的数字肯定是从小到大排序的
            nums3.add(bst.removeMax());
        }

        for (int i = 1; i < nums3.size(); i++) {
            if (nums3.get(i - 1) < nums3.get(i)) {
                throw new IllegalArgumentException("Error");
            }
        }
        System.out.println("removeMax test completed.");


    }
}
