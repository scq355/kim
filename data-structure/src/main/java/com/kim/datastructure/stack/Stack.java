package com.kim.datastructure.stack;

import java.util.LinkedList;

public class Stack {
    private LinkedList<Integer> list; // 使用链表来实现栈

    // 构造函数
    public Stack() {
        list = new LinkedList<>();
    }

    // 压栈（入栈）
    public void push(int value) {
        list.addLast(value); // 在尾部添加元素
    }

    // 弹栈（出栈）
    public Integer pop() {
        if (isEmpty()) {
            return null; // 栈为空时返回null
        }
        return list.removeLast(); // 移除并返回尾部元素
    }

    // 获取栈顶元素
    public Integer peek() {
        if (isEmpty()) {
            return null; // 栈为空时返回null
        }
        return list.getLast(); // 返回尾部元素但不移除
    }

    // 检查栈是否为空
    public boolean isEmpty() {
        return list.isEmpty(); // 使用链表的isEmpty方法
    }

    // 获取栈的大小
    public int size() {
        return list.size(); // 返回链表的大小
    }

    // 遍历栈
    public void display() {
        for (Integer value : list) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Stack stack = new Stack();

        // 压栈
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("栈元素:");
        stack.display(); // 输出: 10 20 30

        // 弹栈
        System.out.println("弹栈元素: " + stack.pop()); // 输出: 30
        System.out.println("栈元素:");
        stack.display(); // 输出: 10 20

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
