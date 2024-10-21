package com.kim.datastructure.queue;

class CNode {
    char data;     // 节点数据
    CNode next;     // 指向下一个节点的指针

    CNode(char data) {
        this.data = data;
        this.next = null;
    }
}

public class CLinkedStack {
    private CNode top; // 栈顶
    private int size; // 当前元素数量

    public CLinkedStack() {
        this.top = null; // 初始化栈顶
        this.size = 0;   // 初始化元素数量
    }

    public void push(char value) {
        CNode newNode = new CNode(value);
        newNode.next = top; // 新节点指向当前栈顶
        top = newNode;      // 更新栈顶为新节点
        size++;
    }

    public void push(String values) {
        for (char value : values.toCharArray()) {
            CNode newNode = new CNode(value);
            newNode.next = top; // 新节点指向当前栈顶
            top = newNode;      // 更新栈顶为新节点
            size++;
        }
    }

    public Character pop() {
        if (isEmpty()) {
            return null; // 栈空时返回null
        }
        char value = top.data; // 保存栈顶元素
        top = top.next;        // 更新栈顶指针
        size--;
        return value;          // 返回出栈元素
    }

    public Character peek() {
        if (isEmpty()) {
            return null; // 栈空时返回null
        }
        return top.data; // 返回栈顶元素但不移除
    }

    public boolean isEmpty() {
        return size == 0; // 元素数量为0时表示栈空
    }

    public int size() {
        return size; // 返回当前元素数量
    }

    /**
     * 括号匹配算法
     */
    public static boolean isBalanced(String expression) {
        CLinkedStack stack = new CLinkedStack();
        for (char ch : expression.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false; // 不匹配
                }
            }
        }
        return stack.isEmpty(); // 如果栈空，则匹配
    }

    /**
     * 表达式求值
     */
    public static int evaluateExpression(String expression) {
        CLinkedStack operands = new CLinkedStack(); // 操作数栈
        CLinkedStack operators = new CLinkedStack(); // 操作符栈

        for (char ch : expression.toCharArray()) {
            if (Character.isDigit(ch)) {
                operands.push(ch); // 将数字入栈
            } else if ("+-*/".indexOf(ch) != -1) {
                while (!operators.isEmpty()
                        && precedence(ch) <= precedence(operators.peek())) {
                    int right = operands.pop() - '0'; // 转为数字
                    int left = operands.pop() - '0'; // 转为数字
                    char operator = operators.pop();
                    operands.push((char) (calculate(left, right, operator) + '0')); // 计算并压入栈
                }
                operators.push(ch); // 压入操作符
            }
        }

        while (!operators.isEmpty()) {
            int right = operands.pop() - '0';
            int left = operands.pop() - '0';
            char operator = operators.pop();
            operands.push((char) (calculate(left, right, operator) + '0'));
        }

        return operands.pop() - '0'; // 返回结果
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return 0;
    }

    private static int calculate(int left, int right, char operator) {
        switch (operator) {
            case '+': return left + right;
            case '-': return left - right;
            case '*': return left * right;
            case '/': return left / right;
        }
        return 0;
    }

    /**
     * 中缀转后缀
     */
    public static String infixToPostfix(String expression) {
        CLinkedStack stack = new CLinkedStack();
        StringBuilder output = new StringBuilder();

        for (char ch : expression.toCharArray()) {
            if (Character.isDigit(ch)) {
                output.append(ch); // 直接输出数字
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                stack.pop(); // 弹出 '('
            } else {
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    output.append(stack.pop()); // 弹出栈中操作符
                }
                stack.push(ch); // 压入当前操作符
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop()); // 弹出剩余的操作符
        }

        return output.toString();
    }


    /**
     * 后缀转中缀
     */
    public static String postfixToInfix(String expression) {
        CLinkedStack stack = new CLinkedStack();

        for (char ch : expression.toCharArray()) {
            if (Character.isDigit(ch)) {
                stack.push(ch); // 数字压入栈
            } else {
                // 弹出两个操作数
                String right = String.valueOf(stack.pop());
                String left = String.valueOf(stack.pop());
                // 创建新的中缀表达式
                String newExpr = "(" + left + ch + right + ")";
                for (char c : newExpr.toCharArray()) {
                    stack.push(c); // 将新表达式中的每个字符压入栈
                }
            }
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop()); // 将栈中的元素拼接成最终结果
        }

        return result.reverse().toString(); // 反转结果
    }


    public static void main(String[] args) {
        // 括号匹配示例
        String expr1 = "((a+b)*c)";
        System.out.println("括号匹配: " + isBalanced(expr1)); // 输出: true

        // 表达式求值示例
        String expr2 = "3*2+5";
        System.out.println("表达式求值: " + evaluateExpression(expr2)); // 输出: 12

        // 中缀转后缀示例
        String infix = "3+(2*4)";
        String postfix = infixToPostfix(infix);
        System.out.println("中缀转后缀: " + postfix); // 输出: 324*+

        // 后缀转中缀示例
        String infixResult = postfixToInfix(postfix);
        System.out.println("后缀转中缀: " + infixResult); // 输出: (3+(2*4))
    }

}

