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

        System.out.println(segmentTree);
    }
}
