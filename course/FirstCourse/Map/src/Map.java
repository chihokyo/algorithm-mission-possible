public interface Map<K, V> {
    /**
     * 增加键值对
     *
     * @param key   键
     * @param value 值
     */
    void add(K key, V value);

    /**
     * 删除key 返回value
     *
     * @param key 键
     * @return V泛型 返回值
     */
    V remove(K key);

    /**
     * 是否存在某个key
     *
     * @param key 键
     * @return boolean
     */
    boolean contains(K key);

    /**
     * 通过key获取相应的value
     *
     * @param key 键
     * @return V泛型 value
     */
    V get(K key);

    /**
     * 为key设置新的值newValue
     *
     * @param key      键
     * @param newValue 泛型 新的值
     */
    void set(K key, V newValue);

    /**
     * 获取这个映射大小
     *
     * @return int 大小
     */
    int getSize();

    /**
     * 映射是否为空
     *
     * @return boolean
     */
    boolean isEmpty();
}
