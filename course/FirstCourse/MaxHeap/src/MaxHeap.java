public class MaxHeap<E extends Comparable<E>> {

    private Array<E> data;

    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MaxHeap() {
        data = new Array<>();
    }

    /**
     * 返回这个最大堆的size也就是数组的size
     *
     * @return int 大小
     */
    public int size() {
        return data.getSize();
    }

    /**
     * 判断是否为空
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * 在完全二叉树里，给一个index，返回父节点所在的index
     *
     * @param index 索引
     * @return int 父节点索引
     */
    private int getParent(int index) {
        //  根节点没有父亲
        if (index == 0) {
            throw new IllegalArgumentException("index 0 does not have parent");
        }
        return (index - 1) / 2;
    }

    /**
     * 在完全二叉树里，给一个index，返回左child节点的index
     *
     * @param index 索引
     * @return int 左child索引
     */
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    /**
     * 在完全二叉树里，给一个index，返回右child节点的index
     *
     * @param index 索引
     * @return int 右child索引
     */
    private int rightChild(int index) {
        return index * 2 + 2;
    }
}