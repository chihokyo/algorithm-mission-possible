/**
 * 双端队列
 * 其实就是可以向前+- 也可以从后+-的一个特殊队列
 */
public class Deque<E> {

    private E[] data;
    private int front, tail;
    private int size;

    @SuppressWarnings("unchecked")
    public Deque(int capacity) {
        data = (E[]) new Object[capacity];
        front = 0;
        tail = 0;
        size = 0;
    }

    public Deque() {
        this(10);
    }

    public int getCapacity() {
        return data.length;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addLast(E e) {
        if (size == getCapacity()) {
            resize(getCapacity() * 2);
        }
        data[tail] = e;
        tail = (tail + 1) % data.length;
        size++;
    }

    public void addFront(E e) {
        if (size == getCapacity()) {
            resize(getCapacity() * 2);
        }
        front = front == 0 ? data.length - 1 : front - 1;
        data[front] = e;
        size++;
    }

    public E removeFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        if (getSize() == getCapacity() / 4 && getSize() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        return ret;
    }

    public E removeLast() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }
        tail = tail == 0 ? data.length - 1 : tail - 1;
        E ret = data[tail];
        tail = (tail + 1) % data.length;
        size--;
        if (getSize() == getCapacity() / 4 && getSize() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        return ret;
    }

    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deque is empty.");
        }
        return data[front];
    }

    public E getLast() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deque is empty.");
        }
        // 因为 tail 指向的是队尾元素的下一个位置，我们需要计算一下真正队尾元素的索引
        int index = tail == 0 ? data.length - 1 : tail - 1;
        return data[index];
    }


    @SuppressWarnings("unchecked")
    public void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
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
        res.append(String.format("Queue: size = %d , capacity = %d\n", getSize(), getCapacity()));
        res.append("front [");
        for (int i = 0; i < size; i++) {
            res.append(data[(i + front) % data.length]);
            if (i != size - 1)
                res.append(", ");
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        for (int i = 0; i < 10; i++) {
            deque.addLast(i);
            System.out.println(deque);
            if (i % 2 == 0) {
                deque.removeLast();
                System.out.println(deque);
            }
        }

    }

}
