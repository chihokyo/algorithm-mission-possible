package com.chi.binarysearch;

/**
 * 一直以来都是升序的，现在写一个降序的
 */
public class _02_search {
    public static void main(String[] args) {
        int array[] = {40, 30, 20, 17, 5, 4, 1};
        int x = 1;
        int result = binarySearch(array, x); // 迭代
        if (result == -1) {
            System.out.println("元素不存在");
        } else {
            System.out.println("元素存在 index: " + result);
        }
    }

    /**
     * 降序排列找元素
     *
     * @return
     */
    private static int binarySearch(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                return mid;
            // 除了这里有变化 改了一个符号 其他地方都没变
            } else if (array[mid] < target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }

        }
        return -1;
    }


}
