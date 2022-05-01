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

接下来说的二分搜索树都是不包含重复元素的，因为定义的时候就是说左边的都是小于的，右边的都是大于的，那么如果以后想包含重复元素。可以定义成，小于等于，大于等于。（之前的数组和链表都是可以有重复元素的）

### 添加元素

二分搜索树的非递归写法，和链表很像。这里关注递归实现。（二分搜索树，递归比非递归容易）

#### 改进前

```java
// 改进前
    public void add(E e) {
        if (root == null) {
            root = new Node(e);
        } else {
            add(root, e);
        }
    }
    private void add(Node node, E e) {
        // 是否重复
        if (e.equals(node.e)) {
            return;
            // 这里是判定空
        } else if (e.compareTo(node.e) < 0 && node.left == null) {
            node.left = new Node(e);
            size++;
            return;
            // 这里是判定空
        } else if (e.compareTo(node.e) > 0 && node.right == null) {
            node.right = new Node(e);
            size++;
            return;
        }
        // 这里其实是一个递归调用
        if (e.compareTo(node.e) < 0) {
            add(node.left, e);
        } else {
            // e.compareTo(node.e) > 0 因为等于的情况已经判断过了
            add(node.right, e);
        }
        /*************上面的判断的太臃肿***************/
        // 原因就是第一次写的判断并没有递归到底
    }
```

#### 改进后

```java
/**
     * 向二分搜索树添加新元素e
     *
     * @param e 新元素
     */
    public void add(E e) {
        // 这个就直接以root为根节点 想成是插入到根节点，
        root = add(root, e);
    }

    /**
     * 给node为根的二分搜索树插入元素e，递归算法
     *
     * @param node 节点
     * @param e    元素
     */
    private Node add(Node node, E e) {
        // 如果为空，那么直接生成一个以e元素的根结点
        if (node == null) {
            size++;
            return new Node(e); // 这里一定要写成return new ，而不是直接new Node，因为是要挂到根节点上
        }
        if (e.compareTo(node.e) < 0) {
            // 这里本质上其实是一个递归调用
            // 左子树添加元素，最后都将给这个左子树重新赋值
            // 重新赋值之后，依然是以node为根都二分搜索树
            // 这个函数的意思就是在添加e到node的时候本质上你是不知道添加到哪里的，只有在进行完之后，你才能确定的了这个node在哪里
            node.left = add(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            node.right = add(node.right, e);
        }
        // 以上可以看到省略了==0 也就是相等的情况，其实相等的话就是什么都不干，因为二分搜索树这里是没有重复元素都
        return node; // 啥都
    }
```

#### 这段代码错误在哪里？

```java
private void add(Node node, E e){
    if(node == null){
        size ++;
        node = new Node(e); // 这个node，只是一个变量，在函数执行完之后就会小时
        return;
    }

    if(e.compareTo(node.e) < 0)
        add(node.left, e);
    else if(e.compareTo(node.e) > 0)
        add(node.right, e);
}
```

因为 **根本就没有挂接！**

`node = new Node(e);return;`操作，node在这里只是一个临时变量，在函数执行完之后就会消失，并不会返回什么数据。

在这个实现中，node = new Node(e);这句话将node指向了新创建的节点。但是，在return以后，node这个变量消失，node所指向的新创建的节点，也就没有别的引用变量指向它了。对于一个没有引用的内存空间，我们新创建的Node，很快就会被 GC 回收。

#### 如果使用非递归的方法？

非递归的基本上就是循环，

