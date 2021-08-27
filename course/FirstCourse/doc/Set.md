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

