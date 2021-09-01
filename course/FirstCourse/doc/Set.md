# 集合Set

没有重复数据的的数据结构。

先写一个接口，下来无论用什么底层数据结构，都要实现这些方法。

```java
/**
 * Set接口
 *
 * @param <E> 泛型
 */
public interface Set<E> {
    void add(E e);

    void remove(E e);

    boolean contains(E e);

    int getSize();

    boolean isEmpty();
}

```

## 二分搜索树实现集合

使用二分搜索树来实现，二分搜索树默认就是没有重复数据的。

```java
/**
 * 使用BST二分搜索树来实现集合
 *
 * @param <E> 泛型
 */
public class BSTSet<E extends Comparable<E>> implements Set<E> {

    private BST<E> bst;

    public BSTSet() {
        bst = new BST<>();
    }

    @Override
    public void add(E e) {
        bst.add(e);
    }

    @Override
    public void remove(E e) {
        bst.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return bst.contains(e);
    }

    @Override
    public int getSize() {
        return bst.size();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}
```

上面那个实现前提是以前曾经实现了二分搜索树这个结构

代码太长了，直接看这个就可以。

[二分搜索树数据结构实现](https://github.com/chihokyo/algorithm-mission-possible/blob/e77551fc3cfc6ee032efc5e36bbe7f7516664595/course/FirstCourse/Set/src/BST.java)

## 链表实现集合

二分搜索树和链表，都是动态数据结构。

为了体现和表现二分搜索树的优越性。

```java
import java.util.ArrayList;

public class LinkedListSet<E> implements Set<E> {

    // 线性结构这里并不要求有可比性

    private LinkedList<E> list;

    public LinkedListSet() {
        list = new LinkedList<>();
    }

    @Override
    public void add(E e) {
        // 因为原来实现的时候并没有保证链表中又无重复的元素
        if (!list.contains(e)) {
            // 链表头添加O(1)级别
            list.addFirst(e);
        }
    }

    @Override
    public void remove(E e) {
        list.removeElement(e);
    }

    @Override
    public boolean contains(E e) {
        return list.contains(e);
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    public static void main(String[] args) {

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        FileOperation.readFile("pride-prejudice.txt", words);
        System.out.println("Total words: " + words.size()); // 一共多少单词

        LinkedListSet<String> set = new LinkedListSet<>();
        for (String word : words) {
            set.add(word);
        }
        System.out.println("Total different words: " + set.getSize()); // 使用集合计算不同单词
    }
}

```

## 集合的复杂度分析

上面可以看出来**链表是慢于平衡二叉树**的。

写一个测试速度函数

```java
import java.util.ArrayList;

public class Main {
    private static double testSet(Set<String> set, String filename) {
        long startTime = System.nanoTime();

        System.out.println(filename);
        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());
            for (String word : words) {
                set.add(word);
            }
            System.out.println("Total different words: " + set.getSize());
        }

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000_000.0;

    }

    public static void main(String[] args) {
        String filename = "pride-prejudice.txt";
        BSTSet<String> bstSet = new BSTSet<>();
        double time1 = testSet(bstSet, filename);
        System.out.println("BST Set: " + time1 + " s");

        System.out.println();

        LinkedListSet<String> linkedListSet = new LinkedListSet<>();
        double time2 = testSet(linkedListSet, filename);
        System.out.println("LinkedList Set: " + time2 + " s");
    }
}
```

计算出来的速度 可以看出来确实平衡二叉树比较快

```
pride-prejudice.txt
Total words: 125901
Total different words: 6530
BST Set: 0.127746125 s

pride-prejudice.txt
Total words: 125901
Total different words: 6530
LinkedList Set: 1.42124525 s
```

因为链表在查找是否有重复元素的时候，所以增（1） + 查（n） 的出来增就是`O(n)`

链表删除的话，也需要先找。

|      | LinkedList Set | BST Set                                      |
| ---- | -------------- | -------------------------------------------- |
| 增   | `O(n)`         | `O(h)` → 二分搜索树的高度 → 平均： `O(logN)` |
| 查   | `O(n)`         | `O(h)` → 二分搜索树的高度 → 平均： `O(logN)` |
| 删   | `O(n)`         | `O(h)` → 二分搜索树的高度 → 平均： `O(logN)` |

那么n和高度h有什么关系呢。

```
0层 1节点

1层 2节点

2层 4节点

...

h-1层就是 2＾(h-1)节点
所以就是等比数列最后计算出来就是 h = log2(n+1) →log2(n)→log(n)
```

所以这个级别就是log级别的。h和logn数字越大，差距越大。

**但是也要看情况，因为二分搜索树在特殊情况下也有可能退化成链表。**

## LeetCode集合算法

[804. 唯一摩尔斯密码词](https://leetcode-cn.com/problems/unique-morse-code-words/)

treeset平衡二叉树来实现，实际上哈希表也可以实现。

```java
class Solution {
    public int uniqueMorseRepresentations(String[] words) {
        String[] codes = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        TreeSet<String> set = new TreeSet<>();
        for(String word: words) {
            StringBuilder res = new StringBuilder();
            for(int i = 0; i < word.length(); i++) {
                // 这里的出来的 应该是字母对应的顺序数字 word.charAt(i) - 'a'] a对应的就是0 b 1 c 2 这里对应了 ascii
                res.append(codes[word.charAt(i) - 'a']);
            }
            // 相同的会被忽略 不被添加
            set.add(res.toString());
        }
        return set.size();
    }
}
```

#### 有序集合 无序集合

链表实现的是无序的 ，是由插入顺序决定的。

二分搜索树是有序的，通常有序集合都是基于搜索树实现的。但是无序的集合也有很好的实现，就是哈希表。

#### 还有一种多重集合

这种事可以有重复元素的

