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

### 关于层序遍历

其实前面的前序中序后序都是一个深度遍历，都是根据

