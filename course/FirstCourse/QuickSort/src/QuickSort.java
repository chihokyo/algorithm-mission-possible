import java.util.Arrays;

public class QuickSort {

    private QuickSort(){}

    public static <E extends Comparable<E>> void sort(E[] arr){
        // 0 开始 index最后一个结束
        sort(arr, 0, arr.length - 1);
    }

    public static <E extends Comparable<E>> void sort(E[] arr, int left, int right){
        if (left >= right) return;
        int pivot = partition(arr,left, right);
        sort(arr, left, pivot - 1);
        sort(arr, pivot + 1, right);
    }

    /**
     * 这里实现的是原地排序
     * 维持循环不变量
     * arr[left + 1, j] < v; arr[j + 1, i] >= v
     * i是当前需要处理的 变动着的
     * v所在的初始化就是 arr[left]
     *
     * @param arr 数组
     * @param left 左区间
     * @param right 右区间
     * @param <E> 返回泛型
     * @return int 返回那个中间的index是多少
     */
    private static <E extends Comparable<E>> int partition(E[] arr, int left, int right){

        // -----其实这里的逻辑最好用动画来表示一下比较好。语言太贫乏了-----
        // 这里设定一个会移动的j
        int j = left;
        // i从left后面一个开始移动
        for (int i = left + 1; i <= right; i++) {
            // 大于0证明i移动的那个数值要比前面的数字大，说明只要i++就可以
            if (arr[i].compareTo(arr[left]) < 0) {
                j++;
                swap(arr, i , j);
            }
        }
        swap(arr, left, j);
        // -----以上的逻辑有动画就好了-----
        return j;
    }

    // 数组交换
    private static <E> void swap(E[] arr, int i, int j) {
        E t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {

        int n = 5000000;

        // MergeSort, n = 5000000: 1.559710 s
        // QuickSort, n = 5000000: 1.136350 s
        // 还是快速更快呢

        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);

        SortingHelper.sortTest("MergeSort", arr);
        SortingHelper.sortTest("QuickSort", arr2);
    }
}
