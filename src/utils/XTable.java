/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.*;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.XImage;

/**
 *
 * @author ndhlt
 */
public final class XTable {

    public static DefaultTableModel creatTableModel(Object[][] rows, String[] headers, boolean isEditable) {
        DefaultTableModel model = new DefaultTableModel(rows, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isEditable;
            }
        };
        return model;
    }

    public static DefaultTableModel creatTableModel(String[] headers, int rowCount, boolean isEditable) {
        DefaultTableModel model = new DefaultTableModel(headers, rowCount) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isEditable;
            }
        };
        return model;
    }
    
    public static DefaultTableModel creatTableModel(String[] headers, ResultSet rs, boolean isEditable) throws SQLException {
        DefaultTableModel model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isEditable;
            }
        };
        ResultSetMetaData data = rs.getMetaData();
        while(rs.next()){
            Object[] row = new Object[data.getColumnCount()];
            for(int i =0; i<data.getColumnCount();i++){
                row[i] = rs.getObject(i);
            }
            model.addRow(row);
        }
        return model;
    }
    
    public static <Thing> DefaultTableModel creatTableModel(String[] headers, List<Thing> list, boolean isEditable) {
        DefaultTableModel model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isEditable;
            }
        };
        return model;
    }

    public static void fillTable(JTable table, ArrayList<Object[]> rows) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        rows.forEach(row -> {
            model.addRow(row);
        });
        table.setModel(model);
    }

    public static void insertImage(JTable table, int column, int height, int width) {
        for (int i = 0; i < table.getModel().getRowCount(); i++) {
            table.setRowHeight(i, height);
        }
        table.getColumnModel().getColumn(column).setWidth(width);
        table.getColumnModel().getColumn(column).setCellRenderer(new ImageRenderer());
    }
    
    public class ImageTableModel extends DefaultTableModel {
    private static final long serialVersionUID = 1L;
    private final int ImageCol;

    public ImageTableModel(Object[][] data, Object[] columnNames, int ImageCol) {
        super(data, columnNames);
        this.ImageCol = ImageCol;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return column == ImageCol ? ImageIcon.class : Object.class;
    }
}

    private static class ImageRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Gọi phương thức gốc để lấy thành phần hiển thị mặc định
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Kiểm tra nếu giá trị của ô là tên tệp hình ảnh
            if (value instanceof String) {
                String imagePath = (String) value;

                try {
                    int h = table.getRowHeight(row);
                    int w = table.getColumnModel().getColumn(column).getWidth();

                    // Thiết lập hình ảnh cho JLabel trong thành phần hiển thị
                    JLabel label = new JLabel(XImage.getResized(new ImageIcon(imagePath), w, h));
                    label.setHorizontalAlignment(JLabel.CENTER);

                    // Thiết lập thành phần hiển thị là JLabel chứa hình ảnh
                    return label;
                } catch (Exception e) {
                    return new JLabel(imagePath);
                }
            }

            return component;
        }
    }
}
