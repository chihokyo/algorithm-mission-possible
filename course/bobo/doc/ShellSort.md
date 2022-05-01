# 希尔排序 ShellSort

首先这是一个人名！

希尔排序是希尔（Donald Shell）于1959年提出的一种排序算法。希尔排序也是一种**插入排序**，它是简单插入排序经过改进之后的一个更高效的版本，也称为**缩小增量排序**，同时该算法是冲破O(n2）的第一批算法之一。

简言之 `插入排序 + 隔X分组 = 希尔排序 `

## 插入排序

首先还是要知道插入排序的实现，不然希尔排序没办法搞。

<iframe width="560" height="315" src="https://www.youtube.com/embed/OGzPmgsI-pQ" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

![insertion-sort](https://media.geeksforgeeks.org/wp-content/uploads/insertionsort.png)

- <u>外层循环：</u>一个个
- 弄个暂存区，暂存你要**对比的那个**
- 弄个指针，从你**要对比的那个**后面开始比较
- <u>内层循环：</u>循环条件，边界 +  **要对比的那个更小**
- 直接交换，比较小的在前面
- 以上<u>内层循环</u>结束
- 内层结束循环结束后，感觉就是交换呗。

```java
public static <E extends Comparable<E>> void sort(E[] arr) {
    for (int i = 0; i < arr.length; i++) {
        E target = arr[i];
        // 这个j就是一个暂存区，存放的是最后target该在的index
        // 在外面赋值 因为最后要用到 如果在for里声明的话就是局部变量了
        int j;
        for (j = i; j - 1 >= 0 && target.compareTo(arr[j - 1]) < 0; j--) {
            // 前面元素平移到了后面
            arr[j] = arr[j - 1];
        }
        // 这个时候相当于把target直接就赋值给了该在的地方
        arr[j] = target;
    }
}
```

感觉这个写的容易？理解点，同时跟上面的对比效果更佳。

```java
void sort(int arr[])
{
    int n = arr.length;
    for (int i = 1; i < n; ++i) {
        // 暂存一下
        int key = arr[i];
        int j = i - 1;

        /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j = j - 1;
        }
        arr[j + 1] = key;
    }
}
```

算了，在看看希尔排序吧。

## 思路

为什么会有希尔排序。利用的是一个数组通过**虽然效率很低的排序（冒泡），但也会慢慢变得有序**的这个特性。同时只处理相邻的逆序对，会变得特别没效率。

然后就延伸出来了这样

![image-20210914183907254](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210914183908.png)

让3和6排序，2和1排序，8个5排序，4和7排序。（通过插入排序）

就可以得到相对有序的一个数组。

然后继续，上面是分了 `8 / 2 = 4` 也就是4组。排序之后，分成`4 / 2 = 2`，如下

![image-20210914184033865](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210914184035.png)

红蓝又各自排序，然后结果排序好了之后，最后就剩下了 `2 / 2 = 1`，本质最后**缩小变成了普通的插入排序**。

![image-20210914184253530](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210914184254.png)

那么需要的分隔次数就是

![image-20210914211519999](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210914211521.png)

## 代码实现

**难点1，间隔是多少？**

间隔每一次都是递减的，第1次 **n / 2**，第2次 **n / 4**...

**难点2，如何每隔一个进行排序？**

下面代码有，每一次**+-h**

```java
import java.lang.reflect.Array;
import java.util.Arrays;

public class ShellSort {

    private ShellSort() {
    }

    public static <E extends Comparable<E>> void sort(E[] data) {
        // 初始化分割
        int h = data.length / 2;

        // 只要还有
        while (h >= 1) {
            // start 代表每一个子数组起始位置
            // start < h 是一组内需要循环的总次数。循环h次
            // 比如长度为8，h是4，说明要一组里面有4个数字需要遍历，没遍历完4个数字就不能停
            for (int start = 0; start < h; start++) {
                // data[start, start+h,start+2h,start+3h..]
                // 如果是[12 34 54 2 3 6 7 1]
                // h = 4 index[0,4]
                // h = 2 index[0,2,4]
                // h = 1 index[0,1,2,3 ...]
                // 所以下面对子数组进行插入排序 前一个是+h,后一个是-h，以前是+1和-1
                // 这里要看的就是每个小分队的大小了
                for (int i = start + h; i < data.length; i += h) {
                    E t = data[i];
                    int j;
                    // 这里就是一直向前走，直到找到自己的位置
                    for (j = i; j - h >= 0 && t.compareTo(data[j - h]) < 0; j -= h) {
                        // 相当于前面的转移到了后面，于是前面的位置就空出来了data[j]
                        data[j] = data[j - h];
                    }
                    data[j] = t;
                }
            }
            // 每次结束
            h /= 2; // h = h / 2;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {12, 34, 54, 2, 3, 18, 1, 6};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}

```

上面代码比较难以理解的2个判断，所以在这里用图解析。

![image-20210914222732262](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210914222733.png)

## 性能

主要分析的就是3个循环。下面数学公式，看看就好。

![image-20210915013536920](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915013537.png)

虽然最后是O(n**2)  级别的，但是由于每一次都是更加有序的。所以实际会很快。

## 优化1

三重变两重

可以一次性把前两个循环变成一个，主要就是上一个实现是按照先分成各个小组，在各个小组内部进行分别比较交换。

这个小优化的意思呢。就是从h开始，每一个都要进行，隔断进行，遍历的时候就顺便比较了。这个可能比较难以理解，但是自己没事多想想哈。

![image-20210915150026194](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915150027.png)

```java
public static <E extends Comparable<E>> void sort2(E[] data) {
        // 初始化分割
        int h = data.length / 2;

        // 只要还有
        while (h >= 1) {
            // data[h,n) 进行插入排序
            for (int i = h; i < data.length; i++) {
                E t = data[i];
                int j;
                for (j = i; j - h >= 0 && t.compareTo(data[j - h]) < 0; j -= h) {
                    data[j] = data[j - h];
                }
                data[j] = t;
            }
            // 每次结束
            h /= 2; // h = h / 2;
        }
    }
```

## 优化2

用**步长序列**可以优化

h：1，2，4，8，16，32...  → 这里的步长是一个`2的n次方`

h：1，4，13，40... → 这里的步长是`3n+1`

那么如果使用3n+1的步长是如何实现呢？ 看下面👇🏻

```java
/**
  * 希尔排序ShellSort
  * 步长从2的n次方，变成3n+1的话
  *
  * @param data 数据
  * @param <E>  泛型
  */
public static <E extends Comparable<E>> void sort3(E[] data) {
    // 初始化分割
    int h = 1;
    while (h < data.length) {
        h = h * 3 + 1;
    }

    // 只要还有
    while (h >= 1) {
        // data[h,n) 进行插入排序
        for (int i = h; i < data.length; i++) {
            E t = data[i];
            int j;
            for (j = i; j - h >= 0 && t.compareTo(data[j - h]) < 0; j -= h) {
                data[j] = data[j - h];
            }
            data[j] = t;
        }
        // 每次结束
        h /= 3; // 因为这里反正是取整 不信可以看看 40,13,4,1结果
    }
}
```

上面3个实现的速度PK

```java
 public static void main(String[] args) {
        int n = 5000000;
        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        Integer[] arr3 = Arrays.copyOf(arr, arr.length);

        SortingHelper.sortTest("ShellSort", arr);
        SortingHelper.sortTest("ShellSort2", arr2);
        SortingHelper.sortTest("ShellSort3", arr3);
    }
```

```
ShellSort, n = 5000000: 5.719476 s 
ShellSort2, n = 5000000: 5.229278 s 
ShellSort3, n = 5000000: 4.976240 s 
```

发现稍微优化一点点。

那到底什么步长序列最好？ → 无人知晓

### 超参数

其实关于步长取多少，这是一个很复杂的数学问题。反正不管，这个步长可以说是**超参数**。貌似这个是一个机器学习概念，详情可以百度一下。
