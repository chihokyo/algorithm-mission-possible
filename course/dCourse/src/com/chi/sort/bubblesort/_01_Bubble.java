package com.chi.sort.bubblesort;

/**
 * 冒泡排序
 */
public class _01_Bubble {
    public static void main(String[] args) {
        int[] array = {5, 2, 7, 3, 1, 6, 4, 2, 4, 4, 55, 8, 7, 2};

        System.out.println("排序前的数组：");
        printArray(array);

        // bubbleSort(array); // 思路1
        // bubbleSort2(array); // 思路2
        // bubbleSort3(array); // 思路3
        // bubbleSort4(array); // 思路4
        bubbleSort5(array); // 思路5

        System.out.println("排序后的数组：");
        printArray(array);
    }

    /**
     * 思路1 传统的思路 排序的次数
     *
     * @param array
     */
    public static void bubbleSort(int[] array) {
        // 这里确定回 round代表多少回 这里因为要比较 长度次数-1
        // 如果你这里写的是 round = 0 round <= array.length 虽然回数上是达标了 但是下面compareTime的次数 可能有误差 你可以看写法2
        // 假设数组长度是6，那么就是 1,2,3,4,5
        for (int round = 1; round < array.length; round++) { // 控制冒泡的轮数 需要冒泡几轮呢？
            // 这个i就是第一回比较1次，第二回比较
            int compareTimes = array.length - round; // 控制每轮比较的次数 需要比较几次呢？
            // 这里确定多少次
            for (int j = 0; j < compareTimes; j++) {
                if (array[j] > array[j + 1]) {
                    Sorter.swap(array, j, j + 1);
                }
            }
        }

        // 写法2 用的是 round = 0 round <= array.length
        for (int i = 0; i < array.length - 1; i++) { // 注意看 从0开始 要-1的！！
            int compareTimes = array.length - i - 1; // 注意看 -1了
            for (int j = 0; j < compareTimes; j++) {
                if (array[j] > array[j + 1]) {
                    Sorter.swap(array, j, j + 1);
                }
            }
        }
    }

    /**
     * 思路2 本质和1差不多 只是控制次数的写法不一样而已
     *
     * @param array
     */
    // 总是就是外层需要长度-1次 内存需要长度-回数-1
    public static void bubbleSort2(int[] array) {
        // 回数 需要长度-1次就可以了
        for (int i = 0; i < array.length - 1; i++) {
            // 每次需要比较 总长度-回数 -1（这个1是自身)
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    Sorter.swap(array, j, j + 1);
                }
            }
        }
    }

    /**
     * 思路3 小的优化 增加flag判断是否比较了
     *
     * @param array
     */
    public static void bubbleSort3(int[] array) {
        int n = array.length;
        boolean isSwapped;
        for (int i = 0; i < n - 1; i++) {
            isSwapped = false; // 这里先改成false 说明暂时没有交换
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    isSwapped = true; // 交换了 直接设置成true
                }
            }
            // 如果没有交换 直接就跳出循环 说明整个数组已经是排序好的
            // 要注意这个判断要写在第1个for里面 相当于第i次没有交换 整个程序目前都要break
            // 如果你写在了里面的for里面，你只能跳过里面的for
            if (!isSwapped) break;
        }
    }

    /**
     * 思路4 小的优化 不仅仅记录是否比较 还要记录位置
     *
     * @param array
     */
    public static void bubbleSort4(int[] array) {
        int n = array.length;
        boolean isSwapped;
        int lastSwappedIndex = n - 1; // 记录最后一次交换的位置
        int currentSwappedIndex = 0;
        for (int i = 0; i < n - 1; i++) {
            isSwapped = false; // 是否交换
            // 初始化当前的index 如果没有这个 就没办法传递lastSwappedIndex
            // 想象一下 下面的代码 j的位置进行了交换 已经交换完了 下一次外层遍历的时候 我不用每次都到n-i-1了
            // 我只想要到j的位置 可是j目前在里面的for里面，我如何传递出去呢？这个时候就需要currentSwappedIndex进行传递
            // 那么我这个currentSwappedIndex可以不可以写在最外层呢？答案是可以 至于为什么 因为每一次交换都是从0开始的 你也不知道新的循环
            for (int j = 0; j < lastSwappedIndex; j++) {
                if (array[j] > array[j + 1]) {
                    Sorter.swap(array, j, j + 1);
                    isSwapped = true;
                    currentSwappedIndex = j; // 记录
                }
            }
            if (!isSwapped) break;
            lastSwappedIndex = currentSwappedIndex;
        }


    }

    /**
     * 思路4 使用对撞指针 进行判断
     * 这个思路还是蛮有意思的 我写一下吧
     *
     * @param array
     */
    public static void bubbleSort5(int[] array) {
        int n = array.length;
        int left = 0;
        int right = n - 1;
        // 如果你是while (left <= right) 那么就进行了一次多余的比较 等于自身时候无需比较
        while (left < right) {
            // 此时就找最大
            for (int i = left; i < right; i++) {
                if (array[i] > array[i + 1]) {
                    Sorter.swap(array, i, i + 1);
                }
            }
            right--; // 未排序里最大的已经找到了

            // 此时就找最小
            for (int i = right; i > left; i--) {
                if (array[i] < array[i - 1]) { // 注意区分这里是i-1
                    Sorter.swap(array, i - 1, i);
                }
            }
            left++;
        }
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}
