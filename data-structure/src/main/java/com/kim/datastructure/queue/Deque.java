package com.kim.datastructure.queue;

import java.util.LinkedList;

public class Deque {
    private LinkedList<Integer> list; // 使用链表来实现双端队列

    // 构造函数
    public Deque() {
        list = new LinkedList<>();
    }

    // 从前面入队
    public void addFirst(int value) {
        list.addFirst(value); // 在头部添加元素
    }

    // 从后面入队
    public void addLast(int value) {
        list.addLast(value); // 在尾部添加元素
    }

    // 从前面出队
    public Integer removeFirst() {
        if (isEmpty()) {
            return null; // 队列为空时返回null
        }
        return list.removeFirst(); // 移除并返回头部元素
    }

    // 从后面出队
    public Integer removeLast() {
        if (isEmpty()) {
            return null; // 队列为空时返回null
        }
        return list.removeLast(); // 移除并返回尾部元素
    }

    // 获取队首元素
    public Integer peekFirst() {
        if (isEmpty()) {
            return null; // 队列为空时返回null
        }
        return list.getFirst(); // 返回头部元素但不移除
    }

    // 获取队尾元素
    public Integer peekLast() {
        if (isEmpty()) {
            return null; // 队列为空时返回null
        }
        return list.getLast(); // 返回尾部元素但不移除
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
        Deque deque = new Deque();

        // 从后面入队
        deque.addLast(10);
        deque.addLast(20);
        System.out.println("从后面入队后队列元素:");
        deque.display(); // 输出: 10 20

        // 从前面入队
        deque.addFirst(5);
        System.out.println("从前面入队后队列元素:");
        deque.display(); // 输出: 5 10 20

        // 从前面出队
        System.out.println("从前面出队元素: " + deque.removeFirst()); // 输出: 5
        System.out.println("队列元素:");
        deque.display(); // 输出: 10 20

        // 从后面出队
        System.out.println("从后面出队元素: " + deque.removeLast()); // 输出: 20
        System.out.println("队列元素:");
        deque.display(); // 输出: 10

        // 获取队首和队尾元素
        System.out.println("队首元素: " + deque.peekFirst()); // 输出: 10
        System.out.println("队尾元素: " + deque.peekLast()); // 输出: 10

        // 获取队列长度
        System.out.println("队列长度: " + deque.size()); // 输出: 1

        // 检查队列是否为空
        System.out.println("队列是否为空: " + deque.isEmpty()); // 输出: false

        // 清空队列
        deque.removeFirst(); // 移除10
        System.out.println("队列是否为空: " + deque.isEmpty()); // 输出: true
    }
}
