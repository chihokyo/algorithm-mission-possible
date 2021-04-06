public class LinearSearch {
    // 为了防止别人new 直接把LinearSearch的构造函数设置成私有的
    private LinearSearch() {
    }

    public static void main(String[] args) {

        int[] data = {100, 16 - 9, 0, 1, 13, 18};
        int res1 = LinearSearch.search(data, 18);
        int res2 = LinearSearch.search(data, 1000);
        System.out.println(res1);
        System.out.println(res2);

    }

    /**
     * 线性查找
     *
     * @param data   数组
     * @param target 查找目标
     * @return 返回index索引
     */
    public static int search(int[] data, int target) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target)
                return i;
        }
        return -1;
    }
}
