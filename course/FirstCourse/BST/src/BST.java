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

    // // 改进前
    // public void add(E e) {
    //     if (root == null) {
    //         root = new Node(e);
    //     } else {
    //         add(root, e);
    //     }
    // }
    // private void add(Node node, E e) {
    //     // 是否重复
    //     if (e.equals(node.e)) {
    //         return;
    //         // 这里是判定空
    //     } else if (e.compareTo(node.e) < 0 && node.left == null) {
    //         node.left = new Node(e);
    //         size++;
    //         return;
    //         // 这里是判定空
    //     } else if (e.compareTo(node.e) > 0 && node.right == null) {
    //         node.right = new Node(e);
    //         size++;
    //         return;
    //     }
    //     // 这里其实是一个递归调用
    //     if (e.compareTo(node.e) < 0) {
    //         add(node.left, e);
    //     } else {
    //         // e.compareTo(node.e) > 0 因为等于的情况已经判断过了
    //         add(node.right, e);
    //     }
    //     /*************上面的判断的太臃肿***************/
    //     // 原因就是第一次写的判断并没有递归到底
    // }

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

}
