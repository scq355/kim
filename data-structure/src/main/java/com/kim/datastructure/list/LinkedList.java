package com.kim.datastructure.list;

class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
        this.value = value;
        this.next = null;
    }
}

public class LinkedList {
    ListNode head;

    // 构造函数，初始化头节点
    public LinkedList() {
        head = new ListNode(0); // 头节点
    }

    // 头插法
    public void insertAtHead(int value) {
        ListNode newNode = new ListNode(value);
        newNode.next = head.next; // 新节点指向原来的第一个节点
        head.next = newNode; // 头节点指向新节点
    }

    // 尾插法
    public void insertAtTail(int value) {
        ListNode newNode = new ListNode(value);
        ListNode current = head;

        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode; // 最后一个节点指向新节点
    }

    // 链表逆序
    public void reverse() {
        ListNode prev = null;
        ListNode current = head.next; // 从第一个实际节点开始
        ListNode next = null;

        while (current != null) {
            next = current.next; // 保存下一个节点
            current.next = prev; // 反转当前节点的指针
            prev = current; // 移动 prev 指针
            current = next; // 移动到下一个节点
        }
        head.next = prev; // 更新头节点指向新的第一个节点
    }


    // 删除指定值的节点
    public void delete(int value) {
        ListNode current = head;

        while (current.next != null) {
            if (current.next.value == value) {
                current.next = current.next.next; // 删除节点
                return;
            }
            current = current.next;
        }
    }

    // 查找指定值的节点
    public boolean search(int value) {
        ListNode current = head.next; // 从第一个实际节点开始

        while (current != null) {
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // 遍历链表
    public void display() {
        ListNode current = head.next; // 从第一个实际节点开始
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.println();
    }

    // 清空链表
    public void clear() {
        head.next = null; // 清空实际节点
    }

    // 获取链表长度
    public int length() {
        int length = 0;
        ListNode current = head.next; // 从第一个实际节点开始

        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        // 插入节点
        list.insertAtTail(10);
        list.insertAtTail(20);
        list.insertAtTail(30);
        list.insertAtHead(60);
        list.insertAtHead(80);
        System.out.println("链表元素:");
        list.display(); // 输出: 10 20 30

        // 查找节点
        System.out.println("查找 20: " + list.search(20)); // 输出: true
        System.out.println("查找 40: " + list.search(40)); // 输出: false

        // 删除节点
        list.delete(20);
        System.out.println("删除 20 后的链表元素:");
        list.display(); // 输出: 10 30

        // 获取链表长度
        System.out.println("链表长度: " + list.length()); // 输出: 2

        list.reverse();
        list.display();

        // 清空链表
        list.clear();
        System.out.println("清空后的链表长度: " + list.length()); // 输出: 0
    }
}

