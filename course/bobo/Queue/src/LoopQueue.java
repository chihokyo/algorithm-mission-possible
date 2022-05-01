public class LoopQueue<E> implements Queue<E> {
    private E[] data;
    private int front, tail;
    private int size;

    /**
     * 有参构造函数
     *
     * @param capacity 容量
     */
    @SuppressWarnings("unchecked")
    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1];
    }

    /**
     * 无参构造函数
     */
    public LoopQueue() {
        this(10);
    }

    /**
     * 循环队列容量
     *
     * @return int 返回大小
     */
    public int getCapacity() {
        // 这里的-1 和上面有参的构造函数的 + 1
        // 都是因为用这个循环队列的时候
        // 判断循环队列是否已经满的时候 需要 tail + 1
        return data.length - 1;
    }

    /**
     * 循环队列实际大小
     *
     * @return int 大小
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * 循环队列判断是否为空
     *
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        // 头尾碰一起 就是到头了
        // 因为也有可能是满了 所以才有了 tail + 1 = front 的概念
        return front == tail;
    }

    @Override
    public void enqueue(E e) {
        // 这里其实是最难理解
        // 建议画图
        if ((tail + 1) % data.length == front) {
            resize(getCapacity() * 2);
        }
        // tail位置上+
        data[tail] = e;
        // 指针后移
        // tail ++; 错误
        tail = (tail + 1) % data.length;
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }

        E res = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        // 判断震荡
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        return res;
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }
        return data[front];
    }

    @SuppressWarnings("unchecked")
    public void resize(int newCapacity) {
        // 有可能队首元素不是0
        // 新队列从队首开始计算
        // 所以 newData[0] -- data[front]; newData[1] -- data[front + 1]
        // data所有元素 是有 front 偏移
        // 又因为是循环，所以会产生越界。所以需要和长度取余
        E[] newData = (E[]) new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(i + front) % data.length];
        }
        data = newData;
        front = 0;
        // 元素个数 并没有影响
        tail = size;

    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
        res.append("front [");
        // 遍历方式要注意 front 到 tail
        for (int i = front; i != tail; i = (i + 1) % data.length) {
            res.append(data[i]);
            if ((i + 1) % data.length != tail)
                res.append(", ");
        }
        res.append("] tail");
        return res.toString();
    }


    public static void main(String[] args) {
        LoopQueue<Integer> loopQueue = new LoopQueue<>();
        for (int i = 0; i < 10; i++) {
            loopQueue.enqueue(i);
            System.out.println(loopQueue);
            if (i % 3 == 2) {
                loopQueue.dequeue();
                System.out.println(loopQueue);
            }
        }

    }

}
