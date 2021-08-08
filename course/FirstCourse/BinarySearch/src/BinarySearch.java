public class BinarySearch {
    private BinarySearch() {

    }

    public static <E extends Comparable<E>> int search(E[] data, E target) {
        return search(data, 0, data.length - 1, target);
    }

    /**
     * 递归实现二分法
     *
     * @param data   数组
     * @param left   左区间
     * @param right  有区间
     * @param target 目标
     * @param <E>    泛型（任何类型都可以）
     * @return int 返回目标所在的index
     */
    private static <E extends Comparable<E>> int search(E[] data, int left, int right, E target) {
        // 这里不能是= 因为在只有1的时候[5],5,这个时候直接就-1了！
        if (left > right) return -1;
        // 防止栈溢出的取中
        int mid = left + (right - left) / 2;
        if (data[mid].compareTo(target) == 0) {
            return mid;
        }
        if (data[mid].compareTo(target) < 0) {
            return search(data, mid + 1, right, target);
        }
        return search(data, left, mid - 1, target);
    }

    public static void main(String[] args) {
        Integer[] nums = {0, 1, 3, 4, 5, 9, 12, 13};
        Integer[] nums2 = {5};
        int result = BinarySearch.search(nums, 9);
        int result2 = BinarySearch.search(nums2, 5);
        System.out.println(result);
        System.out.println(result2);
    }
}
