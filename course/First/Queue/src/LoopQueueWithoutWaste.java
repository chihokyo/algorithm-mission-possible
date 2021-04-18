import java.util.Arrays;

public class LoopQueueWithoutWaste<E> implements Queue<E> {
    private E[] data;
    private int front, tail;
    private int size;

    @SuppressWarnings("unchecked")
    public LoopQueueWithoutWaste(int capacity) {
        data = (E[]) new Object[capacity];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueueWithoutWaste() {
        this(10);
    }

    public int getCapacity() {
        return data.length;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Queue is empty");
        }
        return data[front];
    }

    @Override
    public void enqueue(E e) {
        if (size == getCapacity()) {
            resize(getCapacity() * 2);
        }
        data[tail] = e;
        tail = (tail + 1) % data.length;
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(front + i % data.length)];
        }
        // 上面复制过之后 先改变指向
        data = newData;
        // 头部指向0
        front = 0;
        // 尾部直接是size就是最后
        tail = size;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("LoopQueueWithoutWaste: size = %d , capacity = %d\n", size, getCapacity()));
        res.append("front [");
        // 这里一定要记住遍历的是size（实际容量）
        for (int i = 0; i < size; i++) {
            // (front + i) % data.length
            // 这里从头开始 + i 遍历的就是实际的开头然后从i
            res.append(data[(front + i) % data.length]);
            if ((i + front + 1) % data.length != tail) {
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args) {
        LoopQueueWithoutWaste<Integer> loopQueue2 = new LoopQueueWithoutWaste<>();
        for (int i = 0; i < 10; i++) {
            loopQueue2.enqueue(i);
            System.out.println(loopQueue2);
            if (i % 2 == 0) {
                loopQueue2.dequeue();
                System.out.println(loopQueue2);
            }
        }

    }
}
