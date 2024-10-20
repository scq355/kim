package com.kim.datastructure.queue;

public class BaseStack {
    private int[] arr;        // 数组存储栈元素
    private int top;          // 栈顶索引
    private int capacity;     // 栈的容量

    // 构造函数
    public BaseStack(int size) {
        arr = new int[size]; // 初始化数组
        capacity = size;     // 设置栈的容量
        top = -1;           // 初始化栈顶为-1，表示栈空
    }

    // 压栈（入栈）
    public void push(int value) {
        if (top + 1 >= capacity) {
            System.out.println("栈满，无法压栈");
            return; // 栈满时返回
        }
        arr[++top] = value; // 将元素放入栈顶
    }

    // 弹栈（出栈）
    public Integer pop() {
        if (isEmpty()) {
            System.out.println("栈空，无法弹栈");
            return null; // 栈空时返回null
        }
        return arr[top--]; // 返回栈顶元素并将栈顶索引减1
    }

    // 获取栈顶元素
    public Integer peek() {
        if (isEmpty()) {
            return null; // 栈空时返回null
        }
        return arr[top]; // 返回栈顶元素但不移除
    }

    // 检查栈是否为空
    public boolean isEmpty() {
        return top == -1; // 栈顶为-1时表示栈空
    }

    // 获取栈的大小
    public int size() {
        return top + 1; // 返回栈中元素的数量
    }

    public static void main(String[] args) {
        BaseStack stack = new BaseStack(5); // 创建一个容量为5的栈

        // 压栈
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("栈元素:");
        for (int i = 0; i <= stack.top; i++) {
            System.out.print(stack.arr[i] + " ");
        }
        System.out.println();

        // 弹栈
        System.out.println("弹栈元素: " + stack.pop()); // 输出: 30
        System.out.println("栈元素:");
        for (int i = 0; i <= stack.top; i++) {
            System.out.print(stack.arr[i] + " ");
        }
        System.out.println();

        // 获取栈顶元素
        System.out.println("栈顶元素: " + stack.peek()); // 输出: 20

        // 获取栈的大小
        System.out.println("栈的大小: " + stack.size()); // 输出: 2

        // 检查栈是否为空
        System.out.println("栈是否为空: " + stack.isEmpty()); // 输出: false

        // 清空栈
        stack.pop(); // 弹出20
        stack.pop(); // 弹出10
        System.out.println("栈是否为空: " + stack.isEmpty()); // 输出: true
    }
}

