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

        validateIndex(index);

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

    /**
     * 获取指定位置index的元素
     *
     * @param index 指定索引
     * @return E 元素
     */
    public E get(int index) {
        validateIndex(index);
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.e;
    }

    /**
     * 获取第一个元素
     *
     * @return E 元素
     */
    public E getFirst() {
        return get(0);
    }

    /**
     * 获取最后一个元素
     *
     * @return E 元素
     */
    public E getLast() {
        return get(size - 1);
    }

    /**
     * 修改指定位置index的元素
     *
     * @param index 指定位置index
     * @param e     元素
     */
    public void set(int index, E e) {
        validateIndex(index);
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.e = e;
    }

    /**
     * 检查索引是否有问题
     *
     * @param index 索引
     */
    public void validateIndex(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Illegal index.");
        }
    }

    /**
     * 是否包含指定元素
     *
     * @param e 指定元素
     * @return boolean
     */
    public boolean contains(E e) {
        Node cur = dummyHead.next;
        while (cur != null) {
            cur = cur.next;
            if (cur.e.equals(e)) {
                return true;
            }
            // 大意了，这个地方竟然没有写结束条件 死循环啦
            cur = cur.next;
        }
        return false;
    }

    /**
     * 删除指定索引的元素
     *
     * @param index 指定索引
     * @return E 元素
     */
    public E remove(int index) {
        validateIndex(index);
        Node pre = dummyHead;
        for (int i = 0; i < index; i++) {
            pre = pre.next;
        }
        Node retNode = pre.next;
        pre.next = retNode.next;
        retNode.next = null;
        size--;
        // 这里我有误区 我第一次自己写成了retNode
        return retNode.e;
    }

    /**
     * 删除第一个元素并返回
     *
     * @return E
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 删除最后一个元素并返回
     *
     * @return E
     */
    public E removeLast() {
        return remove(size - 1);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Node cur = dummyHead.next;
        while (cur != null) {
            // idea推荐我的 这样写比较好
            // 我本来写的是这样 res.append(cur + "->");
            res.append(cur).append("->");
            cur = cur.next;
        }
        // 第二种循环写法
        // for(Node cur = dummyHead.next; cur != null; cur = cur.next) {
        //     res.append(cur).append("->");
        //     cur = cur.next;
        // }
        res.append("NULL");
        return res.toString();
    }
}
