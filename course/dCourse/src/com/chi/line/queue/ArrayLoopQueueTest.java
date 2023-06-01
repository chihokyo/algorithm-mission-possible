package com.chi.line.queue;

public class ArrayLoopQueueTest {
    public static void main(String[] args) {
        _03_ArrayLoopQueue<Integer> queue = new _03_ArrayLoopQueue<>(5);
        queue.enqueue(10);
        System.out.println(queue);

        queue.enqueue(20);
        System.out.println(queue);

        queue.enqueue(30);
        System.out.println(queue);

        queue.dequeue();
        System.out.println(queue);

        queue.dequeue();
        System.out.println(queue);

        // 测试循环
        queue.enqueue(40);
        System.out.println(queue);
        queue.enqueue(50);
        System.out.println(queue);
        queue.enqueue(60);
        System.out.println(queue);
        // 测试报错
        queue.enqueue(70);
        System.out.println(queue);
    }
}
