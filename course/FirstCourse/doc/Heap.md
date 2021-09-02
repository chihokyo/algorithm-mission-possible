# Heap（堆）

## 基本概念

首先我看的这个教程是从优先队列开始引入堆这个概念的。

**Q：什么是优先队列？**

就是你在银行排队的时候，有人是VIP，即使你来的比他早，但是人家就是先上柜台办理业务的那种天龙人。

**Q：关于优先队列数据结构分析？**

关于出入队的思路的话，2种数据结构的思路。

|              | 入队                                                   | 出队（拿出最大元素）                |
| ------------ | ------------------------------------------------------ | ----------------------------------- |
| 普通线性结构 | `O(1)`                                                 | `O(n)` 因为你要扫描一遍才知道谁最大 |
| 顺序线性结构 | `O(n)`因为你入队的时候也要扫描一遍，才知道我是什么档次 | `O(1)`                              |

所以以上结构来说的话，只要涉及到了n那么，效率总归是很低的。所以引出来了*堆*

**Q：为什么用堆？**

下面再来看。反正复杂度最差情况都是`O(log)`级别的。

我以前写过一篇文章，关于树讲得十分详细。[『二叉树』详解「广度遍历・深度遍历」的Python实现](https://chihokyo.com/post/17/)

主要的思想就是说了什么是**树→二叉树→完全二叉树→堆→堆化→堆排序**

树 → 就是无论少个叉都可以，只要是分叉结构

二叉树 → 就是只能有2个叉

下面这几个概念可以区分一下，下面的图实在太棒了。

|                          | 描述                                                         | 样子省略 |
| ------------------------ | ------------------------------------------------------------ | -------- |
| **perfect binary tree**  | 又叫满二叉树，很完美，很饱满，一个深度为k（>=-1）且有2(k+1)-1个结点的二叉树称为完美二叉树（满二叉树） |          |
| **complete binary tree** | 从根节点到倒数第二层都满足完美二叉树，**最后一层可以不完全填充，但是叶子结点都应该靠左对齐。** |          |
| **full binary tree**     | 非叶子，只要有孩子，孩子必须是俩。即使畸形的树。             |          |

![img](https://miro.medium.com/max/16000/1*CMGFtehu01ZEBgzHG71sMg.png)

## 堆的基本结构

**一个堆就是一个特殊的树！** 那么二叉堆就是特殊的二叉树。特殊在哪里呢？

<u>以下所说的都是最大堆</u>

- 二叉堆是一个**完全二叉树**（上图的**complete binary tree**），特点就是可以不满，但是人家按照顺序来。不满的部位一定是右下角咯。
- 堆中的某个节点一定是小于父节点的。同层可以不一定，但是老子一定大于孩子。

![image-20210902165633879](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210902165634.png)

可以用数组来表示

![image-20210902170034285](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210902170035.png)

**Q：从父如何找孩子？**

parent = i 

- left孩子 = `2 * i`
- right孩子 = `2 * i + 1`

**Q：从孩子如何找父亲？**

孩子 / 2 = parent 无论左右，反正都是取整，那么0.5是被抹去的。比如3/2=1

**Q：为什么要空出来那个0的位置？不空出来可以吗？**

因为为了好计算。当然也可以不偏移，但是计算就可以重新计算。

![image-20210902170137909](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210902170209.png)

那么计算公式就是

![image-20210902170227756](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210902170228.png)

### Array代码实现

首先用动态数组实现一个完全二叉树。其实就是新建了一个数组。并且初始化了一下。

```java
private Array<E> data;

public MaxHeap(int capacity) {
    data = new Array<>(capacity);
}

public MaxHeap() {
    data = new Array<>();
}
```

完整的实现如下

```java
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
```

