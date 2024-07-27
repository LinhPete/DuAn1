/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlls;

import daos.NhanVienDAO;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import utils.Auth;
import utils.MsgBox;
import utils.XDate;

/**
 *
 * @author ndhlt
 */
public class ProfileController {

    private static JFrame frame;
    private static JTextField txtMaNv;
    private static JPasswordField txtPass;
    private static JTextField Name;
    private static JTextField ChucVu;
    private static JTextField NgaySinh;
    private static JRadioButton rdoNam;
    private static JRadioButton rdoNu;
    private static JTextField DienThoai;
    private static JTextField email;
    private static JTextArea DiaChi;

    public static void initialize(JFrame frame, JTextField txtMaNv, JPasswordField txtPass, JTextField Name, JTextField ChucVu, JTextField NgaySinh,
            JRadioButton rdoNam, JRadioButton rdoNu, JTextField DienThoai, JTextField email, JTextArea DiaChi) {
        ProfileController.frame = frame;
        ProfileController.txtMaNv = txtMaNv;
        ProfileController.txtPass = txtPass;
        ProfileController.Name = Name;
        ProfileController.ChucVu = ChucVu;
        ProfileController.NgaySinh = NgaySinh;
        ProfileController.rdoNam = rdoNam;
        ProfileController.rdoNu = rdoNu;
        ProfileController.DienThoai = DienThoai;
        ProfileController.email = email;
        ProfileController.DiaChi = DiaChi;
    }

    public static void getComponents(JFrame frame, JTextField txtMaNv, JPasswordField txtPass, JTextField Name, JTextField ChucVu, JTextField NgaySinh,
            JRadioButton rdoNam, JRadioButton rdoNu, JTextField DienThoai, JTextField email, JTextArea DiaChi) {
        frame = ProfileController.frame;
        txtMaNv = ProfileController.txtMaNv;
        txtPass = ProfileController.txtPass;
        Name = ProfileController.Name;
        ChucVu = ProfileController.ChucVu;
        NgaySinh = ProfileController.NgaySinh;
        rdoNam = ProfileController.rdoNam;
        rdoNu = ProfileController.rdoNu;
        DienThoai = ProfileController.DienThoai;
        email = ProfileController.email;
        DiaChi = ProfileController.DiaChi;
    }

    public static void setForm() {
        txtMaNv.setText(Auth.getUser().getMaNV());
        txtPass.setText(Auth.getUser().getMatKhau());
        Name.setText(Auth.getUser().getHoVaTen());
        ChucVu.setText(Auth.getUser().getCv().getTenCV());
        NgaySinh.setText(XDate.toString(Auth.getUser().getNgaySinh(), "dd/MM/yyyy"));
        DienThoai.setText(Auth.getUser().getSDT());
        email.setText(Auth.getUser().getEmail());
        if (Auth.getUser().isGioiTinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        DiaChi.setText(Auth.getUser().getDiaChi());
    }

    public static void updateProfile() {
        if (checkInput()) {
            Auth.getUser().setHoVaTen(Name.getText());
            Auth.getUser().setNgaySinh(XDate.toDate(NgaySinh.getText(), "dd/MM/yyyy"));
            Auth.getUser().setSDT(DienThoai.getText());
            Auth.getUser().setEmail(email.getText());
            Auth.getUser().setDiaChi(DiaChi.getText());
            Auth.getUser().setGioiTinh(rdoNam.isSelected());
            new NhanVienDAO().update(Auth.getUser());
        }
    }

    private static boolean checkInput() {
        if (Name.getText().isBlank()) {
            MsgBox.alert(frame, "Tên không được để trống");
            Name.requestFocus();
            return false;
        }
        if (NgaySinh.getText().isBlank()) {
            MsgBox.alert(frame, "Ngày sinh không được để trống");
            NgaySinh.requestFocus();
            return false;
        }
        if (DienThoai.getText().isBlank()) {
            MsgBox.alert(frame, "SDT không được để trống");
            DienThoai.requestFocus();
            return false;
        }
        if (email.getText().isBlank()) {
            MsgBox.alert(frame, "Email không được để trống");
            email.requestFocus();
            return false;
        }
        if (DiaChi.getText().isBlank()) {
            MsgBox.alert(frame, "Địa chỉ không được để trống");
            DiaChi.requestFocus();
            return false;
        }
        return true;
    }
}
