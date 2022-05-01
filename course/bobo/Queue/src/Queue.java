public interface Queue<E> {

    /**
     * 入队
     *
     * @param e 需要加入的队列元素
     */
    void enqueue(E e);

    /**
     * 出队
     *
     * @return 出来的队列元素
     */
    E dequeue();

    /**
     * 获取队首元素
     *
     * @return 队首元素
     */
    E getFront();

    /**
     * 获取队列长度
     *
     * @return int 队列长度
     */
    int getSize();

    /**
     * 是否为空
     *
     * @return boolean
     */
    boolean isEmpty();
}
