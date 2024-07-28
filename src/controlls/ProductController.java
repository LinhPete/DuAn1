/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlls;

import daoimpl.SanPhamImple;
import entities.SanPham;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utils.XTable;
import views.CheBienJDialog;

/**
 *
 * @author ndhlt
 */
public class ProductController {

    private static JFrame frame;
    private static JTable tblSanPham;

    public static void initialize(JFrame frame, JTable tblSanPham) {
        ProductController.frame = frame;
        ProductController.tblSanPham = tblSanPham;
    }
    
    public static void getComponents(JFrame frame, JTable tblSanPham) {
        frame = ProductController.frame;
        tblSanPham = ProductController.tblSanPham;
    }

    private static final SanPhamImple spdao = new SanPhamImple();

    public static void fillTableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
//        try {
            List<SanPham> list = spdao.getItemsByID("");
            model.setRowCount(0);
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getHinh(),
                    sp.getLoaiSP(),
                    sp.getTenSP(),
                    sp.getGiaTien()
                };
                model.addRow(row);
            }
            XTable.insertImage(tblSanPham, 1, 100, 100, "ProdImages");
//        } catch (Exception e) {
//            MsgBox.alert(frame, "Lỗi truy vấn dữ liệu !!");
//        }
    }
//    private static String masp;

    public static void chuyenTrang() {
//        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
//        masp = model.getValueAt(tblSanPham.getSelectedRow(), 0) + "";
        new CheBienJDialog(frame, true).setVisible(true);
    }
}
