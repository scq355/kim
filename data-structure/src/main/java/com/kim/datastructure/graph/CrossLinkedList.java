package com.kim.datastructure.graph;

class CrossLinkedList {
    static class Node {
        int vertex; // 顶点编号
        Node down; // 指向下一个同一列的节点（出边）
        Node right; // 指向下一个同一行的节点（入边）

        Node(int vertex) {
            this.vertex = vertex;
            this.down = null;
            this.right = null;
        }
    }

    private Node[] rowHeaders; // 行头指针数组
    private Node[] colHeaders; // 列头指针数组
    private int numberOfVertices; // 顶点数量

    // 构造函数
    public CrossLinkedList(int size) {
        numberOfVertices = size;
        rowHeaders = new Node[size];
        colHeaders = new Node[size];
    }

    // 添加边
    public void addEdge(int from, int to) {
        if (from >= numberOfVertices || to >= numberOfVertices || from < 0 || to < 0) {
            System.out.println("无效的顶点索引");
            return;
        }

        Node newNode = new Node(to);

        // 在行链表中插入
        if (rowHeaders[from] == null) {
            rowHeaders[from] = newNode;
        } else {
            Node current = rowHeaders[from];
            while (current.right != null) {
                current = current.right;
            }
            current.right = newNode;
        }

        // 在列链表中插入
        if (colHeaders[to] == null) {
            colHeaders[to] = newNode;
        } else {
            Node current = colHeaders[to];
            while (current.down != null) {
                current = current.down;
            }
            current.down = newNode;
        }
    }

    // 删除边
    public void removeEdge(int from, int to) {
        if (from >= numberOfVertices || to >= numberOfVertices || from < 0 || to < 0) {
            System.out.println("无效的顶点索引");
            return;
        }

        // 从行链表中删除
        if (rowHeaders[from] != null) {
            Node current = rowHeaders[from];
            Node prev = null;
            while (current != null && current.vertex != to) {
                prev = current;
                current = current.right;
            }
            if (current != null) {
                if (prev == null) {
                    rowHeaders[from] = current.right; // 删除头节点
                } else {
                    prev.right = current.right; // 删除中间节点
                }
            }
        }

        // 从列链表中删除
        if (colHeaders[to] != null) {
            Node current = colHeaders[to];
            Node prev = null;
            while (current != null && current.vertex != from) {
                prev = current;
                current = current.down;
            }
            if (current != null) {
                if (prev == null) {
                    colHeaders[to] = current.down; // 删除头节点
                } else {
                    prev.down = current.down; // 删除中间节点
                }
            }
        }
    }

    // 打印十字链表
    public void printCrossList() {
        System.out.println("十字链表:");
        for (int i = 0; i < numberOfVertices; i++) {
            System.out.print(i + ": ");
            Node current = rowHeaders[i];
            while (current != null) {
                System.out.print(current.vertex + " ");
                current = current.right;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // 创建一个十字链表，包含5个顶点
        CrossLinkedList crossList = new CrossLinkedList(5);

        // 添加边
        crossList.addEdge(0, 1);
        crossList.addEdge(0, 4);
        crossList.addEdge(1, 3);
        crossList.addEdge(1, 4);
        crossList.addEdge(4, 3);

        // 打印十字链表
        crossList.printCrossList();

        // 删除边
        crossList.removeEdge(1, 3);

        // 打印十字链表
        crossList.printCrossList();
    }
}

