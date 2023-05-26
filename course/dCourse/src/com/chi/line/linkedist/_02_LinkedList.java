package com.chi.line.linkedist;


/**
 * 这里开始写增删改查
 * 是实现了增删改查之后的链表 在01注释过的 这里就不注释
 * 只写新增的重点
 *
 * @param <T>
 */
public class _02_LinkedList<T> {
    private class Node {
        T e;
        Node next;

        public Node(T e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(T e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node head; // 头节点
    private int size;

    public _02_LinkedList() {
        head = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    /**
     * 通过index查询元素
     *
     * @param index 索引
     * @return 查找值
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("get Failed, index is illegal");
        }
        Node cur = head; // 这里很重要 找到头节点
        // 这里要注意 大于等于size
        // 为什么这里有等于？索引+1才是长度 到了size根本就没了
        // 你成这样也可以 if (index < 0 || index > size - 1)
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.e;
    }

    /**
     * 增加一个头节点实现
     *
     * @param e
     */
    public void addFirst(T e) {
        // 1.新建一个节点
        Node newNode = new Node(e);
        // 2.将这个新节点的next指向head
        newNode.next = head;
        // 3.将新的head指向node
        head = newNode;

        // 优化1
        // Node newNode = new Node(e, head); // 直接创建一个连接到链表头的节点
        // head = newNode;// 移动链表头
        // 这里一上来看不懂 大概率是因为你需要画个图 然后你要搞懂函数的执行顺序
        // 右边 →执行顺序先执行右边的new Node(e, head); 这时会有两个node都指向 head
        // 左边 → 这个时候head会移动一下，赋值的意义在于移动一下了。

        // 优化2
        // head = new Node(e, head); // 新建之后直接给了链表头
        size++;
    }

    /**
     * 在最后index增加元素
     *
     * @param e
     */
    public void addLast(T e) {
        add(size, e);
    }

    /**
     * 在任意位置添加元素e
     *
     * @param index
     * @param e
     */
    public void add(int index, T e) {
        // 要注意这里有个坑，这里index是可以=size的。因为有可能在最后一个节点添加元素，此时最后一个节点的index就是size
        // 不同于删除，删除的话是size-1才是最后一个节点，但是add的时候，最后一个新增的节点是size-1+1也就是size
        if (index < 0 || index > size)
            throw new IllegalArgumentException("add Failed, index is illegal");

        if (index == 0) {
            addFirst(e);
        } else {
            // 1. 暂存头节点用于下面向前走
            Node prev = head;
            // 2.初始化新元素
            Node newNode = new Node(e);
            // 3.找到前一个节点
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
            }
            // 4.找到之后 先把后面的起来，再把前面的连起来！
            newNode.next = prev.next;
            prev.next = newNode;
            size++;

            // 优化1 其实和addFirst一样
            // 如果上一个能看懂，这个就必然能看懂。
            // Node newNode =  newNode(e, prev.next);
            // prev.next = newNode;

            // 优化2  无需初始化
            // prev.next = newNode(e, prev.next);
        }
    }


    /**
     * 删除头结点
     *
     * @return
     */
    public T removeFirst() {
        if (head == null) return null; // 如果为空 就直接null
        // 1.暂存链表头
        Node delNode = head;
        // 2.移动
        head = head.next;
        // 3.删除暂存的链表头
        delNode.next = null;
        size--;
        return delNode.e;
    }

    /**
     * 删除最后一个元素
     *
     * @return
     */
    public T removeLast() {
        return remove(size - 1);
    }

    /**
     * 删除任意位置的节点
     *
     * @param index
     * @return
     */
    public T remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("remove Failed, index is illegal");
        if (index == 0) return removeFirst();
        Node prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        // 1. 暂存
        Node delNode = prev.next;
        // 2. 掐断第一条线，直接略过本身指向下一个
        prev.next = delNode.next;
        // 3. 掐断第二条线 暂存指向null 彻底断开
        delNode.next = null;
        size--;
        return delNode.e;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node cur = head;
        while (cur != null) {
            // 这里+e,不+e都是可以的 因为上面重写了toString()
            // sb.append(cur.e).append("=>");
            sb.append(cur).append("->");
            cur = cur.next;
        }
        sb.append("null");
        return sb.toString();
    }
}
