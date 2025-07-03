package com.hs.views;

import com.hs.utils.AdminController;
import com.hs.utils.User;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AdminView {
    private AdminController adminController;

    public JTable getUserTable() {
        JTable userTable = null;
        return userTable;
    }

    public AdminView() {
        this.adminController = new AdminController();
    }

    public JPanel getAdminPanel() {
        JPanel adminPanel = new JPanel(new BorderLayout());
        adminPanel.setBackground(Color.WHITE);

        // 创建选项卡面板
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        // 设置选项卡样式
        tabbedPane.setBackground(new Color(240, 240, 240));
        tabbedPane.setForeground(new Color(44, 62, 80));

        // 用户管理面板
        JPanel userManagementPanel = createUserManagementPanel();
        tabbedPane.addTab("用户管理", createTabIcon("👥"), userManagementPanel);

        adminPanel.add(tabbedPane, BorderLayout.CENTER);

        return adminPanel;
    }

    private Icon createTabIcon(String emoji) {
        JLabel label = new JLabel(emoji);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        return new ImageIcon(String.valueOf(((new JLabel(emoji)).getIcon())));
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        // 工具栏
        JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolPanel.setBackground(Color.WHITE);
        toolPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(0, 0, 10, 0)
        ));

        JButton addUserButton = createStyledButton("添加用户", new Color(46, 204, 113));
        JButton resetPasswordButton = createStyledButton("重置密码", new Color(52, 152, 219));
        JButton refreshButton = createStyledButton("刷新", new Color(155, 89, 182));
        JButton deleteUserButton = createStyledButton("删除用户", new Color(231, 76, 60));
        JButton editUserButton = createStyledButton("编辑用户", new Color(241, 196, 15));
        JButton logoutButton = createStyledButton("退出登录", new Color(149, 165, 166));

        toolPanel.add(addUserButton);
        toolPanel.add(resetPasswordButton);
        toolPanel.add(deleteUserButton);
        toolPanel.add(editUserButton);
        toolPanel.add(refreshButton);
        toolPanel.add(logoutButton);

        // 用户表格
        String[] columns = {"ID", "用户名", "姓名", "电话", "角色", "性别", "出生日期", "创建时间"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable userTable = new JTable(model);
        userTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        userTable.setRowHeight(30);
        userTable.setSelectionBackground(new Color(52, 152, 219, 100));
        userTable.setSelectionForeground(Color.BLACK);
        userTable.setGridColor(new Color(240, 240, 240));
        userTable.setShowGrid(true);
        userTable.setIntercellSpacing(new Dimension(0, 0));

        // 设置表头样式
        JTableHeader header = userTable.getTableHeader();
        header.setFont(new Font("微软雅黑", Font.BOLD, 14));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // 加载用户数据
        adminController.loadUserData(model);

        // 添加组件到面板
        panel.add(toolPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 按钮事件
        addUserButton.addActionListener(e -> showAddUserDialog(model));

        resetPasswordButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel, "请先选择要重置密码的用户", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int userId = (int) model.getValueAt(selectedRow, 0);
            String newPassword = JOptionPane.showInputDialog(panel, "输入新密码:", "重置密码", JOptionPane.PLAIN_MESSAGE);

            if (newPassword != null && !newPassword.isEmpty()) {
                if (adminController.resetPassword(userId, newPassword)) {
                    JOptionPane.showMessageDialog(panel, "密码重置成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panel, "密码重置失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteUserButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel, "请先选择要删除的用户", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int userId = (int) model.getValueAt(selectedRow, 0);
            String username = (String) model.getValueAt(selectedRow, 1);

            int confirm = JOptionPane.showConfirmDialog(panel,
                    "确定要删除用户 " + username + " 吗?",
                    "确认删除",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (adminController.deleteUser(userId)) {
                    JOptionPane.showMessageDialog(panel, "用户删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    adminController.loadUserData(model);
                } else {
                    JOptionPane.showMessageDialog(panel, "用户删除失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editUserButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel, "请先选择要编辑的用户", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int userId = (int) model.getValueAt(selectedRow, 0);
            String username = (String) model.getValueAt(selectedRow, 1);
            String name = (String) model.getValueAt(selectedRow, 2);
            String phone = (String) model.getValueAt(selectedRow, 3);
            String role = ((String) model.getValueAt(selectedRow, 4)).equals("管理员") ? "admin" : "doctor";
            String gender = (String) model.getValueAt(selectedRow, 5);
            LocalDate birthDate = LocalDate.parse(model.getValueAt(selectedRow, 6).toString());

            showEditUserDialog(model, userId, username, name, phone, role, gender, birthDate);
        });

        logoutButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(panel);
            if (window != null) {
                window.dispose();
                new LoginView().setVisible(true);
            }
        });

        refreshButton.addActionListener(e -> adminController.loadUserData(model));

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 35));

        // 添加悬停效果
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void showAddUserDialog(DefaultTableModel model) {
        JDialog dialog = new JDialog();
        dialog.setTitle("添加新用户");
        dialog.setModal(true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 15, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 标题
        JLabel titleLabel = new JLabel("添加新用户");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // 表单字段
        String[] labels = {"用户名:", "初始密码:", "角色:", "姓名:", "电话:", "出生日期:", "性别:", "身份证号（医护人员）:"};
        JComponent[] fields = {
                new JTextField(20),
                new JPasswordField(20),
                new JComboBox<>(new String[]{"admin", "doctor"}),
                new JTextField(20),
                new JTextField(20),
                new JDateChooser(),
                new JComboBox<>(new String[]{"男", "女", "其他"}),
                new JTextField(20)
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            panel.add(label, gbc);

            gbc.gridx = 1;
            styleFormField(fields[i]);
            panel.add(fields[i], gbc);
        }

        // 设置身份证号字段初始状态
        ((JTextField)fields[7]).setEnabled(false);
        ((JComboBox<String>)fields[2]).addActionListener(e -> {
            String selectedRole = (String) ((JComboBox<String>)fields[2]).getSelectedItem();
            ((JTextField)fields[7]).setEnabled("doctor".equals(selectedRole));
        });

        // 添加按钮
        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JButton addButton = createStyledButton("添加", new Color(46, 204, 113));
        panel.add(addButton, gbc);

        addButton.addActionListener(e -> {
            String username = ((JTextField)fields[0]).getText().trim();
            String password = new String(((JPasswordField)fields[1]).getPassword());
            String role = (String) ((JComboBox<String>)fields[2]).getSelectedItem();
            String name = ((JTextField)fields[3]).getText().trim();
            String phone = ((JTextField)fields[4]).getText().trim();
            Date birthDate = ((JDateChooser)fields[5]).getDate();
            String gender = (String) ((JComboBox<String>)fields[6]).getSelectedItem();
            String idCard = ((JTextField)fields[7]).getText().trim();

            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty() || birthDate == null) {
                JOptionPane.showMessageDialog(dialog, "请填写所有必填字段", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (adminController.isUsernameExists(username)) {
                JOptionPane.showMessageDialog(dialog, "用户名已存在", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (adminController.addUser(username, password, role, name, phone,
                    birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    gender, idCard)) {
                JOptionPane.showMessageDialog(dialog, "用户添加成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                adminController.loadUserData(model);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "用户添加失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditUserDialog(DefaultTableModel model, int userId, String username,
                                    String name, String phone, String role,
                                    String gender, LocalDate birthDate) {
        JDialog dialog = new JDialog();
        dialog.setTitle("编辑用户信息");
        dialog.setModal(true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 15, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 标题
        JLabel titleLabel = new JLabel("编辑用户信息");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // 表单字段
        String[] labels = {"用户名:", "角色:", "姓名:", "电话:", "出生日期:", "性别:", "身份证号（医护人员）:"};
        JComponent[] fields = {
                new JTextField(username),
                new JComboBox<>(new String[]{"admin", "doctor"}),
                new JTextField(name),
                new JTextField(phone),
                new JDateChooser(Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant())),
                new JComboBox<>(new String[]{"男", "女", "其他"}),
                new JTextField(20)
        };

        ((JComboBox<String>)fields[1]).setSelectedItem(role);
        ((JComboBox<String>)fields[5]).setSelectedItem(gender);

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            panel.add(label, gbc);

            gbc.gridx = 1;
            styleFormField(fields[i]);
            panel.add(fields[i], gbc);
        }

        // 设置身份证号字段初始状态
        ((JTextField)fields[6]).setEnabled("doctor".equals(role));
        ((JComboBox<String>)fields[1]).addActionListener(e -> {
            String selectedRole = (String) ((JComboBox<String>)fields[1]).getSelectedItem();
            ((JTextField)fields[6]).setEnabled("doctor".equals(selectedRole));
        });

        // 更新按钮
        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JButton updateButton = createStyledButton("更新", new Color(46, 204, 113));
        panel.add(updateButton, gbc);

        updateButton.addActionListener(e -> {
            String newUsername = ((JTextField)fields[0]).getText().trim();
            String newRole = (String) ((JComboBox<String>)fields[1]).getSelectedItem();
            String newName = ((JTextField)fields[2]).getText().trim();
            String newPhone = ((JTextField)fields[3]).getText().trim();
            Date newBirthDate = ((JDateChooser)fields[4]).getDate();
            String newGender = (String) ((JComboBox<String>)fields[5]).getSelectedItem();
            String newIdCard = ((JTextField)fields[6]).getText().trim();

            if (newUsername.isEmpty() || newName.isEmpty() || newPhone.isEmpty() || newBirthDate == null) {
                JOptionPane.showMessageDialog(dialog, "请填写所有必填字段", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = new User(
                    userId,
                    newUsername,
                    "", // 密码不在此处更新
                    newRole,
                    false,
                    newName,
                    newPhone,
                    newBirthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    newGender,
                    "doctor".equals(newRole) ? newIdCard : null
            );

            if (adminController.updateUser(user)) {
                JOptionPane.showMessageDialog(dialog, "用户信息更新成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                adminController.loadUserData(model);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "用户信息更新失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void styleFormField(JComponent field) {
        if (field instanceof JTextField) {
            JTextField textField = (JTextField) field;
            textField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            textField.setPreferredSize(new Dimension(0, 35));
        } else if (field instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) field;
            comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            comboBox.setBackground(Color.WHITE);
            comboBox.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        } else if (field instanceof JDateChooser) {
            JDateChooser dateChooser = (JDateChooser) field;
            dateChooser.setDateFormatString("yyyy-MM-dd");
            dateChooser.getDateEditor().getUiComponent().setFont(new Font("微软雅黑", Font.PLAIN, 14));
        }
    }
}