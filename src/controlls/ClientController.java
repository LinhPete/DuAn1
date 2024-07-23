/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlls;

import daos.KhachHangDAO;
import entities.KhachHang;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import utils.MsgBox;

/**
 *
 * @author ndhlt
 */
public class ClientController {
    private static int curr = -1;
    private static final KhachHangDAO dao = new KhachHangDAO();
    public static JFrame frame;
    public static JTable table;
    public static JTextField txtMaKH;
    public static JTextField txtHoVaTen;
    public static JTextField txtSDT;
    public static JButton btnThem;
    public static JButton btnSua;
    public static JButton btnXoa;
    
    public static void initialize(JFrame frame, JTable table, JTextField txtMaKH, JTextField txtHoVaTen, JTextField txtSDT,JButton btnThem,JButton btnSua,JButton btnXoa){
        ClientController.frame = frame;
        ClientController.table = table;
        ClientController.txtMaKH = txtMaKH;
        ClientController.txtHoVaTen = txtHoVaTen;
        ClientController.txtSDT = txtSDT;
        ClientController.btnThem = btnThem;
        ClientController.btnSua = btnSua;
        ClientController.btnXoa = btnXoa;
    }
    
    public static void getComponents(JFrame frame, JTable table, JTextField txtMaKH, JTextField txtHoVaTen, JTextField txtSDT,JButton btnThem,JButton btnSua,JButton btnXoa){
        frame = ClientController.frame;
        table = ClientController.table;
        txtMaKH = ClientController.txtMaKH;
        txtHoVaTen = ClientController.txtHoVaTen;
        txtSDT = ClientController.txtSDT;
        btnThem = ClientController.btnThem;
        btnSua = ClientController.btnSua;
        btnXoa = ClientController.btnXoa;
    }

    public static void init() {
        fillTable();
        curr = -1;
        updateStatus();
    }

    public static void fillTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            List<KhachHang> list = dao.selectAll();
            for (KhachHang sp : list) {
                Object[] row = {
                    sp.getMaKH(),
                    sp.getHoVaTen(),
                    sp.getSDT()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(frame, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    public static void tableClick(){
        curr = table.getSelectedRow();
        edit();
    }

    public static void insert(){
        KhachHang model = getForm();
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
        KhachHang model = getForm();
        try {
            dao.update(model);
            fillTable();
            MsgBox.alert(frame, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(frame, "Cập nhật thất bại!");
        }
    }

    public static void delete() {

        String MaKH = txtMaKH.getText();
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
        txtMaKH.setText("");
        txtHoVaTen.setText("");
        txtSDT.setText("");
        curr = -1;
        table.clearSelection();
        updateStatus();
    }

    public static void edit() {
        String MaKH = (String) table.getValueAt(curr, 0);
        KhachHang cd = dao.selectByID(MaKH);
        setForm(cd);
        updateStatus();
    }

    public static void setForm(KhachHang kh) {
        txtMaKH.setText(kh.getMaKH());
        txtHoVaTen.setText(kh.getHoVaTen());
        txtSDT.setText(kh.getSDT());
    }

    public static KhachHang getForm(){
        KhachHang sp = new KhachHang();
        sp.setMaKH(txtMaKH.getText());
        sp.setHoVaTen(txtHoVaTen.getText());
        sp.setSDT(txtSDT.getText());
        return sp;
    }

    private static void updateStatus()  {
        boolean edit = (curr >= 0);

        // Trạng thái form
        txtMaKH.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

    }
}
