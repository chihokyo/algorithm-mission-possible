package com.chi.line.queue;

import java.util.Random;

/**
 * 性能测试
 */
public class PerformanceTest {
    private static Random random = new Random();

    private static double testQueue(Queue<Integer> queue, int cnt) {
        long startTime = System.nanoTime();

        for (int i = 0; i < cnt; i++) {
            queue.enqueue(random.nextInt());
        }
        for (int i = 0; i < cnt; i++) {
            queue.dequeue();
        }

        return (System.nanoTime() - startTime) / 1000_000_000.0;
    }

    public static void main(String[] args) {
        int cnt = 100000;
        Queue<Integer> queue = new _01_ArrayQueue<>();
        // double time1 = testQueue(queue, cnt);
        // System.out.println("_01_ArrayQueue 普通静态数组队列 花费的时间：" + time1);
        //
        // queue = new _02_LinkedListQueue<>();
        // double time2 = testQueue(queue, cnt);
        // System.out.println("_02_LinkedListQueue 普通链表队列 花费的时间：" + time2);

        queue = new _04_ResizeLoopQueue<>(20);
        double time3 = testQueue(queue, cnt);
        System.out.println("_04_ResizeLoopQueue 循环队列[数组优化] 花费的时间：" + time3);

        queue = new _05_UpLinkedListQueue<>();
        double time4 = testQueue(queue, cnt);
        System.out.println("_05_UpLinkedListQueue 队列[链表优化] 花费的时间：" + time4);

        // 双端队列测试
        _06_DoubleLinkedDeque<Integer> deque = new _06_DoubleLinkedDeque<>();
        deque.addFirst(22);
        deque.addFirst(23);
        deque.addLast(25);
        deque.addLast(88);
        deque.addLast(109);
        System.out.println(deque.getFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.getFirst());
        deque.removeLast();
        System.out.println(deque.getLast());

        _07_ArrayDeque<Integer> deque1 = new _07_ArrayDeque<>();
        deque.addFirst(22);
        deque.addFirst(23);
        deque.addLast(25);
        deque.addLast(88);
        deque.addLast(109);
        System.out.println(deque.getFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.getFirst());
        deque.removeLast();
        System.out.println(deque.getLast());


    }
}
