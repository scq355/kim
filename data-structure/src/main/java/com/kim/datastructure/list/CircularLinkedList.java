package com.kim.datastructure.list;

class CircularListNode {
    int value;
    CircularListNode next;

    CircularListNode(int value) {
        this.value = value;
        this.next = null;
    }
}

public class CircularLinkedList {
    CircularListNode tail; // 指向尾节点

    // 构造函数，初始化循环链表
    public CircularLinkedList() {
        tail = null; // 初始为空
    }

    // 尾插法
    public void insertAtTail(int value) {
        CircularListNode newNode = new CircularListNode(value);
        if (tail == null) {
            tail = newNode; // 创建第一个节点
            tail.next = tail; // 指向自身
        } else {
            newNode.next = tail.next; // 新节点指向头节点
            tail.next = newNode; // 尾节点指向新节点
            tail = newNode; // 更新尾节点
        }
    }

    // 头插法
    public void insertAtHead(int value) {
        CircularListNode newNode = new CircularListNode(value);
        if (tail == null) {
            tail = newNode; // 创建第一个节点
            tail.next = tail; // 指向自身
        } else {
            newNode.next = tail.next; // 新节点指向头节点
            tail.next = newNode; // 尾节点指向新节点
        }
    }

    // 删除指定值的节点
    public void delete(int value) {
        if (tail == null) return; // 空链表

        CircularListNode current = tail.next; // 从头节点开始
        CircularListNode prev = tail; // 尾节点

        do {
            if (current.value == value) {
                if (current == tail && current.next == tail) {
                    // 只有一个节点的情况
                    tail = null;
                } else {
                    // 连接前后节点
                    if (current == tail) {
                        tail = prev; // 更新尾节点
                    }
                    prev.next = current.next; // 删除节点
                }
                return; // 找到并删除后退出
            }
            prev = current;
            current = current.next;
        } while (current != tail.next);
    }

    // 查找指定值的节点
    public boolean search(int value) {
        if (tail == null) return false; // 空链表

        CircularListNode current = tail.next; // 从头节点开始

        do {
            if (current.value == value) {
                return true; // 找到值
            }
            current = current.next;
        } while (current != tail.next);

        return false; // 未找到值
    }

    // 遍历链表
    public void display() {
        if (tail == null) return; // 空链表

        CircularListNode current = tail.next; // 从头节点开始
        do {
            System.out.print(current.value + " ");
            current = current.next;
        } while (current != tail.next);
        System.out.println();
    }

    // 获取链表长度
    public int length() {
        if (tail == null) return 0; // 空链表

        int count = 0;
        CircularListNode current = tail.next; // 从头节点开始
        do {
            count++;
            current = current.next;
        } while (current != tail.next);
        return count;
    }

    public static void main(String[] args) {
        CircularLinkedList list = new CircularLinkedList();

        // 尾插法
        list.insertAtTail(10);
        list.insertAtTail(20);
        list.insertAtTail(30);
        System.out.println("尾插法后链表元素:");
        list.display(); // 输出: 10 20 30

        // 头插法
        list.insertAtHead(5);
        System.out.println("头插法后链表元素:");
        list.display(); // 输出: 5 10 20 30

        // 查找节点
        System.out.println("查找 20: " + list.search(20)); // 输出: true
        System.out.println("查找 40: " + list.search(40)); // 输出: false

        // 删除节点
        list.delete(20);
        System.out.println("删除 20 后的链表元素:");
        list.display(); // 输出: 5 10 30

        // 获取链表长度
        System.out.println("链表长度: " + list.length()); // 输出: 3
    }
}

