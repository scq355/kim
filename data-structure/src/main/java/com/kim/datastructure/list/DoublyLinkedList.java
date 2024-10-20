package com.kim.datastructure.list;

class DoublyListNode {
    int value;
    DoublyListNode prev; // 前驱节点
    DoublyListNode next; // 后继节点

    DoublyListNode(int value) {
        this.value = value;
        this.prev = null;
        this.next = null;
    }
}

public class DoublyLinkedList {
    DoublyListNode head; // 头节点

    // 构造函数，初始化头节点
    public DoublyLinkedList() {
        head = new DoublyListNode(0); // 头节点，值为0
    }

    // 尾插法
    public void insertAtTail(int value) {
        DoublyListNode newNode = new DoublyListNode(value);
        DoublyListNode current = head;

        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode; // 将新节点连接到最后
        newNode.prev = current; // 设置新节点的前驱
    }

    // 头插法
    public void insertAtHead(int value) {
        DoublyListNode newNode = new DoublyListNode(value);
        newNode.next = head.next; // 新节点指向原来的第一个节点
        head.next = newNode; // 头节点指向新节点
        newNode.prev = head; // 新节点的前驱是头节点
        if (newNode.next != null) {
            newNode.next.prev = newNode; // 如果链表不是空的，更新原第一个节点的前驱
        }
    }

    // 删除指定值的节点
    public void delete(int value) {
        DoublyListNode current = head.next; // 从第一个实际节点开始

        while (current != null) {
            if (current.value == value) {
                if (current.prev != null) {
                    current.prev.next = current.next; // 连接前驱和后继
                }
                if (current.next != null) {
                    current.next.prev = current.prev; // 连接后继和前驱
                }
                return;
            }
            current = current.next;
        }
    }

    // 查找指定值的节点
    public boolean search(int value) {
        DoublyListNode current = head.next; // 从第一个实际节点开始

        while (current != null) {
            if (current.value == value) {
                return true; // 找到值
            }
            current = current.next;
        }
        return false; // 未找到值
    }

    // 遍历链表
    public void display() {
        DoublyListNode current = head.next; // 从第一个实际节点开始
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.println();
    }

    // 清空链表
    public void clear() {
        head.next = null; // 直接清空实际节点
    }

    // 获取链表长度
    public int length() {
        int length = 0;
        DoublyListNode current = head.next; // 从第一个实际节点开始

        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }

    public static void main(String[] args) {
        DoublyLinkedList list = new DoublyLinkedList();

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

        // 清空链表
        list.clear();
        System.out.println("清空后的链表长度: " + list.length()); // 输出: 0
    }
}
