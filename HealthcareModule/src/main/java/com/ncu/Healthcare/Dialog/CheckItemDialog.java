package com.ncu.Healthcare.Dialog;

import com.ncu.Common.CheckItem;
import javax.swing.*;
import java.awt.*;

public class CheckItemDialog extends JDialog {
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private CheckItem checkItem;
    private int option = CANCEL_OPTION;

    // 对话框组件
    private JTextField nameField;
    private JTextField codeField;
    private JTextField descriptionField;
    private JTextField normalRangeField;
    private JTextField priceField;

    public CheckItemDialog(CheckItem checkItem) {
        this.checkItem = checkItem != null ? checkItem : new CheckItem();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setModal(true);

        // 表单面板
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 添加表单字段
        addFormField(formPanel, "名称:", nameField = new JTextField(checkItem.getName()));
        addFormField(formPanel, "代码:", codeField = new JTextField(checkItem.getCode()));
        addFormField(formPanel, "描述:", descriptionField = new JTextField(checkItem.getDescription()));
        addFormField(formPanel, "正常范围:", normalRangeField = new JTextField(checkItem.getNormalRange()));
        addFormField(formPanel, "价格:", priceField = new JTextField(checkItem.getPrice() != null ? checkItem.getPrice().toString() : ""));

        add(formPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));

        JButton okButton = new JButton("确定");
        okButton.addActionListener(e -> {
            if (validateInput()) {
                option = OK_OPTION;
                dispose();
            }
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

    private boolean validateInput() {
        try {
            Double.parseDouble(priceField.getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的价格", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public int showDialog() {
        setVisible(true);
        return option;
    }

    public CheckItem getCheckItem() {
        checkItem.setName(nameField.getText());
        checkItem.setCode(codeField.getText());
        checkItem.setDescription(descriptionField.getText());
        checkItem.setNormalRange(normalRangeField.getText());
        checkItem.setPrice(Double.parseDouble(priceField.getText()));
        return checkItem;
    }
}
