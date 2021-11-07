package com.chi.line.array;

import java.util.Arrays;

public class ArrayUtils {

    /**
     * 数组大小
     *
     * @param arr 需要检查的数组
     * @return boolean
     */
    public static int getSize(int[] arr) {
        int size = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) size++;
        }
        return size;
    }

    /**
     * 数组大小
     *
     * @param arr 需要知道大小的数组
     * @return int 大小
     */
    public static boolean isEmpty(int[] arr) {
        // 这里假设0就没有元素
        // 但是不严谨，因为元素的值可能就是0
        for (int num : arr) {
            if (num != 0) return false;
        }
        return true;
    }

    /**
     * 目标数字是否在数组里
     *
     * @param arr    需要搜索的数组
     * @param target 目标是多少
     * @return boolean
     */
    public static boolean contains(int[] arr, int target) {
        for (int num : arr) {
            if (num == target) return true;
        }
        return false;
    }

    /**
     * 将指定的元素插入到新的指定数组的指定位置上
     *
     * @param src     需要插入元素的数组
     * @param index   插入数组的元素
     * @param element 需要插入的元素值
     * @return 返回最新的数组
     */
    public static int[] insertElement(int[] src, int index, int element) {
        int length = src.length;
        // 1 初始化新数组
        int[] dest = new int[length + 1];

        // 2 把index前面的拷贝到新数组里
        for (int i = 0; i < index; i++) {
            dest[i] = src[i];
        }

        // if (index >= 0) System.arraycopy(src, 0, dest, 0, index);

        // 3 插入操作
        dest[index] = element;

        // 4 把index后面的拷贝到新数组里

        for (int i = index; i < length; i++) {
            dest[i + 1] = src[i];
        }
        // System.arraycopy(src, index, dest, index + 1, dest.length - index);

        return dest;

    }

    /**
     * 删除指定位置的元素
     *
     * @param src   需要删除的元素
     * @param index 指定位置
     * @return 新的数组
     */
    public static int[] removeElement(int[] src, int index) {
        int[] dest = new int[src.length - 1];
        for (int i = 0; i < index; i++) {
            dest[i] = src[i];
        }
        // if (index >= 0) System.arraycopy(src, 0, dest, 0, index);
        // 这里就忽略了index
        for (int i = index; i < src.length - 1; i++) {
            // 这里就忽略了index
            dest[i] = src[i + 1];
        }
        // if (src.length - 1 - index >= 0) System.arraycopy(src, index + 1, dest, index, src.length - 1 - index);

        return dest;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 6, 9, 5};
        System.out.println(ArrayUtils.getSize(array));
        System.out.println(ArrayUtils.isEmpty(array));
        System.out.println(ArrayUtils.contains(array, 8));
        int[] t1 = ArrayUtils.insertElement(array, 1, 11);
        System.out.println(Arrays.toString(t1));
        int[] t2 = ArrayUtils.removeElement(array, 2);
        System.out.println(Arrays.toString(t2));
    }
}
