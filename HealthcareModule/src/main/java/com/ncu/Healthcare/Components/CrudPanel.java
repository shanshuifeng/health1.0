package com.ncu.Healthcare.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class CrudPanel<T> extends JPanel {
    private JPanel contentPanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    // 添加查询面板
    private JPanel searchPanel;
    protected JButton searchButton;

    // 在构造函数中调用创建查询面板的方法
    public CrudPanel() {
        setLayout(new BorderLayout());
        createToolbar();
        createSearchPanel();
        createContent();
    }

    // 创建查询面板
    private void createSearchPanel() {
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(245, 245, 245));
        searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        searchButton = createStyledButton("查询", new Color(153, 204, 153));
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);
    }

    // 获取查询面板以便子类添加查询字段
    public JPanel getSearchPanel() {
        return searchPanel;
    }

    // 获取查询按钮
    public JButton getSearchButton() {
        return searchButton;
    }

    private void createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        toolbar.setBackground(new Color(245, 245, 245));
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        addButton = createStyledButton("添加", new Color(102, 153, 204));
        editButton = createStyledButton("编辑", new Color(153, 204, 255));
        deleteButton = createStyledButton("删除", new Color(204, 153, 153));
        searchButton = createStyledButton("查询", new Color(153, 204, 153));

        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.add(searchButton);

        add(toolbar, BorderLayout.NORTH);
    }

    public static JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(bgColor.darker(), 1),
                new EmptyBorder(8, 20, 8, 20)
        ));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void createContent() {
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

    public void setContent(JComponent component) {
        contentPanel.removeAll();
        contentPanel.add(component, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public abstract void refreshData();
}

