package com.chi.binarysearch;

public class _03_search {
    public static void main(String[] args) {
        int array[] = {1, 1, 2, 3, 5, 8, 8, 8, 9, 9, 10, 14};
        int x = 11;
        // int result = binarySearch1(array, x); // 第一个等于目标
        // int result = binarySearch2(array, x); // 第一个大于等于目标
        // int result = binarySearch3(array, x); // 最后一个等于目标
        int result = binarySearch4(array, x); // 最后一个小于等于目标

        if (result == -1) {
            System.out.println("元素不存在");
        } else {
            System.out.println("元素存在 index: " + result);
        }
    }

    /**
     * 找寻第一个等于目标元素的下标
     *
     * @param array
     * @param target
     * @return
     */
    private static int binarySearch1(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                // 有可能index为0 那么前面是没有元素的 mid-1的话会超出 所以要判断
                if (mid == 0 || array[mid - 1] != target) {
                    return mid;
                } else {
                    // 如果前面还是的话 就继续缩小范围
                    right = mid - 1;
                }
            } else if (array[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 找寻第一个大于等于目标值的元素
     *
     * @return
     */
    private static int binarySearch2(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            // int mid = left + (right - left) / 2;
            // // 等于的时候 也要判断
            // if (array[mid] == target) {
            //     if(mid == 0 || array[mid- 1] < target)return mid;
            //     else right = mid - 1;
            // // 大于的时候 也要判断
            // } else if (array[mid] > target) {
            //     if(mid == 0 || array[mid- 1] < target)return mid;
            //     else right = mid - 1;
            // } else {
            //     left = mid + 1;
            // }

            // 上面的代码可以看出来 >=的时候是一样的逻辑 所以可以合在一起
            int mid = left + (right - left) / 2;
            if (array[mid] >= target) {
                // 记住这里是 array[mid - 1] < target 前一个小于
                if (mid == 0 || array[mid - 1] < target) return mid;
                else right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 找寻最后一个等于目标值的元素
     *
     * @return
     */
    private static int binarySearch3(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                if (mid == array.length - 1 || array[mid + 1] != target) return mid;
                else left = mid + 1;
            } else if (array[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 找寻最后一个小于等于目标值的元素
     *
     * @return
     */
    private static int binarySearch4(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // if (array[mid] == target) {
            //     if (mid == array.length - 1 || array[mid + 1] > target) return mid;
            //     else left = mid + 1;
            // } else if (array[mid] > target) {
            //     right = mid - 1;
            // } else {
            //     if (mid == array.length - 1 || array[mid + 1] > target) return mid;
            //     else left = mid + 1;
            // }
            // 上面的逻辑重合了
            if (array[mid] <= target) {
                if (mid == array.length - 1 || array[mid + 1] > target) return mid;
                else left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
