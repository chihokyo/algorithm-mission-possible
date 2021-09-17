# 线段树区间树 Segment Tree

为什么要有这个线段树？

区间染色（更新区间）

查询操作（查询区间）

## 什么是线段树？

![img](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210917225512.jpg)

平衡二叉树 + 每一个节点存储的是区间信息（可以是**加减乘除**）。

当然也要看情况，如果长度是一个2<sup>n</sup>次。👇🏻那么就是**满二叉树**

![image-20210917231400631](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210917231401.png)

那么如果不是呢？

就是一个平衡二叉树

![image-20210917231441992](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210917231442.png)

**如果区间内有n个元素，那么需要多少个节点？**

![image-20210917231529936](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210917231531.png)

![image-20210917231557703](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210917231558.png)

这也是初始化一个线段树的时候，为什么需要这样一个4n空间的原因。

## 实现一个线段树

这里使用的是数组存储的线段树，也就是说进去的是一个数组。出来的是一个线段树。下面是一个加法的例子。

- Input`{-2, 0, 3, -5, 2, -1};`
- Output`{[-3,1,-4,-2,3,-3,-1,-2,0,null,null,-5,2,null,null,null,null,null,null,null,null,null,null,null]};`

#### 代码实现

```java
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

```

逻辑接口 *Merger.java*

```java
public interface Merger<E> {
    E merge(E a, E b);
}
```

验证 *Main.java*

```java
public class Main {
    public static void main(String[] args) {
        Integer[] nums = {-2, 0, 3, -5, 2, -1};
        // 写法1
        // SegmentTree<Integer> segmentTree = new SegmentTree<>(nums, new Merger<Integer>() {
        //     @Override
        //     public Integer merge(Integer a, Integer b) {
        //         return a + b;
        //     }
        // });
        // 写法2
        // SegmentTree<Integer> segmentTree = new SegmentTree<>(nums, (a, b) -> a + b);

        SegmentTree<Integer> segmentTree = new SegmentTree<>(nums, Integer::sum);

        System.out.println(segmentTree);
    }
}
```



