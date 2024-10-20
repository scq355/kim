package com.kim.datastructure.queue;

import java.util.LinkedList;

class Queue {
    private LinkedList<Integer> list; // 使用链表来实现队列

    // 构造函数
    public Queue() {
        list = new LinkedList<>();
    }

    // 入队操作
    public void enqueue(int value) {
        list.addLast(value); // 在尾部添加元素
    }

    // 出队操作
    public Integer dequeue() {
        if (isEmpty()) {
            return null; // 队列为空时返回null
        }
        return list.removeFirst(); // 移除并返回头部元素
    }

    // 获取队首元素
    public Integer peek() {
        if (isEmpty()) {
            return null; // 队列为空时返回null
        }
        return list.getFirst(); // 返回头部元素但不移除
    }

    // 检查队列是否为空
    public boolean isEmpty() {
        return list.isEmpty(); // 使用链表的isEmpty方法
    }

    // 获取队列长度
    public int size() {
        return list.size(); // 返回链表的大小
    }

    // 遍历队列
    public void display() {
        for (Integer value : list) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Queue queue = new Queue();

        // 入队操作
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        System.out.println("队列元素:");
        queue.display(); // 输出: 10 20 30

        // 出队操作
        System.out.println("出队元素: " + queue.dequeue()); // 输出: 10
        System.out.println("队列元素:");
        queue.display(); // 输出: 20 30

        // 获取队首元素
        System.out.println("队首元素: " + queue.peek()); // 输出: 20

        // 获取队列长度
        System.out.println("队列长度: " + queue.size()); // 输出: 2

        // 检查队列是否为空
        System.out.println("队列是否为空: " + queue.isEmpty()); // 输出: false

        // 清空队列
        queue.dequeue(); // 出队20
        queue.dequeue(); // 出队30
        System.out.println("队列是否为空: " + queue.isEmpty()); // 输出: true
    }
}

