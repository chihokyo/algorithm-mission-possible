package com.chi.line.linkedist;

public class LinkedListTest {

    public static void main(String[] args) {
        // 测试普通链表
        _02_LinkedList<Integer> linkedList = new _02_LinkedList<>();
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
