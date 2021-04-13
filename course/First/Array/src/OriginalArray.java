import java.util.Arrays;
import java.util.stream.IntStream;

public class OriginalArray {

    // capacity 容量 因为可以通过data.length算出来 就不新建了
    // size 实际数组大小

    private int[] data;
    private int size;

    /**
     * 有参数构造函数 传入数组的容量为capacity
     *
     * @param capacity 表示这个数组能装多少 容量大小
     */
    public OriginalArray(int capacity) {
        data = new int[capacity];
        size = 0;
    }

    /**
     * 无参构造函数
     * 这里默认数组的容量就是capacity=10
     */
    public OriginalArray() {
        this(10);
    }

    /**
     * 获取数组实际大小
     *
     * @return int 实际大小
     */
    public int getSize() {
        return size;
    }

    /**
     * 获取数组容量
     *
     * @return int 数组可以容纳的容量
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * 判断数组是否为空
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 获取指定index位置的元素
     *
     * @param index 指定位置
     * @return int 获取元素
     */
    int get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get Failed. Index is illegal");
        }
        return data[index];
    }

    /**
     * 设置指定index位置的元素
     *
     * @param index 指定位置
     * @param e     新的元素
     */
    void set(int index, int e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get Failed. Index is illegal");
        }
        data[index] = e;
    }

    /**
     * 在最后添加数组元素
     *
     * @param e 元素
     */
    // 思路
    // 以向后添加为例
    // 1 判断是否满了
    // 2 在size的位置赋值 并且size++
    public void addLast(int e) {
        // if (size == data.length){
        //     throw new IllegalArgumentException("AddLast Failed ! Array is full");
        // }
        //
        // // 不建议这么写
        // // data[size++] = e;
        // data[size] = e;
        // size ++;
        // 在写了下面的add方法之后 这个方法可以一句话概括了
        add(size, e);
    }

    /**
     * 在最前面添加元素
     *
     * @param e 元素
     */
    // 在first添加元素
    public void addFirst(int e) {
        add(0, e);
    }

    /**
     * 在指定位置添加
     *
     * @param index 指定位置
     * @param e     需要添加的元素
     */
    // 在任意位置添加
    public void add(int index, int e) {
        if (size == data.length) {
            throw new IllegalArgumentException("AddLast Failed ! Array is full");
        }
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add Failed. Require index >= 0 and index < =size");
        }
        // 这里最难理解的可能是这个循环了
        // 为什么要从size - 1 开始挪动呢 因为 必须要要从后向前，这样才不会有覆盖。
        // 这一段其实用图说明会更清晰 1 2 3 4 5 要在2和3之间增加的话 也就是3的话 必须要 3 4 5 一起向后挪动
        // for (int i = size - 1; i >=index; i-- ) {
        //     data[i + 1] = data[i];
        // }

        // idea推荐我这样写 没想到还是对的，还需要我干什么
        // 好吧 但是上面的是基础
        System.arraycopy(data, index, data, index + 1, size - index);

        data[index] = e;
        size++;
    }

    /**
     * 查询是否包含元素e
     *
     * @param e 目标元素
     * @return 返回布尔值
     */
    public boolean contains(int e) {
        for (int i = 0; i < size; i++) {
            if (data[i] == e) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查询数组元素所在的索引，不存在返回-1
     *
     * @param e 目标元素
     * @return int 返回索引
     */
    public int find(int e) {
        for (int i = 0; i < size; i++) {
            if (data[i] == e) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据索引删除元素 并且返回元素
     *
     * @param index 索引
     * @return 返回删除的元素
     */
    public int remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Remove Failed. Require index >= 0 and index < =size");
        }
        // 1 暂存需要删除的元素
        int res = data[index];
        // 2 所有index之后的元素都向前挪动一个元素
        // 又是idea推荐写法
        // IntStream.range(index + 1, size).forEach(i -> data[i - 1] = data[i]);
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        // 3 删除之后长度size要--
        size--;
        return res;
    }

    /**
     * 删除index为0的元素
     *
     * @return 返回index为0的元素
     */
    public int removeFirst() {
        return remove(0);
    }

    /**
     * 删除index在最后一个的元素
     *
     * @return 返回index在最后 也就是size - 1的元素
     */
    public int removeLast() {
        // 我第一次写成了size 是错误的 size是长度
        // 最后一个index应该是size - 1
        return remove(size - 1);
    }

    /**
     * 直接删除元素（第一个出现的）
     *
     * @param e 直接删除
     */
    public void removeElement(int e) {
        // 先判断一下是否有
        int index = find(e);
        if (index != -1) {
            remove(index);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array:size=%d, capacity=%d\n", size, data.length));
        res.append("[");
        // 这里为什么是size 因为实际上就是size capacity是实际的
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1) {
                res.append(", ");
            }
        }
        res.append("]");
        // 因为是StringBuilder 所以需要toString
        return res.toString();
    }
}
