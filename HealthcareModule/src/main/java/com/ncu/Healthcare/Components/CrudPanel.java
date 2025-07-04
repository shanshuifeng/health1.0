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
    private JButton searchButton;
    private JPanel searchPanel;

    public CrudPanel() {
        setLayout(new BorderLayout());
        createToolbar();
        createContent();
    }

    private void createToolbar() {
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(new Color(245, 245, 245));
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        // 创建按钮面板(左侧)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBackground(new Color(245, 245, 245));

        // 创建所有按钮并添加到按钮面板
        addButton = createStyledButton("添加", new Color(102, 153, 204));
        editButton = createStyledButton("编辑", new Color(153, 204, 255));
        deleteButton = createStyledButton("删除", new Color(204, 153, 153));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // 创建搜索面板(右侧)
        searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        searchPanel.setBackground(new Color(245, 245, 245));

        // 创建搜索按钮并添加到搜索面板
        searchButton = createStyledButton("查询", new Color(153, 204, 153));
        searchPanel.add(searchButton);

        // 将按钮面板和搜索面板添加到工具栏
        toolbar.add(buttonPanel, BorderLayout.WEST);
        toolbar.add(searchPanel, BorderLayout.CENTER);

        add(toolbar, BorderLayout.NORTH);
    }

    public static JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(bgColor.darker(), 1),
                new EmptyBorder(5, 15, 5, 15)
        ));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(MouseEvent evt) {
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

    // Getter方法
    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JPanel getSearchPanel() {
        return searchPanel;
    }

    public abstract void refreshData();
}

