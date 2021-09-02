import java.text.MessageFormat;
import java.util.ArrayList;

public class LinkedListMap<K, V> implements Map<K, V> {

    private class Node {
        // 这里是为了实现映射而设计的一个链表数据结构内部类
        public K key;
        public V value;
        public Node next;

        // 初始化这里有kv
        public Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        // 初始化这里只有k
        public Node(K key) {
            this(key, null, null);
        }

        // 初始化这里什么都没
        public Node() {
            this(null, null, null);
        }

        @Override
        public String toString() {
            return key.toString() + " : " + value.toString();
        }
    }

    // 这里开始用链表初始化一个映射
    private Node dummyHead;
    private int size;

    // 初始化这个映射数据类型
    public LinkedListMap() {
        dummyHead = new Node();
        size = 0;
    }

    /**
     * 为了接下来的操作，给你一个key从链表上取得节点
     *
     * @param key 目标key
     * @return Node 节点
     */
    private Node getNode(K key) {
        // 虚拟头节点
        Node cur = dummyHead.next;
        // 只要不为空
        while (cur != null) {
            // 当此前节点的key等于目标key
            if (cur.key.equals(key)) {
                return cur;
            }
            cur = cur.next;
        }
        // 遍历到最后没结果 返回null
        return null;
    }

    /**
     * 添加一个键值对
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void add(K key, V value) {
        // 因为不允许存在2个相同的key 所以要先看是否存在
        // 存在就覆盖，不存在就添加
        Node node = getNode(key);
        if (node == null) {
            // 这里一定要看懂，为什么第3个参数，相当于增加了一个节点
            dummyHead.next = new Node(key, value, dummyHead.next);
            size++;
        } else {
            node.value = value;
        }

    }

    /**
     * 删除key 返回相应的value
     *
     * @param key 键
     * @return V 泛型 v值
     */
    @Override
    public V remove(K key) {
        Node prev = dummyHead;
        // 只要不为空就遍历
        while (prev.next != null) {
            // 如果找到了就退出
            if (prev.next.key.equals(key)) {
                break;
            }
            // 向下走
            prev = prev.next;
        }
        if (prev.next != null) {
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            // 以上操作成功删除了你的目标节点，同时还保存了一下(prev.next)
            size--;
            return delNode.value;
        }
        // 根本没找到 直接null
        return null;
    }

    /**
     * key 是否存在
     *
     * @param key 键
     * @return boolean
     */
    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    /**
     * 通过key获取value
     *
     * @param key 键
     * @return V 值
     */
    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    /**
     * 通过key设置一个新的value
     *
     * @param key      键
     * @param newValue 泛型 新的值
     */
    @Override
    public void set(K key, V newValue) {
        // 还是要先找，找到了就添加，没找到就报错
        Node node = getNode(key);
        if (node == null) {
            throw new IllegalArgumentException(key + " does not exist!");
        }
        node.value = newValue;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        String filename = "pride-prejudice.txt";
        System.out.println(filename);
        // 用于存储文本所有的单词
        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());
            // 链表集合 key 单词 value 频率值
            LinkedListMap<String, Integer> map = new LinkedListMap<>();
            // 找到并且循环每一个单词
            for (String word : words) {
                // 原来有就继续+1，没有设置为1
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1);
                } else {
                    map.add(word, 1);
                }
            }
            System.out.println("Total different words: " + map.getSize());
            String targetWord1 = "pride";
            String targetWord2 = "prejudice";
            System.out.println(MessageFormat.format("Frequency of {0}: {1}", targetWord1, map.get(targetWord1)));
            System.out.println(MessageFormat.format("Frequency of {0}: {1}", targetWord2, map.get(targetWord2)));
        }
    }
}
