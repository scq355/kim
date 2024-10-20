package com.kim.datastructure.tree;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

class BTreeNode {
    int t; // 最小度数
    List<Integer> keys; // 存储键的列表
    List<BTreeNode> children; // 子节点列表
    boolean isLeaf; // 是否为叶子节点

    public BTreeNode(int t, boolean isLeaf) {
        this.t = t;
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public void insertNonFull(int key) {
        int i = keys.size() - 1;

        if (isLeaf) {
            // 找到插入位置
            while (i >= 0 && keys.get(i) > key) {
                i--;
            }
            keys.add(i + 1, key); // 插入键
        } else {
            // 找到子节点
            while (i >= 0 && keys.get(i) > key) {
                i--;
            }
            i++; // 找到子节点位置

            // 检查子节点是否满
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
        BTreeNode z = new BTreeNode(t, children.get(i).isLeaf);
        BTreeNode y = children.get(i);

        // 复制 y 的后半部分键到 z
        for (int j = 0; j < t - 1; j++) {
            z.keys.add(y.keys.remove(t)); // 移除并添加
        }
        if (!y.isLeaf) {
            for (int j = 0; j < t; j++) {
                z.children.add(y.children.remove(t)); // 移除并添加
            }
        }

        // 更新 y 的键和子节点
        children.add(i + 1, z); // 插入 z
        keys.add(i, y.keys.remove(y.keys.size() - 1)); // 提升中间键
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

    public BTreeNode search(int key) {
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

    public void delete(int key) {
        int idx = findKey(key);

        // 如果找到该键
        if (idx < keys.size() && keys.get(idx) == key) {
            if (isLeaf) {
                keys.remove(idx); // 在叶子节点中删除
            } else {
                // 如果是内部节点
                if (children.get(idx).keys.size() >= t) {
                    int pred = getPredecessor(idx);
                    keys.set(idx, pred); // 用前驱替代
                    children.get(idx).delete(pred); // 删除前驱
                } else if (children.get(idx + 1).keys.size() >= t) {
                    int succ = getSuccessor(idx);
                    keys.set(idx, succ); // 用后继替代
                    children.get(idx + 1).delete(succ); // 删除后继
                } else {
                    merge(idx); // 合并节点
                    children.get(idx).delete(key); // 在合并后的节点中删除
                }
            }
        } else {
            // 到达叶子节点后未找到
            if (isLeaf) {
                System.out.println("键 " + key + " 不存在");
                return;
            }
            boolean shouldMerge = (idx == keys.size()); // 是否需要合并
            if (children.get(idx).keys.size() < t) {
                fill(idx); // 填充节点
            }
            if (shouldMerge && idx > keys.size()) {
                children.get(idx - 1).delete(key); // 在左兄弟节点中删除
            } else {
                children.get(idx).delete(key); // 在当前节点中删除
            }
        }
    }

    private int findKey(int key) {
        int idx = 0;
        while (idx < keys.size() && keys.get(idx) < key) {
            idx++;
        }
        return idx;
    }

    private int getPredecessor(int idx) {
        BTreeNode current = children.get(idx);
        while (!current.isLeaf) {
            current = current.children.get(current.keys.size());
        }
        return current.keys.get(current.keys.size() - 1);
    }

    private int getSuccessor(int idx) {
        BTreeNode current = children.get(idx + 1);
        while (!current.isLeaf) {
            current = current.children.get(0);
        }
        return current.keys.get(0);
    }

    private void merge(int idx) {
        BTreeNode leftChild = children.get(idx);
        BTreeNode rightChild = children.get(idx + 1);

        leftChild.keys.add(keys.get(idx)); // 提升中间键
        leftChild.keys.addAll(rightChild.keys); // 合并右节点的键

        if (!leftChild.isLeaf) {
            leftChild.children.addAll(rightChild.children); // 合并右节点的子节点
        }

        // 移除右节点和中间键
        keys.remove(idx);
        children.remove(idx + 1);
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
        BTreeNode child = children.get(idx);
        BTreeNode sibling = children.get(idx - 1);

        child.keys.add(0, keys.get(idx - 1)); // 移动中间键到当前节点
        keys.set(idx - 1, sibling.keys.remove(sibling.keys.size() - 1)); // 移动兄弟的最后一个键

        if (!child.isLeaf) {
            child.children.add(0, sibling.children.remove(sibling.children.size() - 1)); // 移动兄弟的最后一个子节点
        }
    }

    private void borrowFromNext(int idx) {
        BTreeNode child = children.get(idx);
        BTreeNode sibling = children.get(idx + 1);

        child.keys.add(keys.get(idx)); // 移动中间键到当前节点
        keys.set(idx, sibling.keys.remove(0)); // 移动兄弟的第一个键

        if (!child.isLeaf) {
            child.children.add(sibling.children.remove(0)); // 移动兄弟的第一个子节点
        }
    }

    public int depth() {
        return depthHelper(this);
    }

    private int depthHelper(BTreeNode node) {
        if (node.isLeaf) {
            return 1; // 叶子节点深度为 1
        }
        int maxDepth = 0;
        for (BTreeNode child : node.children) {
            maxDepth = Math.max(maxDepth, depthHelper(child));
        }
        return maxDepth + 1; // 加上当前层级
    }
}

public class BTree {
    private BTreeNode root;
    private int t; // 最小度数

    public BTree(int t) {
        this.root = null;
        this.t = t;
    }

    public void traverse() {
        if (root != null) {
            root.traverse();
        }
    }

    public BTreeNode search(int key) {
        return root == null ? null : root.search(key);
    }

    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys.add(key); // 插入到根节点
        } else {
            if (root.keys.size() == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false);
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

    public void delete(int key) {
        if (root == null) {
            System.out.println("树为空");
            return;
        }
        root.delete(key);
        if (root.keys.isEmpty()) {
            root = root.isLeaf ? null : root.children.get(0); // 更新根节点
        }
    }

    public int depth() {
        return root == null ? 0 : root.depth();
    }

    public static void main(String[] args) {
        BTree bTree = new BTree(3); // 创建最小度数为 3 的 B 树

        // 插入节点
        bTree.insert(10);
        bTree.insert(20);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(12);
        bTree.insert(30);
        bTree.insert(7);
        bTree.insert(17);

        System.out.println("B 树的遍历结果:");
        bTree.traverse(); // 输出 B 树的遍历结果
        System.out.println();

        // 查找节点
        int keyToFind = 6;
        BTreeNode foundNode = bTree.search(keyToFind);
        System.out.println("查找 " + keyToFind + ": " + (foundNode != null)); // 输出: true

        // 删除节点
        System.out.println("删除节点 12:");
        bTree.delete(12);
        bTree.traverse();
        System.out.println();

        // 计算树的深度
        System.out.println("B 树的深度: " + bTree.depth());
    }
}







