# 哈希表 HashCode

## 什么是哈希表

从一个LeetCode题目开始的问题。

#### [387. 字符串中的第一个唯一字符](https://leetcode-cn.com/problems/first-unique-character-in-a-string/)

制作一个包含有26个字母。每一个字母对应的频率。

[a,b,c....z] index是字母对应的顺序，value是频率(次数)

比如a就是index为0，b就是1，c就是2

```java
class Solution {
    public int firstUniqChar(String s) {
        // 因为只有26个字母，所以可以新建一个数组
        // key是26个小写字母的数字顺序，value是频率
        int[] freq = new int[26];
        // 第1次遍历所有字母，统计频率
        for(int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a'] ++;
        }
        // 这里再一次的从头遍历，如果为1说明就是第1个不重复的
        // 因为这里首先s是按照字母顺序来的
        // 其次上面的遍历之前已经统计了所有的频率
        for(int i = 0; i < s.length(); i++) {
            // 只要频率为1 就找到了
            if(freq[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}
```

关于ASCII

```
System.out.println('a' - 'a'); // 97 - 97 = 0
System.out.println('b' - 'a'); // 98 - 97 = 1
System.out.println('c' - 'a'); // 99 - 97 = 2
System.out.println('d' - 'a'); // 100 - 97 = 3
```

那什么是哈希表。

每一个字符都有一个索引对应。

类型转换成索引，就是哈希函数。

```
fx(ch) = ch  - 'a'
这个fx就是哈希函数。
```

![image-20211005181021413](/Users/chin/Library/Application Support/typora-user-images/image-20211005181021413.png)

但是字符串浮点数日期什么都很麻烦，所以如何把 **键 → 索引** 就是重点。一一对应很难。

![image-20211005181227744](/Users/chin/Library/Application Support/typora-user-images/image-20211005181227744.png)



哈希表充分体现了算法设计领域的经典思想，**空间换时间**。所以如何设计哈希函数，让时间和空间平衡是很重要的。

## 如何设计哈希函数

![image-20211005181545000](/Users/chin/Library/Application Support/typora-user-images/image-20211005181545000.png)

#### 整数

具体问题具体分析。哈希函数设计的会很复杂，

取模使用 **素数** 为什么使用素数？→ 数学理论，不用深究。反正冲突最小。

![image-20211005230648017](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211005230648.png)

下面就是一个表，根据你的数据规模不同，看看选择什么样的素数prime

![image-20211005230756297](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211005230758.png)

#### 浮点型

在计算机中都是32位or64位的二进制表示，只不过计算机解析成了浮点数。

最后还是转换成整型处理。

#### 字符串

转换成整型处理。

![image-20211005231106767](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211005231109.png)

最后可以使用B然后取模。

因为有可能指数过大，所以套娃。目的是为了计算简单，结果是一样的。

![image-20211005231208453](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211005231209.png)

#### 复合类型

日期类等等，可以当做是字符串类型，然后最后整型处理。

**反正结果都是整型处理。** 但这并不是唯一的方法！

![image-20211005231442553](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211005231443.png)

## Java中的hashCode

java里可以直接通过`hashCode()`获得哈希值

```java
public static void main(String[] args) {
    int a = 42;
    System.out.println(((Integer)a).hashCode()); // 42

    int b = -42;
    System.out.println(((Integer)b).hashCode()); // -42
}
```

Java所有类Object都有一个`hashCode()`方法。

下面开始写一个自定义的类。

```java
import java.util.Locale;
import java.util.Objects;

public class Student {
    int grade;
    int cls;
    String firstName;
    String lastName;

    public Student(int grade, int cls, String firstName, String lastName) {
        this.grade = grade;
        this.cls = cls;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        // 判断是否同一个引用
        if (this == o) return true;
        // 判断是否为空，当前类对象是否和o对象一样
        if (o == null || getClass() != o.getClass()) return false;
        // 强制类型转换
        Student student = (Student) o;
        // 一个个成员变量进行比较
        return grade == student.grade && cls == student.cls && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        int B = 31;
        int hash = 0;
        hash = hash * B + grade;
        hash = hash * B + cls;
        hash = hash * B + firstName.toLowerCase().hashCode(); // 不区分大小写
        hash = hash * B + lastName.toLowerCase().hashCode();
        return hash;
    }
}

```

自己不写hashCode，就默认用系统给的。使用的是地址。

自己写hashCode，可以自己指定逻辑。

**Q: 如果hashCode出现冲突了还想比较是否相等怎么办？**

A: 重写`equals()`

  