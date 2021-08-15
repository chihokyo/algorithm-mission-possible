# 树结构

## 基本概念

以前我在用Python写的时候的文章可以用来参考

[『二叉树』详解「广度遍历・深度遍历」的Python实现](https://chihokyo.com/post/17/)

**Q：二叉树是什么？**

- 二叉树具有唯一的根节点
- 二叉树每个节点最多有2个孩子
- 二叉树每个节点最多有1个父亲

```java
class Node {
    E e;
    Node left;
    Node right;
}
```

二叉树具有天然的递归结构 → 每个节点的左子树也是二叉树，每个节点的右子树也是二叉树。

**二叉树不一定是满的。** → 只有1个节点也是二叉树，空也是二叉树。

**Q：二分搜索树是什么？**

**二分搜索树首先也是二叉树！**

- 大于其左子树所有节点的值
- 小于其右子树所有节点的值
- 每一颗子树都是二叉搜索树

```
	   18
 10			  28
8   12	   20	39
```

 存储的元素必须有可比较性。

## 创造一个二分搜索树

注意1 私有内部类的实现

注意2 初始化的数值

注意3 一个二分搜索树肯定也有根结点

```java
public class BST<E extends Comparable<E>> {

    // 节点 私有内部类，用户不知
    private class Node {
        public E e;
        public Node left, right;

        // 构造函数 初始化
        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }
    }

    // 二分搜索树 肯定要有根结点
    private Node root;
    // 大小
    private int size;

    // 这个构造函数其实你不写默认也是对象null，数字0
    // 这里是显式的写了出来
    public BST() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
```

