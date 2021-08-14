import java.util.Arrays;

public class BinarySearchLeet {

    /**
     * [875. 爱吃香蕉的珂珂]
     * (https://leetcode-cn.com/problems/koko-eating-bananas/)
     *
     * @param piles 数组
     * @param h     最大离开
     * @return int  吃多少香蕉
     */
    public static int minEatingSpeed(int[] piles, int h) {
        // 最小吃1个，最大吃数组最大那个
        int left = 1;
        int right = Arrays.stream(piles).max().getAsInt();

        // eatingTime(k) <= H 求小于等于的话，最右可能合法解

        while (left < right) {
            // 这里最终求的是<=h,所以下取整就可以
            int mid = left + (right - left) / 2;
            if (eatingTime(piles, mid) <= h) {
                // 说明等于小于h,说明这个right也有可能是解
                right = mid;
            } else {
                // 每小时吃mid个，还是大于h,说明要多吃，向大的右边走
                left = mid + 1;
            }
        }
        return left;
    }

    // 1小时吃k个，求总共需要多少小时
    private static int eatingTime(int[] piles, int k) {
        int result = 0;
        for (int pile : piles) {
            // 是否可以整除，向上取整 但是当不能整除的时候要加上1个小时
            result += pile / k + (pile % k > 0 ? 1 : 0);
        }
        return result;
    }

    /**
     * 1011. 在 D 天内送达包裹的能力
     * https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days/
     * 本质就是 求 <= days 最小值
     *
     * @param weights 货物载重数组
     * @param days    用时天数
     * @return int 找到最小的单次载重量
     */
    public static int shipWithinDays(int[] weights, int days) {
        // 二分法左右边界，最小值应该是整个货车中最大的重量，也就是数组中最大值。如果传送低于这个值，货物将永远无法运走
        // 最大值，应该是整个货物数组的总和。这样1天就全部拉走
        int left = Arrays.stream(weights).max().getAsInt();
        int right = Arrays.stream(weights).sum();
        while (left < right) {
            int mid = left + (right - left) / 2; // 关于上取整和下取整，可以在下面缩小范围进行判断
            if (days(weights, mid) <= days) {
                // 小于等于days，符合题意，说明用时正好，但我们可以继续找看能不能在小一点也能运走，所以从右找
                right = mid;
            } else {
                // 说明大于days，肯定不符合题意，需要增加载重量
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 计算k为载重量额度需要多少天
     *
     * @param weights 总货物
     * @param k       载重
     * @return int  需要多少天
     */
    private static int days(int[] weights, int k) {
        int cur = 0; // 当天载重量 初始是0
        int res = 0; // 天数 初始0天
        for (int weight : weights) {
            if (cur + weight <= k) {
                cur += weight;
            } else {
                // 超过了说明要重新计算一天
                res++;
                cur = weight; // 并且cur就是当前重量
            }
        }
        // 最后还要做一次 res ++，因为在这里 cur 肯定不为零，还记录着最后一天需要运送的货物重量
        // 最后一天，要加到 res 中，所以 res ++
        // 这里比较难，理解，其实可以用这样的数据走一趟
        // {1,2} k:3 说明1天就可以结束，如果不加res++，那么结果就是0了，显然不符合题意和现实
        res++;
        return res;
    }

    public static void main(String[] args) {
        int[] test1 = {30, 11, 23, 4, 20};
        int r1 = minEatingSpeed(test1, 5);
        System.out.println(r1 == 30); // true

        int[] test2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int r2 = shipWithinDays(test2, 5);
        System.out.println(r2 == 15); // true
    }
}
