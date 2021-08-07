# 快速排序

基本思路，先分分分。在排排排。

哇哦，这个图很清晰明了。

![截屏2021-07-04 23.35.51](https://raw.githubusercontent.com/chihokyo/image_host/master/20210704233558.png)

## 1. 第一个难点

一个数组 8 6 4 9 12 1 0 这样的一个数组，首先我们随便指定一个。比如8，

*partition()*这个函数过后，8前面应该都是比8小的，8后面应该都是比8大的。

```
int indexP = partition(arr, left, right); // 返回8所在的索引
```

其实通过新开辟一个空间，然后进行遍历会很好的解决这个问题。但这样就是使用了**额外空间**，**非原地排序**了，非原地排序那么是很浪费空间的，也就不是什么快速了。

### 如何解决原地排序？

就是跟v这个数值进行比较，如果**e>v**那么i直接++就可以，如果**e<v**就交换位置。交换什么位置呢，那么就是j所指的位置下一个元素进行交换。然后j++,然后继续i++。

最后的最后，一切都到头了之后，直接让v这个位置的i和j交换，那么v就正好在中间了。

![截屏2021-08-05 23.30.09](https://raw.githubusercontent.com/chihokyo/image_host/master/20210805233016.png)

按照下面这个图的顺序来的话。

初始情况下，*arr[l+1...j] < v* 和 *arr[j+1...i-1] > v* 这个两个地方都是空区间arr[2,1] arr[2,0]，什么都没。

i就是j+1，然后慢慢走路，i一直向前走，遇到了第一个真命天子6大于这个4，那么就继续向前走(i++)，发现5也是真命天子，那么还是继续向前走(i++)。向前走的时候，都一直维持着循环不变量arr[j+1...i-1] > v其实就是紫色的地方。

这个时候继续向前走，会发现遇到了2，2是庶民，那么如何安置呢。那么就需要扩充前面的空间，也就是arr[l+1...j]。这个时候怎么扩充呢，那么就是j++。j++遇到了一个问题，会发现j++之后，就是6了。那么就指向了arr[j+1...i-1] > v的第一个元素了。那这样就不对了。

![截屏2021-08-05 23.38.39](https://raw.githubusercontent.com/chihokyo/image_host/master/20210805233843.png)

那么结果就是i和j进行交换，相当于小于的4这个数字的2挪动到了前面，而不是在后面了。可以看出来依然是维持着循环不变量的。![截屏2021-08-05 23.45.55](https://raw.githubusercontent.com/chihokyo/image_host/master/20210805234558.png)

继续向前走，会发现遇到了3。然后继续j++，交换。会发现一直还是维持循环不变量。

到最后你会发现是这样的。这样的话其实还没有完全完成，因为4还是在最左边。如何放在中间呢。l的值和j的值交换其实就可以了。

![截屏2021-08-05 23.49.06](https://raw.githubusercontent.com/chihokyo/image_host/master/20210805234910.png)

这个函数完成之后，其实并没有完全排序完成。

### 完全实现

```java
public static <E extends Comparable<E>> void sort(E[] arr, int left, int right) {
        // 这里递归终止条件就是左边大于右边
        if (left >= right) return;
        int p = partition(arr, left, right);
        // 上面结束之后arr[left, p-1] < arr[p];arr[p+1,right] >= arr[p]的
        sort(arr, left, p - 1);
        sort(arr, p + 1, right);
    }

    /**
     * 实现原地数组排序
     *
     * @param arr   数组
     * @param left  左区间
     * @param right 右区间
     * @param <E>   返回泛型
     * @return int 返回基准点pivot所在的index位置
     */
    private static <E extends Comparable<E>> int partition(E[] arr, int left, int right) {
        // arr[left+1,j] < v ;arr[j+1,i] >= v
        // 因为这里你会发现>=v 都是可以的。所以就+
        int j = left;
        for (int i = j + 1; i <= right; i++) {
            // 因为如果要大于的话 直接就是i++就可以
            // 所以这里开始只写小于的情况，那么就是j++，然后ij互换
            if (arr[i].compareTo(arr[left]) < 0) {
                j++;
                swap(arr, i, j);
            }
        }

        // 以上流程全部走完的话，这里依然没有结束arr[left] 也就是基准点，还没有回归原位
        // 进行最后一次交换，然后让left到该到的位置
        swap(arr, left, j);
        // 为什么最后要返回j 因为这个j其实就是我们需要的基准点的原位
        return j;
    }
```

但是上面的算法写法有一个问题就是在有序数组的情况下会出现栈溢出并且速度特别慢。

```java
// 有序数组
Integer[] arr3 = ArrayGenerator.generateOrderArray(n);
Integer[] arr4 = Arrays.copyOf(arr3, arr3.length);

SortingHelper.sortTest("MergeSort", arr3);
SortingHelper.sortTest("QuickSort", arr4); // 栈溢出
```

为什么慢？如果是排序的话，那么1次扫描就需要n，那么n个最后的时间复杂度就是O(n**2)级别。跟冒泡似的。

为什么会栈溢出？因为这样的话，递归深度就成了O(n)。那么n大到一个级别，那么就不行了。

### 如何解决有序数组效率低问题？

**基准点，不能找第一个！！**

目标：生成一个**[left, right]区间的随机值**

实现：生成一个[0, right -left]区间的随机值，这样就是保证了和上面一样。![截屏2021-08-06 01.04.42](https://raw.githubusercontent.com/chihokyo/image_host/master/20210806010447.png)

```java
private static <E extends Comparable<E>> int partition(E[] arr, int left, int right) {
        // 解决基准点是left而不是随机值的问题
        // 第二版 想要生成一个随机值[left,right]
        // 因为nextInt里面的数字是不包含的 所以要 + 1
        int p = left + (new Random()).nextInt(right - left + 1);
        // 这里就是交换left和p，保证了下面的j其实不是第一个，而是上面生成的那个p的随机
        swap(arr, left, p);
}    
```

### 为什么不取最中间？

对于特定化的固定的测试用例，这样就会**算法退化**。随机的话就不会。

### 万一每一次随机都取最小值？

概率极低极低 大概是 1/n! 低到宇宙大爆炸！

## 2. 新的问题出来了

如果数组里全部的数据都相同呢！！[0,0,0,0,0...]

发现龟速的慢，原因其实和上面一样。

都一样的情况下，j每一次都停留，一直在i++。完全没有交换过位置。最后，左边是空，右边是n-2元素。

还是每一次都在递归。。。下面的解决方案依然错误。一样。

```java
arr[left+1,j] < v ;arr[j+1,i] >= v 不行
arr[left+1,j] <= v ;arr[j+1,i] > v 不行
```

### 解决方法 → 双路快速排序法

双路快速排序法

这里可以和上面的图进行对比，循环不变量有了很大的不同。至于为什么是**<= >=**，而不是**< >**这是因为这样做会保证在数值相等的时候，数值会均匀的分散到左右2部分。

![image-20210807022123358](https://raw.githubusercontent.com/chihokyo/image_host/master/20210807022124.png)

具体下来。

初始化，2个都是一个空区间。

![image-20210807022524192](https://raw.githubusercontent.com/chihokyo/image_host/master/20210807022525.png)

这样继续向前走，6因为是大于4的，停住。而1正好是小于4的，也停住。left和right都停住了，那么双方交换，交换之后i++,j--，这样继续缩小范围到下一轮循环。直到i是大于j的，这时候全部停止。然后交换left和j就可以

![Aug-07-2021 02-31-13](https://raw.githubusercontent.com/chihokyo/image_host/master/20210807023135.gif)

而，如果是[0,0,0,0...]这样的全部相同的数组，这时候i和j如果都停住，并且都相等，说明*i == j == left*都一样的。

代码实现如下

```java
/**
     * 双路原地排序
     *
     * @param arr   数组
     * @param left  左区间
     * @param right 右区间
     * @param <E>   返回泛型
     * @return int 返回基准点pivot所在的index位置
     */
    private static <E extends Comparable<E>> int partition2(E[] arr, int left, int right) {
        // 解决基准点是left而不是随机值的问题
        // 第二版 想要生成一个随机值[left,right]
        // 因为nextInt里面的数字是不包含的 所以要 + 1
        int p = left + (new Random()).nextInt(right - left + 1);
        // 这里就是交换left和p，保证了下面的j其实不是第一个，而是上面生成的那个p的随机
        swap(arr, left, p);

        // 新的循环不变量
        // arr[left+1,i-1] <= v;arr[j+1,right]>=v
        // 这2个指针，一个指针是从left+1开始，一个是从最后面开始。
        int i = left + 1, j = right;

        while (true) {
            // i<=j 证明还有未遍历的 <0 证明要继续，因为>=0就要停住了
            while (i <= j && arr[i].compareTo(arr[left]) < 0) {
                i++;
            }
            // j >= i 证明还有未遍历的 这里j因为是从右向左进行遍历，所以要求必须要>0
            // 小于就终止了
            while (j >= i && arr[j].compareTo(arr[left]) > 0) {
                j--;
            }
            // 为什么i要>=j，而不是i>j。i=j 证明同一个元素
            if (i >= j) break;
            // 上面2个都停住了，证明都遇到了真命天子。需要交换+向前走
            swap(arr, i, j);
            i++;
            j--;
        }

        // 以上流程全部走完的话，这里依然没有结束arr[left] 也就是基准点，还没有回归原位
        // 进行最后一次交换，然后让left到该到的位置
        swap(arr, left, j);
        // 为什么最后要返回j 因为这个j其实就是我们需要的基准点的原位
        return j;
    }
```

## 3. 复杂度分析

partition那个点的选择，不一定。 所以是一个随机算法，实际意义很低，所以不能用最坏复杂度。

最坏复杂度O(n**2) → 概率极低

数学期望角度 → 其实本质就是看平均值，虽然不能保证每一次都是平分，所以就是平均。

**层数的期望值 ：O<u>(logn)</u>** **复杂度期望值：<u>O(nlogn)</u>**

![image-20210807030053640](https://raw.githubusercontent.com/chihokyo/image_host/master/20210807030054.png)

> 普通算法：看最差 能找到一组数据能恶化到100%
>
> 随机算法：看期望 没有一组数据能恶化到100%
>
> 多次调用不一定会恶化到100%，所以可以尝试均摊分析。

## 4. 三路快速排序

**Q1：为什么要有三路？**

因为在上面可以发现数组全部数值一样的情况下，双路排序也有会浪费。

**Q2：三路是哪三路？**

![image-20210808020910846](https://raw.githubusercontent.com/chihokyo/image_host/master/20210808021046.png)

①大于v ②小于v ③等于v

**Q3:循环结束的条件是什么？**

当*i>=gt*的时候，就说明全部元素都已经排序完毕。同上图。并且结束之后的区间如下图

![image-20210808021024891](https://raw.githubusercontent.com/chihokyo/image_host/master/20210808021031.png)

**Q4：需要几个指针？**

需要设置3个指针，①正在处理移动的指针*i*②需要一个lt（less then），指向了小于v部分的最后一个元素。③gt（greater then）指向了大于v部分的第一个元素。

小于v的情况。其实和单路一样，移动，交换，在走路。

![Aug-08-2021 02-06-29](https://raw.githubusercontent.com/chihokyo/image_host/master/20210808021217.gif)

大于v的情况，直接移动，gt向前走就行，注意，这里的i不要进行移动！！

![Aug-08-2021 02-07-57](https://raw.githubusercontent.com/chihokyo/image_host/master/20210808021242.gif)

等于的情况，直接向前走就可以了。

![Aug-08-2021 02-06-53](https://raw.githubusercontent.com/chihokyo/image_host/master/20210808021404.gif)

经过这样一轮下来写的代码应该是这样的。

```java
/**
     * 三路快速排序
     * @param arr 数组
     * @param left 左区间
     * @param right 有区间
     * @param rnd 随机数
     * @param <E> void
     */
    public static <E extends Comparable<E>> void sort3ways(E[] arr, int left, int right, Random rnd) {
        // 这里递归终止条件就是左边大于右边
        if (left >= right) return;

        // 生成[left, right] 随机
        int p = left + rnd.nextInt(right - left + 1);
        swap(arr, left, p);

        // 这里开始真正的三路排序
        // arr[left+1,lt]<v;arr[lt+1,gt-1]=v;arr[gt,right]>v
        // 以下初始值都是空的
        int lt = left, i = left + 1, gt = right + 1;
        // 为什么用while而不是用for，因为不一定每次都是i++
        // 只要i<gt证明循环并没有结束
        while (i < gt) {
            if (arr[i].compareTo(arr[left]) < 0) {
                //　lt先向后走，扩充空间。然后交换，然后i++继续向前走
                lt++;
                swap(arr, i, lt);
                i++;
            } else if(arr[i].compareTo(arr[left]) > 0) {
                gt--; // 从后向前走 继续缩小范围，就是扩充
                swap(arr, i, gt);
                // i++ 不用++的，这是因为i这个位置目前是交换后gt的元素并没有比较
            } else {
                i++;
            }
        }

        swap(arr, left, lt);
        // 执行完上面的步骤之后 应该是这样的 arr[left,lt-1]<v;arr[lt,gt]=v;arr[gt+1,right]>v
        sort3ways(arr, left, lt - 1, rnd);
        sort3ways(arr, gt, right, rnd);

    }
```

