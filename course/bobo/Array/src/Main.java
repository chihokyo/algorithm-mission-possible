public class Main {
    public static void main(String[] args) {
        OriginalArray<Integer> arr = new OriginalArray<>(20);
        for (int i = 0; i < 10; i++) {
            arr.addLast(i);
        }
        arr.add(1, 100);
        System.out.println(arr);

        arr.removeFirst();
        System.out.println(arr);

        arr.removeFirst();
        System.out.println(arr);
        // 因为重写了toString 所以自定义的这个数组可以直接打印
        // System.out.println(arr);
        //
        // arr.add(2, -9);
        // System.out.println(arr);
        // arr.addFirst(100);
        // System.out.println(arr);
        //
        // int res = arr.get(5);
        // System.out.println(res);
        // arr.set(0, 1000);
        // System.out.println(arr);
        //
        // System.out.println("开始删除");
        // arr.removeFirst();
        // System.out.println(arr);
        // arr.removeElement(8);
        // System.out.println(arr);
        // arr.removeLast();
        // System.out.println(arr);
        // arr.removeFirst();
        // System.out.println(arr);
    }
}
