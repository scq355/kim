package com.kim.datastructure.tree;

import java.util.ArrayList;
import java.util.List;

class BPlusTreeNode {
    int t; // 最小度数
    List<Integer> keys; // 存储键的列表
    List<BPlusTreeNode> children; // 子节点列表
    boolean isLeaf; // 是否为叶子节点

    public BPlusTreeNode(int t, boolean isLeaf) {
        this.t = t;
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public void insertNonFull(int key) {
        int i = keys.size() - 1;

        if (isLeaf) {
            while (i >= 0 && keys.get(i) > key) {
                i--;
            }
            keys.add(i + 1, key); // 插入键
        } else {
            while (i >= 0 && keys.get(i) > key) {
                i--;
            }
            i++; // 找到子节点位置

            if (children.get(i).keys.size() == 2 * t - 1) {
                splitChild(i); // 分裂子节点
                if (keys.get(i) < key) {
                    i++;
                }
            }
            children.get(i).insertNonFull(key); // 递归插入
        }
    }

    public void splitChild(int i) {
        BPlusTreeNode z = new BPlusTreeNode(t, children.get(i).isLeaf);
        BPlusTreeNode y = children.get(i);

        for (int j = 0; j < t - 1; j++) {
            z.keys.add(y.keys.remove(t)); // 移除并添加
        }

        if (!y.isLeaf) {
            for (int j = 0; j < t; j++) {
                z.children.add(y.children.remove(t)); // 移除并添加
            }
        }

        keys.add(i, y.keys.remove(y.keys.size() - 1)); // 提升中间键
        children.add(i + 1, z); // 插入新子节点
    }

    public void traverse() {
        for (int i = 0; i < keys.size(); i++) {
            if (!isLeaf) {
                children.get(i).traverse();
            }
            System.out.print(keys.get(i) + " ");
        }
        if (!isLeaf) {
            children.get(keys.size()).traverse();
        }
    }

    public BPlusTreeNode search(int key) {
        int i = 0;
        while (i < keys.size() && key > keys.get(i)) {
            i++;
        }

        if (i < keys.size() && keys.get(i) == key) {
            return this; // 找到键
        }

        if (isLeaf) {
            return null; // 到达叶子节点仍未找到
        }

        return children.get(i).search(key); // 递归搜索
    }

    public boolean delete(int key) {
        int idx = findKey(key);

        if (idx < keys.size() && keys.get(idx) == key) {
            if (isLeaf) {
                keys.remove(idx); // 在叶子节点中删除
            } else {
                return false; // B+ 树的内部节点不能直接删除
            }
        } else {
            if (isLeaf) {
                return false; // 在叶子节点未找到
            }

            boolean shouldMerge = (idx == keys.size());
            if (children.get(idx).keys.size() < t) {
                fill(idx);
            }

            if (shouldMerge && idx > keys.size()) {
                children.get(idx - 1).delete(key);
            } else {
                children.get(idx).delete(key);
            }
        }
        return true;
    }

    private int findKey(int key) {
        int idx = 0;
        while (idx < keys.size() && keys.get(idx) < key) {
            idx++;
        }
        return idx;
    }

    private void fill(int idx) {
        if (idx != 0 && children.get(idx - 1).keys.size() >= t) {
            borrowFromPrev(idx);
        } else if (idx != keys.size() && children.get(idx + 1).keys.size() >= t) {
            borrowFromNext(idx);
        } else {
            if (idx != keys.size()) {
                merge(idx); // 合并节点
            } else {
                merge(idx - 1); // 合并节点
            }
        }
    }

    private void borrowFromPrev(int idx) {
        BPlusTreeNode child = children.get(idx);
        BPlusTreeNode sibling = children.get(idx - 1);

        child.keys.add(0, keys.get(idx - 1)); // 移动中间键到当前节点
        keys.set(idx - 1, sibling.keys.remove(sibling.keys.size() - 1)); // 移动兄弟的最后一个键

        if (!child.isLeaf) {
            child.children.add(0, sibling.children.remove(sibling.children.size() - 1)); // 移动兄弟的最后一个子节点
        }
    }

    private void borrowFromNext(int idx) {
        BPlusTreeNode child = children.get(idx);
        BPlusTreeNode sibling = children.get(idx + 1);

        child.keys.add(keys.get(idx)); // 移动中间键到当前节点
        keys.set(idx, sibling.keys.remove(0)); // 移动兄弟的第一个键

        if (!child.isLeaf) {
            child.children.add(sibling.children.remove(0)); // 移动兄弟的第一个子节点
        }
    }

    private void merge(int idx) {
        BPlusTreeNode leftChild = children.get(idx);
        BPlusTreeNode rightChild = children.get(idx + 1);

        leftChild.keys.add(keys.get(idx)); // 提升中间键
        leftChild.keys.addAll(rightChild.keys); // 合并右节点的键

        if (!leftChild.isLeaf) {
            leftChild.children.addAll(rightChild.children); // 合并右节点的子节点
        }

        // 移除右节点和中间键
        keys.remove(idx);
        children.remove(idx + 1);
    }
}

public class BPlusTree {
    private BPlusTreeNode root;
    private int t; // 最小度数

    public BPlusTree(int t) {
        this.root = null;
        this.t = t;
    }

    public void traverse() {
        if (root != null) {
            root.traverse();
        }
    }

    public BPlusTreeNode search(int key) {
        return root == null ? null : root.search(key);
    }

    public void insert(int key) {
        if (root == null) {
            root = new BPlusTreeNode(t, true);
            root.keys.add(key); // 插入到根节点
        } else {
            if (root.keys.size() == 2 * t - 1) {
                BPlusTreeNode s = new BPlusTreeNode(t, false);
                s.children.add(root); // 创建新的根节点
                s.splitChild(0);
                int i = 0;
                if (s.keys.get(0) < key) {
                    i++;
                }
                s.children.get(i).insertNonFull(key);
                root = s; // 更新根节点
            } else {
                root.insertNonFull(key);
            }
        }
    }

    public boolean delete(int key) {
        if (root == null) {
            return false;
        }
        boolean deleted = root.delete(key);
        if (root.keys.isEmpty()) {
            root = root.isLeaf ? null : root.children.get(0); // 更新根节点
        }
        return deleted;
    }

    public static void main(String[] args) {
        BPlusTree bPlusTree = new BPlusTree(3); // 创建最小度数为 3 的 B+ 树

        // 插入节点
        bPlusTree.insert(10);
        bPlusTree.insert(20);
        bPlusTree.insert(5);
        bPlusTree.insert(6);
        bPlusTree.insert(12);
        bPlusTree.insert(30);
        bPlusTree.insert(7);
        bPlusTree.insert(17);

        System.out.println("B+ 树的遍历结果:");
        bPlusTree.traverse(); // 输出 B+ 树的遍历结果
        System.out.println();

        // 查找节点
        int keyToFind = 6;
        BPlusTreeNode foundNode = bPlusTree.search(keyToFind);
        System.out.println("查找 " + keyToFind + ": " + (foundNode != null)); // 输出: true

        // 删除节点
        System.out.println("删除节点 12:");
        bPlusTree.delete(12);
        bPlusTree.traverse();
        System.out.println();
    }
}



