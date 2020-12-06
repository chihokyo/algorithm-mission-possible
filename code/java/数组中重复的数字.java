import java.util.HashSet;
import java.util.Set;

/**
 * 思路1 利用hash表
 */

class Solution {
    public int findRepeatNumber(int[] nums) {
        // 新建一个hash表 存储k-v
        Set<Integer> dic = new HashSet<>();
        // 开始遍历 每一个数字
        for (Integer num : nums) {
            // 哈希表里有了，直接弹出来
            if (dic.contains(num)) {
                return num;
            }
            // 有了就加进去
            dic.add(num);
        }

        return -1;
    }
}


/**
 * 思路2 原地置换。
 * 主要利用的是如果
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1
 * 那么就是说n对应了只会index为n的地方。
 * 那么思路就是重头扫描，遇到了n不是index为n的那么就交换。
 * 如果交换过程中，如果有重复数字，那么就返回true
 */
class Solution {
    public int findRepeatNumber(int[] nums) {
        
        int temp;
        // 开始遍历
        for (int i = 0; i < nums.length; i++) {
            // 只要i和index i 不一样
            while(nums[i] != i) {
                // 如果数字和 现在已有的 index 为 i 所在的 nums 一样的
                // 说明这是重复了，直接弹出来这个数字
                if (nums[i] == nums[nums[i]]) {
                    return nums[i];
                }
                // 如果不是重复，那么就要对这个数字交换到他该有的位置
                // 这个最难理解了。结论就是 通过 temp这个中间 达到 nums[i] 和 i 的 一致性。
                temp = nums[i];
                nums[i] = nums[temp];
                nums[temp] = temp;
            }
        }
        return -1;
    }
}