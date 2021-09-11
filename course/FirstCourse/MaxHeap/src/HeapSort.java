import java.util.Arrays;

public class HeapSort {

    private HeapSort() {
    }

    public static <E extends Comparable<E>> void sort(E[] data) {
        // 生成一个最大堆数据结构
        // 开始添加到这个最大堆结构里，这样堆顶最大值
        // 每一次都取出来最大值，这样添加到新到数组里。就完成了堆排序
        MaxHeap<E> maxHeap = new MaxHeap<>();
        for (E e : data) {
            maxHeap.add(e);
        }
        // 注意这里是最大堆排序，如果为了完成从小到大，需要从最后一个元素，也就是data.length - 1 从后向前
        for (int i = data.length - 1; i >= 0; i--) {
            data[i] = maxHeap.extractMax();
        }
    }

    // 原地排序版本的
    public static <E extends Comparable<E>> void sort2(E[] data) {
        if (data.length <= 0) return;
        // 对倒数第一个非叶子节点，开始逐层遍历完成
        for (int i = (data.length - 2) / 2; i >= 0; i--) {
            siftDown(data, i, data.length);
        }
        // 都到这里了，就决定已经完成了堆化，然后index[0]就是最大值
        for (int i = data.length - 1; i >= 0; i--) {
            // 交换
            swap(data, 0, i);
            // 交换之后index[0]就不是最大值了，然后进行堆化
            siftDown(data, 0, i);
        }
    }

    // 这里就是对data[0, n]形成的堆，以k为索引，进行siftDown
    private static <E extends Comparable<E>> void siftDown(E[] data, int k, int n) {
        // 循环结束条件 知道没有叶子（左子树没有就肯定是了，因为完全二叉树特性，左都没，右肯定没）
        // 上面就是左孩子都已经越界了，超过了堆最大值，就说明左子树没有了。
        while (2 * k + 1 < n) {
            int j = 2 * k + 1; // 在这个循环里 data[k]和data[j]交换位置
            // j+ 1 本质 右子树 说明有右子树
            // 有右子树 && 右子树大于左子树
            if (j + 1 < n && data[j + 1].compareTo(data[j]) > 0) {
                j++; // j++ 这里就是右子树
            }
            // data[j] 是左右子树的最大值
            if (data[k].compareTo(data[j]) >= 0) {
                break;
            }
            // 如果小，就交换
            swap(data, k, j);
            k = j;
        }
    }

    private static <E extends Comparable<E>> void swap(E[] data, int i, int j) {
        E t = data[i];
        data[i] = data[j];
        data[j] = t;
    }

    public static void main(String[] args) {

        int n = 1000000;
        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        Integer[] arr3 = Arrays.copyOf(arr, arr.length);
        Integer[] arr4 = Arrays.copyOf(arr, arr.length);
        Integer[] arr5 = Arrays.copyOf(arr, arr.length);

        SortingHelper.sortTest("MergeSort", arr);
        SortingHelper.sortTest("QuickSort2Ways", arr2);
        SortingHelper.sortTest("QuickSort3Ways", arr3);
        SortingHelper.sortTest("HeapSort", arr4);
        SortingHelper.sortTest("HeapSort2", arr5);

    }
}
