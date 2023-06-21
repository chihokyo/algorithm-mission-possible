package com.chi.binarysearch;

/**
 * 第2种思路 排除一定不在区间的值
 */
public class _04_leet_27_binaryseach {
    public static void main(String[] args) {
        int array[] = {1, 1, 2, 3, 5, 8, 8, 8, 9, 9, 10, 14};
        int x = 5;
        // int result = binarySearch1(array, x); // 一定不在区间内的值
        int result = binarySearch2(array, x); // 一定不在区间内的值

        if (result == -1) {
            System.out.println("元素不存在");
        } else {
            System.out.println("元素存在 index: " + result);
        }
    }

    /**
     * 思路2 排除不必要的区间
     *
     * @return
     */
    private static int binarySearch1(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (array[mid] >= target) {
                // 要注意这里不是mid-1 这个一定要记住
                // 至于为什么 你看上面的array[mid] >= target 也就是说有可能是等于的
                // 有可能等于的话 你就不能排除掉mid
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        // 走完之后 只剩下 left = right这个选项 那就看看你是不是答案了
        if (array[left] == target) {
            return left;
        } else {
            return -1;
        }
    }

    // 使用另一种判断 向右取整 array[mid] > target
    private static int binarySearch2(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while (left < right) {
            // 这里为了向右取整 要+1
            int mid = left + (right - left + 1) / 2;
            if (array[mid] > target) {
                // 这里记得-1
                right = mid - 1;
            } else {
                // 这里也要修改
                left = mid;
            }
        }
        // 走完之后 只剩下 left = right这个选项 那就看看你是不是答案了
        if (array[left] == target) {
            return left;
        } else {
            return -1;
        }
    }

}
