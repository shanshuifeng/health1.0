package com.ncu.Healthcare.Components;

import com.ncu.Common.Users;
import com.ncu.Healthcare.dao.UserDAO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class UserPanel extends CrudPanel<Users> {
    private UserDAO userDAO;
    private JTable table;
    private UserTableModel tableModel;

    public UserPanel() {
        userDAO = new UserDAO();
        initializeTable();
        refreshData();
    }
    private void initializeTable() {
        tableModel = new UserTableModel();
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
        tableModel.setData(userDAO.getAll());
        tableModel.fireTableDataChanged();
    }

    public Users getSelectedUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            return tableModel.getItemAt(selectedRow);
        }
        return null;
    }

    private class UserTableModel extends AbstractTableModel {
        private String[] columnNames = {"ID", "手机号", "姓名", "性别", "角色", "身份证号", "创建时间"};
        private List<Users> data;

        public void setData(List<Users> data) {
            this.data = data;
        }

        public Users getItemAt(int index) {
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
            Users user = data.get(row);
            switch (col) {
                case 0: return user.getId();
                case 1: return user.getPhone();
                case 2: return user.getName();
                case 3: return user.getGender();
                case 4: return user.getRole();
                case 5: return user.getIdNumber();
                case 6: return user.getCreatedAt();
                default: return null;
            }
        }
    }
}

