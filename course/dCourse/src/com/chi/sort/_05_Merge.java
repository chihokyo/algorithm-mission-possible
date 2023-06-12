package com.chi.sort;

import static com.chi.sort.Sorter.printArray;

public class _05_Merge {

    public static void main(String[] args) {
        int[] array = {38, 27, 43, 3, 9, 82, 10, -5, 555};
        System.out.println("排序前的数组：");
        printArray(array);

        // 思路1 每次都申请新临时数组
        // mergesort1(array, 0, array.length - 1);
        // 思路2 一次性临时数组
        int[] tempArr = new int[array.length];
        // mergesort2(array, 0, array.length - 1, tempArr); // 赋值
        // 思路3 使用临时数组对比
        // mergesort3(array, 0, array.length - 1, tempArr); // 赋值
        // 思路4 迭代实现
        merge4(array);
        // 练手
        // mergesortPractise(array, 0, array.length - 1, tempArr);

        System.out.println("排序后的数组：");
        printArray(array);
    }

    private static void mergesortPractise(int[] array, int left, int right, int[] tempArr) {
        if (left == right) return;
        int mid = (left + right) / 2;
        mergesortPractise(array, left, mid, tempArr);
        mergesortPractise(array, mid + 1, right, tempArr);
        mergePractise(array, left, mid, right, tempArr);
    }

    private static void mergePractise(int[] array, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int index = left;
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                tempArr[index++] = array[i++];
            } else {
                tempArr[index++] = array[j++];
            }
        }
        while (i <= mid) {
            tempArr[index++] = array[i++];
        }
        while (j <= right) {
            tempArr[index++] = array[j++];
        }
        // 走到这里临时数组应该ok了
        for (index = left; index <= right; index++) {
            array[left++] = tempArr[index];
        }
    }

    /**
     * 传统的归并排序
     *
     * @param array
     * @param left
     * @param right
     */
    public static void mergesort1(int[] array, int left, int right) {
        if (left == right) return;
        int mid = (left + right) / 2;
        mergesort1(array, left, mid);
        mergesort1(array, mid + 1, right);
        merge1(array, left, mid, right);
    }

    // 合并2个数组
    private static void merge1(int[] array, int left, int mid, int right) {
        int[] tempArr = new int[right - left + 1]; // 左右+1 因为数组是从index=0开始计算的
        int i = left, j = mid + 1;
        int tempIndex = 0; // 临时数组的index
        // 这里代表的是左右目前都有
        while (i <= mid && j <= right) {
            // 开始比较
            if (array[i] <= array[j]) {
                // array[i]更小
                tempArr[tempIndex++] = array[i++];
            } else {
                // array[j]更小
                tempArr[tempIndex++] = array[j++];
            }
        }

        // 这里左边还有没走完的
        while (i <= mid) {
            tempArr[tempIndex++] = array[i++];
        }
        // 这里右边还有没走完的
        while (j <= right) {
            tempArr[tempIndex++] = array[j++];
        }
        // 走到这里 tempArr已经合并完了，但是还需要把临时数组拷贝到原来的数组上
        // 拷贝过程需要从0开始 记得起始点是left
        for (tempIndex = 0; tempIndex < tempArr.length; tempIndex++) {
            // 这里很重要，左边你要赋值到哪里呢？ 为什么从left，因为你定义了从left开始
            array[left] = tempArr[tempIndex];
            left++;
        }
    }

    /**
     * 这里就是每一次不用在内部新建数组
     * 你可以一次性就申请一个同样大小的
     * 然后对比的时候把最小值就放在你给的[left,right]区间内
     */
    public static void mergesort2(int[] array, int left, int right, int[] tempArr) {
        if (left == right) return;
        int mid = (left + right) / 2;
        mergesort2(array, left, mid, tempArr);
        mergesort2(array, mid + 1, right, tempArr);
        // 开始合并
        merge2(array, left, mid, right, tempArr);

    }

    /**
     * 临时数组不可能超过原始数组长度
     * 所以可以一次性申请原数组大小
     */
    private static void merge2(int[] array, int left, int mid, int right, int[] tempArr) {
        int i = left, j = mid + 1;
        int tempPos = left;
        // 谁小就拷贝过去
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                tempArr[tempPos++] = array[i++];
            } else {
                tempArr[tempPos++] = array[j++];
            }
        }

        while (i <= mid) {
            tempArr[tempPos++] = array[i++];
        }
        while (j <= right) {
            tempArr[tempPos++] = array[j++];
        }
        // 这里拷贝逻辑很重要 目的就是拷贝temp
        // 从left开始，一直到right结束,tempPos这里就是重置的感觉
        for (tempPos = left; tempPos <= right; tempPos++) {
            array[left++] = tempArr[tempPos];
        }
    }


    /**
     * 这里使用临时数组进行对比
     * 所以要复制一个一模一样的临时数组，但是最后不用复制给原数组了
     */
    private static void mergesort3(int[] array, int left, int right, int[] tempArr) {
        if (left == right) return;
        int mid = (left + right) / 2;
        mergesort3(array, left, mid, tempArr);
        mergesort3(array, mid + 1, right, tempArr);
        merge3(array, left, mid, right, tempArr);
    }

    // 这里用的是对比的方法
    private static void merge3(int[] array, int left, int mid, int right, int[] tempArr) {
        // 先拷贝一份一模一样的数组 让arr和tempArr是一致的
        for (int i = left; i <= right; i++) {
            tempArr[i] = array[i];
        }
        int i = left, j = mid + 1;
        // k用于填充原始数据的值
        for (int k = left; k <= right; k++) {
            // 为什么是+1 因为只有+1才是真正的超过
            if (i == mid + 1) { // 左边没有元素（已经走完了，那么只有右边有
                array[k] = tempArr[j++];
            } else if (j == right + 1) { // 右边没有元素（已经走完了，那么只有左边有
                array[k] = tempArr[i++];
            } else if (tempArr[i] <= tempArr[j]) { // 左边更小
                array[k] = tempArr[i++];
            } else { // 右边更小
                array[k] = tempArr[j++];
            }
        }
        // 这里就不用拷贝到原数组了
    }

    /**
     * 迭代实现 merge4- mergeSortIterative
     *
     * @param array 这里只要给原数组就行
     */
    private static void merge4(int[] array) {
        int len = array.length;
        int[] tempArr = new int[len];

        // 从这里来说就很重要了 1,2,4,8,16 按照方式进行一一合并
        // 这个for控制子数组的大小 每次迭代后，子数组的大小都会翻倍
        for (int size = 1; size < len; size *= 2) {
            // 这个for代表的是每一对需要合并的子数组的开始位置
            // 每次迭代，它都会跳过两个大小为m的子数组（i += 2 * i）。
            // 比如，当子数组的大小是4时，它会处理数组的第1-4个元素，然后跳过第5-8个元素，处理第9-12个元素，以此类推
            // j每次都要增加2个size 如果你不理解的话 可以这样想 [3,6,8,2,1,0]
            // 当你处理完index=0也就是3的时候 下一次处理8 如果你只是+size 那么就是0+1=1 处理成6了 所以间隔必须是2倍的size
            for (int j = 0; j < len - size; j += 2 * size) {
                // 这里为什么要判断取最小呢？因为有可能你的数组是一个奇数[2,6,7,8,2] 此时如果j + 2 * size - 1可能已经越界了
                int right = Math.min(j + 2 * size - 1, len - 1);
                merge3(array, j, j + size - 1, right, tempArr);
            }
        }
    }
}
