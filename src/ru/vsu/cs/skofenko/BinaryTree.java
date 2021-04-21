package ru.vsu.cs.skofenko;

import java.awt.Color;
import java.util.Iterator;

public interface BinaryTree<T> extends Iterable<T> {

    interface TreeNode<T> extends Iterable<T> {

        T getValue();

        void setValue(T value);

        default TreeNode<T> getLeft() {
            return null;
        }

        default TreeNode<T> getRight() {
            return null;
        }

        default Color getColor() {
            return Color.BLACK;
        }

        @Override
        default Iterator<T> iterator() {
            return null;
        }

        default String toBracketStr() {
            return BinaryTreeAlgorithms.toBracketStr(this);
        }
    }

    TreeNode<T> getRoot();

    @Override
    default Iterator<T> iterator() {
        return this.getRoot().iterator();
    }

    default String toBracketStr() {
        return this.getRoot().toBracketStr();
    }
}
