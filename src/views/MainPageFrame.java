/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import controlls.ApplianceController;
import controlls.ClientController;
import controlls.EmployeeController;
import controlls.ProfileController;
import controlls.ReceiptController;
import java.awt.CardLayout;
import java.awt.Color;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import utils.Auth;
import utils.MsgBox;
import utils.SubController;
import utils.XDate;
import utils.XImage;

/**
 *
 * @author acer
 */
public class MainPageFrame extends javax.swing.JFrame {

    private final Color normalBackground = new Color(135, 206, 235);
    private final Color hover = new Color(99, 216, 242);
    private final Color clicked = new Color(242, 171, 39);
    private final Map<JPanel, Boolean> btnMap = new HashMap();
    private Set<JPanel> btnSet;
    private CardLayout cards;

    /**
     * Creates new form MainPagFrame
     */
    public MainPageFrame() {
        new ChaoJDialog(this, true).setVisible(true);
        new LoginDialog(this, true).setVisible(true);
        initComponents();
        init();
        setUser();
        this.setLocationRelativeTo(null);
    }

    private void init() {
        setInteract(btnHome, btnEmployee, btnClient, btnProduct, btnAppliance, btnReceipt, btnSummary, btnProfile, btnLogout, btnExit);
        btnSet = btnMap.keySet();
        cards = (CardLayout) container.getLayout();
        clock().start();
        btnClicked(btnHome, () -> {
            showCard("trangChu");
        });
    }

    private void setUser() {
        File file = new File("EmpImages", Auth.getUser().getHinh());
        if (!file.exists()) {
            MsgBox.alert(this, "Chưa cập nhật ảnh đại diện!!!");
            btnProfile.requestFocus();
        }
        side_lblPic.setIcon(XImage.getResized(XImage.read("EmpImages", Auth.getUser().getHinh()), side_lblPic.getWidth(), side_lblPic.getHeight()));
        side_lblUser.setText(Auth.getUser().getHoVaTen());
    }

    private void initController() {
        ClientController.initialize(this, tblClient, txtMaKH, txtTenClient, txtSDTClient, btnCreateClient, btnUpdateClient, btnDeleteClient);
        EmployeeController.initialize(this, tblNhanVien, txtManv, txtHoVaTenNV, txtmatkhauNV, rdoCvAdmin, rdoCvBan, rdoCvKho, rdoNvNam, rdoNvNu, buttonGroup2, buttonGroup3, txtnamsinh_NV, txtsodienthoai_NV, txtemail_NV, txtdiachi_NV, lblhinhNV, btncapnhatNV, btnThemNv, btndatlaiNv, btnXoaNV);
        ApplianceController.initialize(this, tblNL_SP, txtmasp_KH, txtTSP_KH, txtDonVI_SP, txtGT_SP, txtTKHo_SP, txtTThieu_SP, lblhinh_SP, btndatlai_SP, btnthem_SP, btnsua_SP, btnxoa_SP);
        ReceiptController.initialize(this, tblHoaDon, tblCTHoaDon, txtTimHD, cboTime);
        ProfileController.initialize(this, txtMaNV, txtMatKhau, txtHoTen, Prof_txtMaCV, Prof_txtNgaySinh, Prof_rdoNam, Prof_rdoNu, Prof_txtSDT, Prof_txtEmail, Prof_txtDiaChi, Prof_lblHinh);
    }

    private void getController() {
        ClientController.getComponents(this, tblClient, txtMaKH, txtTenClient, txtSDTClient, btnCreateClient, btnUpdateClient, btnDeleteClient);
        EmployeeController.getComponents(this, tblNhanVien, txtManv, txtHoVaTenNV, txtmatkhauNV, rdoCvAdmin, rdoCvBan, rdoCvKho, rdoNvNam, rdoNvNu, buttonGroup2, buttonGroup3, txtnamsinh_NV, txtsodienthoai_NV, txtemail_NV, txtdiachi_NV, lblhinhNV, btncapnhatNV, btnThemNv, btndatlaiNv, btnXoaNV);
        ApplianceController.getComponents(this, tblNL_SP, txtmasp_KH, txtTSP_KH, txtDonVI_SP, txtGT_SP, txtTKHo_SP, txtTThieu_SP, lblhinh_SP, btndatlai_SP, btnthem_SP, btnsua_SP, btnxoa_SP);
        ReceiptController.getComponents(this, tblHoaDon, tblCTHoaDon, txtTimHD, cboTime);
        ProfileController.getComponents(this, txtMaNV, txtMatKhau, txtHoTen, Prof_txtMaCV, Prof_txtNgaySinh, Prof_rdoNam, Prof_rdoNu, Prof_txtSDT, Prof_txtEmail, Prof_txtDiaChi, Prof_lblHinh);
    }

    private void runController(Runnable run) {
        initController();
        run.run();
        getController();
    }

