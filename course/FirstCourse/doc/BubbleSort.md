# 冒泡排序Bubble Sort

## 基本说明

冒泡排序法并不常用，选择插入生活更常用。

冒泡排序法。324615

相邻的2个元素是不是逆序对，在归并的时候貌似学过。

## 主要思想

相邻的对比，大的就向后，小的在前。3比2小，所以23交换。

![image-20210914135928360](/Users/chin/Library/Application Support/typora-user-images/image-20210914135928360.png)

以此类推，交换到最后。**最大的元素就在最后。**

![image-20210914140032804](/Users/chin/Library/Application Support/typora-user-images/image-20210914140032804.png)

因为循环1轮最后只能确定1个最大，所以有几个，就要循环几轮。那么复杂度就是`O(n*n)`

下面就是循环不变量

![image-20210914140611706](/Users/chin/Library/Application Support/typora-user-images/image-20210914140611706.png)

### 代码实现

只需要进行n-1循环，因为都看循环，第n的时候就没有相邻的元素了。

```java
import java.util.Arrays;

public class BubbleSort {
    public static <E extends Comparable<E>> void sort(E[] data) {
        // 这里为什么是+1大于长度，因为+1意味着相邻的那个，如果没有+1直接就飞出去了
        for (int i = 0; i + 1 < data.length; i++) {
            // arr[n-i, n) 已经OK
            // 通过冒泡 arr[n-i-1] 放上合适的元素
            // for (int j = 0; j + 1 <= data.length - i - 1; j++)
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(data, j, j + 1);
                }
            }
        }
    }

    private static <E> void swap(E[] arr, int i, int j) {
        E e = arr[i];
        arr[i] = arr[j];
        arr[j] = e;
    }

    public static void main(String[] args) {

        int n = 100000; // BubbleSort, n = 100000: 31.940092 s
        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        SortingHelper.sortTest("BubbleSort", arr);
    }
}

```

## 优化1

如果是全部已经排序完毕的，那么复杂度就是`O(n)`级别的。

比如下面56排序之后发现前面的1234都是排序好的（就是没发生任何交换），说明前面就是排序的就不用判断了

![image-20210914143625287](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210914143635.png)

代码实现

```java
/**
     * 冒泡排序-小优化
     * 如果没有发生交换，说明已经排序
     *
     * @param data 数据
     * @param <E> 泛型
     */
    public static <E extends Comparable<E>> void sort2(E[] data) {
        // 这里为什么是+1大于长度，因为+1意味着相邻的那个，如果没有+1直接就飞出去了
        for (int i = 0; i + 1 < data.length; i++) {
            // arr[n-i, n) 已经OK
            // 通过冒泡 arr[n-i-1] 放上合适的元素
            // for (int j = 0; j + 1 <= data.length - i - 1; j++)
            boolean isSwapped = false;
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(data, j, j + 1);
                    isSwapped = true;
                }
            }
            // isSwapped默认是没有交换，如果这里走break，说明没有任何交换
            if (!isSwapped) break;
        }
    }
```

速度从结果来看的话

```
BubbleSort, n = 100000: 31.928892 s 
BubbleSort2, n = 100000: 32.005186 s 
BubbleSort, n = 100000: 7.445845 s 
BubbleSort2, n = 100000: 0.000247 s 
```

**Q：为什么无序数组优化之后还慢了？**

因为优化之后增加了很多判断还有赋值，都会影响效率

```
boolean isSwapped = false;
isSwapped = true;
if (!isSwapped) break;
```

## 优化2

在交换，4和2之后，会发现后面的456都排好序的了，会发现**最后一次交换如果在中间，那么后面的都是排序好的。**

记录一下交换位置，就可以跳过。

![image-20210914144703791](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210914144705.png)

代码实现

```java
/**
     * 冒泡排序-小优化2
     * 不仅仅记录是否交换，还要记录交换的位置
     *
     * @param data 数据
     * @param <E> 泛型
     */
    public static <E extends Comparable<E>> void sort3(E[] data) {
        // 这里为什么是+1大于长度，因为+1意味着相邻的那个，如果没有+1直接就飞出去了
        for (int i = 0; i + 1 < data.length;) {
            // arr[n-i, n) 已经OK
            // 通过冒泡 arr[n-i-1] 放上合适的元素
            // for (int j = 0; j + 1 <= data.length - i - 1; j++)
            int lastSwappedIndex = 0;
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(data, j, j + 1);
                    // 后一个的位置赋值给 lastSwappedIndex
                    lastSwappedIndex = j + 1;
                }
            }
            // 因为通过下面的 data.length - lastSwappedIndex; 说明lastSwappedIndex为0的话
            // 那么data.length - lastSwappedIndex;就是data.length
            // 肯定是不符合 for (int i = 0; i + 1 < data.length;) 这个条件的，自然可以不用了
            // if (lastSwappedIndex == 0) break;
            // 优化关键
            // i表示有多少个排序好 [lastSwappedIndex，n]都是排好序的
            i = data.length - lastSwappedIndex;
        }
    }
```

注意3个地方

- 为什么记录`lastSwappedIndex`用的j+1 **→ 因为这里最后一次交换的位置**
- 为什么`if (lastSwappedIndex == 0) break;` 可以不用判断 而且不用写最外层for终止条件了→ 参考👆🏻注释
- 为什么i需要重新重新赋值而且是这样赋值？→ 因为这个i记录的是已经排好序的区间。

## 另一种方式实现

上面实现的是从前开始交换，那么另一种实现方式就是从后开始对比。一直对比到0。

![image-20210914173856602](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210914173857.png)

注意这里是最小的在前面，每次循环结束就能确定一个最小的在最前面

![Sep-14-2021 17-40-35](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210914174054.gif)

代码实现

```java
```