```java
/**
  * 非递归实现二分搜索树的添加元素e
  *
  * @param e 元素
  */
public void addNotR(E e) {
    if (root == null) {
        root = new Node(e);
        size++;
        return;
    }
    // 不为空，那么循环遍历开始
    // p的作用就是用来跟踪上一个节点，临时存储
    Node p = root;
    // 循环开始，终止条件，p是一个空的null
    while (p != null) {
        // 开始判断这个元素是大于root，还是小于root
        if (e.compareTo(p.e) < 0) {
            // 小于0，放到左边
            if (p.left == null) {
                p.left = new Node(e);
                size++;
                return; // 为什么直接return，因为这时候已经找到了，并且添加完毕
            }
            // 这里第一次我没能理解，其实就是向下（left的方向）走，
            p = p.left;
        } else if (e.compareTo(p.e) > 0) {
            if (p.right == null) {
                p.right = new Node(e);
                size++;
                return;
            }
            p = p.right;
        } else {
            // 如果待插入的值等于当前 p 节点的值，说明二分搜索树中已经有这个值了
            return;
        }
    }
}
```

## 查询操作

因为查询也只是查询有没有，所以对每个节点`node`进行递归调用查找。

```java
/**
  * 查询二分搜索树里是否有元素e
  *
  * @param e 元素e
  * @return boolean 是否存在
  */
public boolean contains(E e) {
    return contains(root, e);
}

// 以node为根的二分搜索树是否有e 递归实现
// 这里的递归，大半夜终于开窍了以下，其实就是一个个递归检查node
private boolean contains(Node node, E e) {
    if (root == null) {
        return false;
    }
    if (e.compareTo(node.e) == 0) {
        return true;
    } else if (e.compareTo(node.e) > 0) {
        return contains(root.left, e);
    } else {
        return contains(root.right, e);
    }
}
```

### 前序遍历

中 → 左 → 右的顺序

