import java.util.Arrays;
import java.util.Random;

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
        // 解决基准点是left而不是随机值的问题
        // 第二版 想要生成一个随机值[left,right]
        // 因为nextInt里面的数字是不包含的 所以要 + 1
        int p = left + (new Random()).nextInt(right - left + 1);
        // 这里就是交换left和p，保证了下面的j其实不是第一个，而是上面生成的那个p的随机
        swap(arr, left, p);

        // arr[left+1,j] < v ;arr[j+1,i] >= v
        // 因为这里你会发现>=v 都是可以的。所以就是>=v
        // 首先要有2指针 i j  i要比j大1，比较的时候为什么要跟arr[left]比，因为left是不动的哦
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

    public static <E extends Comparable<E>> void sort2ways(E[] arr) {
        sort2ways(arr, 0, arr.length - 1);
    }

    public static <E extends Comparable<E>> void sort2ways(E[] arr, int left, int right) {
        // 这里递归终止条件就是左边大于右边
        if (left >= right) return;
        int p = partition2(arr, left, right);
        // 上面结束之后arr[left, p-1] < arr[p];arr[p+1,right] >= arr[p]的
        sort(arr, left, p - 1);
        sort(arr, p + 1, right);
    }

    /**
     * 双路原地排序
     *
     * @param arr   数组
     * @param left  左区间
     * @param right 右区间
     * @param <E>   返回泛型
     * @return int 返回基准点pivot所在的index位置
     */
    private static <E extends Comparable<E>> int partition2(E[] arr, int left, int right) {
        // 解决基准点是left而不是随机值的问题
        // 第二版 想要生成一个随机值[left,right]
        // 因为nextInt里面的数字是不包含的 所以要 + 1
        int p = left + (new Random()).nextInt(right - left + 1);
        // 这里就是交换left和p，保证了下面的j其实不是第一个，而是上面生成的那个p的随机
        swap(arr, left, p);

        // 新的循环不变量
        // arr[left+1,i-1] <= v;arr[j+1,right]>=v
        // 这2个指针，一个指针是从left+1开始，一个是从最后面开始。
        int i = left + 1, j = right;

        while (true) {
            // i<=j 证明还有未遍历的 <0 证明要继续，因为>=0就要停住了
            while (i <= j && arr[i].compareTo(arr[left]) < 0) {
                i++;
            }
            // j >= i 证明还有未遍历的 这里j因为是从右向左进行遍历，所以要求必须要>0
            // 小于就终止了
            while (j >= i && arr[j].compareTo(arr[left]) > 0) {
                j--;
            }
            // 为什么i要>=j，而不是i>j。i=j 证明同一个元素
            if (i >= j) break;
            // 上面2个都停住了，证明都遇到了真命天子。需要交换+向前走
            swap(arr, i, j);
            i++;
            j--;
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
        // Integer[] arr3 = ArrayGenerator.generateOrderArray(n);
        // Integer[] arr4 = Arrays.copyOf(arr3, arr3.length);
        //
        // SortingHelper.sortTest("MergeSort", arr3);
        // SortingHelper.sortTest("QuickSort", arr4); // 栈溢出 解决之后，就不会栈溢出了


        // 下面测试的是双路有序无序

        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);


        System.out.println("Random Array");
        SortingHelper.sortTest("QuickSort", arr);
        SortingHelper.sortTest("QuickSort2Ways", arr2);
        System.out.println();

        arr = ArrayGenerator.generateOrderArray(n);
        arr2 = Arrays.copyOf(arr, arr.length);

        System.out.println("Ordered Array");
        SortingHelper.sortTest("QuickSort", arr);
        SortingHelper.sortTest("QuickSort2Ways", arr2);
        System.out.println();

        arr = ArrayGenerator.generateRandomArray(n, 1);
        arr2 = Arrays.copyOf(arr, arr.length);

        System.out.println("Same Value Array");
        // SortingHelper.sortTest("QuickSort", arr);
        SortingHelper.sortTest("QuickSort2Ways", arr2);
    }

}
