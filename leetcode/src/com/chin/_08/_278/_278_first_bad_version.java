package com.chin._08._278;

public class _278_first_bad_version {
    // lc给了API，这里随便给了一个
    private boolean isBadVersion(int n) {
        return true;
    }

    public int firstBadVersion1(int n) {
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 如果mid这个是坏版本，那么就一定是这个吗
            // 不一定，因为前面可能还是，所以还要判断一下
            if (isBadVersion(mid)) {
                if (mid == 1 || !isBadVersion(mid - 1)) return mid;
                    // 如果还是坏版本，就继续缩小
                else right = mid - 1;
            } else {
                // 到这里说明肯定不是坏版本，需要向右找
                left = mid + 1;
            }
        }
        return -1;
    }

    // 在一定范围内找
    public int firstBadVersion2(int n) {
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 坏版本，说明自己是or自己后面也有可能是
            if (isBadVersion(mid)) {
                left = mid;
            } else {
                // 那肯定是前面了
                right = mid - 1;
            }
        }
        // 走完了还不是，就最后了。
        return left;
    }

}
