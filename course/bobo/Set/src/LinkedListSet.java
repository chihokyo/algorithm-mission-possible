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
