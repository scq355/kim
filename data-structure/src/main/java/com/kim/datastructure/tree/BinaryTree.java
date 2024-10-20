package com.kim.datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class TreeNode {
    int value;
    TreeNode left, right;

    TreeNode(int item) {
        value = item;
        left = right = null;
    }
}

public class BinaryTree {
    TreeNode root;

    // 判断二叉树是否为空
    boolean isEmpty() {
        return root == null;
    }

    // 插入节点
    void insert(int value) {
        root = insertRec(root, value);
    }

    TreeNode insertRec(TreeNode node, int value) {
        if (node == null) {
            return new TreeNode(value);
        }
        if (value < node.value)
            node.left = insertRec(node.left, value);
        else if (value > node.value)
            node.right = insertRec(node.right, value);
        return node;
    }

    // 查找节点
    boolean search(int value) {
        return searchRec(root, value);
    }

    boolean searchRec(TreeNode node, int value) {
        if (node == null) return false;
        if (node.value == value) return true;
        return value < node.value ? searchRec(node.left, value) : searchRec(node.right, value);
    }

    // 删除节点
    void delete(int value) {
        root = deleteRec(root, value);
    }

    TreeNode deleteRec(TreeNode root, int value) {
        if (root == null) return root;

        if (value < root.value)
            root.left = deleteRec(root.left, value);
        else if (value > root.value)
            root.right = deleteRec(root.right, value);
        else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            root.value = minValue(root.right);
            root.right = deleteRec(root.right, root.value);
        }
        return root;
    }

    int minValue(TreeNode node) {
        int minValue = node.value;
        while (node.left != null) {
            minValue = node.left.value;
            node = node.left;
        }
        return minValue;
    }

    // 中序遍历
    void inorderTraversal(TreeNode node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.print(node.value + " ");
            inorderTraversal(node.right);
        }
    }

    // 前序遍历
    void preorderTraversal(TreeNode node) {
        if (node != null) {
            System.out.print(node.value + " ");
            preorderTraversal(node.left);
            preorderTraversal(node.right);
        }
    }

    // 后序遍历
    void postorderTraversal(TreeNode node) {
        if (node != null) {
            postorderTraversal(node.left);
            postorderTraversal(node.right);
            System.out.print(node.value + " ");
        }
    }


    // 非递归中序遍历
    void inorderTraversal() {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            System.out.print(current.value + " ");
            current = current.right;
        }
    }

    // 非递归前序遍历
    void preorderTraversal() {
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            System.out.print(current.value + " ");

            if (current.right != null) stack.push(current.right);
            if (current.left != null) stack.push(current.left);
        }
    }

    // 非递归后序遍历
    void postorderTraversal() {
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> output = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            output.push(current);

            if (current.left != null) stack.push(current.left);
            if (current.right != null) stack.push(current.right);
        }

        while (!output.isEmpty()) {
            System.out.print(output.pop().value + " ");
        }
    }


    // 计算树的高度
    int height(TreeNode node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    // 层次遍历
    void levelOrderTraversal() {
        if (root == null) return;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.value + " ");

            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }


    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);

        System.out.println(tree.isEmpty());

        System.out.println("Inorder traversal:");
        tree.inorderTraversal(tree.root);
        System.out.println();

        System.out.println("Preorder traversal:");
        tree.preorderTraversal(tree.root);
        System.out.println();

        System.out.println("Postorder traversal:");
        tree.postorderTraversal(tree.root);
        System.out.println();

        System.out.println("Level order traversal:");
        tree.levelOrderTraversal();

        System.out.println("Non-recursive Inorder traversal:");
        tree.inorderTraversal();
        System.out.println();

        System.out.println("Non-recursive Preorder traversal:");
        tree.preorderTraversal();
        System.out.println();

        System.out.println("Non-recursive Postorder traversal:");
        tree.postorderTraversal();

        System.out.println("Height of tree: " + tree.height(tree.root));

        System.out.println("Search for 40: " + tree.search(40));
        tree.delete(20);
        System.out.println("Inorder traversal after deleting 20:");
        tree.inorderTraversal(tree.root);
    }
}
