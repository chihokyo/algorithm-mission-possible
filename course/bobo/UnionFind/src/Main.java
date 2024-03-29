import java.util.Random;

public class Main {
    // 测试2个版本的并查集

    /**
     * 测试并查集
     * 这里不是纠结成功与否，主要是看数据执行m次需要多久
     *
     * @param uf 多态操作，这里传入实现了UF的数据类型就可以
     * @param m  m次 执行次数
     * @return double 执行时间
     */
    private static double testUF(UF uf, int m) {
        int size = uf.getSize();
        Random random = new Random();
        long startTime = System.nanoTime();

        for (int i = 0; i < m; i++) {
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.unionElements(a, b);
        }

        for (int i = 0; i < m; i++) {
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.isConnected(a, b);
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 100000000.0;
    }

    public static void main(String[] args) {
        // int size = 100000;
        // int m = 10000;
        // UnionFind1 : 2.21875459 s
        // UnionFind2 : 0.01389166 s

        int size = 1000000;
        int m = 1000000;

        // UnionFind1 : 39.69679417 s
        // UnionFind2 : 64.55422291 s

        // UnionFind1 uf1 = new UnionFind1(size);
        // System.out.println("UnionFind1 : " + testUF(uf1, m) + " s");
        //
        // UnionFind2 uf2 = new UnionFind2(size);
        // System.out.println("UnionFind2 : " + testUF(uf2, m) + " s");

        UnionFind3 uf3 = new UnionFind3(size);
        System.out.println("UnionFind3 : " + testUF(uf3, m) + " s");

        // 太快了
        // UnionFind1 : 39.62142208 s
        // UnionFind2 : 65.45789666 s
        // UnionFind3 : 0.09888625 s

        UnionFind4 uf4 = new UnionFind4(size);
        System.out.println("UnionFind4 : " + testUF(uf4, m) + " s");

        UnionFind5 uf5 = new UnionFind5(size);
        System.out.println("UnionFind5 : " + testUF(uf5, m) + " s");

        UnionFind6 uf6 = new UnionFind6(size);
        System.out.println("UnionFind6 : " + testUF(uf6, m) + " s");
    }
}
