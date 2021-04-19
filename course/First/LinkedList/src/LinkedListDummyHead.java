/**
 * 基础链表类 - 有虚拟头节点
 * 一个链表应该包含节点和next信息（指针，指向下一个节点）
 */
public class LinkedListDummyHead<E> {
    private class Node {
        private E e;
        private Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    public Node dummyHead; // 虚拟头结点
    private int size; // 链表长度

    public LinkedListDummyHead() {
        // 这个就是意义。改变的地方。
        // 不同于 head = null;
        // 这个的意义就在于是有一个节点的 只不过为空
        dummyHead = new Node(null, null);
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
     * 在链表任意位置添加元素
     * 其实这种方法在链表中并不常见
     *
     * @param index 索引
     * @param e     元素
     */
    public void add(int index, E e) {

        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index. ");
        }

        Node pre = dummyHead;
        // 为什么用了index 而不是index - 1 因为上一个是从0 而这一个是从 dummyHead
        for (int i = 0; i < index; i++) {
            pre = pre.next;
        }
        Node node = new Node(e, null);
        node.next = pre.next;
        pre.next = node;
        size++;
    }

    /**
     * 链表头部添加元素 node
     *
     * @param e 元素 node
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 链表尾部添加元素 node
     *
     * @param e 元素 node
     */
    public void addLast(E e) {
        add(size, e);
    }
}
