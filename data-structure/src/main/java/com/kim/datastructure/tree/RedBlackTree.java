package com.kim.datastructure.tree;

public class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    static class Node {
        int key;
        boolean color;
        Node left, right, parent;

        Node(int key) {
            this.key = key;
            this.color = RED; // 新节点总是红色
            this.left = this.right = this.parent = null;
        }
    }

    private Node root;
    private Node TNULL; // 哨兵节点

    public RedBlackTree() {
        TNULL = new Node(0); // 哨兵节点初始化
        TNULL.color = BLACK; // 哨兵节点为黑色
        root = TNULL; // 根节点初始化为哨兵节点
    }

    // 前序遍历
    private void preOrderHelper(Node node) {
        if (node != TNULL) {
            System.out.print(node.key + " ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    // 中序遍历
    private void inOrderHelper(Node node) {
        if (node != TNULL) {
            inOrderHelper(node.left);
            System.out.print(node.key + " ");
            inOrderHelper(node.right);
        }
    }

    // 后序遍历
    private void postOrderHelper(Node node) {
        if (node != TNULL) {
            postOrderHelper(node.left);
            postOrderHelper(node.right);
            System.out.print(node.key + " ");
        }
    }

    // 查找节点
    private Node searchHelper(Node node, int key) {
        if (node == TNULL || key == node.key) {
            return node;
        }
        return key < node.key ? searchHelper(node.left, key) : searchHelper(node.right, key);
    }

    // 旋转左
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;

        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    // 旋转右
    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;

        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;

        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }

        y.right = x;
        x.parent = y;
    }

    // 修正红黑树
    private void fixInsert(Node k) {
        Node u; // 叔叔节点
        while (k.parent != null && k.parent.color == RED) {
            if (k.parent == k.parent.parent.left) {
                u = k.parent.parent.right;
                if (u.color == RED) {
                    // 叔叔节点为红色
                    k.parent.color = BLACK;
                    u.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.left;
                if (u.color == RED) {
                    // 叔叔节点为红色
                    k.parent.color = BLACK;
                    u.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = BLACK; // 根节点始终为黑色
    }

    // 插入节点
    public void insert(int key) {
        Node node = new Node(key);
        node.parent = null;
        node.left = TNULL;
        node.right = TNULL;

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node; // 新根
        } else if (node.key < y.key) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = BLACK;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

    // 查找节点
    public Node search(int key) {
        return searchHelper(this.root, key);
    }

    // 中序遍历
    public void inOrder() {
        inOrderHelper(this.root);
    }

    // 前序遍历
    public void preOrder() {
        preOrderHelper(this.root);
    }

    // 后序遍历
    public void postOrder() {
        postOrderHelper(this.root);
    }

    // 删除节点
    public void delete(int key) {
        deleteNodeHelper(this.root, key);
    }

    private void deleteNodeHelper(Node node, int key) {
        Node z = TNULL;
        Node x, y;
        while (node != TNULL) {
            if (node.key == key) {
                z = node;
            }

            if (node.key <= key) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (z == TNULL) {
            System.out.println("找不到要删除的节点");
            return;
        }

        y = z;
        boolean yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == BLACK) {
            fixDelete(x);
        }
    }

    // 替换子树
    private void rbTransplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    // 修正删除后的红黑树
    private void fixDelete(Node x) {
        Node s;
        while (x != root && x.color == BLACK) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == RED) {
                    s.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == BLACK && s.right.color == BLACK) {
                    s.color = RED;
                    x = x.parent;
                } else {
                    if (s.right.color == BLACK) {
                        s.left.color = BLACK;
                        s.color = RED;
                        rightRotate(s);
                        s = x.parent.right;
                    }

                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == RED) {
                    s.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == BLACK && s.left.color == BLACK) {
                    s.color = RED;
                    x = x.parent;
                } else {
                    if (s.left.color == BLACK) {
                        s.right.color = BLACK;
                        s.color = RED;
                        leftRotate(s);
                        s = x.parent.left;
                    }

                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

    // 找到树中的最小值
    private Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    // 主方法
    public static void main(String[] args) {
        RedBlackTree rbTree = new RedBlackTree();

        // 插入节点
        rbTree.insert(55);
        rbTree.insert(40);
        rbTree.insert(65);
        rbTree.insert(30);
        rbTree.insert(50);
        rbTree.insert(60);
        rbTree.insert(70);

        System.out.println("中序遍历:");
        rbTree.inOrder(); // 输出: 30 40 50 55 60 65 70
        System.out.println();

        System.out.println("前序遍历:");
        rbTree.preOrder(); // 输出: 55 40 30 50 65 60 70
        System.out.println();

        System.out.println("后序遍历:");
        rbTree.postOrder(); // 输出: 30 50 40 60 70 65 55
        System.out.println();

        // 查找节点
        Node foundNode = rbTree.search(50);
        System.out.println("查找 50: " + (foundNode != rbTree.TNULL)); // 输出: true

        // 删除节点
        rbTree.delete(40);
        System.out.println("删除 40 后的中序遍历:");
        rbTree.inOrder(); // 输出: 30 50 55 60 65 70
        System.out.println();
    }
}