![image-20210818012908034](https://raw.githubusercontent.com/chihokyo/image_host/master/20210818012914.png)

代码如下

```java
/**
  * 二分搜索树前序遍历
  */
public void preOrder() {
    preOrder(root);
}

// 前序遍历以node为根递归进行遍历
private void preOrder(Node node) {
    if (node == null) {
        return;
    }
    System.out.println(node.e);
    preOrder(node.left);
    preOrder(node.right);
}
```

为了让自己更好的去了解，看一下这个遍历的图。

![image-20210818015625337](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210818015627.png)

这样无论是从微观(324,526,56)，还是从宏观来看(5根，3根，6根)。都是一个**中 ，左，右**的顺序

顺便写了一个直接打印树结构的`toString()`

```java
@Override
public String toString() {
    StringBuilder res = new StringBuilder();
    generateBSTString(root, 0, res);
    return res.toString();
}

private void generateBSTString(Node node, int depth, StringBuilder res) {
    if (node == null) {
        res.append(generateDepthString(depth)).append("null\n");
        return;
    }
    res.append(generateDepthString(depth)).append(node.e).append("\n");
    generateBSTString(node.left, depth + 1, res);
    generateBSTString(node.right, depth + 1, res);
}

// 根据层数输出*，根节点就是0
private String generateDepthString(int depth) {
    // StringBuilder res = new StringBuilder();
    // for (int i = 0; i < depth; i++) {
    //     res.append("*");
    // }
    // return res.toString();
    // 上面一大堆，IDE提醒我可以这样替换。
    // 那要我干什么
    return "*".repeat(Math.max(0, depth));
}
```

### 中序遍历

**左→中→右**

因为左子树都要比根小，右子树都比根大。所以中序遍历天然的出来就是一个已经排序好的。

![image-20210819004306033](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210819004307.png)

```java
/**
 * 二分搜索树中序遍历
 */
public void inOrder() {
    inOrder(root);
}

private void inOrder(Node node) {
    if (node == null) {
        return;
    }
    inOrder(node.left);
    System.out.println(node.e);
    inOrder(node.right);
}
```

### 后序遍历

**左→右→中**

后续排序有一个应用，就是为二分搜索树释放内存。因为是把孩子全部给释放出来。

![image-20210819004330744](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210819004331.png)

```java
/**
 * 二分搜索树后序遍历
 */
public void postOrder() {
    postOrder(root);
}

private void postOrder(Node node) {
    if (node == null) {
        return;
    }
    postOrder(node.left);
    postOrder(node.right);
    System.out.println(node.e);
}
```

> 本质上就是根在什么时候访问，就叫什么。比如前序，就是根前，中序，就是根中访问。

总结一下规律的图，应该就是这样。

![image-20210819004401236](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210819004403.png)

### 前序遍历非递归写法

示意图，要有一个栈用来存储临时的节点。按照下面这个逻辑，以没一个节点访问的顺序，访问root之后，就看有没有**右左子树**，按照右，左的顺序压栈，因为后入先出！

![image-20210819005255184](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210819005256.png)

```java
/**
  * 非递归实现二分搜索树前序遍历
  */
public void preOrderNotR() {
    // 新建一个栈用来存储
    // 压入root
    // 判断一下是否为空
    // 不为空就pop弹出然后记录以下，以便于下一次寻找左右子树
    // 打印输出
    // 如果左子树不为空，压
    // 如果右子树不为空，压
    Stack<Node> stack = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        Node cur = stack.pop();
        System.out.println(cur.e);
        if (cur.right != null) {
            stack.push(cur.right);
        }
        if (cur.left != null) {
            stack.push(cur.left);
        }
    }
}
```

可以看出来非递归实现的话，需要多余的一个数据结构来实现。

### 关于层序遍历 → BFS

其实前面的前序中序后序都是一个深度遍历，都是根据深度优先的。层序就是一层一层遍历，先遍历完一层在进入下一层进行遍历。

**DFS** => Depth First Search 深度优先
**BFS** => Breadth First Search 广度优先

我自己写的一篇文章曾经用Python实现了这俩遍历。[『二叉树』详解「广度遍历・深度遍历」的Python实现](https://chihokyo.com/post/17/)

![image-20210819011545070](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210819011546.png)

代码实现

```java
/**
  * 二分搜索树的层序遍历（广度优先，一层层来）
  */
public void levelOrder() {
    // 队列数据类型 因为队列是个接口 所以要用链表多态实现
    // 根节点入队
    // 如果节点不为空
    // 出队并且保存临时节点
    // 输出这个临时节点元素
    // 如果临时节点左边不为空，入队
    // 如果临时节点右边不为空，入队
    Queue<Node> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
        Node cur = queue.remove();
        System.out.println(cur.e);
        if (cur.left != null) {
            queue.add(cur.left);
        }
        if (cur.right != null) {
            queue.add(cur.right);
        }
    }
}
```

**广度优先遍历的意义**

在寻找一个值的时候广度其实是有优势的，因为是一层层的进行遍历，所以比起深度那种一直到最低层的遍历来说，可以在最早的阶段找到你想要的值，比如一个值在一棵树的中间层，那么你一次次的深度到底也不能找到，但是层序的话到中间也许就能找到了。

常用于算法设计中，最短路径。

## 删除操作

### 删除最小值和最大值

要删除最大值和最小值，最重要的是先找到最大值和最小值。

如果一个节点的最left之后没有值了，说明这个值就是最小值。最大值也同理。

```java
/**
  * 二分搜索树：获取最小值
  *
  * @return 返回最小值
  */
public E minimum() {
    if (size == 0) {
        throw new IllegalArgumentException("BST is Empty");
    }
    // 这里获取的只是最小的节点，如果要值，需要.e
    return minimum(root).e;
}

// 这里是获取最小值所在的节点node(e+2个叉)
private Node minimum(Node node) {
    // 没有左子树了 说明他就是最小的了
    if (node.left == null) {
        return node;
    }
    // 否则继续递归
    return minimum(node.left);
}

/**
  * 二分搜索树：获取最大值
  *
  * @return 返回最大值
  */
public E maximum() {
    if (size == 0) {
        throw new IllegalArgumentException("BST is Empty");
    }
    return maximum(root).e;
}

// 这里是获取最大值所在的节点node(e+2个叉)
private Node maximum(Node node) {
    if (node.right == null) {
        return node;
    }
    return maximum(node.right);
}
```

那么删除的逻辑也就不是这么难书写了。

如果最小值是叶子节点→那么直接就删除这个节点就行，很easy

![image-20210824003506545](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210824003514.png)

但如果是 **22** 那种非叶子节点，有33和37节点的情况的话。

- 删除
- 整个右子树变成左子树（为什么是右子树变左子树，因为肯定没有左子树的，不然就不是最小咯）

![image-20210824003907281](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210824003908.png)

所以代码实现删除最小&最大元素

```java
/**
  * 二分搜索树：删除最小值所在节点，返回最小值
  *
  * @return 最小值
  */
public E removeMin() {
    E ret = minimum();
    // 这点和add方法很像，关于为什么要返回并且root接收
    // 主要是看下面的方法实现
    root = removeMin(root);
    return ret;
}

// 删除以node为根的二分搜索树的最小节点
// 返回删除节点后的新的二分搜索树的根
// 这里只要理解了上面就很容易理解，删除的是一个节点，
// 返回的是新二分搜索树的这个根到底是什么？
private Node removeMin(Node node) {
    // 如果没有了left，说明自己就是到底了
    // 那么要删除的就是自己，但是当前的节点如果有右子树的话怎么办
    if (node.left == null) {
        // 暂存右子树
        Node rightNode = node.right;
        // 这里让要删除脱离关系 让右子树为空，
        node.right = null;
        size--;
        // 删除了旧了，那么你就是老大，就是新的二分搜索树的根
        return rightNode;
    }
    // 还没走到底
    node.left = removeMin(node.left);
    return node;
}

/**
  * 二分搜索树：删除最大值所在节点，返回最大值
  *
  * @return 最大值
  */
public E removeMax() {
    E ret = maximum();
    root = removeMax(root);
    return ret;
}

private Node removeMax(Node node) {
    if (node.right == null) {
        Node leftNode = node.left;
        node.left = null;
        size--;
        return leftNode;
    }
    node.right = removeMax(node.right);
    return node;
}
```

### 删除任意元素

这个删除任意元素其实是分情况的，感觉比较复杂。

删除的第一步就是先找，找到之后才能删除。找到之后又分为下面的情况。

- 只有左子树 → 删除下图**29这个node**

- 只有右子树→ 删除下图**25这个node**

- 左右子树都有 （**最麻烦**）

  - **后继** ①找到比待删除的节点更大的最小节点（右子树的最小节点）②用这个节点顶替待删除的节点
  - **前驱**①找到比待删除节点更小的最大节点（左子树的最大节点）②用这个节点顶替待删除的节点

  ![image-20210824173701236](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210824173702.png)

看图就可以看出来了，书写代码的话也会有点麻烦。

```java
/**
  * 二分搜索树：删除任意元素
  *
  * @param e 要删除的值
  */
public void remove(E e) {
    root = remove(root, e);
}

// 删除以node为节点的二分搜索树里为e的值
// 返回删除节点后新的二分搜索树的根
private Node remove(Node node, E e) {
    if (node == null) return null;
    if (e.compareTo(node.e) < 0) {
        node.left = remove(node.left, e);
        return node;
    } else if (e.compareTo(node.e) > 0) {
        node.right = remove(node.right, e);
        return node;
    } else { // e == node.e 已经找到了 开始删除
        // 左边是空，那么直接把右边的挂在新的上
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        // 右边是空，那么直接把左边的挂在新的上
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        // 左右都不是空
        // 后继的话 就是右子树里最小的，顶替掉待删除的节点
        Node successor = minimum(node.right); // 找到这个最小的保存
        successor.right = removeMin(node.right); // 这里最难理解分解一下remove这个操作相当于删除并且返回此时的根节点，这个时候返回的正好给了替代品的右侧 右边挂靠成功
        successor.left = node.left; // 左边挂靠
        node.left = node.right = null; // 左右都结束了 清空
        return successor; // 返回这个节点
    }
}
```

