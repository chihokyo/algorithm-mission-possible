package com.chi.line.array;

/**
 * 因为内置的数组功能太少
 * 所以在这里写一个自己的数组
 * 主要实现了增删改查
 */
public class _02_MyArray {
    int[] data; // 数组本身
    int capacity; // 这里表示的是容量 也就是数组的固定大小
    int size; // 这里表示的是实际大小 也就是真实有多少数据

    // 初始化
    public _02_MyArray(int capacity) {
        this.capacity = capacity;
        this.data = new int[capacity];
        this.size = 0; // 此时还没有任何数据
    }

    // 初始化一个默认长度
    public _02_MyArray() {
        this(15); // 此时默认15
    }

    // 判断是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * 增 在指定位置
     * 思路就是从后向前开始遍历，然后移位
     * 这个后就是最后一个，这个前，就是直到index 这个理解是最难的 index到底取不取得到
     * 遇到了index之后就放进去
     */
    public void add(int index, int e) {
        // 检查数组是否已满
        if (size == data.length) {
            throw new IllegalArgumentException("add 失败 已满");
        }
        // 检查index区间
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index 超出范围");
        }
        // 【这个循环重点】 这里因为遍历是从后向前走 移动是每一个都向后 index本身也要向后一下的
        // 所以是必须=index的
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++; // 这个不能忘记
    }

    public void addFirst(int e) {
        add(0, e);
    }

    public void addLast(int e) {
        add(size - 1, e);
    }

    /**
     * 获取index的元素
     */
    public int get(int index) {
        // 检查index区间
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index 超出范围");
        }
        return data[index];
    }

    /**
     * 找到指定元素 返回index
     */
    public int find(int e) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == e) return i;
        }
        return -1;
    }

    /**
     * 查询是否存在某个元素
     *
     * @param target
     * @return
     */
    public boolean contains(int target) {
        for (int num : data) {
            if (num == target) return true;
        }
        return false;
    }

    /**
     * 修改操作
     */
    public void set(int index, int e) {
        // 检查index区间
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index 超出范围");
        }

        data[index] = e;
    }

    /**
     * 删除操作
     * 删除其实有2种 删除指定index
     */
    public int removeIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index 超出范围");
        }
        int temp = data[index];
        // 这里是重点 两种思考都可以
        // a 这里是从后向前走
        // for (int i = size - 1; i > index; i--) {
        //     data[i - 1] = data[i];
        // }
        // b 这里是从后向前走 两个答案都是对的
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        return temp;
    }

    /**
     * 删除操作
     * 删除其实有2种 删除指定元素
     */
    public void removeElement(int e) {
        // 先找到
        int index = find(e);
        // 找到了直接删除指定index就可以
        if (index != -1) {
            removeIndex(index);
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Array:size = %d, capacity = %d\n", size, data.length));
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i != size - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}

