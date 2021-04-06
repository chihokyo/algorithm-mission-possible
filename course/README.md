# 主要记录一些课程的笔记

## Java

### 1 线性查找

#### 普通的线性查找

```java
/**
 * 线性查找法 
 * 找到返回index 
 * 找不到返回 -1
 */
public class LinearSearch {

    // 如果不想让这个类可以实例化怎么办。
    // 使用构造函数私有化
    // 但是这样不能阻止本类来实例化。
    private LinearSearch (){};

    public static int search(int[] data, int target) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // 如果for和if这样只有一句statement 就是可以省略大括号的
    // 顺便java是可以不缩进的，但是为了层次可读性。还是加上了。
    public static int search2(int[] data, int target) {
        for (int i = 0; i < data.length; i++)
            if (data[i] == target)
                return i;
        return -1;
    }

    public static void main(String[] args) {

        int[] data = { 1, 29, -9, 18, 7 };
        // 测试1
        // LinearSearch ls = new LinearSearch();
        // int res = ls.search(data, 18);
        // int res2 = ls.search(data, 0);
        // System.out.println(res);
        // System.out.println(res2);

        // 测试2
        // 无需新建一个对象，直接static调用静态方法。多用于工具类。
        int res = LinearSearch.search(data, 1);
        int res2 = LinearSearch.search(data, 20);
        System.out.println(res);
        System.out.println(res2);


    }
}
```

上面的写法无法解决的就是**数据类型**只能是*int*的这个问题。

使用泛型就可以解决这一问题。

#### 泛型的线性查找

- 为什么不是泛型类而是方法？因为我们的目的不是为了new一个对象出来，而是使用里面的方法，方法为了不同的数据类型而变化的。所以没必要设计一个泛型类，方法足矣。
- 泛型只能接受对象，不能是基本数据类型。
- Java数据结构几乎所有标准库都是泛型类
- 下面的代码要好好看看 ，特别有意义。

```java
/**
 * 设计一个有泛型的方法
 * 为什么不是泛型类
 * 现在这个类具体使用的不是对象，而是这个类的方法。
 * 所以没必要去设计一个泛型类，使用泛型方法就可以。
 * 
 * 泛型只能接受类对象，不能是基本数据类型。所以int不行 Integer行
 * 
 * Java数据结构几乎所有标准库都是泛型类
 */
public class LinearSearch2 {

    private LinearSearch2() {
    };

    public static <E> int search(E[] data, E target) {
        for (int i = 0; i < data.length; i++) {
            // 这里注意点就是比较的是String，是一个对象。所以不能用==
            if (data[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        // 由于泛型只能接受类对象。所以这不能是int 而是 Integer
        Integer[] data = { 24, 18, 12, 9, -6, 89 };
        // Java SE5开始就提供了自动装箱 输入的是int 8 实际上装箱之后就是 Integer 8
        int res = LinearSearch2.<Integer>search(data, 8);
        int res2 = LinearSearch2.<Integer>search(data, -6);
        System.out.println(res);
        System.out.println(res2);

        // 下面测试一个student类是否能查找到
        // 新建一个数组
        Student[] stus = {
            new Student("Alice"),
            new Student("Chin"),
            new Student("Bob"),
        };
        // 测试chin是否在stus数组里
        Student chin = new Student("Chin");

        int res3 = LinearSearch2.search(stus, chin);
        System.out.println(res3); // 1
        
        // 明明是在的，为什么-1 是因为 Student 没有重写 equals 
        // equals 父类Object默认的方法是对比的地址。那么肯定是找不到了。
        // 基本上包装类都会重写 equals ，自定义的类 就是使用的Object的 equals
        // 所以需要重写方法 忽视大小

        Student ali = new Student("alice");
        int res4 = LinearSearch2.search(stus, ali);
        System.out.println(res4); // 0
 
    }
}

/**
 * 测试用类Student
 */

class Student {

    private String name;
    public Student(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    @Override 
    public boolean equals(Object student) {
        // 当前类对象是否就是当前这个类，看是否是同一个对象
        // 同一个对象直接就true了不用比了，就一模一样了。
        if (this == student) {
            return true;
        }
        // 传入的对象是否为空
        if (student == null) {
            return false;
        }
        // 当前对象的类，和传入过来你要对比的类是否是一个类—
        if (this.getClass() != student.getClass()) {
            return false;
        }
        Student another = (Student)student;
        // return this.name.equals(another.name);
        return this.name.toLowerCase().equals(another.name.toLowerCase());
    }

}
```

#### 循环不变量

关于同一个区间的问题。有时候我自己也总弄混了一些问题。

```
data[i] 是否有目标
data[0....i-1] 没有找到目标 ①
data[0....i) 没有找到目标 ②
其实上面的①和②是一样的意思，但却是一个开，一个闭。
```

循环不变量的意思就是在每一次循环开始的时候，都满足②和③那个条件。

**循环体：就是维持循环不变量。**

