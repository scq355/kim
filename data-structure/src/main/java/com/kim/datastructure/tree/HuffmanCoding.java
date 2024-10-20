package com.kim.datastructure.tree;

import java.util.HashMap;
import java.util.Map;

public class HuffmanCoding {
    public static void main(String[] args) {
        // 频率表
        Map<Character, Integer> frequencies = new HashMap<>();
        frequencies.put('a', 5);
        frequencies.put('b', 9);
        frequencies.put('c', 12);
        frequencies.put('d', 13);
        frequencies.put('e', 16);
        frequencies.put('f', 45);

        // 构建哈夫曼树
        HuffmanTree huffmanTree = new HuffmanTree(frequencies);

        // 获取哈夫曼编码
        Map<Character, String> huffmanCodes = huffmanTree.getHuffmanCodes();
        System.out.println("哈夫曼编码:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // 编码示例
        String text = "abcde";
        String encodedString = huffmanTree.encode(text);
        System.out.println("编码后的字符串: " + encodedString);

        // 解码示例
        String decodedString = huffmanTree.decode(encodedString);
        System.out.println("解码后的字符串: " + decodedString);
    }
}

