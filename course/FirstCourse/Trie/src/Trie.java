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

    /**
     * 查询单词是否在Trie中
     *
     * @param word 单词
     * @return boolean
     */
    public boolean contains(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            // 遍历到第一个字符
            char c = word.charAt(i);
            // 没有这个字符，证明根本没这个单词
            if (cur.next.get(c) == null) {
                return false;
            }
            // 有这个字符，那么就继续向下找
            cur = cur.next.get(c);
        }
        // 到了这个节点，也就来到了最后
        // 要看是否是尾
        return cur.isWord;
    }
}
