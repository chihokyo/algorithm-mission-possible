public class LinearSearch {
    // 为了防止别人new 直接把LinearSearch的构造函数设置成私有的
    private LinearSearch() {
    }

    public static void main(String[] args) {

        Integer[] data = {100, 16 - 9, 0, 1, 13, 18};
        int res1 = LinearSearch.search(data, 18); // 自动解包 包装类
        int res2 = LinearSearch.search(data, 1000); // Java类型推断可以省略方法前面的类型 LinearSearch.<Integer>search
        System.out.println(res1);
        System.out.println(res2);

        System.out.println("****自定义Student类测试***");
        Student[] students = {new Student("Amy"), new Student("Kitty"), new Student("Tom")};
        Student kitty = new Student("Kitty");
        int res3 = LinearSearch.search(students, kitty);
        System.out.println(res3);

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
