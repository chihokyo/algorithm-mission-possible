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

    // 因为线段树是不需要知道父亲parent的，所以就不实现了

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    /**
     * 区间查询操作
     *
     * @param queryL 左区间
     * @param queryR 有区间
     * @return E 返回那个区间的数值
     */
    public E query(int queryL, int queryR) {
        if (queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length) {
            throw new IllegalArgumentException("Index is illegal");
        }

        return query(0, 0, data.length - 1, queryL, queryR);
    }

    /**
     * 在以treeIndex为root的线段树[l,r]里，搜索范围[queryL,queryR]的值
     *
     * @param treeIndex root
     * @param l         线段树范围
     * @param r         线段树范围
     * @param queryL    要查找的范围
     * @param queryR    要查找的范围
     * @return 泛型 返回值
     */
    private E query(int treeIndex, int l, int r, int queryL, int queryR) {
        if (l == queryL && r == queryR) return tree[treeIndex];
        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        //  1 要查找的目标比mid+1还要大，说明左边可以不要了
        if (queryL >= mid + 1) {
            // 1-1左孩子没有关系，直接到右子树去查找
            return query(rightTreeIndex, mid + 1, r, queryL, queryR);
            // 2 这里说明目标的右，比二分的还小。说明右边可以不要了
        } else if (queryR <= mid) {
            // 2-1 同理
            return query(leftTreeIndex, l, mid, queryL, queryR);
        }
        // 3 横跨左右孩子，一部分在左边，一部分在右边
        // 这里的第五个参数需要注意,为什么是mid，因为这个时候目标也被拆分了。
        E leftRes = query(leftTreeIndex, l, mid, queryL, mid);
        E rightRes = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
        return merger.merge(leftRes, rightRes);
    }

    /**
     * 更新操作
     *
     * @param index 索引
     * @param val   更新为val
     */
    public void set(int index, E val) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index is illegal");
        }
        data[index] = val; // 更新
        // 更新本身是很快的 但是还要维持树结构，就是重新开始计算
        // 所以下面就是递归寻找重新组成一个线段的过程
        set(0, 0, data.length - 1, index, val);
    }

    /**
     * 以treeIndex为root向下找到值并更新值
     *
     * @param treeIndex 根
     * @param l         左边界
     * @param r         右边界
     * @param index     想找到的索引
     * @param val       更新为
     */
    private void set(int treeIndex, int l, int r, int index, E val) {
        if (l == r) {
            tree[treeIndex] = val;
            return;
        }
        int mid = l + (r - l) / 2;
        int leftChildIndex = leftChild(treeIndex);
        int rightChildIndex = rightChild(treeIndex);
        // 目标index，比mid+1还大，说明左边全部没戏
        if (index >= mid + 1) {
            set(rightChildIndex, mid + 1, r, index, val);
        } else {
            set(leftChildIndex, l, mid, index, val);
        }
        tree[treeIndex] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);

    }

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
