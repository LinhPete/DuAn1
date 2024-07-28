/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlls;

import daos.NhanVienDAO;
import entities.NhanVien;
import java.io.File;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import utils.MsgBox;
import utils.XDate;
import utils.XImage;
import utils.XTable;

/**
 *
 * @author ndhlt
 */
public class EmployeeController {

    private static int curr = -1;
    private static final NhanVienDAO dao = new NhanVienDAO();
    public static JFrame frame;
    public static JTable table;
    public static JTextField txtManv;
    public static JTextField txtHoVaTen;
    public static JPasswordField txtmatkhau;
    public static JRadioButton rdoAdmin;
    public static JRadioButton rdoKho;
    public static JRadioButton rdoBan;
    public static JRadioButton rdonam;
    public static JRadioButton rdonu;
    public static ButtonGroup group1;
    public static ButtonGroup group2;
    public static JTextField txtnamsinh;
    public static JTextField txtsodienthoai_NV;
    public static JTextField txtemail;
    public static JTextArea txtdiachi_NV;
    public static JLabel lblhinh;
    public static JButton btncapnhat;
    public static JButton btnThem;
    public static JButton btndatlai;
    public static JButton btnXoa;
    private static File img;

    public static void initialize(JFrame frame, JTable table, JTextField txtManv,
            JTextField txtHoVaTen, JPasswordField txtmatkhau,
            JRadioButton rdoAdmin, JRadioButton rdoKho, JRadioButton rdoBan, JRadioButton rdonam, JRadioButton rdonu, ButtonGroup group1, ButtonGroup group2,
            JTextField txtnamsinh, JTextField txtsodienthoai_NV, JTextField txtemail, JTextArea txtdiachi_NV, JLabel lblhinh, JButton btncapnhat, JButton btnThem,
            JButton btndatlai, JButton btnXoa) {
        EmployeeController.frame = frame;
        EmployeeController.table = table;
        EmployeeController.txtManv = txtManv;
        EmployeeController.txtHoVaTen = txtHoVaTen;
        EmployeeController.txtmatkhau = txtmatkhau;
        EmployeeController.rdoAdmin = rdoAdmin;
        EmployeeController.rdoKho = rdoKho;
        EmployeeController.rdoBan = rdoBan;
        EmployeeController.rdonam = rdonam;
        EmployeeController.rdonu = rdonu;
        EmployeeController.group1 = group1;
        EmployeeController.group2 = group2;
        EmployeeController.txtnamsinh = txtnamsinh;
        EmployeeController.txtsodienthoai_NV = txtsodienthoai_NV;
        EmployeeController.txtemail = txtemail;
        EmployeeController.txtdiachi_NV = txtdiachi_NV;
        EmployeeController.lblhinh = lblhinh;
        EmployeeController.btnThem = btnThem;
        EmployeeController.btncapnhat = btncapnhat;
        EmployeeController.btndatlai = btndatlai;
        EmployeeController.btnXoa = btnXoa;
    }

