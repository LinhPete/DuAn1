
package controlls;

public interface NguyenLieuController {
    void initialize();
    void fillTableNguyenLieu();
    void fillTableThanhPhan(String maNL, Integer soLuong);
    void setImageProduct();
    void searchNL();
    void deleteNL();
    void insertSanPham();
    void insertCTSanPham();
    void resetForm();
}
