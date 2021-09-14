import java.util.Arrays;

public class BubbleSort {
    /**
     * 冒泡排序
     * 维持循环不变量一定要清除
     *
     * @param data 数据
     * @param <E>  泛型
     */
    public static <E extends Comparable<E>> void sort(E[] data) {
        // 这里为什么是+1大于长度，因为+1意味着相邻的那个，如果没有+1直接就飞出去了
        for (int i = 0; i + 1 < data.length; i++) {
            // arr[n-i, n) 已经OK
            // 通过冒泡 arr[n-i-1] 放上合适的元素
            // for (int j = 0; j + 1 <= data.length - i - 1; j++)
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(data, j, j + 1);
                }
            }
        }
    }

    /**
     * 冒泡排序-小优化
     * 如果没有发生交换，说明已经排序
     *
     * @param data 数据
     * @param <E>  泛型
     */
    public static <E extends Comparable<E>> void sort2(E[] data) {
        // 这里为什么是+1大于长度，因为+1意味着相邻的那个，如果没有+1直接就飞出去了
        for (int i = 0; i + 1 < data.length; i++) {
            // arr[n-i, n) 已经OK
            // 通过冒泡 arr[n-i-1] 放上合适的元素
            // for (int j = 0; j + 1 <= data.length - i - 1; j++)
            boolean isSwapped = false;
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(data, j, j + 1);
                    isSwapped = true;
                }
            }
            // isSwapped默认是没有交换，如果这里走break，说明没有任何交换
            if (!isSwapped) break;
        }
    }

    /**
     * 冒泡排序-小优化2
     * 不仅仅记录是否交换，还要记录交换的位置
     *
     * @param data 数据
     * @param <E>  泛型
     */
    public static <E extends Comparable<E>> void sort3(E[] data) {
        // 这里为什么是+1大于长度，因为+1意味着相邻的那个，如果没有+1直接就飞出去了
        for (int i = 0; i + 1 < data.length; ) {
            // arr[n-i, n) 已经OK
            // 通过冒泡 arr[n-i-1] 放上合适的元素
            // for (int j = 0; j + 1 <= data.length - i - 1; j++)
            int lastSwappedIndex = 0;
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(data, j, j + 1);
                    // 后一个的位置赋值给 lastSwappedIndex
                    lastSwappedIndex = j + 1;
                }
            }
            // 因为通过下面的 data.length - lastSwappedIndex; 说明lastSwappedIndex为0的话
            // 那么data.length - lastSwappedIndex;就是data.length
            // 肯定是不符合 for (int i = 0; i + 1 < data.length;) 这个条件的，自然可以不用了
            // if (lastSwappedIndex == 0) break;
            // 优化关键
            // i表示有多少个排序好 [lastSwappedIndex，n]都是排好序的
            i = data.length - lastSwappedIndex;
        }
    }

    private static <E> void swap(E[] arr, int i, int j) {
        E e = arr[i];
        arr[i] = arr[j];
        arr[j] = e;
    }

    public static void main(String[] args) {

        // int n = 10000;
        // Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        // Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        // Integer[] arr5 = Arrays.copyOf(arr, arr.length);
        // SortingHelper.sortTest("BubbleSort", arr);
        // SortingHelper.sortTest("BubbleSort2", arr2);
        // SortingHelper.sortTest("BubbleSort3", arr5);

        int m = 100000;
        Integer[] arr3 = ArrayGenerator.generateOrderArray(m);
        Integer[] arr4 = Arrays.copyOf(arr3, arr3.length);
        Integer[] arr6 = Arrays.copyOf(arr3, arr3.length);
        SortingHelper.sortTest("BubbleSort", arr3);
        SortingHelper.sortTest("BubbleSort2", arr4);
        SortingHelper.sortTest("BubbleSort3", arr6);
        // BubbleSort, n = 100000: 31.928892 s
        // BubbleSort2, n = 100000: 32.005186 s
        // BubbleSort, n = 100000: 7.445845 s
        // BubbleSort2, n = 100000: 0.000247 s
        // 为什么无序数组优化之后还慢了？
        // 因为优化之后增加了很多判断还有赋值，都会影响效率
    }
}
