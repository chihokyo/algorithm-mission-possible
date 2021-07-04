import java.util.Random;

public class ArrayGeneraor {

    private ArrayGeneraor(){}

    /**
     * 生成一个有序大小为n的数组
     * @param n 数组大小
     * @return 返回有序数组
     */
    public static Integer[] generateOrderArray(int n){
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return arr;
    }

    /**
     * 生成一个长度n的随机数组，每个数字的范围是[0,bound)
     * @param n 数组大小
     * @param bound 随机数范围 是一个最大值bound 是一个界 左闭右开[0,bound)
     * @return 返回一个无序数组
     */
    public static Integer[] generateRandomArray(int n, int bound) {
        Integer[] arr = new Integer[n];
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = rnd.nextInt(bound);
        }
        return arr;
    }
}
