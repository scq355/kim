package com.kim.datastructure.queue;

class CircularQueue {
    private int[] arr;        // 数组存储队列元素
    private int front;        // 队首索引
    private int rear;         // 队尾索引
    private int capacity;     // 队列的容量
    private int size;         // 当前元素数量

    // 构造函数
    public CircularQueue(int size) {
        arr = new int[size];
        capacity = size;
        front = 0;          // 初始化队首索引
        rear = 0;           // 初始化队尾索引
        this.size = 0;      // 初始化元素数量
    }

    // 入队
    public void enqueue(int value) {
        if (size == capacity) {
            System.out.println("队列满，无法入队");
            return; // 队列满时返回
        }
        arr[rear] = value;            // 在队尾添加元素
        rear = (rear + 1) % capacity; // 循环更新队尾索引
        size++;
    }

    // 出队
    public Integer dequeue() {
        if (isEmpty()) {
            System.out.println("队列空，无法出队");
            return null; // 队列空时返回null
        }
        int value = arr[front];         // 保存队首元素
        front = (front + 1) % capacity; // 循环更新队首索引
        size--;
        return value;                   // 返回出队元素
    }

    // 获取队首元素
    public Integer peek() {
        if (isEmpty()) {
            return null; // 队列空时返回null
        }
        return arr[front]; // 返回队首元素但不移除
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
        CircularQueue queue = new CircularQueue(5); // 创建一个容量为5的循环队列

        // 入队
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        System.out.println("队列元素:");
        for (int i = 0; i < queue.size(); i++) {
            System.out.print(queue.arr[(queue.front + i) % queue.capacity] + " ");
        }
        System.out.println();

        // 出队
        System.out.println("出队元素: " + queue.dequeue()); // 输出: 10
        System.out.println("队列元素:");
        for (int i = 0; i < queue.size(); i++) {
            System.out.print(queue.arr[(queue.front + i) % queue.capacity] + " ");
        }
        System.out.println();

        // 获取队首元素
        System.out.println("队首元素: " + queue.peek()); // 输出: 20

        // 获取队列的大小
        System.out.println("队列的大小: " + queue.size()); // 输出: 2

        // 清空队列
        queue.dequeue(); // 移除20
        queue.dequeue(); // 移除30
        System.out.println("队列是否为空: " + queue.isEmpty()); // 输出: true
    }
}

