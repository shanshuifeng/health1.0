package com.ncu.Healthcare.Components;

import com.ncu.Common.CheckItem;
import com.ncu.Healthcare.dao.CheckItemDAO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class CheckItemPanel extends CrudPanel<CheckItem> {
    private CheckItemDAO checkItemDAO;
    private JTable table;
    private CheckItemTableModel tableModel;

    public CheckItemPanel() {
        checkItemDAO = new CheckItemDAO();
        initializeTable();
        refreshData();
    }

    private void initializeTable() {
        tableModel = new CheckItemTableModel();
        table = new JTable(tableModel);

        // 表格样式优化
        table.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(220, 240, 255));
        table.setSelectionForeground(Color.BLACK);

        // 隔行变色
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
                }
                return c;
            }
        });

        // 列宽自动调整
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        setContent(scrollPane);
    }

    @Override
    public void refreshData() {
        tableModel.setData(checkItemDAO.getAll());
        tableModel.fireTableDataChanged();
    }

    public CheckItem getSelectedCheckItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            return tableModel.getItemAt(selectedRow);
        }
        return null;
    }

    private class CheckItemTableModel extends AbstractTableModel {
        private String[] columnNames = {"ID", "名称", "代码", "描述", "正常范围", "价格", "创建时间"};
        private List<CheckItem> data;

        public void setData(List<CheckItem> data) {
            this.data = data;
        }

        public CheckItem getItemAt(int index) {
            return data.get(index);
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data == null ? 0 : data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            CheckItem item = data.get(row);
            switch (col) {
                case 0: return item.getId();
                case 1: return item.getName();
                case 2: return item.getCode();
                case 3: return item.getDescription();
                case 4: return item.getNormalRange();
                case 5: return item.getPrice();
                case 6: return item.getCreatedAt();
                default: return null;
            }
        }
    }
}

