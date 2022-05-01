import java.util.Random;

public class Main {


    /**
     * 测试堆排序速度
     *
     * @param testData 未排序数组
     * @param isHeap   是否采用堆化
     * @return double 速度
     */
    private static double testHeap(Integer[] testData, boolean isHeap) {
        long startTime = System.nanoTime();

        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        // 2种方式测试
        if (isHeap) {
            maxHeap = new MaxHeap<>(testData);
        } else {
            maxHeap = new MaxHeap<>();
            for (int num : testData) {
                maxHeap.add(num);
            }
        }

        // 这里生成了一个新的数组
        int[] arr = new int[testData.length];
        for (int i = 0; i < testData.length; i++) {
            // 这里是把已经排序好的数组结构只拿走最大的堆顶，放进去新的数组
            arr[i] = maxHeap.extractMax();
        }
        // 测试数组是否排序好（从大到小）
        for (int i = 1; i < testData.length; i++) {
            // 前一个小于后一个
            if (arr[i - 1] < arr[i]) {
                throw new IllegalArgumentException("Error");
            }
        }
        System.out.println("Test MaxHeap completed.");

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000_000.0;
    }

    public static void main(String[] args) {

        /*====================测试最大堆===========================*/
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

        /*===================测试添加操作和堆化操作=====================*/

        // 测试2种速度 添加操作PKHeapify堆化操作
        System.out.println("====测试2种速度 添加操作PK堆化Heapify操作====");

        int m = 10000000;
        Random random2 = new Random();
        Integer[] testData = new Integer[m];
        for (int i = 0; i < m; i++) {
            testData[i] = random2.nextInt(Integer.MAX_VALUE);
        }

        double time1 = testHeap(testData, false);
        System.out.println("Without heapify: " + time1 + "s");
        double time2 = testHeap(testData, true);
        System.out.println("With heapify: " + time2 + "s");

    }
}
