import java.util.Random;

public class Main {
    // 测试使用q 测试 opCount 次数的出队 入队操作
    private static double testQueue(Queue<Integer> q, int opCount) {
        Random random = new Random();

        long startTime = System.nanoTime();
        for (int i = 0; i < opCount; i++) {
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        }
        for (int i = 0; i < opCount; i++) {
            q.dequeue();
        }
        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;

    }

    public static void main(String[] args) {
        int opCount = 100000;

        ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
        double timeArray = testQueue(arrayQueue, opCount);
        System.out.println("ArrayQueue, time: " + timeArray + "s");

        LoopQueue<Integer> loopQueue = new LoopQueue<>();
        double timeLoop = testQueue(loopQueue, opCount);
        System.out.println("LoopQueue, time: " + timeLoop + "s");

        // 这里数组队列特别拉胯的原因就在于出队的时候的操作是O(n)复杂 合起来就是n2级别
        // 但是循环队列的话 都是O(1)级别
    }
}
