package com.ncu.Healthcare.Views;

import com.ncu.Common.CheckItem;
import com.ncu.Common.CheckItemGroup;
import com.ncu.Common.Users;
import com.ncu.Healthcare.Components.*;
import com.ncu.Healthcare.dao.CheckItemDAO;
import com.ncu.Healthcare.dao.CheckItemGroupDAO;
import com.ncu.Healthcare.dao.UserDAO;
import com.ncu.Common.Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MedicalStaffPanel extends JPanel {
    private JPanel contentPanel;
    private CheckItemPanel checkItemPanel;
    private CheckGroupPanel checkGroupPanel;
    private UserPanel userPanel;
    private AppointmentPanel appointmentPanel;

    public MedicalStaffPanel() {
        setLayout(new BorderLayout());

        // 创建侧边栏
        createSidebar();

        // 创建内容区域
        createContentArea();

        // 显示首页
        showHome();
    }

    private void createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(51, 102, 153));
        sidebar.setPreferredSize(new Dimension(220, Integer.MAX_VALUE));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] navItems = {"首页", "检查项", "检查组", "用户管理", "预约管理", "关于", "退出系统"};

        for (int i = 0; i < navItems.length; i++) {
            JButton button = new JButton(navItems[i]);
            // button.setIcon(icons[i]); // 添加图标
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
            button.setBackground(new Color(51, 102, 153));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // 悬停效果
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    button.setBackground(new Color(70, 130, 180));
                }
                public void mouseExited(MouseEvent evt) {
                    button.setBackground(new Color(51, 102, 153));
                }
            });

            button.addActionListener(getNavActionListener(navItems[i]));
            sidebar.add(button);
            sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        add(sidebar, BorderLayout.WEST);
    }

    private ActionListener getNavActionListener(String itemName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (itemName) {
                    case "首页":
                        showHome();
                        break;
                    case "检查项":
                        showCheckItems();
                        break;
                    case "检查组":
                        showCheckGroups();
                        break;
                    case "用户管理":
                        showUserManagement();
                        break;
                    case "预约管理":
                        showAppointmentManagement();
                        break;
                    case "关于":
                        showMessage("关于", "这是一个医疗健康管理系统。");
                        break;
                    case "退出系统":
                        System.exit(0);
                        break;
                }
            }
        };
    }

    private void createContentArea() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        contentPanel.setBackground(new Color(245, 245, 245));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentPanel, BorderLayout.CENTER);
    }

    private void showHome() {
        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("欢迎使用医疗健康管理系统", SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        homePanel.add(label, BorderLayout.CENTER);

        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, "home");
        contentPanel.add(homePanel, "home");
    }

    private void showMessage(String title, String message) {
        MessageDialog.showMessage(this, title, message);
    }

    private void showCheckItems() {
        if (checkItemPanel == null) {
            checkItemPanel = new CheckItemPanel();
            contentPanel.add(checkItemPanel, "checkItems");
        }

        ((CardLayout) contentPanel.getLayout()).show(contentPanel, "checkItems");
    }

    private void showCheckGroups() {
        if (checkGroupPanel == null) {
            checkGroupPanel = new CheckGroupPanel();
            contentPanel.add(checkGroupPanel, "checkGroups");
        }

        ((CardLayout) contentPanel.getLayout()).show(contentPanel, "checkGroups");
    }

    private void showUserManagement() {
        if (userPanel == null) {
            userPanel = new UserPanel();
            contentPanel.add(userPanel, "users");
        }

        ((CardLayout) contentPanel.getLayout()).show(contentPanel, "users");
    }
    private void showAppointmentManagement() {
        if (appointmentPanel == null) {
            appointmentPanel = new AppointmentPanel();
            contentPanel.add(appointmentPanel, "appointments");
        }

        ((CardLayout) contentPanel.getLayout()).show(contentPanel, "appointments");
    }
}

