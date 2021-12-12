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

## [36. 有效的数独](https://leetcode-cn.com/problems/valid-sudoku/)

```
请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
数字 1-9 在每一行只能出现一次。
数字 1-9 在每一列只能出现一次。
数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
 
注意：
一个有效的数独（部分已被填充）不一定是可解的。
只需要根据以上规则，验证已经填入的数字是否有效即可。
空白格用 '.' 表示。
```

### 思路

这一题的思路，如何判断是否是数独。

- 逐行
- 逐列
- 9宫格

以上三种都只能出现一次！！所以可以**新建3个9×9的九宫格**

**逐行**

![image-20211209234331084](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211209234331084.png)

**逐列**

![image-20211209234516290](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211209234516290.png)

**9宫格**

![image-20211209234729329](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211209234729329.png)

上面这个表达写错了，7原本在`(0,4)` → 转换之后应该是 `(3,6)`

```
boxIndex = 0 / 3  + (4 / 3) *3 = 3
num = 7 - 1 = 6
```

面就是思路，接下来是代码实现

### **代码实现**

```java
class Solution {
    public boolean isValidSudoku(char[][] board) {
        boolean[][] rowUsed = new boolean[9][9];
        boolean[][] colUsed = new boolean[9][9];
        boolean[][] boxUsed = new boolean[9][9];

        // 初始化1个九宫格
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] != '.') {
                    // 寻找相应的位置 比如board是5(1行,2列) 
                  	// 那么逐行这个就是5(1行,1列) 
                    int num = board[row][col] - '1';
                  	// 已经有了数字 那就是重复了
                    if (rowUsed[row][num]) return false;
                    else rowUsed[row][num] = true;
										
                  	// 寻找相应的位置 比如board是7(5行,2列) 
                  	// 那么逐列这个就是5(2行,6列) 
                    if (colUsed[col][num]) return false;
                    else colUsed[col][num] = true;
										// 寻找相应的位置 比如board是7(3行,2列) 
                  	// 那么就属于第几个九宫格呢？ 1+3=4 第4个九宫格
                    int boxIndex = row / 3 + (col / 3) * 3;
                  	// 7(4行，6列)
                    if (boxUsed[boxIndex][num]) return false;
                    else boxUsed[boxIndex][num] = true;
                }
            }
        }

        return true;
    }
}
```

## [73. 矩阵置零](https://leetcode-cn.com/problems/set-matrix-zeroes/)

```
给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
示例 1：
输入：matrix = [[1,1,1],[1,0,1],[1,1,1]]
输出：[[1,0,1],[0,0,0],[1,0,1]]
示例 2：
输入：matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
输出：[[0,0,0,0],[0,4,5,0],[0,3,1,0]]

提示：
m == matrix.length
n == matrix[0].length
1 <= m, n <= 200
-231 <= matrix[i][j] <= 231 - 1

进阶：
一个直观的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
你能想出一个仅使用常量空间的解决方案吗？
```

这一题就是行和列都转换成0。

### 思路O(mn)

首先看一下O(mn)的思路

![2021-12-12 22-18-42.2021-12-12 22_19_39](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-12-12%2022-18-42.2021-12-12%2022_19_39.gif)

因为按照上面图示那样，第一个0所在的行和列转换成0之后。会发现这样就会丢失原来的信息，比如第二个0，就不知道是**转换前0还是转换后0**

所以这个时候就需**要新建一个数组，来记录。**

![image-20211212222148977](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211212222148977.png)

### 思路O(m+n)

下面这个思路就简单多了，可以设置一个数组专门记录列是否为0，记录行是否为0。

- 新建2个数组记录行列信息
- 遇到0就写入数组信息
- 利用2个数组的信息返回来设置矩阵信息

![2021-12-12 22-24-06.2021-12-12 22_26_48](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-12-12%2022-24-06.2021-12-12%2022_26_48.gif)

最后的样子就是

![image-20211212222707769](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211212222707769.png)

### 代码实现

时间复杂度*O(mn)*,空间复杂度*O(m+n)*

#### 基础实现1