    private Thread clock() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Date date = new Date();
                    lblDate.setText(XDate.toString(date, "E dd/MM/yyyy"));
                    lblTime.setText(XDate.toString(date, "hh:mm:ss"));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainPageFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        thread.setDaemon(true);
        return thread;
    }

    private void setInteract(JPanel... others) {
        for (JPanel panel : others) {
            btnMap.put(panel, false);
        }
    }

    private void btnHover(JPanel panel) {
        if (btnMap.containsKey(panel) && btnMap.get(panel) == false) {
            panel.setBackground(hover);
        }
    }

    private void btnExited(JPanel panel) {
        if (btnMap.containsKey(panel) && btnMap.get(panel) == false) {
            panel.setBackground(normalBackground);
        }
    }

    private void btnClicked(JPanel clicked, Runnable run) {
        if (run != null) {
            if (btnMap.containsKey(clicked) && btnMap.get(clicked) == true) {
                return;
            }
            clicked.setBackground(this.clicked);

            run.run();

            btnMap.put(clicked, true);
            for (JPanel panel : btnSet) {
                if (panel.equals(clicked)) {
                    continue;
                }
                panel.setBackground(normalBackground);
                btnMap.put(panel, false);
            }
        }
    }

    private void btnReset(JPanel panel) {
        if (btnMap.containsKey(panel)) {
            btnMap.put(panel, false);
            panel.setBackground(normalBackground);
        }
    }

    private void showCard(String cardName) {
        cards.show(container, cardName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        lblDate = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        side_lblPic = new javax.swing.JLabel();
        side_lblUser = new javax.swing.JLabel();
        sideNav = new javax.swing.JPanel();
        btnHome = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnEmployee = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnClient = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnProduct = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btnAppliance = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnReceipt = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnSummary = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnProfile = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnLogout = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnExit = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        container = new javax.swing.JPanel();
        pnlShowPanel = new javax.swing.JPanel();
        panelRound1 = new jpanelrounded.PanelRound();
        homeLblSum = new javax.swing.JLabel();
        panelRound6 = new jpanelrounded.PanelRound();
        homeEmpPic = new javax.swing.JLabel();
        homeEmpName = new javax.swing.JLabel();
        panelRound7 = new jpanelrounded.PanelRound();
        homeProdPic = new javax.swing.JLabel();
        homeProdName = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        customJTable1 = new customjtable.CustomJTable();
        jPanel15 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtnamsinh_NV = new javax.swing.JTextField();
        txtsodienthoai_NV = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtdiachi_NV = new javax.swing.JTextArea();
        txtManv = new javax.swing.JTextField();
        txtHoVaTenNV = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        rdoNvNam = new javax.swing.JRadioButton();
        rdoNvNu = new javax.swing.JRadioButton();
        jLabel33 = new javax.swing.JLabel();
        txtemail_NV = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txtmatkhauNV = new javax.swing.JPasswordField();
        rdoCvAdmin = new javax.swing.JRadioButton();
        rdoCvBan = new javax.swing.JRadioButton();
        rdoCvKho = new javax.swing.JRadioButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        lblhinhNV = new javax.swing.JLabel();
        btndatlaiNv = new javax.swing.JButton();
        btnXoaNV = new javax.swing.JButton();
        btncapnhatNV = new javax.swing.JButton();
        btnThemNv = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        btnRefreshClient = new javax.swing.JButton();
        btnDeleteClient = new javax.swing.JButton();
        btnUpdateClient = new javax.swing.JButton();
        btnCreateClient = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txtTenClient = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtSDTClient = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClient = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtmasp_KH = new javax.swing.JTextField();
        txtTSP_KH = new javax.swing.JTextField();
        txtTKHo_SP = new javax.swing.JTextField();
        txtDonVI_SP = new javax.swing.JTextField();
        txtGT_SP = new javax.swing.JTextField();
        btnthem_SP = new javax.swing.JButton();
        btnsua_SP = new javax.swing.JButton();
        btnxoa_SP = new javax.swing.JButton();
        btndatlai_SP = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblNL_SP = new javax.swing.JTable();
        jLabel43 = new javax.swing.JLabel();
        lblhinh_SP = new javax.swing.JLabel();
        txtTThieu_SP = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        pnlTimHD = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTimHD = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        cboTime = new javax.swing.JComboBox<>();
        buttonCustom1 = new button.ButtonCustom();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblCTHoaDon = new customjtable.CustomJTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblHoaDon = new customjtable.CustomJTable();
        jPanel3 = new javax.swing.JPanel();
        Prof_lblHinh = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel15 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        Prof_txtMaCV = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        Prof_txtNgaySinh = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        Prof_rdoNam = new javax.swing.JRadioButton();
        Prof_rdoNu = new javax.swing.JRadioButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        Prof_txtSDT = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        Prof_txtEmail = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Prof_txtDiaChi = new javax.swing.JTextArea();
        btnDoiMK = new javax.swing.JButton();
        btnUpdateProfile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(54, 70, 78));
        jPanel2.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 581));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(135, 206, 235));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel12.setOpaque(false);

        lblDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("Day");

        lblTime.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setText("Time");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTime)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.setOpaque(false);
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        side_lblPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        side_lblPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/user (1).png"))); // NOI18N

        side_lblUser.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        side_lblUser.setForeground(new java.awt.Color(255, 255, 255));
        side_lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        side_lblUser.setText("User");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(side_lblPic, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(side_lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(side_lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
            .addComponent(side_lblPic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        sideNav.setBackground(new java.awt.Color(135, 206, 235));
        sideNav.setLayout(new java.awt.GridLayout(10, 1));

        btnHome.setBackground(new java.awt.Color(36, 103, 191));
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHomeMouseExited(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/home.png"))); // NOI18N
        jLabel4.setText("Trang chủ");

        javax.swing.GroupLayout btnHomeLayout = new javax.swing.GroupLayout(btnHome);
        btnHome.setLayout(btnHomeLayout);
        btnHomeLayout.setHorizontalGroup(
            btnHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnHomeLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnHomeLayout.setVerticalGroup(
            btnHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        sideNav.add(btnHome);

        btnEmployee.setBackground(new java.awt.Color(36, 103, 191));
        btnEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEmployeeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEmployeeMouseExited(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/job.png"))); // NOI18N
        jLabel5.setText("Nhân viên");

        javax.swing.GroupLayout btnEmployeeLayout = new javax.swing.GroupLayout(btnEmployee);
        btnEmployee.setLayout(btnEmployeeLayout);
        btnEmployeeLayout.setHorizontalGroup(
            btnEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnEmployeeLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnEmployeeLayout.setVerticalGroup(
            btnEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        sideNav.add(btnEmployee);

        btnClient.setBackground(new java.awt.Color(36, 103, 191));
        btnClient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClientMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClientMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClientMouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/KhachHang.png"))); // NOI18N
        jLabel6.setText("Khách hàng");

        javax.swing.GroupLayout btnClientLayout = new javax.swing.GroupLayout(btnClient);
        btnClient.setLayout(btnClientLayout);
        btnClientLayout.setHorizontalGroup(
            btnClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnClientLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnClientLayout.setVerticalGroup(
            btnClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        sideNav.add(btnClient);

        btnProduct.setBackground(new java.awt.Color(36, 103, 191));
        btnProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProductMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProductMouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/SanPham.png"))); // NOI18N
        jLabel7.setText("Sản phẩm");

        javax.swing.GroupLayout btnProductLayout = new javax.swing.GroupLayout(btnProduct);
        btnProduct.setLayout(btnProductLayout);
        btnProductLayout.setHorizontalGroup(
            btnProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnProductLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnProductLayout.setVerticalGroup(
            btnProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        sideNav.add(btnProduct);

        btnAppliance.setBackground(new java.awt.Color(36, 103, 191));
        btnAppliance.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAppliance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnApplianceMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnApplianceMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnApplianceMouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/VatTu.png"))); // NOI18N
        jLabel8.setText("Kho hàng");

        javax.swing.GroupLayout btnApplianceLayout = new javax.swing.GroupLayout(btnAppliance);
        btnAppliance.setLayout(btnApplianceLayout);
        btnApplianceLayout.setHorizontalGroup(
            btnApplianceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnApplianceLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnApplianceLayout.setVerticalGroup(
            btnApplianceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        sideNav.add(btnAppliance);

        btnReceipt.setBackground(new java.awt.Color(36, 103, 191));
        btnReceipt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReceipt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReceiptMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReceiptMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReceiptMouseExited(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/invoice.png"))); // NOI18N
        jLabel9.setText("Hóa đơn");

        javax.swing.GroupLayout btnReceiptLayout = new javax.swing.GroupLayout(btnReceipt);
        btnReceipt.setLayout(btnReceiptLayout);
        btnReceiptLayout.setHorizontalGroup(
            btnReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnReceiptLayout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnReceiptLayout.setVerticalGroup(
            btnReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        sideNav.add(btnReceipt);

        btnSummary.setBackground(new java.awt.Color(36, 103, 191));
        btnSummary.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSummary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSummaryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSummaryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSummaryMouseExited(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ThongKe.png"))); // NOI18N
        jLabel10.setText("Thống kê");

        javax.swing.GroupLayout btnSummaryLayout = new javax.swing.GroupLayout(btnSummary);
        btnSummary.setLayout(btnSummaryLayout);
        btnSummaryLayout.setHorizontalGroup(
            btnSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnSummaryLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnSummaryLayout.setVerticalGroup(
            btnSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        sideNav.add(btnSummary);

        btnProfile.setBackground(new java.awt.Color(36, 103, 191));
        btnProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProfileMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProfileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProfileMouseExited(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/user (1).png"))); // NOI18N
        jLabel11.setText("Thiết lập");

        javax.swing.GroupLayout btnProfileLayout = new javax.swing.GroupLayout(btnProfile);
        btnProfile.setLayout(btnProfileLayout);
        btnProfileLayout.setHorizontalGroup(
            btnProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnProfileLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnProfileLayout.setVerticalGroup(
            btnProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        sideNav.add(btnProfile);

        jPanel2.add(sideNav, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(36, 103, 191));
        jPanel5.setPreferredSize(new java.awt.Dimension(100, 80));
        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        btnLogout.setBackground(new java.awt.Color(36, 103, 191));
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutMouseExited(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logout (1).png"))); // NOI18N
        jLabel12.setText("Đăng xuất");

        javax.swing.GroupLayout btnLogoutLayout = new javax.swing.GroupLayout(btnLogout);
        btnLogout.setLayout(btnLogoutLayout);
        btnLogoutLayout.setHorizontalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnLogoutLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnLogoutLayout.setVerticalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel5.add(btnLogout);

        btnExit.setBackground(new java.awt.Color(36, 103, 191));
        btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExitMouseExited(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/turn-off.png"))); // NOI18N
        jLabel13.setText("Thoát");

        javax.swing.GroupLayout btnExitLayout = new javax.swing.GroupLayout(btnExit);
        btnExit.setLayout(btnExitLayout);
        btnExitLayout.setHorizontalGroup(
            btnExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnExitLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnExitLayout.setVerticalGroup(
            btnExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel5.add(btnExit);

        jPanel2.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_START);

        container.setLayout(new java.awt.CardLayout());

        pnlShowPanel.setBackground(new java.awt.Color(255, 255, 255));

        panelRound1.setBackground(new java.awt.Color(0, 255, 204));
        panelRound1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "TỔNG ĐƠN HÀNG TRONG NGÀY", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(102, 102, 102))); // NOI18N
        panelRound1.setRoundButtomLeft(30);
        panelRound1.setRoundButtomRight(30);
        panelRound1.setRoundTopLeft(30);
        panelRound1.setRoundTopRight(30);
        panelRound1.setLayout(new java.awt.BorderLayout());

        homeLblSum.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        homeLblSum.setForeground(new java.awt.Color(102, 102, 255));
        homeLblSum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        homeLblSum.setText("999");
        panelRound1.add(homeLblSum, java.awt.BorderLayout.CENTER);

        panelRound6.setBackground(new java.awt.Color(0, 255, 204));
        panelRound6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "NHÂN VIÊN CỦA THÁNG", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(102, 102, 102))); // NOI18N
        panelRound6.setRoundButtomLeft(30);
        panelRound6.setRoundButtomRight(30);
        panelRound6.setRoundTopLeft(30);
        panelRound6.setRoundTopRight(30);
        panelRound6.setLayout(new java.awt.GridLayout(1, 2));

        homeEmpPic.setText("Hình nhân viên");
        panelRound6.add(homeEmpPic);

        homeEmpName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        homeEmpName.setForeground(new java.awt.Color(102, 102, 255));
        homeEmpName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        homeEmpName.setText("999");
        panelRound6.add(homeEmpName);

        panelRound7.setBackground(new java.awt.Color(0, 255, 204));
        panelRound7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "BÁN CHẠY NHẤT THÁNG", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(102, 102, 102))); // NOI18N
        panelRound7.setRoundButtomLeft(30);
        panelRound7.setRoundButtomRight(30);
        panelRound7.setRoundTopLeft(30);
        panelRound7.setRoundTopRight(30);
        panelRound7.setLayout(new java.awt.GridLayout(1, 2));

        homeProdPic.setText("Hình sản phẩm");
        panelRound7.add(homeProdPic);

        homeProdName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        homeProdName.setForeground(new java.awt.Color(102, 102, 255));
        homeProdName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        homeProdName.setText("999");
        panelRound7.add(homeProdName);

        customJTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(customJTable1);

        javax.swing.GroupLayout pnlShowPanelLayout = new javax.swing.GroupLayout(pnlShowPanel);
        pnlShowPanel.setLayout(pnlShowPanelLayout);
        pnlShowPanelLayout.setHorizontalGroup(
            pnlShowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlShowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlShowPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(panelRound6, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelRound7, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        pnlShowPanelLayout.setVerticalGroup(
            pnlShowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlShowPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlShowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound7, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addGroup(pnlShowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelRound6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        container.add(pnlShowPanel, "trangChu");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Mã NV");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Họ và tên");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Giới tính");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Năm sinh");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Địa chỉ");

        txtdiachi_NV.setColumns(20);
        txtdiachi_NV.setRows(5);
        jScrollPane5.setViewportView(txtdiachi_NV);

        txtManv.setEditable(false);
        txtManv.setFocusable(false);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("SĐT");

        buttonGroup3.add(rdoNvNam);
        rdoNvNam.setText("Nam");
        rdoNvNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNvNamActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdoNvNu);
        rdoNvNu.setText("Nữ");
        rdoNvNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNvNuActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Email");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Quản Lý Nhân Viên");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel36.setText("Chức vụ");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel37.setText("Mật khẩu");

        txtmatkhauNV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        buttonGroup2.add(rdoCvAdmin);
        rdoCvAdmin.setText("Admin");

        buttonGroup2.add(rdoCvBan);
        rdoCvBan.setText("Bán hàng");

        buttonGroup2.add(rdoCvKho);
        rdoCvKho.setText("Quản kho");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtManv))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtHoVaTenNV))
                                .addGroup(jPanel21Layout.createSequentialGroup()
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel21Layout.createSequentialGroup()
                                            .addGap(12, 12, 12)
                                            .addComponent(jLabel36))
                                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel21Layout.createSequentialGroup()
                                            .addComponent(rdoNvNam)
                                            .addGap(36, 36, 36)
                                            .addComponent(rdoNvNu))
                                        .addComponent(txtemail_NV, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                                        .addComponent(txtsodienthoai_NV)
                                        .addComponent(txtnamsinh_NV)
                                        .addComponent(txtmatkhauNV)
                                        .addGroup(jPanel21Layout.createSequentialGroup()
                                            .addComponent(rdoCvAdmin)
                                            .addGap(18, 18, 18)
                                            .addComponent(rdoCvBan)
                                            .addGap(18, 18, 18)
                                            .addComponent(rdoCvKho)))))))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel34)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtManv, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoVaTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txtmatkhauNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(rdoCvAdmin)
                    .addComponent(rdoCvBan)
                    .addComponent(rdoCvKho))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNvNu)
                    .addComponent(rdoNvNam)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnamsinh_NV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsodienthoai_NV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtemail_NV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Hình", "Mã KH", "Họ Tên", "Mật khẩu", "Chức vụ", "Giới Tính", "Ngày sinh", "SĐT", "Email", "Địa Chỉ"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblNhanVien);

        lblhinhNV.setBackground(new java.awt.Color(204, 204, 204));
        lblhinhNV.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblhinhNV.setForeground(new java.awt.Color(102, 153, 255));
        lblhinhNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblhinhNV.setText("Thêm ảnh");
        lblhinhNV.setToolTipText("");
        lblhinhNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        lblhinhNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblhinhNVMouseClicked(evt);
            }
        });

        btndatlaiNv.setBackground(new java.awt.Color(141, 158, 255));
        btndatlaiNv.setText("Đặt Lại");
        btndatlaiNv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndatlaiNvActionPerformed(evt);
            }
        });

        btnXoaNV.setBackground(new java.awt.Color(141, 158, 255));
        btnXoaNV.setText("Xóa");
        btnXoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNVActionPerformed(evt);
            }
        });

        btncapnhatNV.setBackground(new java.awt.Color(141, 158, 255));
        btncapnhatNV.setText("Cập Nhật");
        btncapnhatNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatNVActionPerformed(evt);
            }
        });

        btnThemNv.setBackground(new java.awt.Color(141, 158, 255));
        btnThemNv.setText("Thêm ");
        btnThemNv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNvActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblhinhNV, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnThemNv, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(71, 71, 71)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btndatlaiNv, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btncapnhatNV, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 361, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(lblhinhNV, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemNv, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncapnhatNV, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btndatlaiNv, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        container.add(jPanel15, "nhanVien");

        btnRefreshClient.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnRefreshClient.setText("Reset");
        btnRefreshClient.setMaximumSize(new java.awt.Dimension(95, 32));
        btnRefreshClient.setMinimumSize(new java.awt.Dimension(95, 32));
        btnRefreshClient.setPreferredSize(new java.awt.Dimension(95, 32));
        btnRefreshClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshClientActionPerformed(evt);
            }
        });

        btnDeleteClient.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDeleteClient.setText("Remove");
        btnDeleteClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteClientActionPerformed(evt);
            }
        });

        btnUpdateClient.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnUpdateClient.setText("Update");
        btnUpdateClient.setMaximumSize(new java.awt.Dimension(95, 32));
        btnUpdateClient.setMinimumSize(new java.awt.Dimension(95, 32));
        btnUpdateClient.setPreferredSize(new java.awt.Dimension(95, 32));
        btnUpdateClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateClientActionPerformed(evt);
            }
        });

        btnCreateClient.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCreateClient.setText("Create");
        btnCreateClient.setMaximumSize(new java.awt.Dimension(95, 32));
        btnCreateClient.setMinimumSize(new java.awt.Dimension(95, 32));
        btnCreateClient.setPreferredSize(new java.awt.Dimension(95, 32));
        btnCreateClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateClientActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setText("HỌ VÀ TÊN");

        txtTenClient.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setText("SĐT");

        txtSDTClient.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel25.setText("MaKH");

        txtMaKH.setEditable(false);
        txtMaKH.setBackground(new java.awt.Color(204, 204, 204));
        txtMaKH.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaKH.setFocusable(false);

        tblClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MaKH", "HỌ VÀ TÊN", "SĐT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClient.setGridColor(new java.awt.Color(255, 0, 0));
        tblClient.setRowHeight(25);
        tblClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblClient);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 204));
        jLabel26.setText("QUẢN LÝ KHÁCH HÀNG");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1176, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDTClient)
                    .addComponent(txtMaKH)
                    .addComponent(txtTenClient)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(btnCreateClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpdateClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btnDeleteClient)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRefreshClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24)
                            .addComponent(jLabel23)
                            .addComponent(jLabel25))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSDTClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteClient)
                    .addComponent(btnRefreshClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE))
        );

        container.add(jPanel13, "khachHang");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Quản Lý Kho Hàng");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel38.setText("Mã nguyên liệu");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel39.setText("Tên nguyên liệu");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("Giá Tiền");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Tối Thiểu");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("Tồn kho");

        txtmasp_KH.setEditable(false);
        txtmasp_KH.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtmasp_KH.setFocusable(false);

        txtTSP_KH.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtTKHo_SP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtDonVI_SP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtGT_SP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnthem_SP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnthem_SP.setText("Thêm");
        btnthem_SP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthem_SPActionPerformed(evt);
            }
        });

        btnsua_SP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnsua_SP.setText("Sửa");
        btnsua_SP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsua_SPActionPerformed(evt);
            }
        });

        btnxoa_SP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnxoa_SP.setText("Xóa");
        btnxoa_SP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoa_SPActionPerformed(evt);
            }
        });

        btndatlai_SP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btndatlai_SP.setText("Đặt Lại");
        btndatlai_SP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndatlai_SPActionPerformed(evt);
            }
        });

        tblNL_SP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Hình", "Mã sản phẩm", "Tên sản phẩm", "Giá Tiền", "Tồn kho", "Tối Thiểu", "Đơn vị"
            }
        ));
        tblNL_SP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNL_SPMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblNL_SP);

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("Đơn Vị");

        lblhinh_SP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblhinh_SP.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 1, true));
        lblhinh_SP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblhinh_SP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblhinh_SPMouseClicked(evt);
            }
        });

        txtTThieu_SP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGap(211, 211, 211)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel39)
                                        .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDonVI_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel22Layout.createSequentialGroup()
                                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtTThieu_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtmasp_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTSP_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGT_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTKHo_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel22Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(btnthem_SP)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnsua_SP)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnxoa_SP))
                                            .addGroup(jPanel22Layout.createSequentialGroup()
                                                .addGap(71, 71, 71)
                                                .addComponent(lblhinh_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(18, 18, 18)
                                .addComponent(btndatlai_SP)))
                        .addGap(0, 337, Short.MAX_VALUE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7)))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(txtmasp_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTSP_KH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTKHo_SP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGT_SP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTThieu_SP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41)))
                    .addComponent(lblhinh_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel43)
                        .addComponent(txtDonVI_SP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnthem_SP)
                        .addComponent(btnsua_SP)
                        .addComponent(btnxoa_SP)
                        .addComponent(btndatlai_SP)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        container.add(jPanel22, "khoHang");

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setLayout(new java.awt.BorderLayout());

        pnlTimHD.setBackground(new java.awt.Color(255, 255, 255));
        pnlTimHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlTimHD.setPreferredSize(new java.awt.Dimension(1270, 80));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Tìm hóa đơn: ");

        txtTimHD.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        txtTimHD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimHDKeyReleased(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("Theo:");

        cboTime.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        cboTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hôm nay", "Tháng trước", "Năm nay" }));
        cboTime.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTimeItemStateChanged(evt);
            }
        });

        buttonCustom1.setText("Tạo đơn hàng");
        buttonCustom1.setBorderColor(new java.awt.Color(102, 102, 102));
        buttonCustom1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTimHDLayout = new javax.swing.GroupLayout(pnlTimHD);
        pnlTimHD.setLayout(pnlTimHDLayout);
        pnlTimHDLayout.setHorizontalGroup(
            pnlTimHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimHDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTimHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTimHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimHD)
                    .addComponent(cboTime, 0, 274, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 626, Short.MAX_VALUE)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        pnlTimHDLayout.setVerticalGroup(
            pnlTimHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimHDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTimHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlTimHDLayout.createSequentialGroup()
                        .addGroup(pnlTimHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlTimHDLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 3, Short.MAX_VALUE))
                            .addComponent(txtTimHD))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlTimHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboTime, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel23.add(pnlTimHD, java.awt.BorderLayout.PAGE_START);

        jScrollPane8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHI TIẾT HÓA ĐƠN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N
        jScrollPane8.setPreferredSize(new java.awt.Dimension(450, 500));

        tblCTHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ SP", "SẢN PHẨM", "SỐ LƯỢNG", "ĐƠN GIÁ", "THÀNH TIỀN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tblCTHoaDon);

        jPanel23.add(jScrollPane8, java.awt.BorderLayout.LINE_END);

        jScrollPane9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DANH SÁCH HÓA ĐƠN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MÃ HD", "KHÁCH HÀNG", "NHÂN VIÊN", "TỔNG TIỀN", "GIẢM GIÁ", "NGÀY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblHoaDon);

        jPanel23.add(jScrollPane9, java.awt.BorderLayout.CENTER);

        container.add(jPanel23, "hoaDon");

        Prof_lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Prof_lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Prof_lblHinhMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Mã nhân viên");

        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new java.awt.Color(204, 204, 204));
        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaNV.setText("Test");
        txtMaNV.setFocusable(false);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Mật khẩu");

        txtMatKhau.setEditable(false);
        txtMatKhau.setBackground(new java.awt.Color(204, 204, 204));
        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMatKhau.setFocusable(false);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Họ và tên");

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Chức vụ");

        Prof_txtMaCV.setEditable(false);
        Prof_txtMaCV.setBackground(new java.awt.Color(204, 204, 204));
        Prof_txtMaCV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Prof_txtMaCV.setFocusable(false);

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Ngày sinh");

        Prof_txtNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 497, Short.MAX_VALUE))
                    .addComponent(Prof_txtNgaySinh))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Prof_txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jPanel6.add(jPanel7);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Giới tính");

        buttonGroup1.add(Prof_rdoNam);
        Prof_rdoNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Prof_rdoNam.setText("Nam");

        buttonGroup1.add(Prof_rdoNu);
        Prof_rdoNu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Prof_rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(Prof_rdoNam)
                        .addGap(18, 18, 18)
                        .addComponent(Prof_rdoNu)))
                .addContainerGap(456, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Prof_rdoNam)
                    .addComponent(Prof_rdoNu))
                .addGap(16, 16, 16))
        );

        jPanel6.add(jPanel8);

        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("Điện thoại");

        Prof_txtSDT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 495, Short.MAX_VALUE))
                    .addComponent(Prof_txtSDT))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Prof_txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jPanel9.add(jPanel10);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setText("Email");

        Prof_txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(Prof_txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Prof_txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jPanel9.add(jPanel11);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setText("Địa chỉ");

        Prof_txtDiaChi.setColumns(20);
        Prof_txtDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Prof_txtDiaChi.setRows(3);
        jScrollPane1.setViewportView(Prof_txtDiaChi);

        btnDoiMK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDoiMK.setText("Đổi mật khẩu");
        btnDoiMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMKActionPerformed(evt);
            }
        });

        btnUpdateProfile.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnUpdateProfile.setText("Cập nhật");
        btnUpdateProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Prof_lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV)
                            .addComponent(txtMatKhau)
                            .addComponent(txtHoTen)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(Prof_txtMaCV)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnUpdateProfile)
                        .addGap(18, 18, 18)
                        .addComponent(btnDoiMK)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Prof_txtMaCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Prof_lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDoiMK)
                    .addComponent(btnUpdateProfile))
                .addContainerGap())
        );

        container.add(jPanel3, "thietLap");

        getContentPane().add(container, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseEntered
        btnHover(btnHome);
    }//GEN-LAST:event_btnHomeMouseEntered

    private void btnHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseExited
        btnExited(btnHome);
    }//GEN-LAST:event_btnHomeMouseExited

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        btnClicked(btnHome, () -> {
            showCard("trangChu");
        });
    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnEmployeeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeMouseEntered
        btnHover(btnEmployee);
    }//GEN-LAST:event_btnEmployeeMouseEntered

    private void btnEmployeeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeMouseExited
        btnExited(btnEmployee);
    }//GEN-LAST:event_btnEmployeeMouseExited

    private void btnEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeMouseClicked
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Không đủ quyền hạn truy cập!!!");
            return;
        }
        btnClicked(btnEmployee, () -> {
            showCard("nhanVien");
            runController(() -> {
                EmployeeController.init();
            });
        });
    }//GEN-LAST:event_btnEmployeeMouseClicked

    private void btnClientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientMouseEntered
        btnHover(btnClient);
    }//GEN-LAST:event_btnClientMouseEntered

    private void btnClientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientMouseExited
        btnExited(btnClient);
    }//GEN-LAST:event_btnClientMouseExited

    private void btnClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientMouseClicked
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Không đủ quyền hạn truy cập!!!");
            return;
        }
        btnClicked(btnClient, () -> {
            showCard("khachHang");
            runController(() -> {
                ClientController.init();
            });
        });
    }//GEN-LAST:event_btnClientMouseClicked

    private void btnProductMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductMouseEntered
        btnHover(btnProduct);
    }//GEN-LAST:event_btnProductMouseEntered

    private void btnProductMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductMouseExited
        btnExited(btnProduct);
    }//GEN-LAST:event_btnProductMouseExited

    private void btnProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductMouseClicked
        btnClicked(btnProduct, null);
    }//GEN-LAST:event_btnProductMouseClicked

    private void btnApplianceMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApplianceMouseEntered
        btnHover(btnAppliance);
    }//GEN-LAST:event_btnApplianceMouseEntered

    private void btnApplianceMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApplianceMouseExited
        btnExited(btnAppliance);
    }//GEN-LAST:event_btnApplianceMouseExited

    private void btnApplianceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApplianceMouseClicked
        if (Auth.isBanHang()) {
            MsgBox.alert(this, "Không đủ quyền hạn truy cập!!!");
            return;
        }
        btnClicked(btnAppliance, () -> {
            showCard("khoHang");
            runController(() -> {
                ApplianceController.init();
            });
        });
    }//GEN-LAST:event_btnApplianceMouseClicked

    private void btnReceiptMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReceiptMouseEntered
        btnHover(btnReceipt);
    }//GEN-LAST:event_btnReceiptMouseEntered

    private void btnReceiptMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReceiptMouseExited
        btnExited(btnReceipt);
    }//GEN-LAST:event_btnReceiptMouseExited

    private void btnReceiptMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReceiptMouseClicked
        if (Auth.isKho()) {
            MsgBox.alert(this, "Không đủ quyền hạn truy cập!!!");
            return;
        }
        btnClicked(btnReceipt, () -> {
            showCard("hoaDon");
            runController(() -> {
                ReceiptController.init();
            });
        });
    }//GEN-LAST:event_btnReceiptMouseClicked

    private void btnSummaryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSummaryMouseEntered
        btnHover(btnSummary);
    }//GEN-LAST:event_btnSummaryMouseEntered

    private void btnSummaryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSummaryMouseExited
        btnExited(btnSummary);
    }//GEN-LAST:event_btnSummaryMouseExited

    private void btnSummaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSummaryMouseClicked
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Không đủ quyền hạn truy cập!!!");
            return;
        }
        btnClicked(btnSummary, null);
    }//GEN-LAST:event_btnSummaryMouseClicked

    private void btnProfileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfileMouseEntered
        btnHover(btnProfile);
    }//GEN-LAST:event_btnProfileMouseEntered

    private void btnProfileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfileMouseExited
        btnExited(btnProfile);
    }//GEN-LAST:event_btnProfileMouseExited

    private void btnProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfileMouseClicked
        btnClicked(btnProfile, () -> {
            showCard("thietLap");
            runController(() -> {
                ProfileController.setForm();
            });
        });
    }//GEN-LAST:event_btnProfileMouseClicked

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        btnHover(btnLogout);
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        btnExited(btnLogout);
    }//GEN-LAST:event_btnLogoutMouseExited

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        btnClicked(btnLogout, this::logOut);
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseEntered
        btnHover(btnExit);
    }//GEN-LAST:event_btnExitMouseEntered

    private void btnExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseExited
        btnExited(btnExit);
    }//GEN-LAST:event_btnExitMouseExited

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        btnClicked(btnExit, this::exit);
        btnReset(btnExit);
    }//GEN-LAST:event_btnExitMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exit();
    }//GEN-LAST:event_formWindowClosing

    private void btnDoiMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMKActionPerformed
        PasswordChangeWindow wdw = new PasswordChangeWindow();
        wdw.setReturnWindow(this);
        wdw.setVisible(true);
    }//GEN-LAST:event_btnDoiMKActionPerformed

    private void btnRefreshClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshClientActionPerformed
        runController(() -> {
            ClientController.clearForm();
        });
    }//GEN-LAST:event_btnRefreshClientActionPerformed

    private void btnDeleteClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteClientActionPerformed
        runController(() -> {
            ClientController.delete();
        });
    }//GEN-LAST:event_btnDeleteClientActionPerformed

    private void btnUpdateClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateClientActionPerformed
        runController(() -> {
            ClientController.update();
        });
    }//GEN-LAST:event_btnUpdateClientActionPerformed

    private void btnCreateClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateClientActionPerformed
        runController(() -> {
            ClientController.insert();
        });
    }//GEN-LAST:event_btnCreateClientActionPerformed

    private void tblClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientMouseClicked
        runController(() -> {
            ClientController.tableClick();
        });
    }//GEN-LAST:event_tblClientMouseClicked

    private void rdoNvNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNvNamActionPerformed
        //runController(()->{
        //            EmployeeController.
        //        });
    }//GEN-LAST:event_rdoNvNamActionPerformed

    private void rdoNvNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNvNuActionPerformed
        //runController(()->{
        //            EmployeeController.
        //        });
    }//GEN-LAST:event_rdoNvNuActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        runController(() -> {
            EmployeeController.tableClick();
        });
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void lblhinhNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblhinhNVMouseClicked
        //runController(()->{
        //            EmployeeController.
        //        });
    }//GEN-LAST:event_lblhinhNVMouseClicked

    private void btndatlaiNvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndatlaiNvActionPerformed
        runController(() -> {
            EmployeeController.clearForm();
        });
    }//GEN-LAST:event_btndatlaiNvActionPerformed

    private void btnXoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNVActionPerformed
        runController(() -> {
            EmployeeController.delete();
        });
    }//GEN-LAST:event_btnXoaNVActionPerformed

    private void btncapnhatNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatNVActionPerformed
        runController(() -> {
            EmployeeController.update();
        });
    }//GEN-LAST:event_btncapnhatNVActionPerformed

    private void btnThemNvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNvActionPerformed
        runController(() -> {
            EmployeeController.insert();
        });
    }//GEN-LAST:event_btnThemNvActionPerformed

    private void btnthem_SPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthem_SPActionPerformed
        runController(() -> {
            ApplianceController.insert();
        });
    }//GEN-LAST:event_btnthem_SPActionPerformed

    private void btnsua_SPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsua_SPActionPerformed
        runController(() -> {
            ApplianceController.update();
        });
    }//GEN-LAST:event_btnsua_SPActionPerformed

    private void btnxoa_SPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoa_SPActionPerformed
        runController(() -> {
            ApplianceController.delete();
        });
    }//GEN-LAST:event_btnxoa_SPActionPerformed

    private void btndatlai_SPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndatlai_SPActionPerformed
        runController(() -> {
            ApplianceController.clearForm();
        });
    }//GEN-LAST:event_btndatlai_SPActionPerformed

    private void tblNL_SPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNL_SPMouseClicked
        runController(() -> {
            ApplianceController.tableClick();
        });
    }//GEN-LAST:event_tblNL_SPMouseClicked

    private void lblhinh_SPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblhinh_SPMouseClicked
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
            String path = selectedFile.getAbsolutePath();

            // Hiển thị hình ảnh được chọn
            lblhinh_SP.setIcon(new ImageIcon(path));
            lblhinh_SP.setToolTipText(path);
        }
    }//GEN-LAST:event_lblhinh_SPMouseClicked

    private void txtTimHDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimHDKeyReleased
