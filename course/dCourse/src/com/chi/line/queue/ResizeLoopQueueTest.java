package com.chi.line.queue;

public class ResizeLoopQueueTest {
    public static void main(String[] args) {
        _04_ResizeLoopQueue<Integer> queue = new _04_ResizeLoopQueue<>(10);
        queue.enqueue(11);
        System.out.println(queue);

        queue.enqueue(22);
        System.out.println(queue);

        queue.enqueue(33);
        System.out.println(queue);

        queue.dequeue();
        System.out.println(queue);

        queue.dequeue();
        System.out.println(queue);
    }
}
