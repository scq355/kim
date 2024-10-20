package com.kim.datastructure.graph;

import java.util.LinkedList;
import java.util.Queue;

public class GraphAdjacencyList {
    private LinkedList<Integer>[] adjacencyList; // 邻接表
    private int numberOfVertices; // 顶点数量

    // 构造函数
    public GraphAdjacencyList(int size) {
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
        adjacencyList[source].add(destination); // 添加边
        adjacencyList[destination].add(source); // 无向图，添加反向边
    }

    // 删除边
    public void removeEdge(int source, int destination) {
        if (source >= numberOfVertices || destination >= numberOfVertices || source < 0 || destination < 0) {
            System.out.println("无效的顶点索引");
            return;
        }
        adjacencyList[source].remove(Integer.valueOf(destination)); // 删除边
        adjacencyList[destination].remove(Integer.valueOf(source)); // 无向图，删除反向边
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

    // 广度优先遍历
    public void bfs(int startVertex) {
        boolean[] visited = new boolean[numberOfVertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);
        visited[startVertex] = true;

        System.out.print("广度优先遍历: ");
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");

            for (int neighbor : adjacencyList[vertex]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    // 深度优先遍历
    public void dfs(int startVertex) {
        boolean[] visited = new boolean[numberOfVertices];
        System.out.print("深度优先遍历: ");
        dfsUtil(startVertex, visited);
        System.out.println();
    }

    private void dfsUtil(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");

        for (int neighbor : adjacencyList[vertex]) {
            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited);
            }
        }
    }

    public static void main(String[] args) {
        // 创建一个无向图，包含5个顶点
        GraphAdjacencyList graph = new GraphAdjacencyList(5);

        // 添加边
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 4);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);

        // 打印图
        graph.printGraph();

        // 查找邻接节点
        graph.findAdjacent(1);

        // 删除边
        graph.removeEdge(1, 3);

        // 打印图
        graph.printGraph();

        graph.bfs(0);
        graph.dfs(0);
    }
}
