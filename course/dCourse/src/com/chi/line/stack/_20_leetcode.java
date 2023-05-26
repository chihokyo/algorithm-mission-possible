import java.util.Stack;

/**
 * 有效的括号
 */
public class _20_leetcode {

    // 1. 暴力解法
    public boolean isValid(String s) {
        StringBuilder sb = new StringBuilder(s);
        int count = s.length() / 2;
        // 如果按照每一次就消掉一对括号来算 顶多i会循环一半的次数
        for (int i = 0; i < count; i++) {
            // 这里为什么是s.length - 1 因为你要循环到底
            // 如果不明白可以画图
            for (int j = 0; j < s.length() - 1; j++) {
                // 对比两个 如果是一对话 就删除掉这俩
                char c1 = sb.charAt(j);
                char c2 = sb.charAt(j + 1);
                if (isMatched(c1, c2)) {
                    sb.delete(j, j + 2);
                    // 终止此次循环
                    break;
                }
            }
        }

        return s.length() == 0; // 如果0就是说明已经全部剔除掉了 是有效的
    }

    private boolean isMatched(char c1, char c2) {
        if (c1 == '(')
            return c2 == ')';
        else if (c1 == '[')
            return c2 == ']';
        else if (c1 == '{')
            return c2 == '}';
        else
            return false;
    }

    //2. 使用栈来暂存
    public boolean isValid2(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                char topChar = stack.pop();
                if (c == ')' && topChar != '(') {
                    return false;
                }
                if (c == ']' && topChar != '[') {
                    return false;
                }
                if (c == '}' && topChar != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // 3. 借鉴题解
    public boolean isValid3(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(') stack.push(')');
            else if (c == '[') stack.push(']');
            else if (c == '{') stack.push('}');
            else if (stack.isEmpty() || c != stack.pop()) return false;
        }
        return stack.isEmpty();
    }
}
