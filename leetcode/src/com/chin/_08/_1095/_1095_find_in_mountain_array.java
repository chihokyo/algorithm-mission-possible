package com.chin._08._1095;

public class _1095_find_in_mountain_array {
    /**
     * // This is MountainArray's API interface.
     * // You should not implement it, or speculate about its implementation
     * interface MountainArray {
     * public int get(int index) {}
     * public int length() {}
     * }
     */
    interface MountainArray {

    }

    public int findInMountainArray(int target, MountainArray mountainArr) {
        // 1. 找到峰顶的index
        int peakIndex = searchPeakIndex(mountainArr);
        // 2. 先对前半部分使用二分法进行查找
        int index = binarySearchFrontPart(mountainArr, 0, peakIndex, target);
        if (index != -1) {
            return index;
        }
        // 3. 找不到再对后半部分进行查找
        return binarySearchLatterPart(mountainArr, peakIndex, mountainArr.length() - 1, target);
    }

    // 找到峰顶的index
    private int searchPeakIndex(MountainArray mountainArr) {
        int left = 0, right = mountainArr.length() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (mountainArr.get(mid) < mountainArr.get(mid + 1)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // 先对前半部分使用二分法进行查找
    private int binarySearchFrontPart(MountainArray mountainArr, int left, int peakIndex, int target) {
        while (left < peakIndex) {
            int mid = left + (peakIndex - left) / 2;
            if (target > mountainArr.get(mid)) {
                left = mid + 1;
            } else {
                peakIndex = mid;
            }
        }

        if (mountainArr.get(left) == target) return left;
        return -1;
    }

    private int binarySearchLatterPart(MountainArray mountainArr, int peakIndex, int right, int target) {
        while (peakIndex < right) {
            int mid = peakIndex + (right - peakIndex) / 2;
            // 这里第一次写错了，因为忘记后面的是降序的！！！一定不能记错
            // if (target > mountainArr.get(mid))
            if (target < mountainArr.get(mid)) {
                peakIndex = mid + 1;
            } else {
                right = mid;
            }
        }

        if (mountainArr.get(peakIndex) == target) return peakIndex;
        return -1;
    }
}
