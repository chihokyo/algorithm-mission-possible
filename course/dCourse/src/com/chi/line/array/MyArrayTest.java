package com.chi.line.array;

/**
 * 为了测试自己写的MyArray测试类
 */
public class MyArrayTest {
    public static void main(String[] args) {
        _02_MyArray myArray = new _02_MyArray(10);
        myArray.addFirst(99);
        myArray.addFirst(19);
        myArray.add(2, 88);
        myArray.addLast(100);
        myArray.get(0);

        System.out.println(myArray.contains(7));
        System.out.println(myArray.contains(100));
        System.out.println(myArray);
        System.out.println(myArray.removeIndex(1));
        myArray.removeElement(88);
        System.out.println(myArray);
    }
}
