import java.util.Arrays;
import java.util.stream.IntStream;

public class LoopQueueWithoutSize<E> implements Queue<E> {
    private E[] data;
    private int front, tail;

    @SuppressWarnings("unchecked")
    public LoopQueueWithoutSize(int capacity) {
        data = (E[]) new Object[capacity + 1];
    }

    public LoopQueueWithoutSize() {
        this(10);
    }

    public int getCapacity() {
        return data.length - 1;
    }

    @Override
    public int getSize() {
        // 如果 tail >= front 那么队列实际容量就是 tail - front
        // 如果 tail < front 那么说明已经循环，队列实际容量就是
        // tail - front + data.length 注意这里的 length 实际上要大一号
        return tail >= front ? tail - front : tail - front + data.length;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public void enqueue(E e) {
        if (tail + 1 % data.length == front) {
            resize(getCapacity() * 2);
        }
        data[tail] = e;
        tail = (tail + 1) % data.length;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from an empty queue");
        }
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        if (getSize() == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        return ret;
    }

    @Override
    public E getFront() {
        return data[front];
    }

    @SuppressWarnings("unchecked")
    public void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity + 1];
        // 这里要拿出来的原因是因为后面有2个地方都要使用
        // 如果在 tail = getSize使用的话 那么那个时候计算的size也就是有问题的了
        int size = getSize();
        for (int i = 0; i < size; i++) {
            newData[i] = data[(front + i) % data.length];
        }
        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d, capacity = %d\n", getSize(), getCapacity()));
        res.append("front [");
        for (int i = front; i != tail; i = (i + 1) % data.length) {
            res.append(data[i]);
            if ((i + 1) % data.length != tail) {
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args) {
        LoopQueueWithoutSize<Integer> loopQueue3 = new LoopQueueWithoutSize<>();
        IntStream.range(0, 10).forEach(i -> {
            loopQueue3.enqueue(i);
            System.out.println(loopQueue3);
            if (i % 2 == 0) {
                loopQueue3.dequeue();
                System.out.println(loopQueue3);
            }
        });

    }
}
