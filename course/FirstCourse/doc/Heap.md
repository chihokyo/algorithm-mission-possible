# Heap（堆）

## 1. 基本概念

首先我看的这个教程是从优先队列开始引入堆这个概念的。

**Q：什么是优先队列？**

就是你在银行排队的时候，有人是VIP，即使你来的比他早，但是人家就是先上柜台办理业务的那种天龙人。

**Q：关于优先队列数据结构分析？**

关于出入队的思路的话，2种数据结构的思路。

|              | 入队                                                   | 出队（拿出最大元素）                |
| ------------ | ------------------------------------------------------ | ----------------------------------- |
| 普通线性结构 | `O(1)`                                                 | `O(n)` 因为你要扫描一遍才知道谁最大 |
| 顺序线性结构 | `O(n)`因为你入队的时候也要扫描一遍，才知道我是什么档次 | `O(1)`                              |

所以以上结构来说的话，只要涉及到了n那么，效率总归是很低的。所以引出来了*堆*

**Q：为什么用堆？**

下面再来看。反正复杂度最差情况都是`O(log)`级别的。

我以前写过一篇文章，关于树讲得十分详细。[『二叉树』详解「广度遍历・深度遍历」的Python实现](https://chihokyo.com/post/17/)

主要的思想就是说了什么是**树→二叉树→完全二叉树→堆→堆化→堆排序**

树 → 就是无论少个叉都可以，只要是分叉结构

二叉树 → 就是只能有2个叉

下面这几个概念可以区分一下，下面的图实在太棒了。

|                          | 描述                                                         | 样子省略 |
| ------------------------ | ------------------------------------------------------------ | -------- |
| **perfect binary tree**  | 又叫满二叉树，很完美，很饱满，一个深度为k（>=-1）且有2(k+1)-1个结点的二叉树称为完美二叉树（满二叉树） |          |
| **complete binary tree** | 从根节点到倒数第二层都满足完美二叉树，**最后一层可以不完全填充，但是叶子结点都应该靠左对齐。** |          |
| **full binary tree**     | 非叶子，只要有孩子，孩子必须是俩。即使畸形的树。             |          |

![img](https://miro.medium.com/max/16000/1*CMGFtehu01ZEBgzHG71sMg.png)

## 2. 堆的基本结构

**一个堆就是一个特殊的树！** 那么二叉堆就是特殊的二叉树。特殊在哪里呢？

<u>以下所说的都是最大堆</u>

- 二叉堆是一个**完全二叉树**（上图的**complete binary tree**），特点就是可以不满，但是人家按照顺序来。不满的部位一定是右下角咯。
- 堆中的某个节点一定是小于父节点的。同层可以不一定，但是老子一定大于孩子。

![image-20210902165633879](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210902165634.png)

可以用数组来表示

![image-20210902170034285](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210902170035.png)

**Q：从父如何找孩子？**

如果说`parent = i `的话

- `left孩子 = 2 * i`
- `right孩子 = 2 * i + 1`

**Q：从孩子如何找父亲？**

`孩子 / 2 = parent` 无论左右，反正都是取整，那么0.5是被抹去的。比如3/2=1

**Q：为什么要空出来那个0的位置？不空出来可以吗？**

因为为了好计算。当然也可以不偏移，但是计算就可以重新计算。

![image-20210902170137909](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210902170209.png)

那么计算公式就是

![image-20210902170227756](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210902170228.png)

### 2.1 Array代码实现

首先用[动态数组](https://github.com/chihokyo/algorithm-mission-possible/blob/master/course/FirstCourse/Heap/src/Array.java)实现一个完全二叉树。其实就是新建了一个数组。并且初始化了一下。

```java
private Array<E> data;

public MaxHeap(int capacity) {
    data = new Array<>(capacity);
}

public MaxHeap() {
    data = new Array<>();
}
```

完整的实现如下

```java
public class MaxHeap<E extends Comparable<E>> {

    private Array<E> data;

    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MaxHeap() {
        data = new Array<>();
    }

    /**
     * 返回这个最大堆的size也就是数组的size
     *
     * @return int 大小
     */
    public int size() {
        return data.getSize();
    }

    /**
     * 判断是否为空
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * 在完全二叉树里，给一个index，返回父节点所在的index
     *
     * @param index 索引
     * @return int 父节点索引
     */
    private int getParent(int index) {
        //  根节点没有父亲
        if (index == 0) {
            throw new IllegalArgumentException("index 0 does not have parent");
        }
        return (index - 1) / 2;
    }

    /**
     * 在完全二叉树里，给一个index，返回左child节点的index
     *
     * @param index 索引
     * @return int 左child索引
     */
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    /**
     * 在完全二叉树里，给一个index，返回右child节点的index
     *
     * @param index 索引
     * @return int 右child索引
     */
    private int rightChild(int index) {
        return index * 2 + 2;
    }
}
```

## 3. 添加元素Sift Up

上面的只是了写了一些最基本的属性和方法，那么如何添加元素呢。其实向堆里面添加元素这个本身并不是很难，就像给一个数组末尾添加一个value一样

```
[1, 2, 5, 7, newValue]
```

但是比较难的是，添加元素之后你还能维持你的堆结构，意思就是你添加之后还要**维持堆结构**。不然这就不是一个堆了。

**Q：如何添加之后还能维持堆结构？**

在末尾添加的元素，那么问题只有可能发生在**末尾元素的父节点和父节点的父节点**身上，所以只要对比父节点进行**交换**即可。

![Sep-03-2021 12-59-54](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210903130032.gif)

所以继续，然后对比41，继续交换，最后对比62，发现小于，那就不动了。（因为都已经比父亲小了，说明也比所有的祖先小）**交换结束。**

这种上浮的过程，就是**sift up**

所以添加操作就是这样实现的

```java
/**
 * 给堆中添加元素
 *
 * @param e 值
 */
public void add(E e) {
    // 添加元素
    data.addLast(e);
    // 维护结构 这里传入的是最后一个元素的索引
    siftUp(data.getSize() - 1);
}

// 上浮操作 传入的是索引而不是元素！
private void siftUp(int index) {
    // 自身索引要大于0 并且 自己的值要大于父节点的值
    while (index > 0 && data.get(getParent(index)).compareTo(data.get(index)) < 0) {
        // 交换元素
        data.swap(index, getParent(index));
        // 交换完之后还要继续比较 这里属于循环的变量
        index = getParent(index);
    }
}
```

## 3. 取出堆中最大元素Sift Down

堆中的最大元素就是**堆顶元素。** extract max，相当于是索引为0的元素。取出来之后，这样就分成2个堆了。2个堆合成1个堆的并不是很容易，于是就有了以下的技巧。

- 缓存第一个index为0的元素
- 把最后一个元素复制到了堆顶root
- 然后删除最后一个元素

![Sep-03-2021 13-30-54](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210903133127.gif)

这样就发生了一样的问题，虽然还是完全二叉树**complete binary tree**，就不是**最大堆**结构了。所以就有了下沉操作**sift down**

- 跟自己俩孩子对比，跟俩孩子里**最大**的比较大小，如果小，就交换。
- 直到孩子没有比自己更大的

![Sep-03-2021 13-34-41](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210903133558.gif)

所以取出最大堆max代码思路应该就是这样的

- 找到最大堆元素并缓存
- 交换堆顶元素和最堆底元素（此时，最大元素就跑到了**堆底**）
- 删除堆底元素
- 维持最大堆结构
- 返回最开始缓存的最大堆元素

```java
private E findMax() {
    if (data.getSize() == 0) {
        throw new IllegalArgumentException("Cannot findMax when heap is empty.");
    }
    // 最大就是堆顶
    return data.get(0);
}

// 完成下沉，维持最大堆结构
private void siftDown(int index) {
    // 循环结束条件 知道没有叶子（左子树没有就肯定是了，因为完全二叉树特性，左都没，右肯定没）
    // 上面就是左孩子都已经越界了，超过了堆最大值，就说明左子树没有了。
    while (leftChild(index) < data.getSize()) {
        // 走到了这里 说明肯定是有左节点的
        int j = leftChild(index);
        // j+ 1 本质 右子树 说明有右子树
        // 有右子树 && 右子树大于左子树
        if (j + 1 < data.getSize() && data.get(j + 1).compareTo(data.get(j)) > 0) {
            j = rightChild(index); // j++ 这里就是右子树
        }
        // 这个时候 data[j] 就是左右子树里最大的值
        // 这个判断说明index这个值比左右子树里的最大值还大，那么根本不用下沉。直接break
        if (data.get(index).compareTo(data.get(j)) > 0) {
            break;
        }
        // 如果小，就交换
        data.swap(index, j);
        // 循环的变量
        index = j;
    }
}

/**
 * 取出堆顶最大元素
 *
 * @return E 泛型
 */
public E extractMax() {
    // 找到最大元素并缓存
    E ret = findMax();
    // 交换最大和最小 （也就是堆顶和堆底）
    data.swap(0, data.getSize() - 1);
    // 删除堆底
    data.removeLast();
    // 维持最大堆结构
    siftDown(0);
    // 返回缓存
    return ret;
}
```

## 4. 实现一个堆排序 HeapSort()

因为前面在实现`add()`方法的时候相当于每一次都维持堆结构，实质上已经是实现了堆排序的了。

所以这一次**简单的堆排序**是这样实现的

```java
public class HeapSort {
    private HeapSort(){}

    public static <E extends Comparable<E>> void sort(E[] data) {
        // 生成一个最大堆数据结构
        // 开始添加到这个最大堆结构里，这样堆顶最大值
        // 每一次都取出来最大值，这样添加到新到数组里。就完成了堆排序
        MaxHeap<E> maxHeap = new MaxHeap<>();
        for (E e: data) {
            maxHeap.add(e);
        }
        // 注意这里是最大堆排序，如果为了完成从小到大，需要从最后一个元素，也就是data.length - 1 从后向前
        for (int i = data.length - 1; i >= 0; i--) {
            data[i] = maxHeap.extractMax();
        }
    }
}
```

测试一下速度对比的话，是这样的。但是目前有性能问题。优化余地。

- 没有原地排序，使用了多余的空间。

```
MergeSort, n = 1000000: 0.395324 s 
QuickSort2Ways, n = 1000000: 0.258038 s 
QuickSort3Ways, n = 1000000: 0.210295 s 
HeapSort, n = 1000000: 0.495804 s 
```

可以**原地排序**的向下看。

## 5. 堆化（Heapify）

实现一个`repalce()` 取出最大的元素后，放出一个新元素。相当于拿最大的替换掉新元素呗。

2种思路

- 先`extractMax()`，然后在进行`add()`。2次`O(logN)`操作。
- 也可以直接替换掉堆顶元素之后`siftDown()`,1次`O(logN)`操纵。

```java
/**
 * 取出堆顶元素，并替换成新元素e
 *
 * @return E 返回堆顶元素
 */
public E replace(E e) {
    E ret = findMax();
    data.set(0, e);
    siftDown(0);
    return ret;
}
```

但其实有一个快速的方法就是堆化。

我以前貌似也在文章中写过，关于什么是堆化。

> 将任意数组整理成堆的形状

> 有序的数组不一定就是堆的形状哦。堆要求最大堆最小堆那种，父节点一定大于（小于）子节点那种。下面这个并不是满足堆化的。下面的数组并不是最大堆的性质。从最后一个非叶子节点开始计算。也就是**22**。然后开始对每一个最后一个非叶子节点开始向前遍历进行下沉`siftDown()`操作，下沉到根节点，每一个节点都变成了叶子节点。

**Q:用数组来表示一个完全二叉树，最后一个非叶子节点的index是多少？**

非常简单，找到最后一个节点，然后计算父节点就可以了。`partent(data.size - 1)`

![image-20210903145318953](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210903145320.png)

这里就是22开始下沉，22完成之后13，13之后，28→17→15最大堆完成。。。。这里就是比较难以理解。

![image-20210903153707355](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210903153708.png)

![image-20210903150101377](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210903150102.png)

因为一个个添加的时候每一次都是`O(nLongN)`，但是使用堆化操作，复杂度就是`O(n)`

### 实现Heapify

当用户传入一个数组到这个堆结构里，要求自动是一个最大堆结构。所以直接就可以写在构造函数里。 

- 先给数组弄一个构造函数

```java
/**
 * 为了堆新建的一个动态数组
 *
 * @param arr 数组
 */
@SuppressWarnings("unchecked")
public Array(E[] arr) {
    data = (E[]) new Object[arr.length];
    // for (int i = 0; i < arr.length; i++) {
    //     data[i] = arr[i];
    // }
    System.arraycopy(arr, 0, data, 0, arr.length);
    size = arr.length;
}
```

- 在给MaxHeap新建一个构造函数

```java
/**
 * 为了堆实现构造函数，传入一个数组，生成一个新的动态数组
 *
 * @param arr 数组
 */
public MaxHeap(E[] arr) {
    data = new Array<>(arr);
}
```

- 逻辑书写

为了堆实现构造函数，传入一个数组，生成一个新的动态数组
然后对倒数第一个非叶子节点（也就是最后一个叶子的父节点），开始逐层遍历完成。

这个构造函数实现之后就是 **普通数组 → 堆化结构的数组**

```java
// 从最后一个非叶子的父节点开始
public MaxHeap(E[] arr) {
    data = new Array<>(arr);
    for (int i = getParent(arr.length - 1); i >= 0; i--) {
        siftDown(i);
    }
}
```

上面的堆化就这么结束了。

## 6. 堆排序的2种方式PK

### 6.1 add()添加方式

因为每一次add之后其实都是一个完整的堆结构。所以每一次root都是最大值，这样每次在取出来最大值放进去最新的数组。那么就是已经排序的，这也是上面我写过的。

```java
此处代码省略，上面有。
```

### 6.2 Heapify方式

这个使用的就是上面的堆化方法写的，构造函数传入一个数组默认实现了一个堆化结构的数组。

我感觉堆化，本质就是`siftDown()`

### 6.3 测试PK

```java
import java.util.Random;

public class Main {

    /**
     * 测试堆排序速度
     *
     * @param testData 未排序数组
     * @param isHeap   是否采用堆化
     * @return double 速度
     */
    private static double testHeap(Integer[] testData, boolean isHeap) {
        long startTime = System.nanoTime();

        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        // 2种方式测试
        if (isHeap) {
            maxHeap = new MaxHeap<>(testData);
        } else {
            maxHeap = new MaxHeap<>();
            for (int num : testData) {
                maxHeap.add(num);
            }
        }

        // 这里生成了一个新的数组
        int[] arr = new int[testData.length];
        for (int i = 0; i < testData.length; i++) {
            // 这里是把已经排序好的数组结构只拿走最大的堆顶，放进去新的数组
            arr[i] = maxHeap.extractMax();
        }
        // 测试数组是否排序好（从大到小）
        for (int i = 1; i < testData.length; i++) {
            // 前一个小于后一个
            if (arr[i - 1] < arr[i]) {
                throw new IllegalArgumentException("Error");
            }
        }
        System.out.println("Test MaxHeap completed.");

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000_000.0;
    }

    public static void main(String[] args) {
        
        /*===================测试添加操作和堆化操作=====================*/
        System.out.println("====测试2种速度 添加操作PK堆化Heapify操作====");

        int m = 10000000;
        Random random2 = new Random();
        Integer[] testData = new Integer[m];
        for (int i = 0; i < m; i++) {
            testData[i] = random2.nextInt(Integer.MAX_VALUE);
        }

        double time1 = testHeap(testData, false);
        System.out.println("Without heapify: " + time1 + "s");
        double time2 = testHeap(testData, true);
        System.out.println("With heapify: " + time2 + "s");

    }
}

```

### 6.4 优化堆排序

之前的堆排序需要先排序，然后一个个拿出来，这样就需要**额外的空间。**

所以优化的目的就是→ **原地排序**

那么优化的思路是什么呢?感觉就是一个数组首先默认是实现了最大堆的堆化的，然后取出第一个（此时第一个是最大），然后和最后一个交换，这样最后一个就是最大的。也就是说此时**[0, 倒数第2个]**，是没有排序好的，**[倒数第1个]**是排序好的。

**①本来v是最大堆的最大的索引是index[0]**

![image-20210911205739321](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210911205741.png)

然后交换，交换之后w就不是最大堆了。然后`siftDown()`

![image-20210911205636416](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210911205637.png)

如此继续下去，知道全部都交换完毕，就完成了原地排序。感觉还是使用循环不变量，通过缩小范围交换这样的方式来实现原地排序。貌似原地排序都是这样的方式，定义一个循环不变量，维持循环不变量（需要缩小范围），然后不断交换维持。就是这样的。

#### 代码实现优化堆排序

额外空间不需要了

```java
// 原地排序版本的
public static <E extends Comparable<E>> void sort2(E[] data) {
    if (data.length <= 0) return;
    // 对倒数第一个非叶子节点，开始逐层遍历完成
    for (int i = (data.length - 2) / 2; i >= 0; i--) {
        siftDown(data, i, data.length);
    }
    // 都到这里了，就决定已经完成了堆化，然后index[0]就是最大值
    for (int i = data.length - 1; i >= 0; i--) {
        // 交换
        swap(data, 0, i);
        // 交换之后index[0]就不是最大值了，然后进行堆化
        siftDown(data, 0, i);
    }
}

// 这里就是对data[0, n]形成的堆，以k为索引，进行siftDown
private static <E extends Comparable<E>> void siftDown(E[] data, int k, int n) {
    // 循环结束条件 知道没有叶子（左子树没有就肯定是了，因为完全二叉树特性，左都没，右肯定没）
    // 上面就是左孩子都已经越界了，超过了堆最大值，就说明左子树没有了。
    while (2 * k + 1 < n) {
        int j = 2 * k + 1; // 在这个循环里 data[k]和data[j]交换位置
        // j+ 1 本质 右子树 说明有右子树
        // 有右子树 && 右子树大于左子树
        if (j + 1 < n && data[j + 1].compareTo(data[j]) > 0) {
            j++; // j++ 这里就是右子树
        }
        // data[j] 是左右子树的最大值
        if (data[k].compareTo(data[j]) >= 0) {
            break;
        }
        // 如果小，就交换
        swap(data, k, j);
        k = j;
    }
}

private static <E extends Comparable<E>> void swap(E[] data, int i, int j) {
    E t = data[i];
    data[i] = data[j];
    data[j] = t;
}
```

