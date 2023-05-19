package com.chi.line.array;

/**
 * 这里是扩容缩容 版本这里重点说缩容扩充
 * 因为java内置数组本身长度是不可变的 所以如果你想增加数组的容量
 * 就需要重新复制一份在新的数组里
 */
public class _04_MyArrayResize<T> {
    T[] data; // 数组本身
    int capacity; // 这里表示的是容量 也就是数组的固定大小
    int size;

    public _04_MyArrayResize(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.size = 0; // 此时还没有任何数据
    }

    public _04_MyArrayResize() {
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
     * 时间复杂度 O(n) 这里要计算最差的时间复杂度 比如index为0 那么循环就是最大
     */
    public void add(int index, T e) {
        // 检查index区间
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index 超出范围");
        }
        // 发现满了之后 直接扩容*2
        if (size == data.length) {
            resize(data.length * 2);
        }
        // 【这个循环重点】 这里因为遍历是从后向前走 移动是每一个都向后 index本身也要向后一下的
        // 所以是必须=index的
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++; // 这个不能忘记
    }

    // 时间复杂度O(n)
    public void addFirst(T e) {
        add(0, e);
    }

    // 时间复杂度O(1) 循环体根本不会执行
    public void addLast(T e) {
        add(size - 1, e);
    }

    /**
     * 获取index的元素
     * // 时间复杂度O(1)
     */
    public T get(int index) {
        // 检查index区间
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index 超出范围");
        }
        return data[index];
    }

    /**
     * 找到指定元素 返回index
     */
    public int find(T e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) return i;
        }
        return -1;
    }

    /**
     * 查询是否存在某个元素
     * <p>
     * 时间复杂度O(n)
     */
    public boolean contains(T target) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(target)) return true;
        }
        return false;
    }

    /**
     * 修改操作
     * 时间复杂度O(1)
     */
    public void set(int index, T e) {
        // 检查index区间
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index 超出范围");
        }

        data[index] = e;
    }

    /**
     * 删除操作
     * 删除其实有2种 删除指定index
     * 时间复杂度O(n) 也是找最差
     */
    public T removeIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index 超出范围");
        }
        T temp = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        data[size] = null; // 这里是清除不用的对象 因为虽然你--了，但是此时data[size]本身还是在的

        // 下面就是缩容重点 为了解决2个问题 1个是时间复杂度震荡 1个是无限缩小
        if (size < data.length / 4 && data.length / 2 != 0) {
            resize(data.length / 2);
        }
        return temp;
    }

    /**
     * 删除操作
     * 删除其实有2种 删除指定元素
     * 时间复杂度O(n) 这里算法有俩O(n) 所以合起来就是O(n)
     */
    public void removeElement(T e) {
        // 先找到
        int index = find(e);
        // 找到了直接删除指定index就可以
        if (index != -1) {
            removeIndex(index);
        }
    }

    /**
     * 扩容和缩容的统一逻辑
     * 只要给合适的capacity 就帮你创建一个包含原来老数组全部信息的新数组
     */
    public void resize(int newCapacity) {
        // 1. 创建一个容量为 newCapacity 的临时数组
        T[] newData = (T[]) new Object[newCapacity];
        // 2. 将原来数组中的元素拷贝到新数组中
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        // 3. 覆盖掉老数组
        data = newData;
        // 4. 修改新的容量
        capacity = newCapacity;
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
