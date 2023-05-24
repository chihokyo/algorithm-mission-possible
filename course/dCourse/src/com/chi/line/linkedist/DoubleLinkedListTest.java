package com.chi.line.linkedist;

public class DoubleLinkedListTest {
    public static void main(String[] args) {
        _04_DoubleLinkedList list = new _04_DoubleLinkedList();
        list.addFirst(2);
        System.out.println(list);

        list.addLast(5);
        System.out.println(list);
        list.add(1, 88);
        list.set(1, 99);
        list.addFirst(76);
        System.out.println(list);
        System.out.println("size is " + list.getSize());
        list.removeLast();
        System.out.println(list);
        list.remove(1);
        System.out.println(list);
        list.removeFirst();
        System.out.println(list);
    }
}
