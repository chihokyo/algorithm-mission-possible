package com.chin._02._941;

public class _941_valid_mountain_array {
    // 没有考虑边界的代码实现
    public boolean validMountainArray1(int[] arr) {
        int n = arr.length;
        int i = 0;
        // 1.找最高点 → 这个时候i指向的值就是最高点
        // i = n - 1 的话，数组会越界
        while (i < n - 1 && arr[i] < arr[i + 1]) i++;
        // 2.检查最高点之后是否递减
        while (i < n - 1 && arr[i] > arr[i + 1]) i++;
        // 3.如果i指向最后1个元素，说明到头一直递减 那么就是true
        return i == n - 1;
    }

    // 真正的实现
    public boolean validMountainArray(int[] arr) {
        int n = arr.length;
        int i = 0;
        // 1.找最高点 → 这个时候i指向的值就是最高点
        // i = n - 1 的话，数组会越界
        while (i < n - 1 && arr[i] < arr[i + 1]) i++;
        // !!2.进行判断是否是第一个or最后一个元素 是的话就不符合了
        if (i == 0 || i == n - 1) return false;
        // 3.检查最高点之后是否递减
        while (i < n - 1 && arr[i] > arr[i + 1]) i++;
        // 4.如果i指向最后1个元素，说明到头一直递减 那么就是true
        return i == n - 1;
    }

    // 这里有个双指针?的做法可以试一试
    public boolean validMountainArray2(int[] arr) {
        int n = arr.length;
        int left = 0;
        int right = n - 1;
        // 边界条件要注意
        // left < n - 1 also OK
        while (left + 1 < n && arr[left] < arr[left + 1]) left++;
        while (right > 0 && arr[right] < arr[right - 1]) right--;
        // [0,3,2,1] 错误的 return left == right;
        // [0,1,2,3,4,5,6,7,8,9] 错误 return left > 0 && right < n && left == right;
        return left > 0 && right < n - 1 && left == right;
    }


}
