import java.util.Random;

/**
 * 测试一下链表实现的栈和数组实现的栈
 */
public class TestArrayStackLinkedArrayStack {

    private static double testStack(Stack<Integer> stack, int opCount) {
        long start = System.nanoTime();
        Random random = new Random();
        for (int i = 0; i < opCount; i++) {
            stack.push(random.nextInt(Integer.MAX_VALUE));
        }
        for (int i = 0; i < opCount; i++) {
            stack.pop();
        }
        long end = System.nanoTime();

        return (end - start) / 1000000000.0;
    }

    public static void main(String[] args) {
        int opCount = 100000;
        ArrayStack<Integer> arrayStack = new ArrayStack<>();
        double time1 = testStack(arrayStack, opCount);
        System.out.println("ArrayStack time is " + time1 + "s");

        LinkedListStack<Integer> linkedListStack = new LinkedListStack<>();
        double time2 = testStack(linkedListStack, opCount);
        System.out.println("LinkedListStack time is " + time2 + "s");

        // 会发现十万级别的时候Linked更快
        // 但是百万千万级别的时候会有一个new操作 因为这个比较其实不是什么巨大差距的差别 没有实质意义
        // 只是一个复杂度参考而已
    }
}
