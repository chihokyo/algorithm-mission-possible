package com.chi.sort;

import static com.chi.sort.Sorter.printArray;
import static com.chi.sort.Sorter.swap;

/**
 * 插入排序
 */
public class _03_Insertion {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 155, 2, 7, 3, 1, 6, 4, 2, 4, 4, 55, 8, 7, 2, -8};

        System.out.println("排序前的数组：");
        printArray(array);

        // insert1(array); // 思路1
        // insert2(array); // 思路2
        // insert3(array); // 思路3
        // insert4(array); // 思路3
        insertPractise(array);

        System.out.println("排序后的数组：");
        printArray(array);
    }

    // 练手的
    private static void insertPractise(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int temp = array[i];
            int j = i;
            while (j > 0 && array[j - 1] > temp){
                array[j] = array[j -1];
                j--;
            }
            array[j] = temp;
        }
    }

    /**
     * 传统交换写法
     *
     * @param array
     */
    public static void insert1(int[] array) {
        // 为什么从1开始 因为第一个默认就是有序的
        for (int i = 1; i < array.length; i++) {
            // 这个j至少要等于1，不能小于等于0，如果等于0 那么j-1 就越界了
            for (int j = i; j > 0; j--) {
                // 小于就交换 这样一步步向前 此时j是i
                if (array[j] < array[j - 1]) {
                    swap(array, j, j - 1);
                } else {
                    break; // 不是的话 此次就跳出
                }
            }
        }
    }

    /**
     * 插入排序2 使用赋值代替交换
     * 不懂为什么赋值可以代替 可以画图
     *
     * @param array
     */
    public static void insert2(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j;
            // 这里记住临时变量先
            // 这个j至少要等于1，不能小于等于0，如果等于0 那么j-1 就越界了
            for (j = i; j > 0; j--) {
                // 小于不用交换,使用赋值替代交换
                if (temp < array[j - 1]) {
                    array[j] = array[j - 1]; // 相当于向后赋值
                } else {
                    break;
                }
            }
            // 走到这个的时候就找打了temp应该在的位置 也就是j的所在的位置
            array[j] = temp;
        }
    }

    // 上面不是赋值吗 下面其实也是和上面一模一样的思路。但是j的赋值不一样，而且是使用while写的
    public static void insert3(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1; // 这里j直接取前一个
            // 既是取的是前一个 也就是说可以等于0 并且拿这个值跟key比
            // key是什么呢？是你现在拿在手里的牌，你想插入到哪里呢
            // 只要key小 就继续向前走
            while (j >= 0 && array[j] > key) {
                // 向前走的意思就是 前面的赋值给后面 假如我们手牌现在拿的是4
                // 54302 赋值向前走就是 55302
                array[j + 1] = array[j];
                j = j - 1;
            }
            // 这个时候3应该就在第一个位置 也就是 45302
            array[j + 1] = key;
        }
    }

    // 使用二分查找法查到位置
    public static void insert4(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int item = array[i];
            int j = i - 1;

            // 其实就是在这里找位置 这里使用了二分法
            int pos = binarySearch(array, item, 0, j);

            while (j >= pos) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = item;
        }
    }

    private static int binarySearch(int array[], int item, int low, int high) {
        if (high <= low) {
            return (item > array[low]) ? (low + 1) : low;
        }

        int mid = (low + high) / 2;

        if (item == array[mid]) {
            return mid + 1;
        }

        if (item > array[mid]) {
            return binarySearch(array, item, mid + 1, high);
        }

        return binarySearch(array, item, low, mid - 1);
    }

}
