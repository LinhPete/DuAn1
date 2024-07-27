
package controlls;

public interface KhachHangController {
    void initialize();
    void fillTableCustomer(String values , int maTK);
    void fillTableBills();
    void searchBillCustomer(String MaKH);
    void fillInfoCustomer();
    void insertCustomer();
    void updateInfoCustomer();
    void deleteCustomer(String makh);
    void resetForm();
    
    
}
