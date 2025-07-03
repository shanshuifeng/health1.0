package com.hs.views;


import com.hs.utils.LoginController;
import com.hs.utils.User;
import com.toedter.calendar.JDateChooser;
import com.hs.dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private LoginController loginController;

    public LoginView() {
        loginController = new LoginController();
        initUI();
    }

    private void initUI() {
        setTitle("健康检查系统 - 登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // 使用渐变背景的主面板
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(135, 206, 250); // 浅蓝色
                Color color2 = new Color(70, 130, 180);   // 钢蓝色
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 标题面板
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("健康检查系统", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // 登录表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 用户名标签和输入框
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        userLabel.setForeground(Color.WHITE);
        formPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        formPanel.add(usernameField, gbc);

        // 密码标签和输入框
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passLabel = new JLabel("密码:");
        passLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        passLabel.setForeground(Color.WHITE);
        formPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        formPanel.add(passwordField, gbc);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        loginButton = new JButton("登录");
        styleButton(loginButton, new Color(46, 139, 87)); // 海洋绿
        loginButton.addActionListener(e -> handleLogin());

        registerButton = new JButton("注册");
        styleButton(registerButton, new Color(70, 130, 180)); // 钢蓝色
        registerButton.addActionListener(e -> showRegistrationDialog());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 底部信息
        JLabel footerLabel = new JLabel("© 2025 健康检查系统 - 版本 1.0", JLabel.CENTER);
        footerLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        footerLabel.setForeground(Color.WHITE);
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("微软雅黑", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        loginController.setLoginListener(new LoginController.LoginListener() {
            @Override
            public void onLoginSuccess(User user) {
                JOptionPane.showMessageDialog(LoginView.this, "登录成功!");
                dispose(); // 关闭登录窗口

                // 创建并显示主界面
                SwingUtilities.invokeLater(() -> {
                    MainView mainView = new MainView(user);
                    mainView.setVisible(true);
                });
            }

            @Override
            public void onFirstLogin(User user) {
                handleFirstLogin(user);
            }

            @Override
            public void onLoginFailed(String errorMessage) {
                JOptionPane.showMessageDialog(LoginView.this, errorMessage, "登录失败", JOptionPane.ERROR_MESSAGE);
            }

            @Override
            public void onPasswordChangeSuccess(User user) {
                JOptionPane.showMessageDialog(LoginView.this, "密码修改成功!", "成功", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // 关闭登录窗口

                // 创建并显示主界面
                SwingUtilities.invokeLater(() -> {
                    MainView mainView = new MainView(user);
                    mainView.setVisible(true);
                });
            }

            @Override
            public void onPasswordChangeFailed(String errorMessage) {
                JOptionPane.showMessageDialog(LoginView.this, errorMessage, "密码修改失败", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginController.handleLogin(username, password);
    }
    private void handleFirstLogin(User user) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        panel.add(new JLabel("新密码:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("确认密码:"));
        panel.add(confirmPasswordField);

        int result = JOptionPane.showConfirmDialog(this, panel, "首次登录请修改密码",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            loginController.handleChangePassword(user, newPassword, confirmPassword);
        }
    }

    private void showRegistrationDialog() {
        JDialog dialog = new JDialog(this, "用户注册", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 用户名
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("用户名:"), gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // 密码
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("密码:"), gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // 确认密码
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("确认密码:"), gbc);

        gbc.gridx = 1;
        JPasswordField confirmPasswordField = new JPasswordField(15);
        panel.add(confirmPasswordField, gbc);

        // 姓名
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("姓名:"), gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        panel.add(nameField, gbc);

        // 电话
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("电话:"), gbc);

        gbc.gridx = 1;
        JTextField phoneField = new JTextField(15);
        panel.add(phoneField, gbc);

        // 出生日期
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("出生日期:"), gbc);

        gbc.gridx = 1;
        JDateChooser birthDateChooser = new JDateChooser();
        birthDateChooser.setDateFormatString("yyyy-MM-dd");
        panel.add(birthDateChooser, gbc);

        // 性别
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("性别:"), gbc);

        gbc.gridx = 1;
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"男", "女"});
        panel.add(genderCombo, gbc);
        // 用户类型
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("用户类型:"), gbc);

        gbc.gridx = 1;
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"患者"});
        panel.add(roleCombo, gbc);

        gbc.gridx = 1;
        JTextField idCardField = new JTextField(15);
        idCardField.setEnabled(false);
        panel.add(idCardField, gbc);

        // 注册按钮
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("注册");
        panel.add(registerButton, gbc);

        registerButton.addActionListener(e -> {
            // 验证输入
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            Date birthDate = birthDateChooser.getDate();
            String gender = (String) genderCombo.getSelectedItem();
            String role = "医护人员".equals(roleCombo.getSelectedItem()) ? "doctor" : "patient";
            String idCard = idCardField.getText().trim();

            // 验证逻辑
            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty() || birthDate == null) {
                JOptionPane.showMessageDialog(dialog, "请填写所有必填字段", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(dialog, "两次输入的密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 6) {
                JOptionPane.showMessageDialog(dialog, "密码长度不能少于6位", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!phone.matches("\\d{11}")) {
                JOptionPane.showMessageDialog(dialog, "请输入有效的11位手机号码", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if ("doctor".equals(role) && (idCard.length() != 18 || !idCard.matches("\\d{17}[0-9X]"))) {
                JOptionPane.showMessageDialog(dialog, "请输入有效的18位身份证号码", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserDAO userDAO = new UserDAO();
            if (userDAO.isUsernameExists(username)) {
                JOptionPane.showMessageDialog(dialog, "用户名已存在", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 创建用户对象
            User newUser = new User(
                    0, username, password, role, true,
                    name, phone, birthDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                    gender, "doctor".equals(role) ? idCard : null
            );

            // 保存用户
            if (userDAO.addUser(newUser)) {
                JOptionPane.showMessageDialog(dialog, "注册成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "注册失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public void showLogin() {
        setVisible(true);
    }
}