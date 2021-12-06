# 算法日记

第3天的。继续加油吧。

二维数组开始了！

矩阵其实就是二维数组

图其实就是二维数组，二维数组基本上就3种类型的题目。

![image-20211205231212861](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211205231212861.png)

## [867. 转置矩阵](https://leetcode-cn.com/problems/transpose-matrix/)

```
给你一个二维整数数组 matrix， 返回 matrix 的 转置矩阵 。
矩阵的 转置 是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。
示例 1：
输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
输出：[[1,4,7],[2,5,8],[3,6,9]]
示例 2：
输入：matrix = [[1,2,3],[4,5,6]]
输出：[[1,4],[2,5],[3,6]]

提示：
m == matrix.length
n == matrix[i].length
1 <= m, n <= 1000
1 <= m * n <= 105
-109 <= matrix[i][j] <= 109
```

![img](https://assets.leetcode.com/uploads/2021/02/10/hint_transpose.png)

### 思路

如果是一个行列都是一样的矩阵的话，那么其实很容易就转换过来了。空间复杂度`O(1)`

![image-20211205231808325](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211205231808325.png)

那么如果行列不同呢，比如原来是3行4列。那么转置之后就是4行3列。

![image-20211205232117763](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211205232117763.png)

仔细看的话还是能看懂的`[row][col] → [col][row]`

``` 
(0,0) (0,1) (0,2) (0,3)
(1,0) (1,1) (1,2) (1,3)
(2,0) (2,1) (2,2) (2,3)

(0,0) (0,1) (0,2)
(1,0) (1,1) (1,2) 
(2,0) (2,1) (2,2) 
(3,0) (3,1) (3,2)
```

### 代码实现

```java
class Solution {
    public int[][] transpose(int[][] matrix) {
        int row = matrix.length; // 多少行 row
        int col = matrix[0].length; // 多少列 col
        int[][] res = new int[col][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // [i][j],[i][j+1],[i][j+2].. 进行转换
                res[j][i] = matrix[i][j];
            }
        }
        return res;
    }
}
```

## [48. 旋转图像](https://leetcode-cn.com/problems/rotate-image/)

这一题的官方解答也很好。

https://leetcode-cn.com/problems/rotate-image/solution/xuan-zhuan-tu-xiang-by-leetcode-solution-vu3m/

```
给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。

示例 1：
输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
输出：[[7,4,1],[8,5,2],[9,6,3]]
示例 2：
输入：matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
输出：[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
示例 3：
输入：matrix = [[1]]
输出：[[1]]
示例 4：
输入：matrix = [[1,2],[3,4]]
输出：[[3,1],[4,2]]

提示：
matrix.length == n
matrix[i].length == n
1 <= n <= 20
-1000 <= matrix[i][j] <= 1000
```

首先要知道旋转是怎么回事。

![2021-12-05 23-51-34.2021-12-05 23_52_27](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-12-05%2023-51-34.2021-12-05%2023_52_27.gif)

### 思路1 额外数组

这种题就是找规律，然后直接模拟就可以了。

然后可以看出来，row和col不仅仅要调换位置，还需要倒数。

在编程里只要提到倒数的，基本上就是要求**长度 - 位置 - 1**

- row和col调换位置
- row → `n - row - 1`

![image-20211205235523528](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211205235523528.png)

下面用代码实现，但是要记住，这是使用了额外的数组了。是不符合题意的。

### 代码实现

```java
// 使用额外数组，空间和时间复杂度都是O(n^2)
public void rotateNo(int[][] matrix) {
  int n = matrix.length;
  // 初始化额外数组
  int[][] newM = new int[n][n];
  // 循环
  for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
      // 规律，行列调换之后，row要求长度-1
      newM[col][n - row - 1] = matrix[row][col];
    }
  }
  // 因为这一题要求修改原矩阵，所以必须要重新赋值给原数组
  for (int row = 0; row < n; row++) {
    for (int col = 0; col < n; col++) {
      matrix[row][col] = newM[row][col];
    }
  }
}
```

### 思路2 原地旋转

这里其实用了推导，求下一个应该在的位置，接着求求，会发现一个规律。

![image-20211206223857776](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211206223857776.png)

推导到最后会发现，本质就是一个轮回。

![2021-12-06 12-45-10.2021-12-06 12_45_38](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-12-06%2012-45-10.2021-12-06%2012_45_38.gif)

这样就会发现其实只需要记忆一个元素就可以那就是最初的

一个数在变换4次之后会回到原点，形成闭环

`temp = matrix[row][col]`

>  **那么要循环多少次呢？ **
>
> 这里其实也很好理解，就是一个数学推导。其实也很好理解。
>
> 总共的元素/ 4 ，又因为行列相同。所以总元素就是`n*n`
>
> 所以最后偶数的情况下只要循环红色的部分`[00,01,10,11]`，也就是行列都是2
>
> 所以最后奇数的情况下，中间的心是无需旋转的，如下图，就是2行3列。 

![image-20211206125408193](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211206125408193.png)

### 代码实现

```java
class Solution {
    // 空间和时间复杂度都是O(n^2)
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int row = 0; row < n / 2; row++) {
            for (int col = 0; col < (n + 1) / 2; col++) {
                // [row][col] = [col][n-row-1]
                int temp = matrix[row][col];
                //  直接套用基本公式即可
                matrix[row][col] = matrix[n - col - 1][row];
                // [n - col - 1][row] => [n-(n-row-1)-1][]
                matrix[n - col - 1][row] = matrix[n - row - 1][n - col - 1];
                matrix[n - row - 1][n - col - 1] = matrix[col][n - row - 1];
                matrix[col][n - row - 1] = temp;
            }
        }
    }
}
```

### 思路3 原地翻转

其实 `顺时针旋转90度=主对角线翻转+左右翻转`

![image-20211206224358127](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211206224358127.png)

直接可以手撕代码了。水平翻转，可以找到规律。

- 就是列是不变的，行是变的。`[row][col] = [n - row - 1][col]`
- 对角线，行列互换。`[row][col] = [col][row]`
- 其实这个推导下来就和思路2的公式一模一样！！

```
[row][col] = [n - row - 1][col]
[row][col] = [col][row]
==============================
[n - row - 1][col] = [col][row] // 一起取右边
[row][col] = [col][n - row - 1] //  最后
```

### 代码实现

```java
class Solution {
    // 原地翻转 顺时针旋转90度=主对角线翻转+左右翻转 or 水平翻转+对角线翻转
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        // 水平翻转 [row][col] = [n - row - 1][col]
        // 行的话翻转1半就够了
        for (int row = 0; row < n / 2; row++) {
            for (int col = 0; col < n; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[n - row - 1][col];
                matrix[n - row - 1][col] = temp;
            }
        }
        // 对角线翻转 [row][col] = [col][row]
        // row要翻转n的次数
        for (int row = 0; row < n ; row++) {
            // 对角线翻转 只需要翻转row次
            for (int col = 0; col < row; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
    }
}
```

