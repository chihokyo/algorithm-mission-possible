import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // 测试最大堆
        // 1 生成一个随机数组
        // 2 填进去最大堆
        // 3 每次取出来最大堆的堆顶元素并且填充到新的数组
        // 4 验证这个数组是不是从大到小排列的
        int n = 1000000;
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            maxHeap.add(random.nextInt(Integer.MAX_VALUE));
        }
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = maxHeap.extractMax();
        }
        for (int i = 1; i < arr.length; i++) {
            // 前一个小于后一个
            if (arr[i - 1] < arr[i]) {
                throw new IllegalArgumentException("Error");
            }
        }
        System.out.println("Test MaxHeap completed.");

    }
}
