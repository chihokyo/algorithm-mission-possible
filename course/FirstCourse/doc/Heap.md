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

如果说`parent = i `的话

- `left孩子 = 2 * i`
- `right孩子 = 2 * i + 1`

**Q：从孩子如何找父亲？**

`孩子 / 2 = parent` 无论左右，反正都是取整，那么0.5是被抹去的。比如3/2=1

**Q：为什么要空出来那个0的位置？不空出来可以吗？**

因为为了好计算。当然也可以不偏移，但是计算就可以重新计算。

![image-20210902170137909](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210902170209.png)

那么计算公式就是

![image-20210902170227756](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210902170228.png)

### Array代码实现

首先用[动态数组](https://github.com/chihokyo/algorithm-mission-possible/blob/master/course/FirstCourse/Heap/src/Array.java)实现一个完全二叉树。其实就是新建了一个数组。并且初始化了一下。

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

## 添加元素Sift Up

上面的只是了写了一些最基本的属性和方法，那么如何添加元素呢。其实向堆里面添加元素这个本身并不是很难，就像给一个数组末尾添加一个value一样

```
[1, 2, 5, 7, newValue]
```

但是比较难的是，添加元素之后你还能维持你的堆结构，意思就是你添加之后还要**维持堆结构**。不然这就不是一个堆了。

**Q：如何添加之后还能维持堆结构？**

在末尾添加的元素，那么问题只有可能发生在**末尾元素的父节点和父节点的父节点**身上，所以只要对比父节点进行**交换**即可。

![Sep-03-2021 12-59-54](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210903130032.gif)

所以继续，然后对比41，继续交换，最后对比62，发现小于，那就不动了。（因为都已经比父亲小了，说明也比所有的祖先小）**交换结束。**

这种上浮的过程，就是**sift up**

所以添加操作就是这样实现的

```java
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
```

## 取出堆中最大元素Sift Down

堆中的最大元素就是**堆顶元素。** extract max，相当于是索引为0的元素。取出来之后，这样就分成2个堆了。2个堆合成1个堆的并不是很容易，于是就有了以下的技巧。

- 把最后一个元素复制到了堆顶root
- 然后删除最后一个元素

![Sep-03-2021 13-30-54](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210903133127.gif)

这样就发生了一样的问题，虽然还是完全二叉树**complete binary tree**，就不是**最大堆**结构了。所以就有了下沉操作**sift down**

- 跟自己俩孩子对比，跟俩孩子里**最大**的比较大小，如果小，就交换。
- 直到孩子没有比自己更大的

![Sep-03-2021 13-34-41](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210903133558.gif)

所以取出最大堆max代码思路应该就是这样的

- 找到最大堆元素并缓存
- 交换堆顶元素和最堆底元素（此时，最大元素就跑到了**堆底**）
- 删除堆底元素
- 维持最大堆结构
- 返回最开始缓存的最大堆元素

```java
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
        // 循环的变量
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
```



## 堆排序

