package com.chi.line.array;

public class MyArrayGenericTest {
    public static void main(String[] args) {
        _03_MyArrayGeneric<Integer> myArrayGeneric = new _03_MyArrayGeneric<>(10);
        myArrayGeneric.addFirst(99);
        myArrayGeneric.addFirst(19);
        myArrayGeneric.add(2, 88);
        myArrayGeneric.addLast(100);
        myArrayGeneric.get(0);

        System.out.println(myArrayGeneric.contains(7));
        System.out.println(myArrayGeneric.contains(100));
        System.out.println(myArrayGeneric);
        System.out.println(myArrayGeneric.removeIndex(1));
        myArrayGeneric.removeElement(88);
        System.out.println(myArrayGeneric);

        /********* 开始测试字符串类型的数组***********/
        _03_MyArrayGeneric<String> myStrArr = new _03_MyArrayGeneric<>(10);
        myStrArr.add(0,"hello");
        myStrArr.addFirst("no");
        myStrArr.addLast("algo");
        System.out.println(myStrArr);
    }
}
