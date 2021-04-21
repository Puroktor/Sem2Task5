package ru.vsu.cs.skofenko;

import ru.vsu.cs.skofenko.util.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {

    private JPanel mainPanel;
    private JButton createButton;
    private JButton writeButton;
    private JButton taskButton;
    private JTextField textField;
    private JPanel paintPanel;
    private JLabel label;

    private final JPanel treePanel;

    private BinaryTree<Integer> tree = new SimpleBinaryTree<>();

    public MainForm() {
        this.setTitle("5 Таск");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        textField.setText("1 (2 (3, 4), 12)");

        treePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics gr) {
                super.paintComponent(gr);
                Dimension paintSize = BinaryTreePainter.paint(tree, gr);
                if (!paintSize.equals(this.getPreferredSize())) {
                    SwingUtils.setFixedSize(this, paintSize.width, paintSize.height);
                }
            }
        };

        JScrollPane scrollPane = new JScrollPane(treePanel);
        paintPanel.add(scrollPane);

        createButton.addActionListener(e -> {
            try {
                SimpleBinaryTree<Integer> newTree = new SimpleBinaryTree<>(Integer::parseInt);
                newTree.fromBracketNotation(textField.getText());
                tree = newTree;
                treePanel.repaint();
            } catch (Exception exception) {
                SwingUtils.showErrorMessageBox(exception);
            }
        });
        writeButton.addActionListener(e -> {
            try {
                textField.setText(tree.toBracketStr());
            } catch (Exception exception) {
                SwingUtils.showErrorMessageBox(exception);
            }
        });
        taskButton.addActionListener(e -> {
            try {
                if (BinaryTreeAlgorithms.tryToMakeBST(tree.getRoot())) {
                    label.setText("Теперь это BST");
                    treePanel.repaint();
                } else {
                    label.setText("Невозможно");
                }
            } catch (Exception exception) {
                SwingUtils.showErrorMessageBox(exception);
            }
        });
    }
}
