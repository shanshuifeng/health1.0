package com.ncu.Healthcare.Dialog;

import com.ncu.Common.Appointment;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class AppointmentDialog extends JDialog {
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private Appointment appointment;
    private int option = CANCEL_OPTION;

    // 对话框组件
    private JTextField userIdField;
    private JTextField packageIdField;
    private JComboBox<String> statusComboBox;
    private JCheckBox paymentStatusCheckBox;
    private JDateChooser appointmentDateChooser;
    private JDateChooser examDateChooser;

    public AppointmentDialog(Appointment appointment) {
        this.appointment = appointment != null ? appointment : new Appointment();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setModal(true);
        setTitle(appointment.getId() == null ? "新增预约" : "编辑预约");

        // 表单面板
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 添加表单字段
        addFormField(formPanel, "用户ID:", userIdField = new JTextField(appointment.getUserId() != null ? appointment.getUserId().toString() : ""));
        addFormField(formPanel, "套餐ID:", packageIdField = new JTextField(appointment.getPackageId() != null ? appointment.getPackageId().toString() : ""));

        formPanel.add(new JLabel("预约时间:"));
        appointmentDateChooser = new JDateChooser();
        appointmentDateChooser.setDateFormatString("yyyy-MM-dd HH:mm");
        if (appointment.getAppointmentTime() != null) {
            appointmentDateChooser.setDate(Date.from(appointment.getAppointmentTime().atZone(ZoneId.systemDefault()).toInstant()));
        }
        formPanel.add(appointmentDateChooser);

        formPanel.add(new JLabel("检查时间:"));
        examDateChooser = new JDateChooser();
        examDateChooser.setDateFormatString("yyyy-MM-dd HH:mm");
        if (appointment.getExamTime() != null) {
            examDateChooser.setDate(Date.from(appointment.getExamTime().atZone(ZoneId.systemDefault()).toInstant()));
        }
        formPanel.add(examDateChooser);

        formPanel.add(new JLabel("状态:"));
        statusComboBox = new JComboBox<>(new String[]{"PENDING", "IN_PROGRESS", "COMPLETED"});
        statusComboBox.setSelectedItem(appointment.getStatus() != null ? appointment.getStatus() : "PENDING");
        formPanel.add(statusComboBox);

        formPanel.add(new JLabel("支付状态:"));
        paymentStatusCheckBox = new JCheckBox("已支付");
        paymentStatusCheckBox.setSelected(appointment.getPaymentStatus() != null && appointment.getPaymentStatus());
        formPanel.add(paymentStatusCheckBox);

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

    private void addFormField(JPanel panel, String labelText, JComponent field) {
        panel.add(new JLabel(labelText));
        panel.add(field);
    }

    private boolean validateInput() {
        try {
            // 验证用户ID和套餐ID
            Long.parseLong(userIdField.getText());
            Long.parseLong(packageIdField.getText());

            // 验证预约时间和检查时间
            if (appointmentDateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "请选择预约时间", "错误", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (examDateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "请选择检查时间", "错误", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的用户ID和套餐ID", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public int showDialog() {
        setVisible(true);
        return option;
    }

    public Appointment getAppointment() {
        appointment.setUserId(Long.parseLong(userIdField.getText()));
        appointment.setPackageId(Long.parseLong(packageIdField.getText()));
        appointment.setAppointmentTime(appointmentDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        appointment.setExamTime(examDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        appointment.setStatus((String) statusComboBox.getSelectedItem());
        appointment.setPaymentStatus(paymentStatusCheckBox.isSelected());
        return appointment;
    }
}
