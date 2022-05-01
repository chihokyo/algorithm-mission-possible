public class BinarySearch {
    private BinarySearch() {

    }

    public static <E extends Comparable<E>> int searchR(E[] data, E target) {
        return searchR(data, 0, data.length - 1, target);
    }

    /**
     * 递归实现二分法
     *
     * @param data   数组
     * @param left   左区间
     * @param right  有区间
     * @param target 目标
     * @param <E>    泛型（任何类型都可以）
     * @return int 返回目标所在的index
     */
    private static <E extends Comparable<E>> int searchR(E[] data, int left, int right, E target) {
        // 这里不能是= 因为在只有1的时候[5],5,这个时候直接就-1了！
        if (left > right) return -1;
        // 防止栈溢出的取中
        int mid = left + (right - left) / 2;
        if (data[mid].compareTo(target) == 0) {
            return mid;
        }
        if (data[mid].compareTo(target) < 0) {
            return searchR(data, mid + 1, right, target);
        }
        return searchR(data, left, mid - 1, target);
    }

    /**
     * 非递归实现二分法
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    返回泛型
     * @return int 返回目标所在的index
     */
    public static <E extends Comparable<E>> int search(E[] data, E target) {
        int left = 0, right = data.length - 1;
        // 说明还有未循环完的，和递归正好想法的条件
        // 循环不变量 data[left, right] 内找target
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (data[mid].compareTo(target) == 0) return mid;
            // 小于0 target的值大于mid。想后找。
            if (data[mid].compareTo(target) < 0) {
                left = mid + 1;
            } else {
                // target的值小于mid，向前找。
                right = mid - 1;
            }
        }
        // 全部循环完毕也没有
        return -1;
    }


    /**
     * 寻找大于 > target的最小值的索引
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    泛型
     * @return 返回index
     */
    public static <E extends Comparable<E>> int upper(E[] data, E target) {
        int left = 0;
        int right = data.length; // 这里需要注意！为什么不-1的意思就是说，数组里面最大的一个值，都target小 {1,3,4} 比如target是5 此时返回的就是这个不存在最右边。不是合法索引，但是是合法解。
        // 在data[left, right] 寻找解，这个问题是一定有解的。
        // 为什么不是等于，因为==就已经证明是有解 相当于是解了
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 证明这个比目标等于或者还小，反正就不是大，不是大于就跟题意要求的不符合 那么左边的都不是解
            if (data[mid].compareTo(target) <= 0) {
                // 和下面的right可以一起来看
                left = mid + 1;
            } else {
                // 证明是大于的，那么有可能这个就是解，也有可能她的右边有解。
                // 所以不能-1
                right = mid;
            }
        }

        return left; // 这里return right也是可以的，因为重合的时候证明2个一样的都是解
    }

    /**
     * > target 返回最小值索引
     * == target 返回最大值的索引
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    泛型
     * @return 目标的索引
     */
    public static <E extends Comparable<E>> int ceil(E[] data, E target) {
        int u = upper(data, target);
        // 注意点1 容易忽略这个u-1是否是合法的
        if (u - 1 >= 0 && data[u - 1].compareTo(target) == 0) {
            return u - 1;
        }
        return u;
    }

    /**
     * 寻找 >= target的最小值索引
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    泛型
     * @return int 最小值索引
     */
    public static <E extends Comparable<E>> int lower_ceil(E[] data, E target) {
        // 本质上其实和upper有异曲同工之妙
        int left = 0, right = data.length; // 最右边也可能是合法解
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 最重要的一点，就是这里判断的就是小于0 意思就是说target在右边，也就是大
            // 这时候说明data[mid]的mid索引，包括mid，左边的都是没用的
            // 和上面的思考最大的不同的就是判断=的时候，=这个情况在下面判断了，如果=,也就是说这个mid所在的索引也有可能是正解
            if (data[mid].compareTo(target) < 0) {
                mid = left + 1; // 都是没用的 何必还要，直接+1
            } else {
                right = mid; // 这里说明从>=都是有可能的
            }
        }

        return left;

    }

    /**
     * < target的最大值索引
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    泛型
     * @return int 最大值的索引
     */
    public static <E extends Comparable<E>> int lower(E[] data, E target) {
        int left = -1;
        int right = data.length - 1;
        while (left < right) {
            // 最大的注意点，int mid = left + (right - left) / 2;错误！下面正确
            // 保证了当left和right相邻的时候，上取整
            int mid = left + (right - left + 1) / 2;
            // 这里就是肯定不是小于的，那么就是大于等于，那么这个mid肯定不是解
            // 大于等于目标，肯定就向左找了。
            if (data[mid].compareTo(target) >= 0) {
                right = mid - 1;
            } else {
                // 小于目标，说明这个mid也有可能是解。
                left = mid;
            }
        }

        return left; // 返回right也可以
    }

    /**
     * 存在元素返回最小索引，不存在返回lower
     * < target ，返回最大值索引
     * == target，返回最小索引
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    泛型
     * @return int 索引
     */
    public static <E extends Comparable<E>> int lower_floor(E[] data, E target) {
        // 这里求的是< target的最大值索引 所以合法解会是【越界】的
        int l = lower(data, target);
        // 注意，因为我们要访问 data[left + 1]，所以，我们要先确保 left + 1 不越界
        if (l + 1 < data.length && data[l + 1].compareTo(target) == 0) {
            return l + 1;
        }
        return l;
    }

    /**
     * 存在元素返回最大索引，不存在返回lower
     * 本质 <= target最大索引
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    泛型
     * @return int 索引
     */
    public static <E extends Comparable<E>> int upper_floor(E[] data, E target) {

        int left = -1, right = data.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            // mid大于等于target 反正是不符合<=
            if (data[mid].compareTo(target) > 0) {
                right = mid - 1;
            } else {
                // 大于等于，说明有可能是解
                left = mid;
            }
        }

        return left;
    }

    /**
     * 在有序数组 data 中判断 target 是否存在，存在则返回相应索引，不存在则返回 -1
     * 其实就是另一种二分法求目标在不在的方式
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    泛型
     * @return int 存在返回index,不在-1
     */
    public static <E extends Comparable<E>> int searchBy(E[] data, E target) {
        int left = 0, right = data.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (data[mid].compareTo(target) < 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        // 注意这个判断
        if (left < data.length && data[left].compareTo(target) == 0) {
            return left;
        }
        return -1;
    }

    public static void main(String[] args) {

        // Integer[] nums = {0, 1, 3, 4, 5, 9, 12, 13};
        // Integer[] nums2 = {5};
        // int result = BinarySearch.searchR(nums, 9);
        // int result2 = BinarySearch.searchR(nums2, 5);
        // System.out.println(result);
        // System.out.println(result2);
        //
        // Integer[] nums3 = {0, 1, 3, 4, 5, 9, 12, 13};
        // Integer[] nums4 = {5};
        // int result3 = BinarySearch.search(nums, 9);
        // int result4 = BinarySearch.search(nums2, 5);
        // System.out.println(result3);
        // System.out.println(result4);

        // // 测试upper
        // Integer[] arr = {1, 1, 3, 3, 5, 5};
        // for (int i = 0; i <= 6; i++) {
        //     System.out.print(BinarySearch.upper(arr, i) + " ");
        // }
        // System.out.println(); // 0 2 2 4 4 6 6 (OK)
        //
        // // 测试ceil
        // Integer[] arr2 = {1, 1, 3, 3, 5, 5};
        // for (int i = 0; i <= 6; i++) {
        //     System.out.print(BinarySearch.ceil(arr2, i) + " ");
        // }
        // System.out.println(); // 0 1 2 3 4 5 6  (OK)

        // // 测试lower
        // Integer[] arr3 = {1, 1, 3, 3, 5, 5};
        // for (int i = 0; i <= 6; i++) {
        //     System.out.print(BinarySearch.lower(arr3, i) + " "); // -1 -1 1 1 3 3 5 (OK)
        // }
        // System.out.println();
        //
        // // 测试lower_floor
        // Integer[] arr4 = {1, 1, 3, 3, 5, 5};
        // for (int i = 0; i <= 6; i++) {
        //     System.out.print(BinarySearch.lower_floor(arr4, i) + " "); // -1 0 1 2 3 4 5 (OK)
        // }
        // System.out.println();
        //
        // // 测试upper_floor
        // Integer[] arr5 = {1, 1, 3, 3, 5, 5};
        // for (int i = 0; i <= 6; i++) {
        //     System.out.print(BinarySearch.upper_floor(arr5, i) + " "); // -1 1 1 3 3 5 5  (OK)
        // }
        // System.out.println();

        // 测试searchBy
        Integer[] arr6 = {1, 2, 3, 3, 5, 6};
        System.out.print(BinarySearch.searchBy(arr6, 2) + " ");

    }
}
