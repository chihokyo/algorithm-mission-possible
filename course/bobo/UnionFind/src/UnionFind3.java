public class UnionFind3 implements UF {
    private int[] parent;
    private int[] sz; // sz[i]表示i为root的集合中元素的个数是多少

    public UnionFind3(int size) {
        parent = new int[size];
        sz = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            sz[i] = 1; // 每一个都为1，因为初始化的时候只有自己独立的那一棵树
        }
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    /**
     * 2个元素是否相连
     * 这里用的是查看根节点是否是一个节点就可以了
     *
     * @param p 元素1
     * @param q 元素2
     * @return boolean
     */
    @Override
    public boolean isConnected(int p, int q) {
        return findRoot(p) == findRoot(q);
    }

    /**
     * 合并2个元素 从下到上
     * 这里的复杂度是O(h) h是树的高度
     *
     * @param p 元素1
     * @param q 元素2
     */
    @Override
    public void unionElements(int p, int q) {
        int pRoot = findRoot(p);
        int qRoot = findRoot(q);
        // 相同说明根节点相同，是一个组的。直接return
        if (pRoot == qRoot) return;
        // 版本3最大的优化在这里
        if (sz[pRoot] < sz[qRoot]) {
            // 少 → 多
            parent[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];// qRoot更大了
        } else { // 如果sz的p和q是一样大小的，其实谁指向谁都无所谓，所以等号在哪里都可以，这次用的是sz[pRoot] >= sz[qRoot]
            parent[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot]; // pRoot 更大了
        }
    }

    /**
     * 查找元素index所对应的集合编号
     * 这里的复杂度是O(h) h是树的高度
     *
     * @param index 查找的元素
     * @return int 找到集合编号
     */
    private int findRoot(int index) {
        if (index < 0 || index >= parent.length) {
            throw new IllegalArgumentException("index is out of bound.");
        }
        // 不断向前找自己的父节点，直到相等证明p指向了自己，也就是根节点
        // 从下到上查找
        while (index != parent[index]) {
            index = parent[index];
        }
        return index;
    }
}
