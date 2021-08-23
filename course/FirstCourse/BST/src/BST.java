import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<E extends Comparable<E>> {

    // 节点内部类 表示一个节点 对外不暴露
    private class Node {
        public E e;
        public Node left, right;

        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }
    }

    // 属性 二分搜索树的根节点
    private Node root;

    // 属性 二分搜索树的大小
    private int size;

    // 构造函数 初始化根节点和大小
    public BST() {
        root = null;
        size = 0;
    }

    // 方法 获取二分搜索树的大小
    public int size() {
        return size;
    }

    // 方法 判断二分搜索树是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 二分搜索树：递归add值(本质是加上一个node)
     *
     * @param e 值
     */
    public void add(E e) {
        // 以root为根节点，添加e
        root = add(root, e);
    }

    private Node add(Node node, E e) {
        // 如果为空 大小+。返回一个新的node
        if (node == null) {
            size++;
            return new Node(e); //这里一定要写成return 而不是直接new Node，因为是要挂到根节点上
        }
        // 如果不为空，按照二分搜索树的特性，要看左右子树
        if (e.compareTo(node.e) < 0) {
            // 递归调用
            // 比较难以的理解的地方是这个递归过程，可以用{5,3}这样一个例子走一下流程
            // 3在添加到5后面的时候，add(5.left, 3) 这样的感觉，这时候5.left因为是空，走上面递归结束
            // ① 添加不只是一个元素，而是一个元素+2个根叉(left,right)这样进来的
            // ②无论添加到哪里，最后肯定变成叶子，所以早晚都可以执行到上面的递归结束判断
            node.left = add(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            node.right = add(node.right, e);
        }
        // 这里为什么要返回
        // 以上可以看到省略了==0 也就是相等的情况，其实相等的话就是什么都不干
        // 因为二分搜索树这里我们假设是没有重复元素的
        return node;
    }

    /**
     * 二分搜索树：非递归add值
     *
     * @param e 值
     */
    public void addNotR(E e) {
        if (root == null) {
            // 这里就相当于已经挂上了
            root = new Node(e);
            size++;
            return; // 添加完成，必须要return
        }
        // 临时存储上一个值，用来记忆，好找下一个
        // 递归就不需要，和链表很像
        Node p = root;
        // 只要不为空，就是没找到自己合适的位置
        while (p != null) {
            if (e.compareTo(p.e) < 0) {
                if (p.left == null) {
                    p.left = new Node(e);
                    size++;
                    return;
                }
                // 其实就是向下（left的方向）走
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

    /**
     * 二分搜索树：是否存在元素contains
     *
     * @param e 需要查找的元素
     * @return 布尔
     */
    public boolean contains(E e) {
        return contains(root, e);
    }

    private boolean contains(Node node, E e) {
        // 判断节点是否为空
        if (node == null) {
            return false;
        }
        if (e.compareTo(node.e) == 0) {
            return true;
        } else if (e.compareTo(node.e) < 0) {
            return contains(node.left, e);
        } else {
            return contains(node.right, e);
        }
    }

    /**
     * 二分搜索树：前序遍历
     * 访问顺序 根→左→右
     */
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 二分搜索树：前序遍历 非递归实现
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

    /**
     * 二分搜索树：中序遍历
     * 访问顺序 左→根→右
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

    /**
     * 二分搜索树：中序遍历
     * 访问顺序 左→右→根
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

    /**
     * 二分搜索树：层序遍历 广度优先，一层层来）
     */
    public void levelOrder() {
        // 需要辅助的数据结构
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
}