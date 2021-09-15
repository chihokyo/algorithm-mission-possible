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

    public static void main(String[] args) {
        Integer[] arr = {12, 34, 54, 2, 3, 18, 1, 6};
        sort2(arr);
        System.out.println(Arrays.toString(arr));
    }
}
