package com.ncu.view;

import com.healthsys.common.controller.PackageTestController;
import com.healthsys.common.model.TestPackage;
import com.healthsys.common.model.MedicalTest;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PackageDetailView {
    private JPanel detailPanel;
    private TestPackage testPackage;
    private PackageTestController packageTestController = new PackageTestController();

    public PackageDetailView(TestPackage testPackage) {
        this.testPackage = testPackage;
        initializeUI();
    }

    private void initializeUI() {
        detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 套餐基本信息面板
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("套餐信息"));

        infoPanel.add(new JLabel("套餐名称:"));
        infoPanel.add(new JLabel(testPackage.getName()));

        infoPanel.add(new JLabel("描述:"));
        infoPanel.add(new JLabel(testPackage.getDescription()));

        infoPanel.add(new JLabel("价格:"));
        infoPanel.add(new JLabel(String.valueOf(testPackage.getPrice())));

        detailPanel.add(infoPanel, BorderLayout.NORTH);

        // 检查项目列表
        String[] columnNames = { "ID", "检查项目名称", "描述", "价格" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable testTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(testTable);

        loadTestsData(model);

        detailPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void loadTestsData(DefaultTableModel model) {
        model.setRowCount(0);
        List<MedicalTest> tests = packageTestController.getMedicalTestsByPackage(testPackage.getId());

        for (MedicalTest test : tests) {
            Object[] rowData = {
                    test.getId(),
                    test.getName(),
                    test.getDescription(),
                    test.getPrice()
            };
            model.addRow(rowData);
        }
    }

    public JPanel getDetailPanel() {
        return detailPanel;
    }
}