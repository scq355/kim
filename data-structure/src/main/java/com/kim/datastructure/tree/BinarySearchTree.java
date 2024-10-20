package com.kim.datastructure.tree;

public class BinarySearchTree {
    static class Node {
        int key;
        Node left, right;

        Node(int item) {
            key = item;
            left = right = null;
        }
    }

    Node root;

    // 插入节点
    void insert(int key) {
        root = insertRec(root, key);
    }

    // 递归插入
    Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }
        if (key < root.key) {
            root.left = insertRec(root.left, key);
        } else if (key > root.key) {
            root.right = insertRec(root.right, key);
        }
        return root;
    }

    // 查找节点
    boolean search(int key) {
        return searchRec(root, key);
    }

    // 递归查找
    boolean searchRec(Node root, int key) {
        if (root == null) {
            return false;
        }
        if (root.key == key) {
            return true;
        }
        return key < root.key ? searchRec(root.left, key) : searchRec(root.right, key);
    }

    // 中序遍历
    void inorder() {
        inorderRec(root);
    }

    // 递归中序遍历
    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.key + " ");
            inorderRec(root.right);
        }
    }

    // 删除节点
    void delete(int key) {
        root = deleteRec(root, key);
    }

    // 递归删除
    Node deleteRec(Node root, int key) {
        if (root == null) return root;

        if (key < root.key) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.key) {
            root.right = deleteRec(root.right, key);
        } else {
            // 找到要删除的节点
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            // 找到中序后继（最小值）
            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    // 找到最小值
    int minValue(Node root) {
        int minValue = root.key;
        while (root.left != null) {
            minValue = root.left.key;
            root = root.left;
        }
        return minValue;
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        // 插入节点
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);

        // 中序遍历
        System.out.println("中序遍历:");
        bst.inorder(); // 输出：20 30 40 50 60 70 80
        System.out.println();

        // 查找节点
        System.out.println("查找 40: " + bst.search(40)); // 输出：true
        System.out.println("查找 90: " + bst.search(90)); // 输出：false

        // 删除节点
        bst.delete(20);
        System.out.println("删除 20 后的中序遍历:");
        bst.inorder(); // 输出：30 40 50 60 70 80
        System.out.println();

        bst.delete(30);
        System.out.println("删除 30 后的中序遍历:");
        bst.inorder(); // 输出：40 50 60 70 80
        System.out.println();

        bst.delete(50);
        System.out.println("删除 50 后的中序遍历:");
        bst.inorder(); // 输出：40 60 70 80
        System.out.println();
    }
}

