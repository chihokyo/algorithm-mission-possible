import java.util.Arrays;

public class SegmentTree<E> {
    private E[] tree; // 线段树的每一个小数组
    private E[] data; // 表示线段树的整体数组
    private Merger<E> merger; // 表示分段数使用的方法

    /**
     * 初始化一个线段树
     * 进去的是一个数组，出来的就是线段树
     * 那么就需要1 初始化一个副本空间 2 把数组全部放入到线段树里
     *
     * @param arr    进去数组
     * @param merger 需要实现的你线段树的逻辑，比如加减乘除
     */
    public SegmentTree(E[] arr, Merger<E> merger) {

        this.merger = merger;
        // 初始化 data
        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }

        tree = (E[]) new Object[4 * arr.length]; // 总共大概有4*n个空间 推算过了

        // 根节点的信息是根据2个子孩子信息决定的
        // 直到递归到底
        buildSegmentTree(0, 0, data.length - 1);

    }

    /**
     * 开始递归分隔成一个个线段树
     *
     * @param treeIndex root
     * @param left      左孩子
     * @param right     右孩子
     */
    private void buildSegmentTree(int treeIndex, int left, int right) {
        // 递归终止条件（到底条件）
        // 也就是l和r一样 长度为1 就只有1个元素
        if (left == right) {
            tree[treeIndex] = data[left]; // right也可以
            return;
        }
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        int mid = left + (right - left) / 2;
        // left:[left,mid]
        // right:[mid + 1,right]
        buildSegmentTree(leftTreeIndex, left, mid);
        buildSegmentTree(rightTreeIndex, mid + 1, right);

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index is illegal");
        }
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    // 因为线段树是不需要知道父亲parent的，所以就不实现了


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("[");
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                res.append(tree[i]);
            } else {
                res.append("null");
            }
            if (i != tree.length - 1) {
                res.append(",");
            }
        }
        res.append("]");
        return res.toString();
    }
}
