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

但是版本2可以在进行优化。主要在这个步骤。`parent[pRoot] = qRoot;` 这个函数最极端的情况下，前面一个指向后面一个，会成为一个链表，而不是一个树。

![image-20211002002709557](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211002002712.png)

## 版本3 考虑size 少→多

可以看到上边极端情况下可能成为链表的弊端，所以现在用size判断来解决。谁大就从小指向大的那一面。小→大。

看一下下面的情况，如何现在要合并2个集合

**没有优化的情况下**，4的根节点就这样指向→9，可以看到。这样高度就是4了。

![Oct-04-2021 14-39-31](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004144039.gif)

但如果考虑到当前高度的情况，**使用优化**。让8指向→9。

![Oct-04-2021 14-39-37](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004144148.gif)

这样高度就是3，所以接下来的代码实现就是。

```java
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

```

然后速度一对比，效果拔群。

```
太快了
// UnionFind1 : 39.62142208 s
// UnionFind2 : 65.45789666 s
// UnionFind3 : 0.09888625 s
```

## 版本4 rank优化 高→矮

本质上是树的高度进行优化的。具体是如何优化的呢。

上面的版本3用的是个数，就是个数 **少→多**，如果是按照上面的size也就是个数进行合并的话会发生下面的问题。

![Oct-04-2021 14-57-32](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004145842.gif)

这个问题就是虽然以8为根节点的集合是指向了7为根节点的集合。因为8更少，但是这样高度从原来的2→4了。

所以这一次不是以个数，而是用高度 高度高的指向矮的。高→矮。

比如下面这样👇🏻

![Oct-04-2021 14-57-39](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004150040.gif)

这样高度不变，也能合并的更合理。

```java
public class UnionFind4 implements UF {
    private int[] parent;
    private int[] rank; // rank[i]表示i为root的集合中的深度

    public UnionFind4(int size) {
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
            index = parent[index];
        }
        return index;
    }
}

```

在百万的情况下。差异不是很大，但是rank优化上逻辑上更合理，所以都是使用的rank。

```
int size = 1000000;
int m = 1000000;

3和4的差距
UnionFind3 : 1.86387791 s
UnionFind4 : 1.3624425 s
```

## 版本5 路径压缩 Path Compression

![image-20211004150950932](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004150952.png)

什么是路径压缩，目的就是让上面这种高度为5的情况尽量的降低高度成为下面高度为2的那种情况。

路径压缩 → 高度减低

通过什么方式呢。向上遍历的时候同时进行路径压缩。

就是像👇🏻这样**指向你父亲的父亲**

![Oct-04-2021 15-12-47](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004151309.gif)

然后从2开始遍历

![Oct-04-2021 22-28-18](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004222836.gif)

也就是说，在查询2的时候顺便给压缩到了**2的父亲的父亲**也就是0上面。0已经是根节点了，也就不需要了。

最后得到的结果就是 **从原来的高度5 → 现在是3**

![image-20211004223105312](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004223107.png)

代码实现

```java
public class UnionFind5 implements UF {
    private int[] parent;
    private int[] rank; // rank[i]表示i为root的集合中的深度

    public UnionFind5(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1; // 这个其实是一个序，而不是一个高度。
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

```

因为这里路径压缩之后，find时候深度下降了。那么效率也就提升了。

## 版本6 路径再压缩-递归

因为上面的路径压缩可以发现一个问题，就是其实那样的压缩深度还是3，并不是最少的。深度还可以更小。就像👇🏻这样。

![image-20211004224950163](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004224951.png)

```java
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
 
```

这里实现之后会发现一个问题就是，有可能速度并不是最快的，可能和版本5有的时候还差点，因为版本5在一些情况下也是可以直接全部指向根节点的，也就是说深度也有可能是2的，只不过要多执行几次。也就是说多调用几次`find4`，因为版本5本身也不是使用递归实现的。

![image-20211004230341555](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004230343.png)

## 时间复杂度

合并和查找都是

`O(h)` h也就是深度

如果使用了压缩，严格意义上👇🏻

![image-20211004225618839](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211004225620.png)

上面的时间复杂度并不是重点，而且并查集本身这个数据结构也不是考察的特别多。可以忽略。