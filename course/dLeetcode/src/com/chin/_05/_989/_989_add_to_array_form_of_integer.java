package com.chin._05._989;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _989_add_to_array_form_of_integer {
    public List<Integer> addToArrayForm(int[] num, int k) {
        List<Integer> res = new ArrayList<>();
        int len = num.length - 1;
        int carry = 0;
        // 遍历条件 这里一旦数组index没有 并且 K等于0就跳出来了
        while (len >= 0 || k != 0) {
            // 不能这样简单相加 int sum = num[len] + k % 10 + carry;
            int x = len < 0 ? 0 : num[len]; // 因为x和y中间可能任一一方提前结束
            int y = k == 0 ? 0 : k % 10;
            int sum = x + y + carry;
            res.add(sum % 10); // 只能取最后1位
            carry = sum / 10;  // 计算carry
            len--;
            k = k / 10; // 每次都要去掉最后1位 取商 这里的目的是为了逐一去掉个位 1886→188→18→1
        }
        // 这里！！重要，没有的话就会造成 888 + 666 = 554（1554的1 会丢掉）
        if (carry != 0) res.add(carry);
        Collections.reverse(res);
        return res;
    }
}
