public class Main {
    public static void main(String[] args) {
        Integer[] nums = {-2, 0, 3, -5, 2, -1};
        // 写法1
        // SegmentTree<Integer> segmentTree = new SegmentTree<>(nums, new Merger<Integer>() {
        //     @Override
        //     public Integer merge(Integer a, Integer b) {
        //         return a + b;
        //     }
        // });
        // 写法2
        // SegmentTree<Integer> segmentTree = new SegmentTree<>(nums, (a, b) -> a + b);

        SegmentTree<Integer> segmentTree = new SegmentTree<>(nums, Integer::sum);

        System.out.println("打印");
        System.out.println(segmentTree);

        System.out.println("区间查询");
        System.out.println(segmentTree.query(0, 2)); // 1
        System.out.println(segmentTree.query(2, 5)); // -1
        System.out.println(segmentTree.query(0, 5)); // -3

    }
}
