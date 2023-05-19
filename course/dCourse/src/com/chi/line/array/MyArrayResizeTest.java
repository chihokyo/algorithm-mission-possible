package com.chi.line.array;

public class MyArrayResizeTest {
    public static void main(String[] args) {
        _04_MyArrayResize<Integer> myArrayGenericResize = new _04_MyArrayResize<>(4);
        myArrayGenericResize.addFirst(99);
        myArrayGenericResize.addFirst(19);
        myArrayGenericResize.add(2, 88);
        myArrayGenericResize.addLast(100);
        System.out.println(myArrayGenericResize);

        // 测试扩容了
        myArrayGenericResize.add(3,77);
        System.out.println(myArrayGenericResize);
    }
}
