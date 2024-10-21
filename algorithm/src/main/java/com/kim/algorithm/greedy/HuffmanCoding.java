package com.kim.algorithm.greedy;

import java.util.PriorityQueue;

class Node {
    int freq; // 字符频率
    char ch;  // 字符
    Node left, right; // 左右子节点

    Node(int freq, char ch) {
        this.freq = freq;
        this.ch = ch;
        this.left = null;
        this.right = null;
    }
}

public class HuffmanCoding {

    // 构建霍夫曼树
    public static Node buildHuffmanTree(char[] chars, int[] freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.freq - b.freq);

        // 创建初始节点并加入优先队列
        for (int i = 0; i < chars.length; i++) {
            pq.add(new Node(freq[i], chars[i]));
        }

        // 合并节点直到只剩下一个
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node newNode = new Node(left.freq + right.freq, '\0'); // '\0' 表示非叶节点
            newNode.left = left;
            newNode.right = right;
            pq.add(newNode);
        }

        return pq.poll(); // 返回根节点
    }

    // 生成霍夫曼编码
    public static void generateHuffmanCodes(Node root, String code, String[] huffmanCodes) {
        if (root == null) {
            return;
        }

        // 到达叶节点，记录编码
        if (root.left == null && root.right == null) {
            huffmanCodes[root.ch] = code;
        }

        // 递归左子树和右子树
        generateHuffmanCodes(root.left, code + "0", huffmanCodes);
        generateHuffmanCodes(root.right, code + "1", huffmanCodes);
    }

    // 主程序
    public static void main(String[] args) {
        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] freq = {5, 9, 12, 13, 16, 45};

        // 构建霍夫曼树
        Node root = buildHuffmanTree(chars, freq);
        String[] huffmanCodes = new String[256]; // ASCII 范围

        // 生成霍夫曼编码
        generateHuffmanCodes(root, "", huffmanCodes);

        // 打印霍夫曼编码
        System.out.println("Huffman Codes:");
        for (char c : chars) {
            System.out.println(c + ": " + huffmanCodes[c]);
        }
    }
}

