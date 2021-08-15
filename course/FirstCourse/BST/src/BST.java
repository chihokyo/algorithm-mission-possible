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