```java
class Solution {
    public void setZeroes(int[][] matrix) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        // 初始化2个数组记录行列信息
        boolean[] rows = new boolean[rowLen];
        boolean[] cols = new boolean[colLen];
        // 开始遍历然后记录信息
        for (int row = 0; row < rowLen; row++) {
            for (int col = 0; col < colLen; col++) {
                // 其中任意元素只要为0
                if (matrix[row][col] == 0) {
                    // 对应的行和列就为true
                    rows[row] = true;
                    cols[col] = true;
                }
            }
        }

        // 走到这里行列信息记录完毕，修改原数组
        for (int row = 0; row < rowLen; row++) {
            for (int col = 0; col < colLen; col++) {
                // 只要任意达标 是true
                if (rows[row] || cols[col]) {
                    // 所对应的那一行就是0
                    // 比如这时候这个rows[0]位置为true，那么(0,0)(0,1)(0,2)...都会为0
                    // 比如这时候这个cols(2)位置为true，那么(0,2)(1,2)(2,2)...都会为0
                    matrix[row][col] = 0;
                }
            }
        }
    }
}
```

如何进行优化，原地排序呢？

利用原数组的第一行第一列！！记录在原数组的第一行第一列也是可行的！

![image-20211212234536469](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211212234536469.png)

但如果直接这样的写法有一个问题，就是下图这种。

![image-20211212224515553](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211212224515553.png) 

所以为了避免这种bug，需要单独计算第一行，第一列，然后从第2行第2列开始计算。

时间复杂度就是O(mn)空间复杂度就是O(1) ，因为只是使用了2个布尔变量。

#### 优化1 2个变量

```java
class Solution {
    public void setZeroes(int[][] matrix) {
        // 初始化行列信息
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        // 先遍历第1行第1列
        boolean flagRow = false;
        for (int i = 0; i < colLen; i++) {
            if (matrix[0][i] == 0) flagRow = true;
        }

        boolean flagCol = false;
        for (int i = 0; i < rowLen; i++) {
            if (matrix[i][0] == 0) flagCol = true;
        }
        // 从第2行开始记录
        for (int row = 1; row < rowLen; row++) {
            for (int col = 1; col < colLen; col++) {
                if (matrix[row][col] == 0) {
                    matrix[0][col] = 0;
                    matrix[row][0] = 0;
                }
            }
        }
        // 修改原数组
 	      // 如果自己所在的行和列有任意一个为0，那么自己就是0 
        for (int row = 1; row < rowLen; row++) {
            for (int col = 1; col < colLen; col++) {
                // 当第一行任意一个等于0
                if (matrix[0][col] == 0 || matrix[row][0] == 0) {
                    matrix[row][col] = 0;
                }
            }
        }

        // 第1行第1列全为0
        if (flagRow) {
            for (int i = 0; i < colLen; i++) matrix[0][i] = 0;
        }

        if (flagCol) {
            for (int i = 0; i < rowLen; i++) matrix[i][0] = 0;
        }
    }
}
```

其实还可以继续优化

#### 优化2 1个变量

![image-20211212225019259](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211212225019259.png)

其实这一题整体思路就是和上面一样，只不过少用了1个变量。并且是从后向前遍历的。

- 只记录第一列是否包含0
- 从后向前

从后向前的顺序

![image-20211213013400642](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211213013400642.png)

```java
class Solution {
    public void setZeroes(int[][] matrix) {
        // 初始化行列信息
        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        boolean flagRow = false;
        // 因为只记录第1列的信息，所以row从0开始
        for (int row = 0; row < rowLen; row++) {
            if (matrix[row][0] == 0) flagRow = true;
            for (int col = 1; col < colLen; col++) {
                // 只要元素为0，所在的行列就为0
                if (matrix[row][col] == 0) {
                    matrix[row][0] = 0;
                    matrix[0][col] = 0;
                }
            }
        }

        // 记住！这里要从后向前遍历 并且看好顺序，是从最后一行开始从左向右
        for (int row = rowLen - 1; row >= 0; row--) {
            for (int col = 1; col < colLen; col++) {
                // 只要行列任意为0，那么自己就是0
                if (matrix[row][0] == 0 || matrix[0][col] == 0) {
                    matrix[row][col] = 0;
                }
            }
            // 如果第一列的行头是0，那么整列就是0
            if (flagRow) matrix[row][0] = 0;
        }
    }
}
```

