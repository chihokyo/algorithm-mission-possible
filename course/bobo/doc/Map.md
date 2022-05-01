# Map（映射）

其实本质就是` Key-Value`，键值对

可以形象的理解是一种函数表现`f(x) = 2*x + 1`，或者是字典等等。比如身份证号，数据库唯一id，单词词频统计等等，一个萝卜一个坑就是。

## 如何实现？

开始写，其实就是多了个k和v

不过先写个接口

```java
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
```

## 链表实现

链表只有一个指向那是怎么实现的呢。首先看下面有个内部类，这个内部类就是定义了一个适合Map数据类型的链表

```java
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
```

然后利用这个链表内部类初始化这个Map数据类型。

这里是定义了一个虚拟头。

```java
// 这里开始用链表初始化一个映射
private Node dummyHead;
private int size;

// 初始化这个映射数据类型
public LinkedListMap() {
    dummyHead = new Node();
    size = 0;
}

```

接下来就是完整的操作了。

```java
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

```

## 二分搜索树实现

首先你要有一个二分搜索树的内部类，因为有键值对。所以和以往的二分搜索树并不一样。

首先初始化一个为映射Map而生的二分搜索树的内部类。

```java
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
```

然后这个Map结构就是这样的，这里主要注意下根节点的初始化。

```java
private Node root;
private int size;

public BSTMap() {
    root = null;
    size = 0;
}
```

然后具体到增删改查的完整版就是↓ ，这里使用到了以前二分搜索树的**增删改查**。删除是最难的，因为考虑情况比较多。忘记的可以看BST那一章的doc文件。这里的K因为是二分搜索树，是有顺序的，所以还特地实现了`Comparable`

```java
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
```

## 复杂度分析

首先这个复杂度是在上面的集合里分析过的。

然后具体的代码测试

```java
import java.text.MessageFormat;
import java.util.ArrayList;

public class Main {
    private static double testMap(Map<String, Integer> map, String filename) {
        long startTime = System.nanoTime();

        System.out.println(filename);
        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());
            for (String word : words) {
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

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000_000.0;

    }

    public static void main(String[] args) {
        String filename = "pride-prejudice.txt";
        BSTMap<String, Integer> bstMap = new BSTMap<>();
        double time1 = testMap(bstMap, filename);
        System.out.println("BST Set: " + time1 + " s");

        System.out.println();

        LinkedListMap<String, Integer> linkedListMap = new LinkedListMap<>();
        double time2 = testMap(linkedListMap, filename);
        System.out.println("LinkedList Set: " + time2 + " s");
    }
}
```

|                | LinkedListMap | BSTMap | 平均      | 最差   |
| -------------- | ------------- | ------ | --------- | ------ |
| 增`add()`      | `O(n)`        | `O(h)` | `O(logN)` | `O(n)` |
| 删`remove()`   | `O(n)`        | `O(h)` | `O(logN)` | `O(n)` |
| 改`set()`      | `O(n)`        | `O(h)` | `O(logN)` | `O(n)` |
| 查`get()`      | `O(n)`        | `O(h)` | `O(logN)` | `O(n)` |
| 查`contains()` | `O(n)`        | `O(h)` | `O(logN)` | `O(n)` |

### 集合和映射的关系

其实映射的value为null的情况下，就是集合。所以集合和映射之间是有关系的。2者可以互相实现。