public class MaxHeap<E extends Comparable<E>> {

    private Array<E> data;

    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MaxHeap() {
        data = new Array<>();
    }

    /**
     * 为了堆实现构造函数，传入一个数组，生成一个新的动态数组
     * 然后对倒数第一个非叶子节点，开始逐层遍历完成
     *
     * @param arr 数组
     */
    public MaxHeap(E[] arr) {
        data = new Array<>(arr);
        for (int i = getParent(arr.length - 1); i >= 0; i--) {
            siftDown(i);
        }
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


    /**
     * 给堆中添加元素
     *
     * @param e 值
     */
    public void add(E e) {
        // 添加元素
        data.addLast(e);
        // 维护结构 这里传入的是最后一个元素的索引
        siftUp(data.getSize() - 1);
    }

    // 上浮操作 传入的是索引而不是元素！
    private void siftUp(int index) {
        // 自身索引要大于0 并且 自己的值要大于父节点的值
        while (index > 0 && data.get(getParent(index)).compareTo(data.get(index)) < 0) {
            // 交换元素
            data.swap(index, getParent(index));
            // 交换完之后还要继续比较 这里属于循环的变量
            index = getParent(index);
        }
    }


    private E findMax() {
        if (data.getSize() == 0) {
            throw new IllegalArgumentException("Cannot findMax when heap is empty.");
        }
        // 最大就是堆顶
        return data.get(0);
    }

    // 完成下沉，维持最大堆结构
    private void siftDown(int index) {
        // 循环结束条件 知道没有叶子（左子树没有就肯定是了，因为完全二叉树特性，左都没，右肯定没）
        // 上面就是左孩子都已经越界了，超过了堆最大值，就说明左子树没有了。
        while (leftChild(index) < data.getSize()) {
            // 走到了这里 说明肯定是有左节点的
            int j = leftChild(index);
            // j+ 1 本质 右子树 说明有右子树
            // 有右子树 && 右子树大于左子树
            if (j + 1 < data.getSize() && data.get(j + 1).compareTo(data.get(j)) > 0) {
                j = rightChild(index); // j++ 这里就是右子树
            }
            // 这个时候 data[j] 就是左右子树里最大的值
            // 这个判断说明index这个值比左右子树里的最大值还大，那么根本不用下沉。直接break
            if (data.get(index).compareTo(data.get(j)) > 0) {
                break;
            }
            // 如果小，就交换
            data.swap(index, j);
            index = j;
        }
    }

    /**
     * 取出堆顶最大元素
     *
     * @return E 泛型
     */
    public E extractMax() {
        // 找到最大元素并缓存
        E ret = findMax();
        // 交换最大和最小 （也就是堆顶和堆底）
        data.swap(0, data.getSize() - 1);
        // 删除堆底
        data.removeLast();
        // 维持最大堆结构
        siftDown(0);
        // 返回缓存
        return ret;
    }

    /**
     * 取出堆顶元素，并替换成新元素e
     *
     * @return E 返回堆顶元素
     */
    public E replace(E e) {
        E ret = findMax();
        data.set(0, e);
        siftDown(0);
        return ret;
    }

}