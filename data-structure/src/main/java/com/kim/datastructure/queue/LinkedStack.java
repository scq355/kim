package com.kim.datastructure.queue;

public class LinkedStack {
    private Node top; // 栈顶
    private int size; // 当前元素数量

    // 构造函数
    public LinkedStack() {
        this.top = null; // 初始化栈顶
        this.size = 0;   // 初始化元素数量
    }

    // 压栈（入栈）
    public void push(int value) {
        Node newNode = new Node(value); // 创建新节点
        newNode.next = top;             // 新节点指向当前栈顶
        top = newNode;                  // 更新栈顶为新节点
        size++;
    }

    // 弹栈（出栈）
    public Integer pop() {
        if (isEmpty()) {
            System.out.println("栈空，无法弹栈");
            return null; // 栈空时返回null
        }
        int value = top.data; // 保存栈顶元素
        top = top.next;       // 更新栈顶指针
        size--;
        return value;         // 返回出栈元素
    }

    // 获取栈顶元素
    public Integer peek() {
        if (isEmpty()) {
            return null; // 栈空时返回null
        }
        return top.data; // 返回栈顶元素但不移除
    }

    // 检查栈是否为空
    public boolean isEmpty() {
        return size == 0; // 元素数量为0时表示栈空
    }

    // 获取栈的大小
    public int size() {
        return size; // 返回当前元素数量
    }

    public static void main(String[] args) {
        LinkedStack stack = new LinkedStack(); // 创建一个链栈

        // 压栈
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("栈元素:");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " "); // 输出: 30 20 10
        }
        System.out.println();

        // 重新压栈
        stack.push(40);
        stack.push(50);

        // 获取栈顶元素
        System.out.println("栈顶元素: " + stack.peek()); // 输出: 50

        // 获取栈的大小
        System.out.println("栈的大小: " + stack.size()); // 输出: 2

        // 检查栈是否为空
        System.out.println("栈是否为空: " + stack.isEmpty()); // 输出: false

        // 清空栈
        stack.pop(); // 弹出50
        stack.pop(); // 弹出40
        System.out.println("栈是否为空: " + stack.isEmpty()); // 输出: true
    }
}
