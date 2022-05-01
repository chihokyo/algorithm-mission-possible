import java.text.MessageFormat;
import java.util.ArrayList;

public class BSTMap<K extends Comparable<K>, V> implements Map<K, V> {
    private class Node {
        public K key;
        public V value;
        public Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    /**
     * 返回以node为根节点的二分搜索树中 key所在的节点
     * 递归实现
     *
     * @param node 节点
     * @param key  键
     * @return Node 返回key所在的节点
     */
    private Node getNode(Node node, K key) {
        if (node == null) {
            return null;
        }
        // 已经找到了
        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else {
            return getNode(node.right, key);
        }
    }

    /**
     * 添加元素
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    // 以node为根的二分搜索树中添加kv
    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else {
            // 在二分搜索树里因为相等的情况没有所以没有考虑，但是在这里
            // 如果相同的key的话，我们会覆盖原有的值
            node.value = value;
        }
        return node;
    }

    /**
     * 删除指定key 返回value
     *
     * @param key 键
     * @return V 泛型
     */
    @Override
    public V remove(K key) {
        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        // 说明没有这个key，直接返回null
        return null;
    }

    // 删除掉以node为根的二分搜索中以key为键的节点
    // 返回新的删除节点后新的二分搜索树的根
    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else {
            // 这里相当于已经找到了，然后进行删除
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            // 左右都不为空，采用后继
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;
            node.left = node.right = null;

            return successor;
        }
    }

    // 这里是获取最小值所在的节点node(e+2个叉)
    private Node minimum(Node node) {
        // 没有左子树了 说明他就是最小的了
        if (node.left == null) {
            return node;
        }
        // 否则继续递归
        return minimum(node.left);
    }

    // 删除以node为根的二分搜索树的最小节点
    // 返回删除节点后的新的二分搜索树的根
    // 这里只要理解了上面就很容易理解，删除的是一个节点，
    // 返回的是新二分搜索树的这个根到底是什么？
    private Node removeMin(Node node) {
        // 如果没有了left，说明自己就是到底了
        // 那么要删除的就是自己，但是当前的节点如果有右子树的话怎么办
        if (node.left == null) {
            // 暂存右子树
            Node rightNode = node.right;
            // 这里让要删除脱离关系 让右子树为空，
            node.right = null;
            size--;
            // 删除了旧了，那么你就是老大，就是新的二分搜索树的根
            return rightNode;
        }
        // 还没走到底
        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 是否存在key
     *
     * @param key 键
     * @return boolean
     */
    @Override
    public boolean contains(K key) {
        // 从根节点开始找是否有元素
        return getNode(root, key) != null;
    }

    /**
     * 给key找value
     *
     * @param key 键
     * @return V 泛型 返回值
     */
    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException(key + "does not exist!!");
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
            BSTMap<String, Integer> map = new BSTMap<>();
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