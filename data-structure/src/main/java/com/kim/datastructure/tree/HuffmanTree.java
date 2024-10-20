package com.kim.datastructure.tree;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

class HuffmanNode {
    int frequency; // 节点的频率
    char character; // 节点代表的字符
    HuffmanNode left; // 左子树
    HuffmanNode right; // 右子树

    public HuffmanNode(int frequency, char character) {
        this.frequency = frequency;
        this.character = character;
        this.left = null;
        this.right = null;
    }
}

public class HuffmanTree {
    private HuffmanNode root;

    // 构建哈夫曼树
    public HuffmanTree(Map<Character, Integer> frequencies) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));

        // 将所有字符和频率放入优先队列
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getValue(), entry.getKey()));
        }

        // 构建哈夫曼树
        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode parent = new HuffmanNode(left.frequency + right.frequency, '\0'); // 内部节点
            parent.left = left;
            parent.right = right;
            priorityQueue.add(parent);
        }

        root = priorityQueue.poll(); // 最后的根节点
    }

    // 生成哈夫曼编码
    public void generateCodes(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.character, code); // 到达叶子节点，保存编码
        }

        generateCodes(node.left, code + "0", huffmanCodes); // 左边为0
        generateCodes(node.right, code + "1", huffmanCodes); // 右边为1
    }

    // 获取哈夫曼编码
    public Map<Character, String> getHuffmanCodes() {
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateCodes(root, "", huffmanCodes);
        return huffmanCodes;
    }

    // 编码
    public String encode(String text) {
        Map<Character, String> huffmanCodes = getHuffmanCodes();
        StringBuilder encodedString = new StringBuilder();

        for (char c : text.toCharArray()) {
            encodedString.append(huffmanCodes.get(c)); // 根据字符获取编码
        }

        return encodedString.toString();
    }

    // 解码
    public String decode(String encodedString) {
        StringBuilder decodedString = new StringBuilder();
        HuffmanNode current = root;

        for (char bit : encodedString.toCharArray()) {
            if (bit == '0') {
                current = current.left; // 左移
            } else {
                current = current.right; // 右移
            }

            if (current.left == null && current.right == null) { // 到达叶子节点
                decodedString.append(current.character);
                current = root; // 回到根节点
            }
        }

        return decodedString.toString();
    }
}


