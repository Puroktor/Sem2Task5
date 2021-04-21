package ru.vsu.cs.skofenko;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeAlgorithms {

    @FunctionalInterface
    public interface Visitor<T> {
        void visit(T value, int level);
    }

    public static <C extends Comparable<C>> boolean tryToMakeBST(BinaryTree.TreeNode<C> root) {
        for (BinaryTree.TreeNode<C> node : BinaryTreeAlgorithms.inOrderValues(root)) {
            for (BinaryTree.TreeNode<C> node2 : BinaryTreeAlgorithms.inOrderValues(node)) {
                swap(node, node2);
                if (isBST(root)) {
                    return true;
                }
                swap(node, node2);
            }
        }
        return false;
    }//O(n^3)

    private static <C extends Comparable<C>> void swap(BinaryTree.TreeNode<C> f, BinaryTree.TreeNode<C> s) {
        C temp = f.getValue();
        f.setValue(s.getValue());
        s.setValue(temp);
    }

    private static <C extends Comparable<C>> boolean isBST(BinaryTree.TreeNode<C> root) {
        C prev = null;
        for (BinaryTree.TreeNode<C> node : BinaryTreeAlgorithms.inOrderValues(root)) {
            if (prev != null && node.getValue().compareTo(prev) < 0)
                return false;
            prev = node.getValue();
        }
        return true;
    }

    public static <T> Iterable<BinaryTree.TreeNode<T>> inOrderValues(BinaryTree.TreeNode<T> treeNode) {
        return () -> {
            Stack<BinaryTree.TreeNode<T>> stack = new Stack<>();
            BinaryTree.TreeNode<T> node = treeNode;
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }

            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return !stack.isEmpty();
                }

                @Override
                public BinaryTree.TreeNode<T> next() {
                    BinaryTree.TreeNode<T> node = stack.pop();
                    BinaryTree.TreeNode<T> copyOfNode = node;
                    if (node.getRight() != null) {
                        node = node.getRight();
                        while (node != null) {
                            stack.push(node);
                            node = node.getLeft();
                        }
                    }
                    return copyOfNode;
                }
            };
        };
    }

    public static <T> String toBracketStr(BinaryTree.TreeNode<T> treeNode) {
        class Inner {
            void printTo(BinaryTree.TreeNode<T> node, StringBuilder sb) {
                if (node == null) {
                    return;
                }
                sb.append(node.getValue());
                if (node.getLeft() != null || node.getRight() != null) {
                    sb.append(" (");
                    printTo(node.getLeft(), sb);
                    if (node.getRight() != null) {
                        sb.append(", ");
                        printTo(node.getRight(), sb);
                    }
                    sb.append(")");
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        new Inner().printTo(treeNode, sb);

        return sb.toString();
    }
}