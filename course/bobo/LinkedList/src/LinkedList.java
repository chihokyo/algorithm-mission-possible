/**
 * 基础链表类
 * 一个链表应该包含节点和next信息（指针，指向下一个节点）
 */
public class LinkedList<E> {

    /**
     * 内部类 就是一个节点
     */
    private class Node {
        public E e; // 节点需要一个元素值
        public Node next; // 节点需要指向下一个Node

        /**
         * 有参数构造函数
         *
         * @param e    元素
         * @param next 指向到下一个元素到节点是什么
         */
        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        /**
         * 无参构造函数
         */
        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }


    private Node head; // 链表表头（其实就是一个元素 head只是因为是第一个元素）
    private int size; // 链表长度大小

    /**
     * 构造函数 默认是空 啥都没
     */
    public LinkedList() {
        head = null;
        size = 0;
    }

    /**
     * 获取实际长度
     *
     * @return int 长度
     */
    public int getSize() {
        return size;
    }

    /**
     * 判断是否为空
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在链表最前面添加元素
     *
     * @param e 元素
     */
    public void addFirst(E e) {
        // Node node = new Node(e, null);
        // node.next = head;
        // head = node;

        head = new Node(e, head);
        size++;
    }

    /**
     * 在链表任意位置添加元素
     * 其实这种方法在链表中并不常见
     *
     * @param index 索引
     * @param e     元素
     */
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index.");
        }
        if (index == 0) {
            addFirst(e);
        } else {
            // 暂存区域
            Node pre = head;
            // 这个时候其实已经到了你需要插入到那个位置到前一个了
            for (int i = 0; i < index - 1; i++) {
                pre = pre.next;
            }
            // Node node = new Node(e, null);
            // node.next = pre.next;
            // pre.next = node;
            // 3句话汇成一句话
            pre.next = new Node(e, pre.next);
            size++;
        }
    }

    /**
     * 在链表最后添加元素
     *
     * @param e 元素
     */
    public void addLast(E e) {
        add(size, e);
    }

}
