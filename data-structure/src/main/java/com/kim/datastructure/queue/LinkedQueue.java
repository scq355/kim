package com.kim.datastructure.queue;

public class LinkedQueue {
    private Node front; // 队首
    private Node rear;  // 队尾
    private int size;   // 当前元素数量

    // 构造函数
    public LinkedQueue() {
        this.front = null; // 初始化队首
        this.rear = null;  // 初始化队尾
        this.size = 0;     // 初始化元素数量
    }

    // 入队
    public void enqueue(int value) {
        Node newNode = new Node(value); // 创建新节点
        if (rear == null) {
            front = rear = newNode; // 如果队列空，队首和队尾都指向新节点
        } else {
            rear.next = newNode; // 队尾节点的下一个指向新节点
            rear = newNode;      // 更新队尾指针
        }
        size++;
    }

    // 出队
    public Integer dequeue() {
        if (isEmpty()) {
            System.out.println("队列空，无法出队");
            return null; // 队列空时返回null
        }
        int value = front.data; // 保存队首元素
        front = front.next;     // 更新队首指针
        if (front == null) {
            rear = null; // 如果队列为空，队尾也设置为null
        }
        size--;
        return value; // 返回出队元素
    }

    // 获取队首元素
    public Integer peek() {
        if (isEmpty()) {
            return null; // 队列空时返回null
        }
        return front.data; // 返回队首元素但不移除
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
        LinkedQueue queue = new LinkedQueue(); // 创建一个链队列

        // 入队
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        System.out.println("队列元素:");
        for (int i = 0; i < queue.size(); i++) {
            System.out.print(queue.peek() + " ");
            queue.dequeue(); // 为了遍历队列，出队元素
        }
        System.out.println();

        // 重新入队
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        // 出队
        System.out.println("出队元素: " + queue.dequeue()); // 输出: 10
        System.out.println("队列元素:");
        for (int i = 0; i < queue.size(); i++) {
            System.out.print(queue.peek() + " ");
            queue.dequeue(); // 为了遍历队列，出队元素
        }
        System.out.println();

        // 获取队首元素
        queue.enqueue(40);
        queue.enqueue(50);
        System.out.println("队首元素: " + queue.peek()); // 输出: 40

        // 获取队列的大小
        System.out.println("队列的大小: " + queue.size()); // 输出: 2

        // 检查队列是否为空
        System.out.println("队列是否为空: " + queue.isEmpty()); // 输出: false

        // 清空队列
        queue.dequeue(); // 移除40
        queue.dequeue(); // 移除50
        System.out.println("队列是否为空: " + queue.isEmpty()); // 输出: true
    }
}

