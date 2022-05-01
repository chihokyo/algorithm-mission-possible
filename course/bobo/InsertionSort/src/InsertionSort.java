import java.util.Arrays;

public class InsertionSort {
    private InsertionSort() {
    }

    public static <E extends Comparable<E>> void sort(E[] arr) {
        // 方法1
        // 这里第一层是循环所有数字
        for (int i = 0; i < arr.length; i++) {
            // 这里循环的是默认有一个j，这个i=j，j从i这个位置一直向前进行比较
//            for (int j = i; j - 1 >= 0 ; j--) {
//                // 如果后面一个反而比前面一个小的话 就交换
//                if (arr[j].compareTo(arr[j - 1]) < 0) {
//                    swap(arr, j - 1, j);
//                } else {
//                    break;
//                }
//            }

            // 方法2
            // 可以看出来在for里面就一个if进行判断，那么可以直接缩写成这样
            for (int j = i; j - 1 >= 0 && arr[j].compareTo(arr[j - 1]) < 0; j--) {
                swap(arr, j - 1, j);
            }

        }

    }


    public static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 插入排序的方法2
     * @param arr 目标数组
     * @param <E> 实现的排序方法
     */
    public static <E extends Comparable<E>> void sort2(E[] arr) {
        for (int i = 0; i < arr.length; i++) {
            E target = arr[i];
            // 这个j就是一个暂存区，存放的是最后target该在的index
            // 在外面赋值 因为最后要用到 如果在for里声明的话就是局部变量了
            int j;
            for (j = i; j - 1 >= 0 && target.compareTo(arr[j - 1]) < 0; j--) {
                // 前面元素平移到了后面
                arr[j] = arr[j - 1];
            }
            // 这个时候相当于把target直接就赋值给了该在的地方
            arr[j] = target;
        }
    }

    public static void main(String[] args) {
        int[] dataSize = {10000, 100000};
        for (int data : dataSize) {
            System.out.println("Random Array : ");
            Integer[] arr = ArrayGeneraor.generateRandomArray(data, data);
            Integer[] arr2 = Arrays.copyOf(arr, arr.length);
            SortingHelper.sortTest("InsertionSort", arr);
            SortingHelper.sortTest("SelectionSort", arr2);

            // 测试结果发现方法2更合适，因为交换这个有可能有寻址操作所以性能有影响

            System.out.println();

            System.out.println("Ordered Array : ");
            arr = ArrayGeneraor.generateOrderArray(data);
            arr2 = Arrays.copyOf(arr, arr.length);
            SortingHelper.sortTest("InsertionSort", arr);
            SortingHelper.sortTest("SelectionSort", arr2);

            System.out.println();
        }

    }
}
