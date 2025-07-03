package com.hs;

import com.hs.dao.UserDAO;
import com.hs.utils.User;
import com.hs.views.LoginView;

import javax.swing.*;

public class AppMain {

    public static void main(String[] args) {
        // 确保使用Swing的UI线程
        SwingUtilities.invokeLater(() -> {
            try {
                // 设置系统UI风格
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // 确保admin用户存在
                UserDAO userDAO = new UserDAO();
                if (userDAO.getUserByUsername("admin") == null) {
                    User admin = new User(0, "admin", "123456", "admin", false,
                            "管理员", "13800000000", null, "男", null);
                    userDAO.addUser(admin);
                }

                // 创建并显示登录窗口
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "系统初始化失败: " + e.getMessage(),
                        "错误",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}