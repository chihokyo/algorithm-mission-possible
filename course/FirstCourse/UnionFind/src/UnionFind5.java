public class UnionFind5 implements UF {
    private int[] parent;
    private int[] rank; // rank[i]表示i为root的集合中的深度

    public UnionFind5(int size) {
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
        // 不断向前找自己的父节点，直到相等证明p指向了自己，也就是根节点
        // 从下到上查找
        while (index != parent[index]) {
            // 第5版的主要修改点在这里
            // 本质就是一边寻找，一边找到父亲的父亲然后压缩
            parent[index] = parent[parent[index]]; // 自己本来改指向父亲的，现在指向父亲的父亲
            index = parent[index]; // 继续想找遍历
        }
        return index;
    }
}
