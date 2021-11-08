package com.chi.line.linkedist;

public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addFirst(5);
        System.out.println(linkedList);
        linkedList.addLast(1);
        System.out.println(linkedList);
        linkedList.add(2, 100);
        System.out.println(linkedList);
        System.out.println(linkedList.get(1));
        linkedList.removeFirst();
        System.out.println(linkedList);
        linkedList.removeLast();
        System.out.println(linkedList);
        System.out.println(linkedList.contains(1));
    }
}
