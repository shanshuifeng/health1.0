package com.ncu.Healthcare.Dialog;

import com.ncu.Common.Users;
import javax.swing.*;
import java.awt.*;

public class UserDialog extends JDialog {
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    private Users user;
    private int option = CANCEL_OPTION;

    // 对话框组件
    private JTextField phoneField;
    private JTextField nameField;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> roleComboBox;
    private JTextField idNumberField;
    private JPasswordField passwordField;
    private JTextField birthDateField;

    public UserDialog(Users user) {
        this.user = user != null ? user : new Users();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setSize(500, 500); // 增加高度以适应新字段
        setLocationRelativeTo(null);
        setModal(true);

        // 表单面板
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10)); // 改为8行
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 添加表单字段
        addFormField(formPanel, "手机号:", phoneField = new JTextField(user.getPhone()));
        addFormField(formPanel, "密码:", passwordField = new JPasswordField());
        addFormField(formPanel, "姓名:", nameField = new JTextField(user.getName()));
        addFormField(formPanel, "出生日期(YYYY-MM-DD):", birthDateField = new JTextField());

        formPanel.add(new JLabel("性别:"));
        genderComboBox = new JComboBox<>(new String[]{"MALE", "FEMALE"}); // 去掉OTHER选项
        genderComboBox.setSelectedItem(user.getGender());
        formPanel.add(genderComboBox);

        formPanel.add(new JLabel("角色:"));
        roleComboBox = new JComboBox<>(new String[]{"USER", "MEDICAL"});
        roleComboBox.setSelectedItem(user.getRole());
        formPanel.add(roleComboBox);

        addFormField(formPanel, "身份证号:", idNumberField = new JTextField(user.getIdNumber()));

        add(formPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));

        JButton okButton = new JButton("确定");
        okButton.addActionListener(e -> {
            option = OK_OPTION;
            dispose();
        });

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> {
            option = CANCEL_OPTION;
            dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, String labelText, JTextField textField) {
        panel.add(new JLabel(labelText));
        panel.add(textField);
    }

    public int showDialog() {
        setVisible(true);
        return option;
    }


    public Users getUser() {
        user.setPhone(phoneField.getText());
        user.setPassword(new String(passwordField.getPassword()));
        user.setName(nameField.getText());
        // 检查日期字段是否为空
        String birthDateText = birthDateField.getText();
        if (birthDateText != null && !birthDateText.isEmpty()) {
            user.setBirthDate(java.time.LocalDate.parse(birthDateText));
        } else {
            user.setBirthDate(null);  // 或者设置一个默认日期
        }
        user.setGender((String) genderComboBox.getSelectedItem());
        user.setRole((String) roleComboBox.getSelectedItem());
        user.setIdNumber(idNumberField.getText());
        return user;
    }
}
