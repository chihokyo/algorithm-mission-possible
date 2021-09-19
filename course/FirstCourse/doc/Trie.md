# Trie 字典树

![image-20210919003401605](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210919003402.png)

转为字符串而生

![image-20210919195114210](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210919195115.png)

```java
import java.util.TreeMap;

public class Trie {
    // 内部类
    private class Node {
        // 是否是单词结尾
        public boolean isWord;
        // 指向下一个字符，这个结构不用泛型类
        // 在指向下一个字符之前就已经确定
        public TreeMap<Character, Node> next;

        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node() {
            this(false);
        }
    }

    private Node root;
    private int size;

    public Trie() {
        root = new Node();
        size = 0;
    }

    public int getSize() {
        return size;
    }

    /**
     * 向Trie中添加新单词
     *
     * @param word 添加的单词
     */
    public void add(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if (!cur.isWord) {
            cur.isWord = true;
            size++;
        }
    }
}
```

