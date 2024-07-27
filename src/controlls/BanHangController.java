
package controlls;

public interface BanHangController {
     void initialize();
     void fillProductDetail(String loaisp);
     void clearAll();
     void resetBill();
     void taoHoaDon();
     void inhoadon();
     void insertCTHD(String mahd);
     void eventClickAddProduct();
     Double checkKhuyenMai();
     void fillCustomerByID(String makh);
     void xuatBillKhachHang();
     void updateNguyenLieu(String maSP ,int heSo);
     void searchKhachHang();
}
