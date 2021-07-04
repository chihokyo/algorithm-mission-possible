/**
 * 基于链表的队列类
 *
 * @param <E>
 */
public class LinkedListQueue<E> implements Queue<E> {
    public class Node {
        public E e;
        public Node next;

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

    private Node head, tail;
    private int size;

    public LinkedListQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enqueue(E e) {
        // 因为利用了tail删除的话复杂度会增加的问题
        // 所以默认从tail进行增加删除
        // 先判断tail是否为空 如果tail为空 front 也肯定为空
        if (tail == null) {
            tail = new Node(e, null);
            head = tail;
        } else {
            // 这个else证明链表不是为空
            tail.next = new Node(e, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }
        Node retNode = head;
        head = head.next;
        ;
        retNode.next = null;
        // 如果这个时候链表中只有一个元素的话
        // 出队操作就最后都为空
        if (head == null) {
            tail = null;
        }
        size--;
        return retNode.e;
    }

    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }
        return head.e;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        // front 负责出队
        res.append("LinkedListQueue: front ");
        Node cur = head;
        while (cur != null) {
            res.append(cur).append("->");
            cur = cur.next;
        }
        res.append("NULL tail");
        return res.toString();
    }

    public static void main(String[] args) {
        LinkedListQueue<Integer> linkedListQueue = new LinkedListQueue<>();
        for (int i = 0; i < 10; i++) {
            linkedListQueue.enqueue(i);
            System.out.println(linkedListQueue);
            if (i % 3 == 2) {
                linkedListQueue.dequeue();
                System.out.println(linkedListQueue);
            }
        }

    }
}
