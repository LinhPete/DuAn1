/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlls;

import daos.NguyenLieuDao;
import entities.NguyenLieu;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import utils.MsgBox;

/**
 *
 * @author ndhlt
 */
public class ApplianceController {
    private static int curr = -1;
    private static final NguyenLieuDao dao = new NguyenLieuDao();
    public static JFrame frame;
    public static JTable tblNL_SP;
    public static JTextField txtmasp_KH;
    public static JTextField txtTSP_KH;
    public static JTextField txtDonVI_SP;
    public static JTextField txtGT_SP;
    public static JTextField txtTKHo_SP;
    public static JTextField txtTThieu_SP;
    public static JLabel lblhhinh_SP;
    public static JButton btndatlai_SP;
    public static JButton btnthem_SP;
    public static JButton btnsua_SP;
    public static JButton btnxoa_SP;

    public static void initialize(JFrame frame, JTable tblNL_SP, JTextField txtmasp_KH, JTextField txtTSP_KH, JTextField txtDonVI_SP,
            JTextField txtGT_SP, JTextField txtTKHo_SP, JTextField txtTThieu_SP, JLabel lblhhinh_SP,
            JButton btndatlai_SP, JButton btnthem_SP, JButton btnsua_SP, JButton btnxoa_SP) {
        ApplianceController.frame = frame;
        ApplianceController.tblNL_SP = tblNL_SP;
        ApplianceController.txtmasp_KH = txtmasp_KH;
        ApplianceController.txtTSP_KH = txtTSP_KH;
        ApplianceController.txtDonVI_SP = txtDonVI_SP;
        ApplianceController.txtGT_SP = txtGT_SP;
        ApplianceController.txtTKHo_SP = txtTKHo_SP;
        ApplianceController.txtTThieu_SP = txtTThieu_SP;
        ApplianceController.lblhhinh_SP = lblhhinh_SP;
        ApplianceController.btndatlai_SP = btndatlai_SP;
        ApplianceController.btnthem_SP = btnthem_SP;
        ApplianceController.btnsua_SP = btnsua_SP;
        ApplianceController.btnxoa_SP = btnxoa_SP;
    }

    public static void getComponents(JFrame frame, JTable tblNL_SP, JTextField txtmasp_KH, JTextField txtTSP_KH, JTextField txtDonVI_SP,
            JTextField txtGT_SP, JTextField txtTKHo_SP, JTextField txtTThieu_SP, JLabel lblhhinh_SP,
            JButton btndatlai_SP, JButton btnthem_SP, JButton btnsua_SP, JButton btnxoa_SP) {
        frame = ApplianceController.frame;
        tblNL_SP = ApplianceController.tblNL_SP;
        txtmasp_KH = ApplianceController.txtmasp_KH;
        txtTSP_KH = ApplianceController.txtTSP_KH;
        txtDonVI_SP = ApplianceController.txtDonVI_SP;
        txtGT_SP = ApplianceController.txtGT_SP;
        txtTKHo_SP = ApplianceController.txtTKHo_SP;
        txtTThieu_SP = ApplianceController.txtTThieu_SP;
        lblhhinh_SP = ApplianceController.lblhhinh_SP;
        btndatlai_SP = ApplianceController.btndatlai_SP;
        btnthem_SP = ApplianceController.btnthem_SP;
        btnsua_SP = ApplianceController.btnsua_SP;
        btnxoa_SP = ApplianceController.btnxoa_SP;
    }

    public static void init() {
        fillTable();
        curr = -1;
        updateStatus();
    }

    public static void fillTable() {
        
        DefaultTableModel model = (DefaultTableModel) tblNL_SP.getModel();
        model.setRowCount(0);
        try {
            List<NguyenLieu> list = dao.selectAll();
            for (NguyenLieu sp : list) {
                Object[] row = {
                    sp.getMaNL(),
                    sp.getTenNL(),
                    sp.getGiaTien(),
                    sp.getTonKho(),
                    sp.getDonVi(),
                    sp.getToiThieu(),
                    sp.getHinh()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(frame, "Lỗi truy vấn dữ liệu!");
        }
    }

 public static void tableClick() {
    curr = tblNL_SP.getSelectedRow();
    if (curr >= 0) { 
        edit();
    } else {
        MsgBox.alert(frame, "Dữ liệu không có sẳn!");
    }
 }

    public static void insert() {
        NguyenLieu model = getForm();
        try {
            dao.insert(model);
            fillTable();
            clearForm();
            MsgBox.alert(frame, "Thêm mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(frame, "Thêm mới thất bại!");
        }
    }

    public static void update() {
        NguyenLieu model = getForm();
        try {
            dao.update(model);
            fillTable();
            MsgBox.alert(frame, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(frame, "Cập nhật thất bại!");
        }
    }

    public static void delete() {
        String MaKH = txtmasp_KH.getText();
        try {
            dao.delete(MaKH);
            fillTable();
            clearForm();
            MsgBox.alert(frame, "Xóa thành công!");
        } catch (Exception e) {
            MsgBox.alert(frame, "Xóa thất bại!");
        }
    }

    public static void clearForm() {
        txtmasp_KH.setText("");
        txtTSP_KH.setText("");
        txtGT_SP.setText("");
        txtTKHo_SP.setText("");
        txtTThieu_SP.setText("");
        txtDonVI_SP.setText("");
        lblhhinh_SP.setText("");
        curr = -1;
        tblNL_SP.clearSelection();
        updateStatus();
    }

    public static void edit() {
        String MaNL = (String) tblNL_SP.getValueAt(curr, 0);
        NguyenLieu cd = dao.selectByID(MaNL);
        setForm(cd);
        updateStatus();
    }

    public static void setForm(NguyenLieu nl) {
        txtmasp_KH.setText(nl.getMaNL());
        txtTSP_KH.setText(nl.getTenNL());
        txtGT_SP.setText(String.valueOf(nl.getGiaTien()));
        txtTKHo_SP.setText(String.valueOf(nl.getTonKho()));
        txtTThieu_SP.setText(String.valueOf(nl.getToiThieu()));
        lblhhinh_SP.setText(nl.getHinh());
        txtDonVI_SP.setText(nl.getDonVi());
    }

    public static NguyenLieu getForm() {
        String maNL = txtmasp_KH.getText();
        String tenNL = txtTSP_KH.getText();
        double giaTien = Double.parseDouble(txtGT_SP.getText());
        int tonKho = Integer.parseInt(txtTKHo_SP.getText());
        int toiThieu = Integer.parseInt(txtTThieu_SP.getText());
        String hinh = lblhhinh_SP.getToolTipText();
        String donVi = txtDonVI_SP.getText();

        return new NguyenLieu(maNL, tenNL, giaTien, tonKho, toiThieu, hinh, donVi);
    }

    private static void updateStatus() {
        boolean edit = (curr >= 0);
        txtmasp_KH.setEditable(!edit);
        btnthem_SP.setEnabled(!edit);
        btnsua_SP.setEnabled(edit);
        btnxoa_SP.setEnabled(edit);
    }
}
