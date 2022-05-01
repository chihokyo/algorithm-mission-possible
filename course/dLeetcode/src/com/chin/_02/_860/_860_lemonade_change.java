package com.chin._02._860;

public class _860_lemonade_change {
    public boolean lemonadeChange(int[] bills) {
        int five = 0;
        int ten = 0;
        for (int bill : bills) {
            // 如果给的5，直接拿着
            if (bill == 5) {
                five++;
                // 如果给的10，5--
            } else if (bill == 10) {
                if (five == 0) return false;
                five--;
                ten++;
                // 如果给的20，那么2种选择  ①10--,5-- ②5*3--
            } else {
                if (ten > 0 && five > 0) {
                    ten--;
                    five--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
