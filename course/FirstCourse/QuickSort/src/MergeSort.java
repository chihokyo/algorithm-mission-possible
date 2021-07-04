import java.util.Arrays;

public class MergeSort {

    private MergeSort() {
    }

    // 从顶向下
    public static <E extends Comparable<E>> void sort(E[] arr) {
        // 直接调用下面的重载方法
        // 这样就是前面给的你的是arr.sort()
        // 其实你调用的是下面的重载方法 因为归并是需要左右区间index
        sort(arr, 0, arr.length - 1);
    }

    public static <E extends Comparable<E>> void sort(E[] arr, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        sort(arr, left, mid);
        // 为什么上面的不用+1 这里需要呢，因为我们默认的是取得左边的
        sort(arr, mid + 1, right);
        //上面的方法过来之后其实左右的元素是一种完全拆分的状态
        // 这里才是合并
        merge(arr, left, mid, right);
    }

    public static <E extends Comparable<E>> void sort2(E[] arr) {
        // 直接调用下面的重载方法
        // 这样就是前面给的你的是arr.sort()
        // 其实你调用的是下面的重载方法 因为归并是需要左右区间index
        sort2(arr, 0, arr.length - 1);
    }

    public static <E extends Comparable<E>> void sort2(E[] arr, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        sort2(arr, left, mid);
        // 为什么上面的不用+1 这里需要呢，因为我们默认的是取得左边的
        sort2(arr, mid + 1, right);
        //上面的方法过来之后其实左右的元素是一种完全拆分的状态
        // 这里才是合并

        // 优化 合并2个有序区间 arr[left, mid] arr[mid + 1, right]
        // 当前面一个比后面一个大 才进行重新扫描 复杂度变成了O(n)
        if (arr[mid].compareTo(arr[mid + 1]) > 0) {
            merge(arr, left, mid, right);
        }

    }

    public static <E extends Comparable<E>> void sort3(E[] arr) {
        sort3(arr, 0, arr.length - 1);
    }

    public static <E extends Comparable<E>> void sort3(E[] arr, int left, int right) {

        // if(left >= right) return;
        // 对于一个有序的数组来说插入排序法效率更高
        // 对于一个数量少的数组来说，有序的概率也会更高
        // 证明数组数量在16个以下
        // ※根据语言不同可能没用，尤其是高级语言的JS PHP PYTHON等等
        if (right - left <= 15) {
            // 使用插入排序
            insertionSort(arr, left, right);
            // 必须要return回去，不然还要接下来执行下面的代码了。
            return;
        }
        int mid = left + (right - left) / 2;

        sort3(arr, left, mid);
        sort3(arr, mid + 1, right);

        if (arr[mid].compareTo(arr[mid + 1]) > 0) {
            merge(arr, left, mid, right);
        }

    }

    public static <E extends Comparable<E>> void sort4(E[] arr) {
        // 不要在merge里面新建，提前新建一个临时数组
        E[] temp = Arrays.copyOf(arr, arr.length);
        sort4(arr, 0, arr.length - 1, temp);
    }

    public static <E extends Comparable<E>> void sort4(E[] arr, int left, int right, E[] temp) {

        if (left >= right) return;

        int mid = (left + right) / 2;

        sort4(arr, left, mid, temp);
        sort4(arr, mid + 1, right, temp);

        if (arr[mid].compareTo(arr[mid + 1]) > 0) {
            merge2(arr, left, mid, right, temp);
        }

    }

    private static <E extends Comparable<E>> void insertionSort(E[] arr, int left, int right) {
        for (int i = left; i <= right; i++) {
            // 将arr[i] 放到合适的位置，也就是随便抽出来的这个放到合适的位置
            E t = arr[i];
            int j;
            for (j = i; j - 1 >= left && t.compareTo(arr[j - 1]) < 0; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = t;
        }
    }

    /**
     * 合并2个有序区间 arr[left, mid] arr[mid + 1, right]
     *
     * @param arr   原始数组
     * @param left  左边
     * @param mid   中间
     * @param right 右边
     * @param <E>   泛型
     */
    private static <E extends Comparable<E>> void merge(E[] arr, int left, int mid, int right) {
        // 因为这一个方法是生成的数组一个左闭右开的，如果需要完全复制，最右需要+1
        E[] temp = Arrays.copyOfRange(arr, left, right + 1);
        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            // 左边已循环 越界
            if (i > mid) {
                // 为什么要 - left 因为你复制的temp是从0开始的
                // 而真正merge传过来的数组arr可能不是从0开始的
                // 这个时候有一个偏移量，这个偏移量就是left
                // 也就是说temp[0] arr[left]
                // 也就是说temp[1] arr[left + 1] 这种感觉
                // 只有这样才能顺利的把j真正指向的元素给了k
                arr[k] = temp[j - left];
                j++;
                // 右边已循环 越界
            } else if (j > right) {
                arr[k] = temp[i - left];
                i++;
                // 左边的值小于右边的值 取左边 向前进
            } else if (temp[i - left].compareTo(temp[j - left]) <= 0) {
                arr[k] = temp[i - left];
                i++;
                // 右边的值小于左边的值 取右边 向前进
            } else {
                arr[k] = temp[j - left];
                j++;
            }
        }
    }

    /**
     * 合并2个有序区间 arr[left, mid] arr[mid + 1, right]
     *
     * @param arr   原始数组
     * @param left  左边
     * @param mid   中间
     * @param right 右边
     * @param temp  临时数组
     * @param <E>   泛型
     */
    private static <E extends Comparable<E>> void merge2(E[] arr, int left, int mid, int right, E[] temp) {
        // 这个方法就是从内存中完全复制一份 不需要开辟空间 但是要新建空间
        // 这样的话就没有任何偏移量
        System.arraycopy(arr, left, temp, left, right - left + 1);
        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            // 左边已循环 越界
            if (i > mid) {
                // 为什么要 - left 因为你复制的temp是从0开始的
                // 而真正merge传过来的数组arr可能不是从0开始的
                // 这个时候有一个偏移量，这个偏移量就是left
                // 也就是说temp[0] arr[left]
                // 也就是说temp[1] arr[left + 1] 这种感觉
                // 只有这样才能顺利的把j真正指向的元素给了k
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
        int n = 5000000;
        // 无序的时候差距并不是特别大
        // MergeSort, n = 1000000: 0.303964 s
        // MergeSort2, n = 1000000: 0.205886 s

        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        // Integer[] arr2 =  Arrays.copyOf(arr, arr.length);
        // SortingHelper.sortTest("MergeSort", arr);
        // SortingHelper.sortTest("MergeSort2", arr2);

        // 有序的差距是很大的

        // MergeSort, n = 1000000: 0.247782 s
        // MergeSort2, n = 1000000: 0.084870 s

        // Integer[] arr =  ArrayGenerator.generateOrderArray(n);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        // // Integer[] arr3 =  Arrays.copyOf(arr, arr.length);
        Integer[] arr4 = Arrays.copyOf(arr, arr.length);
        // SortingHelper.sortTest("MergeSort", arr);
        SortingHelper.sortTest("MergeSort2", arr2);
        // SortingHelper.sortTest("MergeSort3", arr3);
        SortingHelper.sortTest("MergeSort4", arr4);

    }

}
