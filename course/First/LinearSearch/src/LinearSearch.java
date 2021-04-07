public class LinearSearch {
    // 为了防止别人new 直接把LinearSearch的构造函数设置成私有的
    private LinearSearch() {
    }

    public static void main(String[] args) {
        // 写一个需要的测试数据量
        int[] loopSize = {10_000_000, 1_00_000_000};
        // 循环上面的数据量
        for (int n : loopSize) {
            // 生成一个列表
            Integer[] data = ArrayGenerator.generateOrderedArray(n);
            long startTime = System.nanoTime();
            for (int j = 0; j < 100; j++) {
                int result = LinearSearch.search(data, n);
            }
            long endTime = System.nanoTime();
            double time = (endTime - startTime) / 1000000000.0;
            System.out.println("数据规模: " + n + ",运行速度: " + time + "s");

        }


//        int res1 = LinearSearch.search(data, 11); // 自动解包 包装类
//        int res2 = LinearSearch.search(data, 1000); // Java类型推断可以省略方法前面的类型 LinearSearch.<Integer>search
//        System.out.println(res1);
//        System.out.println(res2);
//
//        System.out.println("****自定义Student类测试***");
//        Student[] students = {new Student("Amy"), new Student("Kitty"), new Student("Tom")};
//        Student kitty = new Student("Kitty");
//        int res3 = LinearSearch.search(students, kitty);
//        System.out.println(res3);

    }

    /**
     * 线性查找
     *
     * @param data   数组
     * @param target 查找目标
     * @return 返回index索引
     */
    public static <E> int search(E[] data, E target) {
        for (int i = 0; i < data.length; i++) {
            // 因为这里不是基本数据类型了
            // 值相等 和 地址相等 这里虽然帮你实现了equals 但是自定义类需要实现
            if (data[i].equals(target))
                return i;
        }
        return -1;
    }
}
