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

