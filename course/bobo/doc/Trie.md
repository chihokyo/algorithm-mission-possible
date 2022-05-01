# Trie 字典树

![image-20210919003401605](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210919003402.png)

转为字符串而生

![image-20210919195114210](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210919195115.png)

## 实现字典树

实现字典树 + 添加word

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

## 查询操作

这里查询就是一个个单词的字母看，如果符合条件就继续走下去。然后看是不是**最后一个字母**

```java
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
```

## 前缀查询

这里的前缀查询和上面的查询最大的区别就是不需要判断是否是**最后一个字母**

比如 *pay* 这个单词，我们使用*paypal* 

- 查询操作 → false

- 前缀查询 → true ※ 因为是以*pay*为前缀的

```java
/**
  * 查询是否在Trie里中以单词prefix为前缀
  *
  * @param prefix 前缀
  * @return boolean
  */
public boolean isPrefix(String prefix) {
    // 当前节点为根节点root
    Node cur = root;
    for (int i = 0; i < prefix.length(); i++) {
        // 遍历一个个字母
        char c = prefix.charAt(i);
        // 为空就是false
        if (cur.next.get(c) == null) {
            return false;
        }
        // 匹配条件，继续向下找
        cur = cur.next.get(c);
    }
    return true;
}
```

### [208. 实现 Trie (前缀树)](https://leetcode-cn.com/problems/implement-trie-prefix-tree/)

这里有一个题目可以看一下，运用了

```java
class Trie {
    // 内部类，表示一个节点
    private class Node {
        public boolean isWord;
        public TreeMap<Character, Node> next;
        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }
        public Node (){
            this(false);
        }
    }
    // 全局属性 根节点
    private Node root;

    public Trie() {
        root = new Node();
    }
    
    public void insert(String word) {
        Node cur = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if(!cur.isWord) {
            cur.isWord = true;
        }
    }
    
    public boolean search(String word) {
        Node cur = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // 开始寻找
            if(cur.next.get(c) == null) {
                return false;
            }
            // 向下走
            cur = cur.next.get(c);
        }
        // 这个时候来到了最后，就看是否属性单词结尾
        return cur.isWord;
    }
    
    public boolean startsWith(String prefix) {
        Node cur = root;
        for(int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        // 上面基本和search一样，只是不用判断是否是单词的结尾
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
```

看评论发现了一个大佬的神解！

```java
class Trie {
    private String data = ",";
    public Trie() {

    }
    
    public void insert(String word) {
        data = data + word + ",";
    }
    
    public boolean search(String word) {
        return data.contains("," + word + ",");
    }
    
    public boolean startsWith(String prefix) {
        return data.contains("," + prefix);
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
```

## 模糊查询

这里的模糊查询具体实现也是看leetcode上的一道题。

### [211. 添加与搜索单词 - 数据结构设计](https://leetcode-cn.com/problems/design-add-and-search-words-data-structure/)

这一题是让你设计一个**数据结构**，用来查找单词，但是.也代表是一个单词。这里使用了**递归**

![image-20210928165449234](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210928165457.png)

