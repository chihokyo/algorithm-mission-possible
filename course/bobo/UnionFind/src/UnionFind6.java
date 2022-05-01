public class UnionFind6 implements UF {
    private int[] parent;
    private int[] rank; // rank[i]表示i为root的集合中的深度

    public UnionFind6(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1; // 每一个都为1，因为初始化的时候只有自己独立的那一棵树高度为0
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
        // 版本4的变化在这里
        // 根据rank[i]也就是深度的，要从深度低的指向高的
        if (rank[pRoot] < rank[qRoot]) {
            parent[pRoot] = qRoot;
            // 因为这里的高度还是qRoot 所以无需维护
        } else if (rank[qRoot] < rank[pRoot]) {
            parent[qRoot] = pRoot;
        } else {
            // 高度相等无所谓谁指向谁
            parent[qRoot] = pRoot;
            // 但是2个高度相等的合并，高度是要+1
            rank[pRoot] += 1;
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
        // 第6版主要是这里使用了递归，自己调用自己
        // 如果自己不是根节点
        if (index != parent[index]) {
            // 就不断找自己父亲的根节点，最后就是根节点
            // 意思就是所有的元素最后都要指向根节点
            parent[index] = findRoot(parent[index]);
        }
        return parent[index]; // 其实这里指向的就是根节点
    }
}
