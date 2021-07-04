public class SortingHelper {

    private SortingHelper() {
    }

    /**
     * 测试是否排序 从小到大
     *
     * @param arr 需要测试的数组
     * @param <E> 必须实现Comparable
     * @return 返回 boolean
     */
    public static <E extends Comparable<E>> boolean isSorted(E[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1].compareTo(arr[i]) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 测试排序的各种详情
     *
     * @param sortName 排序方法
     * @param arr      测试数组
     */
    public static <E extends Comparable<E>> void sortTest(String sortName, E[] arr) {

        long startTime = System.nanoTime();

        switch (sortName) {
            case "MergeSort" -> MergeSort.sort2(arr);
            case "QuickSort" -> QuickSort.sort(arr);
        }

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1000000000.0;

        if (!SortingHelper.isSorted(arr)) {
            throw new RuntimeException(sortName + "failed!!");
        }
        System.out.printf("%s, n = %d: %f s \n", sortName, arr.length, time);


    }
}
