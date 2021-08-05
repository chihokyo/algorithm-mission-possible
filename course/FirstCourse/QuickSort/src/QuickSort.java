import java.util.Arrays;

public class QuickSort {

    private QuickSort() {
    }

    public static <E extends Comparable<E>> void sort(E[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public static <E extends Comparable<E>> void sort(E[] arr, int left, int right) {
        // 这里递归终止条件就是左边大于右边
        if (left >= right) return;
        int p = partition(arr, left, right);
        // 上面结束之后arr[left, p-1] < arr[p];arr[p+1,right] >= arr[p]的
        sort(arr, left, p - 1);
        sort(arr, p + 1, right);
    }

    /**
     * 实现原地数组排序
     *
     * @param arr   数组
     * @param left  左区间
     * @param right 右区间
     * @param <E>   返回泛型
     * @return int 返回基准点pivot所在的index位置
     */
    private static <E extends Comparable<E>> int partition(E[] arr, int left, int right) {
        // arr[left+1,j] < v ;arr[j+1,i] >= v
        // 因为这里你会发现>=v 都是可以的。所以就是>=v
        int j = left;
        for (int i = j + 1; i <= right; i++) {
            // 因为如果要大于的话 直接就是i++就可以
            // 所以这里开始只写小于的情况，那么就是j++，然后ij互换
            if (arr[i].compareTo(arr[left]) < 0) {
                j++;
                swap(arr, i, j);
            }
        }

        // 以上流程全部走完的话，这里依然没有结束arr[left] 也就是基准点，还没有回归原位
        // 进行最后一次交换，然后让left到该到的位置
        swap(arr, left, j);
        // 为什么最后要返回j 因为这个j其实就是我们需要的基准点的原位
        return j;
    }

    private static <E> void swap(E[] arr, int i, int j) {
        E t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {

        int n = 100000;

        // MergeSort, n = 5000000: 1.559710 s
        // QuickSort, n = 5000000: 1.136350 s
        // 还是快速更快呢

        // 无序数组
        // Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        // Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        //
        // SortingHelper.sortTest("MergeSort", arr);
        // SortingHelper.sortTest("QuickSort", arr2);

        // 有序数组
        Integer[] arr3 = ArrayGenerator.generateOrderArray(n);
        Integer[] arr4 = Arrays.copyOf(arr3, arr3.length);

        SortingHelper.sortTest("MergeSort", arr3);
        SortingHelper.sortTest("QuickSort", arr4); // 栈溢出

    }

}