//        this.search();
        runController(() -> {
            ReceiptController.search();
        });
    }//GEN-LAST:event_txtTimHDKeyReleased

    private void cboTimeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTimeItemStateChanged
//        this.fillTableBills();
        runController(() -> {
            ReceiptController.fillTableBills();
        });
    }//GEN-LAST:event_cboTimeItemStateChanged

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        SubController subFrame = new CreateOrdersJFrame();
        subFrame.setReturnWindow(this);
        subFrame.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_buttonCustom1ActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
//        this.fillDetailBills();
        runController(() -> {
            ReceiptController.fillDetailBills();
        });
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnUpdateProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateProfileActionPerformed
        runController(() -> {
            ProfileController.updateProfile();
        });
        setUser();
    }//GEN-LAST:event_btnUpdateProfileActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        btnClicked(btnProfile, () -> {
            showCard("thietLap");
            runController(() -> {
                ProfileController.setForm();
            });
        });
    }//GEN-LAST:event_jPanel1MouseClicked

    private void Prof_lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Prof_lblHinhMouseClicked
        if (evt.getClickCount() == 2) {
            runController(() -> {
                ProfileController.chonAnh();
            });
        }
    }//GEN-LAST:event_Prof_lblHinhMouseClicked

    private void exit() {
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Không đủ quyền hạn truy cập!!!");
            return;
        }
        boolean res = MsgBox.confirm(this, "Chắc chắn muốn thoát ?");
        if (res) {
            System.exit(0);
        } else {
            btnClicked(btnHome, () -> {
                showCard("trangChu");
            });
        }
    }

    private void logOut() {
        Auth.clear();
        new LoginDialog(this, true).setVisible(true);
        setUser();
        btnReset(btnLogout);
        btnClicked(btnHome, () -> {
            showCard("trangChu");
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPageFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Prof_lblHinh;
    private javax.swing.JRadioButton Prof_rdoNam;
    private javax.swing.JRadioButton Prof_rdoNu;
    private javax.swing.JTextArea Prof_txtDiaChi;
    private javax.swing.JTextField Prof_txtEmail;
    private javax.swing.JTextField Prof_txtMaCV;
    private javax.swing.JTextField Prof_txtNgaySinh;
    private javax.swing.JTextField Prof_txtSDT;
    private javax.swing.JPanel btnAppliance;
    private javax.swing.JPanel btnClient;
    private javax.swing.JButton btnCreateClient;
    private javax.swing.JButton btnDeleteClient;
    private javax.swing.JButton btnDoiMK;
    private javax.swing.JPanel btnEmployee;
    private javax.swing.JPanel btnExit;
    private javax.swing.JPanel btnHome;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JPanel btnProduct;
    private javax.swing.JPanel btnProfile;
    private javax.swing.JPanel btnReceipt;
    private javax.swing.JButton btnRefreshClient;
    private javax.swing.JPanel btnSummary;
    private javax.swing.JButton btnThemNv;
    private javax.swing.JButton btnUpdateClient;
    private javax.swing.JButton btnUpdateProfile;
    private javax.swing.JButton btnXoaNV;
    private javax.swing.JButton btncapnhatNV;
    private javax.swing.JButton btndatlaiNv;
    private javax.swing.JButton btndatlai_SP;
    private javax.swing.JButton btnsua_SP;
    private javax.swing.JButton btnthem_SP;
    private javax.swing.JButton btnxoa_SP;
    private button.ButtonCustom buttonCustom1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cboTime;
    private javax.swing.JPanel container;
    private customjtable.CustomJTable customJTable1;
    private javax.swing.JLabel homeEmpName;
    private javax.swing.JLabel homeEmpPic;
    private javax.swing.JLabel homeLblSum;
    private javax.swing.JLabel homeProdName;
    private javax.swing.JLabel homeProdPic;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblhinhNV;
    private javax.swing.JLabel lblhinh_SP;
    private jpanelrounded.PanelRound panelRound1;
    private jpanelrounded.PanelRound panelRound6;
    private jpanelrounded.PanelRound panelRound7;
    private javax.swing.JPanel pnlShowPanel;
    private javax.swing.JPanel pnlTimHD;
    private javax.swing.JRadioButton rdoCvAdmin;
    private javax.swing.JRadioButton rdoCvBan;
    private javax.swing.JRadioButton rdoCvKho;
    private javax.swing.JRadioButton rdoNvNam;
    private javax.swing.JRadioButton rdoNvNu;
    private javax.swing.JPanel sideNav;
    private javax.swing.JLabel side_lblPic;
    private javax.swing.JLabel side_lblUser;
    private customjtable.CustomJTable tblCTHoaDon;
    private javax.swing.JTable tblClient;
    private customjtable.CustomJTable tblHoaDon;
    private javax.swing.JTable tblNL_SP;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDonVI_SP;
    private javax.swing.JTextField txtGT_SP;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtHoVaTenNV;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtManv;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSDTClient;
    private javax.swing.JTextField txtTKHo_SP;
    private javax.swing.JTextField txtTSP_KH;
    private javax.swing.JTextField txtTThieu_SP;
    private javax.swing.JTextField txtTenClient;
    private javax.swing.JTextField txtTimHD;
    private javax.swing.JTextArea txtdiachi_NV;
    private javax.swing.JTextField txtemail_NV;
    private javax.swing.JTextField txtmasp_KH;
    private javax.swing.JPasswordField txtmatkhauNV;
    private javax.swing.JTextField txtnamsinh_NV;
    private javax.swing.JTextField txtsodienthoai_NV;
    // End of variables declaration//GEN-END:variables

}
