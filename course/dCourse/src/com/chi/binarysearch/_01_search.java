package com.chi.binarysearch;

/**
 * 最普通的二分查找
 */
public class _01_search {
    public static void main(String[] args) {
        int array[] = {2, 3, 4, 10, 40};
        int x = 3;
        // int result = binarySearch2(array, x); // 迭代
        int result = binarySearchRec(array, 0, array.length - 1, x); // 递归
        if (result == -1) {
            System.out.println("元素不存在");
        } else {
            System.out.println("元素存在 index: " + result);
        }
    }

    /**
     * 迭代写二分查找
     * 也是最常见的写法
     */
    private static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        // left<=right就一直找
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    // 使用for写的
    private static int binarySearch2(int[] arr, int target) {
        for (int left = 0, right = arr.length - 1; left <= right; ) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 递归写二分查找
     */
    private static int binarySearchRec(int[] arr, int left, int right, int target) {
        if (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                return binarySearchRec(arr, left, mid - 1, target);
            } else {
                return binarySearchRec(arr, mid + 1, right, target);
            }
        }
        return -1;
    }
}
