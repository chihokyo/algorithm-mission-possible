package com.chi.line.linkedist;

public class DummyLinkedListTest {
    public static void main(String[] args) {
        _03_DummyLinkedList<Integer> linkedList = new _03_DummyLinkedList<>();
        linkedList.addFirst(5);
        linkedList.addFirst(9);
        linkedList.addFirst(-7);
        linkedList.add(1, 77);
        linkedList.addLast(100);
        System.out.println(linkedList.get(3));

        System.out.println(linkedList);
        linkedList.removeFirst();
        System.out.println(linkedList);
        linkedList.remove(2);
        System.out.println(linkedList);
        linkedList.removeLast();
        System.out.println(linkedList);
    }
}
