package com.chin._05._204;

public class _204_count_primes {

    // 埃氏筛 O(nlog(log n))
    public int countPrimes2(int n) {
        int res = 0;
        // 初始值全部都是false，也就是说初始值全部都是合数
        boolean[] notPrimes = new boolean[n];
        for (int i = 2; i < n; i++) {
            // 这里第一次也就是notPrimes[2]的时候，肯定是false的，因为2是质数

            // 如果这里notPrimes[i]是true 就是合数 那么直接跳过
            if (notPrimes[i]) continue;
            res++; // 走到这里肯定是质数 那么结果+1
            // 如果 i 是质数，那么 2i、3i、4i.... 肯定不是质数
            // 说明：这里 i 最好是从 i + i 开始，因为 i 有可能是质数
            // 其实 i 从 i 开始也没啥问题，因为 i 在上面已经判断过了
            // 但是，这样就违背了 notPrimes 数组的含义了，所以这里修改为 i + i
            for (int j = i + i; j < n; j += i) {
                notPrimes[j] = true; // ∵ 2是质数 ∴ 2*2,2*3,2*4...全部都是合数
            }
        }
        return res;
    }


    // 解法1 暴力解法 2个循环O(n^2)，数据量大不行
    public int countPrimes1(int n) {
        int res = 0;
        // 1既不是质数，又不是合数
        for (int i = 2; i < n; i++) {
            res += isPrime(i) ? 1 : 0;
        }
        return res;
    }

    // 判断一个数字x是不是指数
    private boolean isPrime(int number) {
        // 1既不是质数，又不是合数
        for (int i = 2; i < number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
