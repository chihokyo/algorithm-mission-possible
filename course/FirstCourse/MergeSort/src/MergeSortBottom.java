import java.util.Arrays;

public class MergeSortBottom {

    private MergeSortBottom() {
    }

    /**
     * 自底向上进行归并
     *
     * @param arr 数组
     * @param <E> 返回泛型
     */
    public static <E extends Comparable<E>> void sortBU(E[] arr) {

        E[] temp = Arrays.copyOf(arr, arr.length);
        int n = arr.length;
        // 遍历合并的区间长度是多少 1 2 4 8 16...
        for (int sz = 1; sz < n; sz += sz) {
            // 合并2个的区间的起始位置
            // [i, i+sz-1] & [i+sz, i+sz+sz-1]
            for (int i = 0; i + sz < n; i += sz + sz) {
                if (arr[i + sz - 1].compareTo(arr[i + sz]) > 0) {
                    // i + sz + sz -1 可能越界
                    merge(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1), temp);
                }
            }
        }
    }

    private static <E extends Comparable<E>> void merge(E[] arr, int left, int mid, int right, E[] temp) {

        System.arraycopy(arr, left, temp, left, right - left + 1);

        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            // 左边已循环 越界
            if (i > mid) {
                arr[k] = temp[j];
                j++;
                // 右边已循环 越界
            } else if (j > right) {
                arr[k] = temp[i];
                i++;
                // 左边的值小于右边的值 取左边 向前进
            } else if (temp[i].compareTo(temp[j]) <= 0) {
                arr[k] = temp[i];
                i++;
                // 右边的值小于左边的值 取右边 向前进
            } else {
                arr[k] = temp[j];
                j++;
            }
        }

    }

    public static void main(String[] args) {
        // int n = 5000000;
        //
        // Integer[] arr =  ArrayGenerator.generateRandomArray(n, n);
        Integer[] arr = {1, 8, 4, 0, 6, 2};

        SortingHelper.sortTest("MergeSortBottom", arr);

    }

}