    public static void getComponents(JFrame frame, JTable table, JTextField txtManv,
            JTextField txtHoVaTen, JPasswordField txtmatkhau,
            JRadioButton rdoAdmin, JRadioButton rdoKho, JRadioButton rdoBan, JRadioButton rdonam, JRadioButton rdonu, ButtonGroup group1, ButtonGroup group2,
            JTextField txtnamsinh, JTextField txtsodienthoai_NV, JTextField txtemail, JTextArea txtdiachi_NV, JLabel lblhinh, JButton btncapnhat, JButton btnThem,
            JButton btndatlai, JButton btnXoa) {
        frame = EmployeeController.frame;
        table = EmployeeController.table;
        txtManv = EmployeeController.txtManv;
        txtHoVaTen = EmployeeController.txtHoVaTen;
        txtmatkhau = EmployeeController.txtmatkhau;
        rdoAdmin = EmployeeController.rdoAdmin;
        rdoKho = EmployeeController.rdoKho;
        rdoBan = EmployeeController.rdoBan;
        rdonam = EmployeeController.rdonam;
        rdonu = EmployeeController.rdonu;
        group1 = EmployeeController.group1;
        group2 = EmployeeController.group2;
        txtnamsinh = EmployeeController.txtnamsinh;
        txtsodienthoai_NV = EmployeeController.txtsodienthoai_NV;
        txtemail = EmployeeController.txtemail;
        txtdiachi_NV = EmployeeController.txtdiachi_NV;
        lblhinh = EmployeeController.lblhinh;
        btnThem = EmployeeController.btnThem;
        btncapnhat = EmployeeController.btncapnhat;
        btnXoa = EmployeeController.btnXoa;
        btndatlai = EmployeeController.btndatlai;
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
            List<NhanVien> list = dao.selectAll();
            for (NhanVien sp : list) {
                Object[] row = {
                    sp.getHinh(),
                    sp.getMaNV(),
                    sp.getHoVaTen(),
                    sp.getMatKhau().replaceAll(".", "*"),
                    sp.getCv().getTenCV(),
                    sp.isGioiTinh(),
                    sp.getNgaySinh(),
                    sp.getSDT(),
                    sp.getEmail(),
                    sp.getDiaChi()
                };
                model.addRow(row);
            }
            table.setModel(model);
            XTable.insertImage(table, 0, 100, 100, "EmpImages");
        } catch (Exception e) {
            MsgBox.alert(frame, "Lỗi truy vấn dữ liệu!");
        }
    }

    public static void tableClick() {
        curr = table.getSelectedRow();
        edit();
    }

    public static void insert() {
        NhanVien model = getForm();
        File file = new File("EmpImages", model.getHinh());
        if (!file.exists()) {
            XImage.save("EmpImages", img);
        } else {
            MsgBox.alert(frame, "Ảnh đã tồn tại");
            return;
        }
        dao.insert(model);
        fillTable();
        clearForm();
        MsgBox.inform(frame, "Thêm mới thành công!");

    }

    public static void update() {
        NhanVien model = getForm();
        NhanVien og = dao.selectByID(model.getMaNV());
        if (!og.getHinh().equals(model.getHinh())) {
            File file = new File("EmpImages", og.getHinh());
            if (file.exists()) {
                file.delete();
            }
            XImage.save("EmpImages", img);
        }
        dao.update(model);
        fillTable();
        MsgBox.alert(frame, "Cập nhật thành công!");
    }

    public static void delete() {

        String MaNV = txtManv.getText();
        File file = new File("EmpImages", lblhinh.getToolTipText());
        if (file.exists()) {
            file.delete();
        }
        dao.delete(MaNV);
        fillTable();
        clearForm();
        MsgBox.alert(frame, "Xóa thành công!");
    }

    public static void clearForm() {
        txtManv.setText("");
        txtHoVaTen.setText("");
        txtmatkhau.setText("");
        group1.clearSelection();
        group2.clearSelection();
        txtnamsinh.setText("");
        txtsodienthoai_NV.setText("");
        txtemail.setText("");
        txtdiachi_NV.setText("");
        lblhinh.setText("");
        curr = -1;
        table.clearSelection();
        updateStatus();
    }

    public static void edit() {
        if (curr >= 0) {
            String MaNV = (String) table.getValueAt(curr, 1);
            NhanVien cd = dao.selectByID(MaNV);
            setForm(cd);
            updateStatus();
        }
    }

    public static void setForm(NhanVien kh) {
        txtManv.setText(kh.getMaNV());
        txtHoVaTen.setText(kh.getHoVaTen());
        txtmatkhau.setText(kh.getMatKhau());
        if (NhanVien.list.get(0).equals(kh.getCv())) {
            rdoAdmin.setSelected(true);
        } else if (NhanVien.list.get(1).equals(kh.getCv())) {
            rdoKho.setSelected(true);
        } else if (NhanVien.list.get(2).equals(kh.getCv())) {
            rdoBan.setSelected(true);
        }
        if (kh.isGioiTinh()) {
            rdonam.setSelected(true);
        } else {
            rdonu.setSelected(true);
        }
        txtnamsinh.setText(XDate.toString(kh.getNgaySinh(), "dd/MM/yyyy"));
        txtsodienthoai_NV.setText(kh.getSDT());
        txtemail.setText(kh.getEmail());
        txtdiachi_NV.setText(kh.getDiaChi());
        File file = new File("EmpImages", kh.getHinh());
        if (!file.exists()) {
            lblhinh.setText(kh.getHinh());
        } else {
            lblhinh.setIcon(XImage.getResized(XImage.read("EmpImages", kh.getHinh()), lblhinh.getWidth(), lblhinh.getHeight()));
            lblhinh.setToolTipText(kh.getHinh());
        }
    }

    public static NhanVien getForm() {
        NhanVien sp = new NhanVien();
        sp.setMaNV(txtManv.getText());
        sp.setMatKhau(new String(txtmatkhau.getPassword()));
        sp.setHoVaTen(txtHoVaTen.getText());
        int i = -1;
        if (rdoAdmin.isSelected()) {
            i = 0;
        } else if (rdoBan.isSelected()) {
            i = 1;
        } else if (rdoKho.isSelected()) {
            i = 2;
        }
        sp.setCv(NhanVien.list.get(i));
        sp.setGioiTinh(rdonam.isSelected());
        sp.setNgaySinh(XDate.toDate(txtnamsinh.getText(), "dd/MM/yyyy"));
        sp.setSDT(txtsodienthoai_NV.getText());
        sp.setEmail(txtemail.getText());
        sp.setDiaChi(txtdiachi_NV.getText());
        if (lblhinh.getIcon() == null) {
            sp.setHinh("none");
        } else {
            sp.setHinh(lblhinh.getToolTipText());
        }
        return sp;
    }

    private static void updateStatus() {
        boolean edit = (curr >= 0);

        // Trạng thái form
        txtManv.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btncapnhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public static void chonAnh() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Thiết lập bộ lọc để chỉ chọn các tệp ảnh
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String ext = getFileExtension(f);
                return ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("gif");
            }

            @Override
            public String getDescription() {
                return "Image files (*.jpg, *.jpeg, *.png, *.gif)";
            }

            private String getFileExtension(File f) {
                String name = f.getName();
                int lastIndex = name.lastIndexOf('.');
                if (lastIndex == -1) {
                    return "";
                }
                return name.substring(lastIndex + 1).toLowerCase();
            }
        });

        // Hiển thị hộp thoại chọn tệp
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Hiển thị hình ảnh được chọn
            lblhinh.setIcon(XImage.getResized(new ImageIcon(selectedFile.getAbsolutePath()), lblhinh.getWidth(), lblhinh.getHeight()));
            lblhinh.setToolTipText(selectedFile.getName());
            EmployeeController.img = selectedFile;
        }
    }
}
