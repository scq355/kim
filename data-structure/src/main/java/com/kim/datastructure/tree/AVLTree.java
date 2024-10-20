package com.kim.datastructure.tree;

public class AVLTree {
    static class Node {
        int key;
        Node left, right;
        int height;

        Node(int key) {
            this.key = key;
            this.height = 1; // 新节点的初始高度为1
        }
    }

    private Node root;

    // 获取节点高度
    private int height(Node N) {
        return N == null ? 0 : N.height;
    }

    // 获取平衡因子
    private int getBalance(Node N) {
        return N == null ? 0 : height(N.left) - height(N.right);
    }

    // 右旋转
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // 进行旋转
        x.right = y;
        y.left = T2;

        // 更新高度
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x; // 返回新的根节点
    }

    // 左旋转
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // 进行旋转
        y.left = x;
        x.right = T2;

        // 更新高度
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y; // 返回新的根节点
    }

    // 插入节点
    public void insert(int key) {
        root = insertRec(root, key);
    }

    private Node insertRec(Node node, int key) {
        // 执行标准的二叉搜索树插入
        if (node == null) {
            return new Node(key);
        }
        if (key < node.key) {
            node.left = insertRec(node.left, key);
        } else if (key > node.key) {
            node.right = insertRec(node.right, key);
        } else {
            return node; // 不允许插入重复键
        }

        // 更新节点高度
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // 检查节点是否失去平衡
        int balance = getBalance(node);

        // 左左情况
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        // 右右情况
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        // 左右情况
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // 右左情况
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node; // 返回未变更的节点指针
    }

    // 删除节点
    public void delete(int key) {
        root = deleteRec(root, key);
    }

    private Node deleteRec(Node root, int key) {
        // 执行标准的二叉搜索树删除
        if (root == null) return root;

        if (key < root.key) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.key) {
            root.right = deleteRec(root.right, key);
        } else {
            // 找到要删除的节点
            if (root.left == null || root.right == null) {
                Node temp = root.left != null ? root.left : root.right;
                if (temp == null) {
                    return null; // 无子节点
                } else {
                    return temp; // 有一个子节点
                }
            } else {
                // 找到中序后继（最小值）
                root.key = minValue(root.right);
                root.right = deleteRec(root.right, root.key);
            }
        }

        // 更新节点高度
        root.height = 1 + Math.max(height(root.left), height(root.right));

        // 检查节点是否失去平衡
        int balance = getBalance(root);

        // 左左情况
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        // 右右情况
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        // 左右情况
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // 右左情况
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root; // 返回未变更的节点指针
    }

    // 找到最小值
    private int minValue(Node root) {
        int minValue = root.key;
        while (root.left != null) {
            minValue = root.left.key;
            root = root.left;
        }
        return minValue;
    }

    // 查找节点
    public boolean search(int key) {
        return searchRec(root, key);
    }

    private boolean searchRec(Node root, int key) {
        if (root == null) {
            return false;
        }
        if (root.key == key) {
            return true;
        }
        return key < root.key ? searchRec(root.left, key) : searchRec(root.right, key);
    }

    // 中序遍历
    public void inorder() {
        inorderRec(root);
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.key + " ");
            inorderRec(root.right);
        }
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

        // 插入节点
        avlTree.insert(30);
        avlTree.insert(20);
        avlTree.insert(40);
        avlTree.insert(10);
        avlTree.insert(25);
        avlTree.insert(35);
        avlTree.insert(50);

        // 中序遍历
        System.out.println("中序遍历:");
        avlTree.inorder(); // 输出: 10 20 25 30 35 40 50
        System.out.println();

        // 查找节点
        System.out.println("查找 25: " + avlTree.search(25)); // 输出: true
        System.out.println("查找 60: " + avlTree.search(60)); // 输出: false

        // 删除节点
        avlTree.delete(20);
        System.out.println("删除 20 后的中序遍历:");
        avlTree.inorder(); // 输出: 10 25 30 35 40 50
        System.out.println();

        avlTree.delete(30);
        System.out.println("删除 30 后的中序遍历:");
        avlTree.inorder(); // 输出: 10 25 35 40 50
        System.out.println();
    }
}

