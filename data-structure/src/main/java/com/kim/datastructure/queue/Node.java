package com.kim.datastructure.queue;

public class Node {
    int data;      // 节点数据
    Node next;    // 指向下一个节点的指针

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}
