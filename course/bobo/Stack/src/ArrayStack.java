public class ArrayStack<E> implements Stack<E> {

    // 成员变量 动态数组
    Array<E> array;

    /**
     * 有容量的构造函数
     *
     * @param capacity 容量数值
     */
    public ArrayStack(int capacity) {
        array = new Array<>(capacity);
    }

    /**
     * 无参构造函数
     */
    public ArrayStack() {
        array = new Array<>();
    }

    /**
     * 获取实际容量
     *
     * @return int 实际大小
     */
    @Override
    public int getSize() {
        return array.getSize();
    }

    /**
     * 查看是否为空
     *
     * @return boolean 是否为空
     */
    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * 这里Stack接口没有的原因是因为
     * 动态数组特有的概念 需要知道多少容积
     *
     * @return int 容积
     */
    public int getCapacity() {
        return array.getCapacity();
    }

    /**
     * ArrayStack 实现
     *
     * @param e 压入的元素
     */
    @Override
    public void push(E e) {
        array.addLast(e);
    }

    /**
     * ArrayStack 实现
     *
     * @return E
     */
    @Override
    public E pop() {
        return array.removeLast();
    }

    /**
     * ArrayStack 实现
     *
     * @return E
     */
    @Override
    public E peek() {
        return array.getFirst();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Stack: ");
        res.append("[");
        for (int i = 0; i < array.getSize(); i++) {
            res.append(array.get(i));
            if (i != array.getSize() - 1) {
                res.append(",");
            }
        }
        res.append("] Top");
        return res.toString();
    }
}
