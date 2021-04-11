public class SelectionSort {

    private SelectionSort() {
    }

    public static <E extends Comparable<E>> void sort(E[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0 ) {
                    minIndex = j;
                }
            }
            swap(arr, minIndex, i);
        }
    }

    /**
     * 数组内交换数值
     * @param arr 数组
     * @param i 数值1
     * @param j 数值2
     */
    private static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
//        Integer[] arr = {22, 11, 0, -9, 100};
//        SelectionSort.sort(arr);
//        for (int i : arr) {
//            System.out.print(i + " ");
//        }
//        System.out.println();
//
//        Student[] stubs = { new Student("Alice", 100), new Student("Chin", 80), new Student("Tom", 18),
//                new Student("Bob", 55), };
//        SelectionSort.sort(stubs);
//        for (Student student : stubs) {
//            System.out.println(student);
//        }

        int[] dataSize = {10000, 100000};
        for (int n: dataSize ){
            Integer[] arr2 = ArrayGeneraor.generateRandomArray(n, n);
            SortingHelper.sortTest("SelectionSort", arr2);
        }


    }
}
