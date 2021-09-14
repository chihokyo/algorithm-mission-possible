import java.util.Arrays;

// 这个class实现的是每一次循环完之后确定一个最小值
public class BubbleSortFront {

    private BubbleSortFront() {

    }

    public static <E extends Comparable<E>> void sort(E[] data) {
        // 外循环，只记录循环多少次
        for (int i = 0; i + 1 < data.length; i++) {
            for (int j = data.length - 1; j > i; j--) {
                if (data[j - 1].compareTo(data[j]) > 0) {
                    swap(data, j - 1, j);
                }
            }
        }
    }

    // 优化1
    public static <E extends Comparable<E>> void sort2(E[] data) {
        for (int i = 0; i + 1 < data.length; i++) {
            boolean isSwapped = false;
            for (int j = data.length - 1; j > i; j--) {
                if (data[j - 1].compareTo(data[j]) > 0) {
                    swap(data, j - 1, j);
                    isSwapped = true;
                }
            }
            if (!isSwapped) break;
        }
    }

    // 优化2
    public static <E extends Comparable<E>> void sort3(E[] data) {
        for (int i = 0; i + 1 < data.length; ) {
            int lashSwappedIndex = data.length - 1; // 初始化就是index为最后
            for (int j = data.length - 1; j > i; j--) {
                if (data[j - 1].compareTo(data[j]) > 0) {
                    swap(data, j - 1, j);
                    lashSwappedIndex = j - 1;
                }
            }
            i = lashSwappedIndex + 1; // 因为[0, lashSwappedIndex] 是已经排序OK的
        }
    }

    private static <E> void swap(E[] arr, int i, int j) {
        E e = arr[i];
        arr[i] = arr[j];
        arr[j] = e;
    }

    public static void main(String[] args) {
        System.out.println("Random Arrays");
        int m = 10000;
        Integer[] arr = ArrayGenerator.generateRandomArray(m, m);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        Integer[] arr3 = Arrays.copyOf(arr, arr.length);
        SortingHelper.sortTest("BubbleSortFront", arr);
        SortingHelper.sortTest("BubbleSortFront2", arr2);
        SortingHelper.sortTest("BubbleSortFront3", arr3);

        System.out.println("Ordered Arrays");

        int n = 10000;
        Integer[] aarr = ArrayGenerator.generateOrderArray(n);
        Integer[] aarr2 = Arrays.copyOf(aarr, aarr.length);
        Integer[] aarr3 = Arrays.copyOf(aarr, aarr.length);
        SortingHelper.sortTest("BubbleSortFront", aarr);
        SortingHelper.sortTest("BubbleSortFront2", aarr2);
        SortingHelper.sortTest("BubbleSortFront3", aarr3);

    }
}
