package com.hs.views;

import com.hs.utils.LoginController;
import com.ncu.Common.Users;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.prefs.Preferences;
import com.healthsys.common.model.User;

public class LoginView extends JFrame {
    private JPanel contentPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JCheckBox rememberMeCheck;
    private JCheckBox showPasswordCheck;
    private LoginController loginController;
    private static final String PREFS_KEY = "health_system_prefs";

    public LoginView() {
        setTitle("健康检查系统 - 登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(Color.WHITE);

        // 标题
        JLabel titleLabel = new JLabel("健康检查系统", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 表单面板
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 15));
        formPanel.setOpaque(false);

        // 用户名
        JPanel usernamePanel = createFieldPanel("用户名:", usernameField = new JTextField(20));
        formPanel.add(usernamePanel);

        // 密码
        JPanel passwordPanel = createFieldPanel("密码:", passwordField = new JPasswordField(20));
        formPanel.add(passwordPanel);

        // 选项面板
        JPanel optionPanel = new JPanel(new GridLayout(1, 2));
        optionPanel.setOpaque(false);

        rememberMeCheck = new JCheckBox("记住用户名");
        rememberMeCheck.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        showPasswordCheck = new JCheckBox("显示密码");
        showPasswordCheck.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        showPasswordCheck.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordCheck.isSelected() ? '\0' : '•');
        });

        optionPanel.add(rememberMeCheck);
        optionPanel.add(showPasswordCheck);
        formPanel.add(optionPanel);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        loginButton = new JButton("登录");
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(e -> handleLogin());

        registerButton = new JButton("注册");
        registerButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        registerButton.setBackground(new Color(100, 150, 100));
        registerButton.setForeground(Color.BLACK);
        registerButton.addActionListener(e -> showRegistrationDialog());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        formPanel.add(buttonPanel);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        initController();
        loadPreferences();
    }

    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        panel.add(label);
        field.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        panel.add(field);
        return panel;
    }

    private void initController() {
        loginController = new LoginController();
        loginController.setLoginListener(new LoginController.LoginListener() {
            @Override
            public void onLoginSuccess(Users user) {
                savePreferences();
                dispose();

                if ("MEDICAL".equals(user.getRole()) || "admin".equals(user.getRole())) {
                    // 打开HealthcareModule中的医护界面
                    JFrame healthcareFrame = new JFrame("医疗健康管理系统");
                    healthcareFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    healthcareFrame.setSize(1200, 800);
                    healthcareFrame.setLocationRelativeTo(null);

                    try {
                        com.ncu.Healthcare.Views.MedicalStaffPanel medicalStaffPanel =
                                new com.ncu.Healthcare.Views.MedicalStaffPanel();
                        healthcareFrame.add(medicalStaffPanel);
                        healthcareFrame.setVisible(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(
                                null,
                                "无法加载医护界面: " + e.getMessage(),
                                "系统错误",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else {
                    // 普通用户直接跳转到UserModule的主界面
                    try {
                        Class<?> mainViewClass = Class.forName("com.healthsys.common.view.MainView");
                        Constructor<?> constructor = mainViewClass.getConstructor(com.healthsys.common.model.User.class);

                        // 将Common模块的Users对象转换为UserModule的User对象
                        com.healthsys.common.model.User moduleUser = new com.healthsys.common.model.User();
                        moduleUser.setId(Math.toIntExact(user.getId()));
                        moduleUser.setPhone(user.getPhone());
                        moduleUser.setPassword(user.getPassword());
                        moduleUser.setName(user.getName());
                        moduleUser.setBirthDate(java.sql.Date.valueOf(user.getBirthDate()));
                        moduleUser.setGender(user.getGender());
                        moduleUser.setRole(user.getRole());
                        moduleUser.setIdNumber(user.getIdNumber());

                        Object mainView = constructor.newInstance(moduleUser);
                        dispose();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(
                                null,
                                "无法加载用户界面: " + e.getMessage(),
                                "系统错误",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }

            @Override
            public void onFirstLogin(Users user) {

            }

            @Override
            public void onLoginFailed(String errorMessage) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(
                            LoginView.this,
                            errorMessage,
                            "登录失败",
                            JOptionPane.ERROR_MESSAGE
                    );
                    passwordField.setText("");
                    passwordField.requestFocus();
                });
            }

            @Override
            public void onPasswordChangeSuccess(Users user) {

            }

            @Override
            public void onPasswordChangeFailed(String errorMessage) {

            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入用户名", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        loginController.handleLogin(username, password);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("微软雅黑", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void loadPreferences() {
        Preferences prefs = Preferences.userRoot().node(PREFS_KEY);
        String savedUsername = prefs.get("username", "");
        if (!savedUsername.isEmpty()) {
            usernameField.setText(savedUsername);
            rememberMeCheck.setSelected(true);
            passwordField.requestFocus();
        }
    }

    private void savePreferences() {
        Preferences prefs = Preferences.userRoot().node(PREFS_KEY);
        if (rememberMeCheck.isSelected()) {
            prefs.put("username", usernameField.getText().trim());
        } else {
            prefs.remove("username");
        }
    }

    private void showRegistrationDialog() {
        RegisterView registerView = new RegisterView();
        registerView.setVisible(true);
    }
}
