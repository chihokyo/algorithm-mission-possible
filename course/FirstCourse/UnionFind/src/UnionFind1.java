public class UnionFind1 implements UF {

    // 这里是是用数组来实现的 所以这里是一个id数组
    private int[] id;

    public UnionFind1(int size) {
        id = new int[size];
        // 初始化一个kv相等的数组
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    @Override
    public int getSize() {
        return id.length;
    }

    /**
     * 查看p和q是否同属于一个集合
     * 因为find复杂度是1，所以这个复杂度就是1
     *
     * @param p 元素1
     * @param q 元素2
     * @return boolean
     */
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * 合并2个集合
     * @param p 元素1
     * @param q 元素2
     */
    @Override
    public void unionElements(int p, int q) {
        int pId = find(p);
        int qId = find(q);
        // 这里说明2个已经是一个集合的
        if (pId == qId) return;
        // 开始遍历，使2个相同。
        for (int i = 0; i < id.length; i++) {
            // 这里要注意，也可以判断是否等于qId，最后统一成p也可以
            if (id[i] == pId) {
                id[i] = qId;
            }
        }
    }

    // 查找p对应的value
    private int find(int index) {
        if (index < 0 && index >= id.length) {
            throw new IllegalArgumentException("p is out of bound.");
        }
        return id[index];
    }
}
