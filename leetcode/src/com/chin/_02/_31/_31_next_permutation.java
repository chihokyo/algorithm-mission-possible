package com.chin._02._31;

public class _31_next_permutation {
    public void nextPermutation(int[] nums) {
        // ① 从倒数第2个开始找[452631]的话就是从3
        int i = nums.length - 2;
        // ② 找到靠右【较小数】 俩俩相比,3和1比，3比1大向前走
        // 最后发现2和6的时候i在2的位置，2小于6，所以2是较小数
        while (i >= 0 && nums[i] >= nums[i + 1]) i--;

        // ③ 开始找【较大数】
        // 这个if (i >= 0) 不写的话 [1]只有1个的情况下数组会越界
        if (i >= 0) {
            // 从最后1个开始找，要找j所在的数字大于i所在的位置
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) j--;
            // ④ 找到之后交换 也就是2和3交换[453621]
            swap(nums, i, j);
        }
        // ⑤ 然后反转i之后的[453621]→621反转[453126]
        reverse(nums, i + 1);
    }


    private void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
