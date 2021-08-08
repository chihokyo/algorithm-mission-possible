public class BinarySearch {
    private BinarySearch() {

    }

    public static <E extends Comparable<E>> int searchR(E[] data, E target) {
        return searchR(data, 0, data.length - 1, target);
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
    private static <E extends Comparable<E>> int searchR(E[] data, int left, int right, E target) {
        // 这里不能是= 因为在只有1的时候[5],5,这个时候直接就-1了！
        if (left > right) return -1;
        // 防止栈溢出的取中
        int mid = left + (right - left) / 2;
        if (data[mid].compareTo(target) == 0) {
            return mid;
        }
        if (data[mid].compareTo(target) < 0) {
            return searchR(data, mid + 1, right, target);
        }
        return searchR(data, left, mid - 1, target);
    }

    /**
     * 非递归实现二分法
     * @param data 数组
     * @param target 目标
     * @param <E> 返回泛型
     * @return int 返回目标所在的index
     */
    public static <E extends Comparable<E>> int search(E[] data, E target) {
        int left = 0, right = data.length - 1;
        // 说明还有未循环完的，和递归正好想法的条件
        // 循环不变量 data[left, right] 内找target
        while (left <=  right) {
            int mid = left + (right - left) / 2;
            if (data[mid].compareTo(target) == 0) return mid;
            // 小于0 target的值大于mid。想后找。
            if (data[mid].compareTo(target) < 0) {
                left = mid + 1;
            } else {
                // target的值小于mid，向前找。
                right = mid - 1;
            }
        }
        // 全部循环完毕也没有
        return -1;
    }


    public static void main(String[] args) {
        Integer[] nums = {0, 1, 3, 4, 5, 9, 12, 13};
        Integer[] nums2 = {5};
        int result = BinarySearch.searchR(nums, 9);
        int result2 = BinarySearch.searchR(nums2, 5);
        System.out.println(result);
        System.out.println(result2);

        Integer[] nums3 = {0, 1, 3, 4, 5, 9, 12, 13};
        Integer[] nums4 = {5};
        int result3 = BinarySearch.search(nums, 9);
        int result4 = BinarySearch.search(nums2, 5);
        System.out.println(result3);
        System.out.println(result4);
    }
}
