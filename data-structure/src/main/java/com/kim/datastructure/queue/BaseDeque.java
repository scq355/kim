package com.kim.datastructure.queue;

public class BaseDeque {
    private int[] arr;        // 数组存储双端队列元素
    private int front;        // 队首索引
    private int rear;         // 队尾索引
    private int capacity;     // 队列的容量
    private int size;         // 当前元素数量

    // 构造函数
    public BaseDeque(int size) {
        arr = new int[size]; // 初始化数组
        capacity = size;     // 设置队列的容量
        front = -1;         // 初始化队首索引
        rear = 0;           // 初始化队尾索引
        this.size = 0;      // 初始化元素数量
    }

    // 从前面入队
    public void addFirst(int value) {
        if (size == capacity) {
            System.out.println("队列满，无法从前面入队");
            return; // 队列满时返回
        }
        front = (front + 1) % capacity; // 循环更新队首索引
        arr[front] = value;              // 在队首位置添加元素
        size++;
    }

    // 从后面入队
    public void addLast(int value) {
        if (size == capacity) {
            System.out.println("队列满，无法从后面入队");
            return; // 队列满时返回
        }
        rear = (rear - 1 + capacity) % capacity; // 循环更新队尾索引
        arr[rear] = value;                      // 在队尾位置添加元素
        size++;
    }

    // 从前面出队
    public Integer removeFirst() {
        if (isEmpty()) {
            System.out.println("队列空，无法从前面出队");
            return null; // 队列空时返回null
        }
        int value = arr[front]; // 保存队首元素
        front = (front - 1 + capacity) % capacity; // 循环更新队首索引
        size--;
        return value; // 返回出队元素
    }

    // 从后面出队
    public Integer removeLast() {
        if (isEmpty()) {
            System.out.println("队列空，无法从后面出队");
            return null; // 队列空时返回null
        }
        int value = arr[rear]; // 保存队尾元素
        rear = (rear + 1) % capacity; // 循环更新队尾索引
        size--;
        return value; // 返回出队元素
    }

    // 获取队首元素
    public Integer peekFirst() {
        if (isEmpty()) {
            return null; // 队列空时返回null
        }
        return arr[front]; // 返回队首元素但不移除
    }

    // 获取队尾元素
    public Integer peekLast() {
        if (isEmpty()) {
            return null; // 队列空时返回null
        }
        return arr[rear]; // 返回队尾元素但不移除
    }

    // 检查队列是否为空
    public boolean isEmpty() {
        return size == 0; // 元素数量为0时表示队列空
    }

    // 获取队列的大小
    public int size() {
        return size; // 返回当前元素数量
    }

    public static void main(String[] args) {
        BaseDeque deque = new BaseDeque(5); // 创建一个容量为5的双端队列

        // 从后面入队
        deque.addLast(10);
        deque.addLast(20);
        System.out.println("从后面入队后队列元素:");
        for (int i = 0; i < deque.size(); i++) {
            System.out.print(deque.arr[(deque.rear + i) % deque.capacity] + " ");
        }
        System.out.println();

        // 从前面入队
        deque.addFirst(5);
        System.out.println("从前面入队后队列元素:");
        for (int i = 0; i < deque.size(); i++) {
            System.out.print(deque.arr[(deque.rear + i) % deque.capacity] + " ");
        }
        System.out.println();

        // 从前面出队
        System.out.println("从前面出队元素: " + deque.removeFirst()); // 输出: 5
        System.out.println("队列元素:");
        for (int i = 0; i < deque.size(); i++) {
            System.out.print(deque.arr[(deque.rear + i) % deque.capacity] + " ");
        }
        System.out.println();

        // 从后面出队
        System.out.println("从后面出队元素: " + deque.removeLast()); // 输出: 20
        System.out.println("队列元素:");
        for (int i = 0; i < deque.size(); i++) {
            System.out.print(deque.arr[(deque.rear + i) % deque.capacity] + " ");
        }
        System.out.println();

        // 获取队首和队尾元素
        System.out.println("队首元素: " + deque.peekFirst()); // 输出: 10
        System.out.println("队尾元素: " + deque.peekLast()); // 输出: 10

        // 获取队列的大小
        System.out.println("队列的大小: " + deque.size()); // 输出: 1

        // 检查队列是否为空
        System.out.println("队列是否为空: " + deque.isEmpty()); // 输出: false

        // 清空队列
        deque.removeFirst(); // 移除10
        System.out.println("队列是否为空: " + deque.isEmpty()); // 输出: true
    }
}

