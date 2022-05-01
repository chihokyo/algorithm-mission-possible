import java.util.Random;

/**
 * 使用自定义的Array实现一个最小堆
 */
public class MinHeap<E extends Comparable<E>> {
    private Array<E> data;

    public MinHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MinHeap() {
        data = new Array<>();
    }

    public MinHeap(E[] arr) {
        data = new Array<>(arr);
        if (arr.length != 1) {
            for (int i = parent(arr.length - 1); i >= 0; i--) {
                siftDown(i);
            }
        }
    }

    public int size() {
        return data.getSize();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public int parent(int index) {
        //  根节点没有父亲
        if (index == 0) {
            throw new IllegalArgumentException("index 0 does not have parent");
        }
        return (index - 1) / 2;
    }

    public int leftChild(int index) {
        return index * 2 + 1;
    }

    public int rightChild(int index) {
        return index * 2 + 2;
    }

    public void add(E e) {
        // 直接添加到末尾
        data.addLast(e);
        // index的最后一个上浮
        siftUp(data.getSize() - 1);
    }

    private void siftUp(int index) {
        // 这里就是先添加到最后，然后跟自己父亲节点比，只要比父亲节点还小就交换
        // 父节点更大，自己更小，自己要更向上走。说明要交换
        while (index > 0 && data.get(parent(index)).compareTo(data.get(index)) > 0) {
            // 数据交换
            data.swap(index, parent(index));
            // 交换完之后记得保持循环
            index = parent(index);
        }
    }

    private void siftDown(int index) {
        // 循环结束条件没有下一个左孩子
        // 跟自己俩孩子对比，跟俩孩子里最小的比，如果比最小的还大就下沉，就交换。目的，保证最小的在上面
        // 找到左节点缓存
        // 如果有右节点，并且右节点的数字小于左子树
        // 那么缓存的这个就是右子树
        // 如果现在的值，比缓存（目前最小的）还小，就交换，保证小的在上面
        // 如果没有就break，说明最小的就是缓存的。
        // 最后很重要，那么最小的就成了这个缓存的
        while (leftChild(index) < data.getSize()) {
            int j = leftChild(index);
            // 找出最小的
            if (j + 1 < data.getSize() && data.get(j + 1).compareTo(data.get(j)) < 0) {
                // j = rightChild(index);
                j++;
            }
            // 如果现在的index所在的值更小，就不用交换
            // 这里的条件是index所在的值更小
            if (data.get(index).compareTo(data.get(j)) <= 0) {
                break;
            }
            data.swap(index, j);
            index = j;
        }


    }

    // 寻找最小（也就是index为0）
    private E findMin() {
        if (data.getSize() == 0) {
            throw new IllegalArgumentException("Can not findMax when heap is empty.");
        }
        return data.get(0);
    }

    // 取出堆里最小的元素
    public E extractMin() {
        // 先找到
        E ret = findMin();
        data.swap(0, data.getSize() - 1);
        data.removeLast();
        siftDown(0);
        return ret;
    }

    // 替换掉栈顶元素（也就是最小的元素）
    public E replace(E e) {
        E ret = findMin();
        // 设置
        data.set(0, e);
        // 下沉（本质就是heapify）
        siftDown(0);
        return ret;
    }

    // 开始测试
    public static void main(String[] args) {
        int n = 1000000;

        MinHeap<Integer> minHeap = new MinHeap<>();
        Random random = new Random();
        for (int i = 0; i < n; i++)
            minHeap.add(random.nextInt(Integer.MAX_VALUE));

        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = minHeap.extractMin();

        for (int i = 1; i < n; i++)
            if (arr[i - 1] > arr[i])
                throw new IllegalArgumentException("Error");

        System.out.println("Test MinHeap completed.");

    }
}
