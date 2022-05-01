# 基于比较的排序算法大总结

既然是基于比较，那么你的数据首先要是可以比较的，也就是说实现了`Comparable`这个接口的。

![image-20210915152802615](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915152804.png)

那么可以解决什么问题呢？

![image-20210915152917175](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915152918.png)

## 排序算法的稳定性

意义不大的玩意儿，但是了解下比较好。

![image-20210915153219606](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915153220.png)

具有稳定性就是一样的值，最后结果排序也是一样的。稳定性必须要达到**100%**，而不稳定的话就不一定了，可能会稳定，可能会不稳定。

![image-20210915153316017](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915153316.png)

而且如果元素只有1个域，那么稳定性就没意义。

上面有意义，下面没意义。

![image-20210915154934354](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915154935.png)

比如下面的代码，只写蓝框里的代码就可能不稳定，但是如果你都写好了红框的，那么就**不依赖稳定性。**

![image-20210915155115139](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915155116.png)

## 选择排序是不稳定

因为在找最小然后交换的时候位置就会有变化。

## 插入排序是 Yes

因为相同大小的元素没机会进行**跳跃**，无交换。（要看你的实现）

![image-20210915153801427](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915153802.png)

## 希尔排序是不稳定

因为有分组，分组就不会稳定下去了。

## 冒泡排序是 Yes

每次只是比较相邻元素，那么也是无法跳跃。

但是也需要看具体实现，写错了就不稳定！

![image-20210915153954608](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915153956.png)

## 快速排序法是不稳定

随机化标定点就直接打乱顺序。

## 堆排序法是不稳定

比如先堆化，最大堆了

![Sep-15-2021 15-43-41](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915154351.gif)

然后在删除80的时候，会发现。

![Sep-15-2021 15-44-20](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915154442.gif)

这样就不稳定了

## 归并排序发是 Yes

![image-20210915154533878](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915154535.png)

但是也要看具体实现！！

![image-20210915154627008](https://raw.githubusercontent.com/chihokyo/image_host/develop/20210915154628.png)

等于的情况，就归并！

