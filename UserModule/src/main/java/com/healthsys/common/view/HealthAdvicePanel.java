package com.healthsys.common.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.healthsys.common.model.ExamRecord;
import com.healthsys.common.model.MedicalTest;
import com.healthsys.common.controller.MedicalTestController;


public class HealthAdvicePanel extends JPanel {
    public HealthAdvicePanel(List<ExamRecord> records) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("健康建议"));

        JTextPane adviceText = new JTextPane();
        adviceText.setContentType("text/html");
        adviceText.setEditable(false);
        adviceText.setBackground(UIManager.getColor("Panel.background"));

        StringBuilder html = new StringBuilder("<html>");

        for (ExamRecord record : records) {
            Long testId = record.getTestId();
            MedicalTest medicalTest = new MedicalTestController().getTestById(testId);
            String testName = medicalTest != null ? medicalTest.getName() : "未知项目";
            String result = record.getResultValue();

            try {
                double value = Double.parseDouble(result);
                String normalRange = medicalTest != null ? medicalTest.getNormalRange() : "";
                if (normalRange == null || normalRange.isEmpty()) {
                    normalRange = "0-0"; // 默认值避免空指针
                }

                String[] rangeParts = normalRange.split("[-~]");
                double min = Double.parseDouble(rangeParts[0]);
                double max = Double.parseDouble(rangeParts[1]);

                if (value < min || value > max) {
                    html.append("<p style='color:red;'>您的 ").append(testName)
                            .append(" 值为 ").append(value)
                            .append(", 超出正常范围 (").append(min).append("-").append(max)
                            .append(")，建议及时复查。</p>");
                }
            } catch (Exception ignored) {}
        }

        html.append("</html>");
        adviceText.setText(html.toString());

        add(new JScrollPane(adviceText), BorderLayout.CENTER);
    }
}
