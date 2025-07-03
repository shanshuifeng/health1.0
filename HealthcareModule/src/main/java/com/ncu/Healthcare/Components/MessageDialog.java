package com.ncu.Healthcare.Components;
import javax.swing.*;
import java.awt.*;

public class MessageDialog extends JDialog {
    // 将JTextArea声明为成员变量
    private final JTextArea messageArea;

    public MessageDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        setSize(700, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // 标题
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // 消息内容 - 使用成员变量
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        messageArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton closeButton = CrudPanel.createStyledButton("关闭", new Color(70, 130, 180));
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    // 设置消息内容的公共方法
    public void setMessage(String message) {
        messageArea.setText(message);
    }

    // 改进后的showMessage方法
    public static void showMessage(Component parent, String title, String message) {
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(parent);
        MessageDialog dialog = new MessageDialog(owner, title, true);
        dialog.setMessage(message); // 使用公共方法设置消息
        dialog.setVisible(true);
    }
}

