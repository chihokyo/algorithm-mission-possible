public interface Stack<E> {

    /**
     * 获取Stack长度
     *
     * @return int 返回长度
     */
    int getSize();

    /**
     * 判断是否为空
     *
     * @return boolean 是否为空
     */
    boolean isEmpty();

    /**
     * 入栈
     * @param e 压入的元素
     */
    void push(E e);

    /**
     * 出栈
     *
     * @return E 出栈的元素
     */
    E pop();

    /**
     * 查看栈顶元素
     *
     * @return E 返回栈顶元素
     */
    E peek();

}
