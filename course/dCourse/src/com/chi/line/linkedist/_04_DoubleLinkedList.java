package com.chi.line.linkedist;

import java.util.NoSuchElementException;

/**
 * 初始化一个最简单的双向链表
 *
 * @param <T>
 */
public class _04_DoubleLinkedList<T> {
    // 1.和单向链表一样 初始化虚拟接地那
    private class Node {
        T e;
        Node prev; // 2.前后都要有
        Node next;

        public Node(Node prev, T e, Node next) {
            this.prev = prev;
            this.e = e;
            this.next = next;
        }

        // 3.初始化一下
        public Node(T e) {
            this(null, e, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    // 4.头节点尾结点 也就是说一个节点初始化的时候 是 <-null-> 是这种感觉
    private Node first;
    private Node last;
    private int size;

    // 5. 初始化
    public _04_DoubleLinkedList() {
        first = last = null; // <-null->
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 通过索引找元素
     * 时间复杂度O(1/2n) 也就是O(n)
     *
     * @param index
     * @return
     */
    public T get(int index) {
        Node node = node(index);
        if (node == null) throw new IllegalArgumentException("index failed");
        return node.e;
    }

    // 时间复杂度 O(1) 双向链表优势
    public Node getFirst() {
        return first;
    }

    // 时间复杂度 O(1) 双向链表优势
    public Node getLast() {
        return last;
    }

    /**
     * 完成了node之后 这里方便多了
     * 时间复杂度O(n)
     *
     * @param index
     * @param e
     */
    public void set(int index, T e) {
        Node node = node(index);
        if (node == null) throw new IllegalArgumentException("index failed");
        node.e = e;
    }

    /**
     * 通过index找节点，注意是node，不是找值
     * 时间复杂度O(1/2n) 也就是O(n)
     *
     * @param index
     * @return
     */
    private Node node(int index) {
        // 说明节点就是空
        if (index < 0 || index >= size) return null;
        Node cur;
        // 小于1/2 从前向后找
        if (index < size / 2) {
            cur = first;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
            // 从后向前找
        } else {
            cur = last;
            // 这里十分重点 因为index是从0开始 如果你从后向前 就必须-1
            // 如果这里不懂 建议就自己画图 for的重点永远在于次数
            for (int i = 0; i < size - index - 1; i++) {
                // 向前移动
                cur = cur.prev;
            }
        }
        return cur;
    }

    /**
     * 在双链表头节点添加
     * 时间复杂度O(1)
     *
     * @param e
     */
    public void addFirst(T e) {
        // 然后判断链表此时为空与否 如果是空，就直接新增一个链表
        Node newNode = new Node(e);
        // 如果头节点为空 说明链表中没有一个节点
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            // 如果不为空，那么就要找到现在的头 把新节点的next指向现在的头，然后修改prev的指向
            newNode.next = first; // 新 → 旧
            first.prev = newNode; // 旧 双方向箭头 新 （完成双向奔赴）
            first = newNode; // 指向新的first
        }
        size++;
    }

    /**
     * 在双链表尾节点添加
     * 时间复杂度O(1)
     *
     * @param e
     */
    public void addLast(T e) {
        Node newNode = new Node(e);
        // 如果此时尾结点为空，那就是说明链表没有节点
        // 那么新插入 既是头节点 也是尾结点
        if (last == null) {
            first = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    /**
     * 在双链表index增加元素
     * 时间复杂度O(n)
     *
     * @param index
     * @param e
     */
    public void add(int index, T e) {
        // 这里可以等于size 等于size就是表尾添加
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index failed");
        }
        if (index == 0) addFirst(e);
        if (index == size) addLast(e);
        // 先找到index所在的oldNode
        Node oldNode = node(index);
        Node prevNode = oldNode.prev;
        // 新建Node
        Node newNode = new Node(prevNode, e, oldNode);
        // 下面开始双向奔赴
        oldNode.prev = newNode; // 画图 后一个节点 →
        prevNode.next = newNode;// 画图 前一个节点 →
        size++;
    }

    /**
     * 删除头节点
     * 时间复杂度O(1)
     *
     * @return
     */
    public T removeFirst() {
        // 空节点
        if (first == null) throw new NoSuchElementException();
        // 暂存
        T e = first.e;
        Node next = first.next;
        // 说明只有1个节点
        if (next == null) {
            last = null;
            first = null;
        } else {
            // 走到这里说明不只是一个 接下来断连
            first.next = null;
            next.prev = null;
            first = next;
        }
        size--;
        return e;
    }

    /**
     * 删除尾节点
     * 时间复杂度O(1)
     *
     * @return
     */
    public T removeLast() {
        if (first == null) throw new NoSuchElementException();
        // 先暂存
        T e = last.e;
        Node prev = last.prev;
        // 说明只有1个节点
        if (prev == null) {
            last = null;
            first = null;
        } else {
            prev.next = null;
            last.prev = null;
            last = prev;
        }
        size--;
        return e;
    }

    /**
     * 删除任意index的节点
     * 时间复杂度(n)
     *
     * @param index
     * @return
     */
    public T remove(int index) {
        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();
        Node delNode = node(index);
        // 找到前后节点
        Node pre = delNode.prev;
        Node nex = delNode.next;
        // 双向奔赴
        pre.next = nex;
        nex.prev = pre;
        // 完成断连删除
        delNode.prev = null;
        delNode.next = null;
        size--;
        return delNode.e;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node cur = first;
        while (cur != null) {
            // 这里+e,不+e都是可以的 因为上面重写了toString()
            // sb.append(cur.e).append("=>");
            sb.append(cur).append("<=>");
            cur = cur.next;
        }
        sb.append("null");
        return sb.toString();
    }
}
