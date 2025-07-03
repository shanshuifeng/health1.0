package com.hs.views;



import com.hs.utils.User;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    public MainView(User user) {
        String roleName = "admin".equals(user.getRole()) ? "管理员" :
                ("doctor".equals(user.getRole()) ? "医护人员" : "患者");

        setTitle("健康检查系统 - " + roleName + "界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 顶部面板
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(70, 130, 180));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // 欢迎信息
        JLabel welcomeLabel = new JLabel("欢迎, " + user.getName() + " (" + roleName + ")");
        welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        // 用户信息
        JLabel userInfoLabel = new JLabel("用户名: " + user.getUsername() + " | 电话: " + user.getPhone());
        userInfoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        userInfoLabel.setForeground(Color.WHITE);
        topPanel.add(userInfoLabel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 内容面板
        if ("admin".equals(user.getRole())) {
            // 管理员界面
            AdminView adminView = new AdminView();
            JPanel adminPanel = adminView.getAdminPanel();

            // 美化管理员面板
            adminPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));

            // 设置表格样式
// 安全地获取并设置表格样式
            Component[] components = adminPanel.getComponents();
            boolean tableFound = false;

// 遍历所有组件查找JTable
            for (Component comp : components) {
                if (comp instanceof JScrollPane) {
                    JScrollPane scrollPane = (JScrollPane) comp;
                    Component view = scrollPane.getViewport().getView();
                    if (view instanceof JTable) {
                        JTable table = (JTable) view;
                        // 设置表格样式
                        table.setRowHeight(30);
                        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
                        tableFound = true;
                        break; // 找到后退出循环
                    }
                }
            }

// 如果没有找到表格，输出警告
            if (!tableFound) {
                System.err.println("警告: 未能在adminPanel中找到表格组件");

                // 调试输出 - 显示adminPanel包含的所有组件
                System.out.println("adminPanel包含的组件:");
                for (int i = 0; i < components.length; i++) {
                    System.out.println(i + ": " + components[i].getClass().getName());
                }
            }

            mainPanel.add(adminPanel, BorderLayout.CENTER);
        } else {
            // 普通用户界面
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));

            // 用户仪表板
            JPanel dashboardPanel = new JPanel(new GridLayout(2, 2, 20, 20));

            // 创建仪表板卡片
            addDashboardCard(dashboardPanel, "健康报告", "查看您的健康报告", new Color(46, 139, 87));
            addDashboardCard(dashboardPanel, "预约检查", "预约新的健康检查", new Color(70, 130, 180));
            addDashboardCard(dashboardPanel, "历史记录", "查看历史检查记录", new Color(147, 112, 219));
            addDashboardCard(dashboardPanel, "个人设置", "修改个人信息", new Color(218, 165, 32));

            contentPanel.add(dashboardPanel, BorderLayout.CENTER);

            // 底部信息
            JLabel footerLabel = new JLabel("© 2023 健康检查系统 - " + roleName + "界面", JLabel.CENTER);
            footerLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            contentPanel.add(footerLabel, BorderLayout.SOUTH);

            mainPanel.add(contentPanel, BorderLayout.CENTER);
        }

        add(mainPanel);
    }

    private void addDashboardCard(JPanel panel, String title, String description, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker()),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel descLabel = new JLabel(description, JLabel.CENTER);
        descLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        descLabel.setForeground(Color.WHITE);

        card.add(titleLabel, BorderLayout.CENTER);
        card.add(descLabel, BorderLayout.SOUTH);

        // 添加悬停效果
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(card);
    }

}