```java
class WordDictionary {

    // 内部类
    private class Node {
        // 单词结尾
        public boolean isWord;
        // 字典
        public TreeMap<Character, Node> next;
        // 一个空字典
        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }
        // 初始化不是单词结尾
        public Node() {
            this(false);
        }
    }
    // 数据结构有一个根节点
    private Node root;
    
    // 初始化一个根节点
    public WordDictionary() {
        root = new Node();
    }
    
    public void addWord(String word) {
        // 从根节点开始查找添加
        Node cur = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // 如果没找到字母，就添加
            if(cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            // 找到了说明已匹配继续向下
            cur = cur.next.get(c);
        }
        // 走到这里就是最后了，标记一下是单词结尾
        cur.isWord = true;
    }
    
    public boolean search(String word) {
        // 这里是一个递归函数，为什么用递归。
        // 树天然可以用递归，便于遍历符合.条件的所有字母

        // 这里的意思就是从根节点开始遍历word，0是word的起始
        return match(root, word, 0);
    }

    private boolean match(Node node, String word, int index) {
        // 递归终止条件，就是已经遍历完全部的word
        if(index == word.length()) {
            return node.isWord;
        }

        char c = word.charAt(index);
        // 不是.就是按照正常的流程找
        if(c != '.') {
            // 正常找的话看是否为空
            if(node.next.get(c) == null) {
                // 为空直接false
                return false;
            }
            return match(node.next.get(c), word, index + 1);
        } else {
            // 这里就说明next下面的字母全部符合条件
            // 那么就需要取得next下所有的一个个字母，
            // 然后在这一个个字母下面进行遍历，不符合条件的直接false
            // node.next.keySet() 
            for(char nextChar : node.next.keySet()) {
                // 这里又是一个递归，基于从符合条件的字母nextChar开始
                if(match(node.next.get(nextChar), word, index + 1)) {
                    return true;
                }
            }
            // 上面的一串for，代表 char c = word.charAt(i); 下所有匹配成功的 如果到这里还没有true
            return false;
        }
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
```

这里使用了一个递归，这个递归的意思就是说从根节点root开始寻找这个word。

- 终止条件 word遍历完成
- 变量，每一次index都要+1
- 变量 Node每一次都是不一样的。都是下一个。理解了这层可能，可能就比较好理解了吧。

## 映射

利用字典树解决LeetCode问题

这一题主要是读懂题目，脑海里要有这个题的一个宏图？

找不到我的Apple

### [677. 键值映射](https://leetcode-cn.com/problems/map-sum-pairs/)

```java
class MapSum {

    // 内部节点类
    private class Node {
        // 初始化
        public int value; // 值
        public TreeMap<Character, Node> next; // 节点

        public Node (int value) {
            this.value = value;
            next = new TreeMap<>();
        }
        public Node () {
            this(0);
        }
    }

    // 全局变量根节点
    private Node root;

    /** Initialize your data structure here. */
    // 构造函数里初始化一个根节点
    public MapSum() {
        root = new Node();
    }

    public void insert(String key, int val) {
        Node cur = root;
        for(int i = 0; i < key.length(); i++) {
            // 遍历每一个字母
            char c = key.charAt(i);
            // 如果为空，直接添加
            if(cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        // 走到这里说明到最后了，那么这个节点的value就是传入的value
        cur.value = val;
    }
    
    // 这个sum比较难理解，整体思路就是要找到全部的前缀并且把value相加
    public int sum(String prefix) {
        Node cur = root;
        for(int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null) {
                return 0;
            }
            cur = cur.next.get(c);
        }
        // 走到这里基本上就是找到了prefix，并且知道了prefix所对应的value是多少
        // 但是本题的要求是要找到所有的前缀单词，所以要继续找，那么就用到了递归
        // 所以以cur这个为root，继续向下找
        return sum(cur);
    }

    // 以node为根节点找所有的子节点的value并且相加
    private int sum(Node node) {

        // 递归的结束条件是什么？是没有了子树，没有子树就是node.next.size() == 0  这样就 return node.value
        // 但是这压力不需要判断，因为如果没有next 那么 node.next.keySet()就根本不会执行，本质还是return node.value


        // 这里表示以node为根的value值并返回
        // 但是实际上这个node后面可能有子树，那么就需要把所有子树的value也要相加
        int res = node.value;
        // node.next.keySet() 所有的子树key
        for(char c : node.next.keySet()) {
            // 这里开始递归，递归每一个子树
            // 递归相加
            res += sum(node.next.get(c));
        }
        return res;
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */
```

英文官方貌似3个解法。偶尔可以看一看。

[英文leetcode的3种解析](https://leetcode.com/problems/map-sum-pairs/solution/)

