package com.hs;

import com.hs.dao.UserDAO;
import com.ncu.Common.Users;
import com.hs.views.LoginView;

import javax.swing.*;

public class AppMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                UserDAO userDAO = new UserDAO();
                if (userDAO.getUserByUsername("admin") == null) {
                    Users admin = new Users();
                    admin.setPhone("admin");
                    admin.setPassword("123456");
                    admin.setName("管理员");
                    admin.setRole("admin");
                    userDAO.addUser(admin);
                }

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
