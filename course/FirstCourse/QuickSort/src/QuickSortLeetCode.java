import java.util.Arrays;
import java.util.Random;

public class QuickSortLeetCode {

    private QuickSortLeetCode() {
    }

    /**
     * Q1:给定一个包含红色、白色和蓝色，一共n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，
     * 并按照红色、白色、蓝色顺序排列。
     * 此题中，我们使用整数 0、1 和 2 分别表示红色、白色和蓝色。
     * 思路，这一题其实就是以一个已知的基准点1，然后进行三路排序。
     * https://leetcode-cn.com/problems/sort-colors/
     *
     * @param nums 数组
     */
    public static void sortColors(int[] nums) {

        int zero = -1, i = 0, two = nums.length;

        while (i < two) {
            if (nums[i] == 0) {
                zero++;
                swap(nums, zero, i);
                i++;
            } else if (nums[i] == 2) {
                two--;
                swap(nums, two, i);
            } else {
                i++;
            }
        }
    }

    /**
     * Q2:数组中的第K个最大元素
     * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
     * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     * https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
     *
     * @param nums 数组
     * @param k    k大
     * @return 返回k所在的index
     */
    public static int findKthLargest(int[] nums, int k) {
        Random rnd = new Random();
        return selectK(nums, 0, nums.length - 1, nums.length - k, rnd);
    }

    /**
     * Q3:输入整数数组 arr ，找出其中最小的 k 个数。
     * 例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
     *
     * @param arr 数组
     * @param k   最小的K
     * @return 最小的K数组
     */
    public static int[] getLeastNumbers(int[] arr, int k) {
        if (k == 0) return new int[0];
        Random rnd = new Random();
        // 注意点1 这里要注意下面的k是最小的k，如果要找个数为k,可以看出来selectK得出来的是index，而不是个数
        // 所以需要k-1
        selectK(arr, 0, arr.length - 1, k - 1, rnd);
        return Arrays.copyOf(arr, k);
    }

    /**
     * selectK大，第1大，就是最大的老大。第2大，就是老二。
     *
     * @param nums  数组
     * @param left  左区间
     * @param right 有区间
     * @param k     k 大
     * @param rnd   随机数
     * @return 返回需要的那个k所在的index
     */
    public static int selectK(int[] nums, int left, int right, int k, Random rnd) {
        int p = partition(nums, left, right, rnd);
        if (k == p) return nums[p];
        if (k > p) {
            return selectK(nums, p + 1, right, k, rnd);
        } else {
            return selectK(nums, left, p - 1, k, rnd);
        }
    }

    /**
     * 双路排序
     *
     * @param nums  数组
     * @param left  左区间
     * @param right 右区间
     * @param rnd   随机数
     * @return int 返回基准点index
     */
    private static int partition(int[] nums, int left, int right, Random rnd) {
        int p = left + rnd.nextInt(right - left + 1);
        swap(nums, left, p);
        int i = left + 1, j = right;
        while (true) {
            while (i <= j && nums[i] < nums[left]) {
                i++;
            }
            while (j >= i && nums[j] > nums[left]) {
                j--;
            }
            if (i >= j) break;
            swap(nums, i, j);
            i++;
            j--;
        }

        swap(nums, left, j);
        return j;
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }

    public static void main(String[] args) {
        // 测试题1 颜色分类
        System.out.println("颜色分类");
        int[] nums = {2, 0, 2, 1, 1, 0};
        QuickSortLeetCode.sortColors(nums);
        System.out.println(Arrays.toString(nums));

        // 测试题2 数组中的第K个最大元素
        System.out.println("数组中的第K个最大元素");
        int[] nums2 = {6, 0, 2, 4, 3, 9};
        System.out.println(Arrays.toString(nums2));
        int p = QuickSortLeetCode.findKthLargest(nums2, 3);
        System.out.println(Arrays.toString(nums2)); // [0, 2, 3, 4, 6, 9]
        System.out.println(p); // 第3大的就是4

        // 测试题3 数组中的第K个最大元素
        System.out.println("最小的k个数");
        int[] nums3 = {3, 2, 1, 6, 0, 8, 9};
        System.out.println(Arrays.toString(nums3));
        int[] result = QuickSortLeetCode.getLeastNumbers(nums3, 3);
        System.out.println(Arrays.toString(nums3)); // [0, 1, 2, 3, 6, 8, 9]
        System.out.println(Arrays.toString(result)); // [0, 1, 2]
    }
}
