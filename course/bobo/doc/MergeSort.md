#  归并排序

要点总结

**先分开 后合并**

```java
MergeSort(arr, left, right);
// 对arr的 l 和 r排序
MergeSort(arr, left, right){
    // 分开
    int mid = (left + right) / 2;
    MergeSort(arr, left, mid)
    MergeSort(arr, mid + 1, right)
    // 合并    
    merge(arr, left , mid, right)
}
```

### 问题1. left和right的问题？

- left = right 相当于数组只有1个元素
- left < right 不可能 相当于没有元素

### 问题2. 如何归并？

一直一个有序的数组A和B。如何将A和B合成一个有序数组

> A 1 2 6
>
> B 0 5 19
>
> 默认没有重复的

思路 就是把A和B里最小的进行比较，谁最小，谁就放在新的数组里。

但是归并里面放的是**一个数组**。

![截屏2021-07-03 23.28.09](https://raw.githubusercontent.com/chihokyo/image_host/master/20210703232814.png)

先拷贝复制一份。因为和上面给的例子不一样，为了知道你用的是什么，同时用于下一次比较，所以就要复制一份（备份，因为最小的会覆盖掉上面的那个数组，用过的++）。![截屏2021-07-03 23.29.25](https://raw.githubusercontent.com/chihokyo/image_host/master/20210703232929.png)

直到什么时候呢？

当**i > mid**的时候，相当于**左边**已经遍历完了。这样直接把j下面的继续放着就可以了。

当**j > r**的时候，相当于右边已经遍历完了。

> **上面的归并的过程无法原地完成。**

### 问题3. 代码怎么写？

```java
import java.util.Arrays;

public class MergeSort {

    private MergeSort(){}

    public static <E extends Comparable<E>> void sort(E[] arr){
        // 直接调用下面的重载方法
        // 这样就是前面给的你的是arr.sort()
        // 其实你调用的是下面的重载方法 因为归并是需要左右区间index
        sort(arr, 0 , arr.length - 1);
    }

    private static <E extends Comparable<E>> void sort(E[] arr, int left, int right){
        if(left >= right) return;
        int mid = (left + right) / 2;
        sort(arr, left, mid);
        // 为什么上面的不用+1 这里需要呢，因为我们默认的是取得左边的
        sort(arr, mid + 1, right);
        //上面的方法过来之后其实左右的元素是一种完全拆分的状态
        // 这里才是合并
        merge(arr, left, mid, right);
    }

    /**
     * 合并2个有序区间 arr[left, mid] arr[mid + 1, right]
     * @param arr 原始数组
     * @param left 左边
     * @param right 右边
     * @param <E> 任意类型
     */
    private static <E extends Comparable<E>> void merge(E[] arr, int left, int mid, int right) {
        // 因为这一个方法是生成的数组一个左闭右开的，如果需要完全复制，最右需要+1
        E[] temp = Arrays.copyOfRange(arr, left, right + 1);

        int i = left, j = mid + 1;

        for (int k = left; k <= right; k++) {
            // 左边已循环 越界
            if(i > mid) {
                // 为什么要 - left 因为你复制的temp是从0开始的
                // 而真正merge传过来的数组arr可能不是从0开始的
                // 这个时候有一个偏移量，这个偏移量就是left
                // 也就是说temp[0] arr[left]
                // 也就是说temp[1] arr[left + 1] 这种感觉
                // 只有这样才能顺利的把j真正指向的元素给了k
                arr[k] = temp[j - left];
                j++;
            // 右边已循环 越界
            } else if(j > right) {
                arr[k] = temp[i - left];
                i++;
            // 左边大于右边 取左 并且向前进
            } else if (temp[i  - left].compareTo(temp[j - left]) <= 0){
                arr[k] = temp[i - left];
                i++;
            // 右边大于左边 取右 并且向前进
            } else {
                arr[k] = temp[j - left];
                j++;
            }
        }
    }

    public static void main(String[] args) {
        int n = 100000;
        Integer[] arr =  ArrayGenerator.generateRandomArray(n, n);
        SortingHelper.sortTest("MergeSort", arr);
    }
}

```

上面有1个问题其实可以解决一下的。

```java
int mid = (left + right) / 2;
// 为了防止l + r太大数值溢出，可以把加法，换成减法。
// 因为 l 和 r没有越界，那么r -l 更不会越界。
int mid = left - (right - left) / 2;
```

## 关于归并的顺序

非常重要！！！因为我多次都没有搞懂。

我以为是先分，然后在合并，其实是分，分，分，部分合，分，分，部分合，最后在整体合起来。

分和合并其实是分块进行的，并非是全部分完，然后在合并。

参考视频 https://www.geeksforgeeks.org/merge-sort/

![File:Merge-Sort-Tutorial.png - Wikimedia Commons](https://upload.wikimedia.org/wikipedia/commons/9/9d/Merge-Sort-Tutorial.png)

## 优化思路1

如果*合并2个有序区间 arr[left, mid] arr[mid + 1, right]* 

- 2个数组已经有序
- 并且*arr[mid] > arr[mid + 1]* 说明没必要在比较了。

```java
// 判断一下是否已经有序
if(arr[mid].compareTo(arr[mid + 1]) > 0) {
      merge(arr, left, mid, right);
}
```

## 优化思路2

对于一个小范围的数组来说，其实可以用插入排序代替merge

```java
public static <E extends Comparable<E>> void sort3(E[] arr){
        // 直接调用下面的重载方法
        // 这样就是前面给的你的是arr.sort()
        // 其实你调用的是下面的重载方法 因为归并是需要左右区间index
        sort3(arr, 0, arr.length - 1);
    }

    public static <E extends Comparable<E>> void sort3(E[] arr, int left, int right){

        // if(left >= right) return;
        // 对于一个有序的数组来说插入排序法效率更高
        // 对于一个数量少的数组来说，有序的概率也会更高
        // 证明数组数量在16个以下
        // ※根据语言不同可能没用，尤其是高级语言的JS PHP PYTHON等等
        if(right - left <= 15) {
            // 使用插入排序
            insertionSort(arr, left, right);
            // 必须要return回去，不然还要接下来执行下面的代码了。
            return;
        }
        int mid = (left + right) / 2;

        sort(arr, left, mid);
        // 为什么上面的不用+1 这里需要呢，因为我们默认的是取得左边的
        sort(arr, mid + 1, right);
        //上面的方法过来之后其实左右的元素是一种完全拆分的状态
        // 这里才是合并

        // 优化 合并2个有序区间 arr[left, mid] arr[mid + 1, right]
        // 当前面一个比后面一个大 才进行重新扫描 复杂度变成了O(n)
        if(arr[mid].compareTo(arr[mid + 1]) > 0) {
            merge(arr, left, mid, right);
        }

    }

    private static <E extends Comparable<E>> void insertionSort(E[] arr, int left, int right) {
        for (int i = left; i <= right; i ++) {
            // 将arr[i] 放到合适的位置，也就是随便抽出来的这个放到合适的位置
            E t = arr[i];
            int j;
            for(j = i; j - 1 >= left && t.compareTo(arr[j - 1]) < 0; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = t;
        }
    }
```

## 优化思路3

在*merge()*函数里面新建了一个数组，这个数组浪费了很多空间。

*merge()*优化一下 这样每执行一次*merge()*就会浪费一次内存空间。

**那么可以在merge()之前就把这个临时数组给创建出来。**

并且没有了偏移量

```java
public static <E extends Comparable<E>> void sort4(E[] arr){
        // 不要在merge里面新建，提前新建一个临时数组
        E[] temp = Arrays.copyOf(arr, arr.length);
        sort4(arr, 0, arr.length - 1, temp);
    }

    public static <E extends Comparable<E>> void sort4(E[] arr, int left, int right, E[] temp){

        if(left >= right) return;

        int mid = (left + right) / 2;

        sort(arr, left, mid);
        sort(arr, mid + 1, right);

        if(arr[mid].compareTo(arr[mid + 1]) > 0) {
            merge2(arr, left, mid, right, temp);
        }

    }
```

## 自底向上

从下到上

```java
import java.util.Arrays;

public class MergeSortBottom {

    private MergeSortBottom() {
    }

    /**
     * 自底向上进行归并
     *
     * @param arr 数组
     * @param <E> 返回泛型
     */
    public static <E extends Comparable<E>> void sortBU(E[] arr) {

        E[] temp = Arrays.copyOf(arr, arr.length);
        int n = arr.length;
        // 遍历合并的区间长度是多少 1 2 4 8 16...
        for (int sz = 1; sz < n; sz += sz) {
            // 合并2个的区间的起始位置
            // [i, i+sz-1] & [i+sz, i+sz+sz-1]
            for (int i = 0; i + sz < n; i += sz + sz) {
                if (arr[i + sz - 1].compareTo(arr[i + sz]) > 0) {
                    // i + sz + sz -1 可能越界
                    merge(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1), temp);
                }
            }
        }
    }

    private static <E extends Comparable<E>> void merge(E[] arr, int left, int mid, int right, E[] temp) {

        System.arraycopy(arr, left, temp, left, right - left + 1);

        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            // 左边已循环 越界
            if (i > mid) {
                arr[k] = temp[j];
                j++;
                // 右边已循环 越界
            } else if (j > right) {
                arr[k] = temp[i];
                i++;
                // 左边的值小于右边的值 取左边 向前进
            } else if (temp[i].compareTo(temp[j]) <= 0) {
                arr[k] = temp[i];
                i++;
                // 右边的值小于左边的值 取右边 向前进
            } else {
                arr[k] = temp[j];
                j++;
            }
        }

    }

    public static void main(String[] args) {
        // int n = 5000000;
        //
        // Integer[] arr =  ArrayGenerator.generateRandomArray(n, n);
        Integer[] arr = {1, 8, 4, 0, 6, 2};

        SortingHelper.sortTest("MergeSortBottom", arr);

    }

}

```

最重要的部分是这里。

```java
E[] temp = Arrays.copyOf(arr, arr.length);
int n = arr.length;
// 遍历合并的区间长度是多少 1 2 4 8 16...
for (int sz = 1; sz < n; sz += sz) {
    // 合并2个的区间的起始位置
    // [i, i+sz-1] & [i+sz, i+sz+sz-1]
    for (int i = 0; i + sz < n; i += sz + sz) {
        if (arr[i + sz - 1].compareTo(arr[i + sz]) > 0) {
            // i + sz + sz -1 可能越界
            merge(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1), temp);
        }
    }
}
```



## 逆序对