# 并查集 UnionFind

## 是什么？

百度一下上网找。

查找2个节点是否连接，那么路径是什么呢？

- 是否连接 → 并查集问题

- 路径是什么 → 并查集不管这个具体路径。

就像我们俩是否有戏一样，有戏是有，但是真正如何走到，详细的话，并查集不关心这个。**堆**的顺序表可以查找路径，但这些维护了不需要的信息。比如最大堆我们如果只需要最大的话，那么其他都是不必须的。

## 主要操作

并查集其实主要要有2个方法

`union(p, q)` → 2个合一起并起来

`isConnected(p ,q)` → 是否连接

## 版本1：使用数组模拟实现 Quick Find

![image-20211001233408997](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211001233410.png)

所以只要看那么find的时间复杂度就是O(1)，所以就是**Quick Find**。查找操作本身还是简单的，只要看2个是否为一个集合就可以。

下面是合并的操作。

比如

![image-20211001234216529](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211001234218.png)

实现之后

![image-20211001234157612](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211001234158.png)

其实就是遍历然后统一为一个数组，至于是0还是1，其实都可以。

```java
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

```

## 版本2：使用树实现 Quick Union

### 模拟过程

这里的两个合并其实就是指A的根节点指向至B的根节点

![Oct-01-2021 23-51-25](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211001235149.gif)

所以我们就可以模拟一下指针，表示某个元素指向哪个元素。

![image-20211001235310384](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211001235311.png)

所以下面就是10棵树，每个都指向自己。如果需要指向union(4,3)，直接让4的根节点也就是4直接指向→3就可以

![Oct-01-2021 23-57-01](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211001235711.gif)

所以最后随机合并就是

![image-20211001235838081](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211001235839.png)

本质上合并查询的复杂度`O(h)` h就是深度大小

所以说快的很。就叫Quick Union

下面Java用的数组，但是是一个奇怪的树来实现的。

```java
public class UnionFind2 implements UF {
    private int[] parent;

    public UnionFind2(int size) {
        parent = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
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
        // 不同就要把p的根节点指向q
        parent[pRoot] = qRoot;
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
```

上面2个版本的速度PK结果如下

```
int size = 100000;
int m = 10000;

UnionFind1 : 2.21875459 s
UnionFind2 : 0.01389166 s

int size = 100000;
int m = 100000;

UnionFind1 : 39.69679417 s
UnionFind2 : 64.55422291 s
```

这里为什么用2反而还慢了，是因为奇怪的树结构，最差的情况可能是一个链表。这样深度也很大。

但是版本2可以在进行优化。主要在这个步骤。`parent[pRoot] = qRoot;` 这个函数最极端的情况下，前面一个指向后面一个，会成为一个链表，而不是一个树

![image-20211002002709557](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211002002712.png)

## 版本3 考虑size

可以看到上边极端情况下可能成为链表的弊端，所以现在用size判断来解决。谁大就从小指向大的那一面。小→大。

## 版本4