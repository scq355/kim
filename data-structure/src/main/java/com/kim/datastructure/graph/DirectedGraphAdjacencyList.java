package com.kim.datastructure.graph;

import java.util.LinkedList;


class DirectedGraphAdjacencyList {
    private LinkedList<Integer>[] adjacencyList; // 邻接表
    private int numberOfVertices; // 顶点数量

    // 构造函数
    public DirectedGraphAdjacencyList(int size) {
        numberOfVertices = size;
        adjacencyList = new LinkedList[size];

        // 初始化邻接表
        for (int i = 0; i < size; i++) {
            adjacencyList[i] = new LinkedList<>(); // 每个顶点对应一个链表
        }
    }

    // 添加边
    public void addEdge(int source, int destination) {
        if (source >= numberOfVertices || destination >= numberOfVertices || source < 0 || destination < 0) {
            System.out.println("无效的顶点索引");
            return;
        }
        adjacencyList[source].add(destination); // 添加有向边
    }

    // 删除边
    public void removeEdge(int source, int destination) {
        if (source >= numberOfVertices || destination >= numberOfVertices || source < 0 || destination < 0) {
            System.out.println("无效的顶点索引");
            return;
        }
        adjacencyList[source].remove(Integer.valueOf(destination)); // 删除有向边
    }

    // 打印邻接表
    public void printGraph() {
        System.out.println("邻接表:");
        for (int i = 0; i < numberOfVertices; i++) {
            System.out.print(i + ": ");
            for (int neighbor : adjacencyList[i]) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    // 查找邻接节点
    public void findAdjacent(int vertex) {
        if (vertex >= numberOfVertices || vertex < 0) {
            System.out.println("无效的顶点索引");
            return;
        }
        System.out.print("顶点 " + vertex + " 的邻接节点: ");
        for (int neighbor : adjacencyList[vertex]) {
            System.out.print(neighbor + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 创建一个有向图，包含5个顶点
        DirectedGraphAdjacencyList graph = new DirectedGraphAdjacencyList(5);

        // 添加边
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(4, 3);

        // 打印图
        graph.printGraph();

        // 查找邻接节点
        graph.findAdjacent(1);

        // 删除边
        graph.removeEdge(1, 3);

        // 打印图
        graph.printGraph();
    }
}
