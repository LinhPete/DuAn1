
package daoimpl;

import daos.SanPhamDAO;
import entities.SanPham;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.XJdbc;

public class SanPhamImple implements SanPhamDAO{

    @Override
    public List<SanPham> selectBySQl(String sql, Object... agrs) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = (ResultSet) XJdbc.select(sql, agrs);
            while(rs.next()){
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString("MASP"));
                sp.setLoaiSP(rs.getString("LoaiSP"));
                sp.setTenSP(rs.getString("TENSANPHAM"));
                sp.setGiaTien(Double.valueOf(rs.getString("GiaTien")));
                sp.setHinh(rs.getString("hinh"));
                list.add(sp);
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException e){
           throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public SanPham getItemsByMaSP(String MaSP) {
        String sql = " SELECT * FROM SANPHAM WHERE MASP = ?";
        List <SanPham> list = this.selectBySQl(sql,MaSP);
        return !list.isEmpty()? list.get(0):null;
    }

    @Override
    public List<SanPham> getItems(String LOAISP) {
        String sql = " SELECT * FROM SANPHAM WHERE LOAISP LIKE ?";
        return this.selectBySQl(sql,"%"+LOAISP+"%");
    }

    @Override
    public void insertSanPham(SanPham Entity) {
        String sql = " INSERT INTO SANPHAM (LOAISP,TENSANPHAM,GIATIEN,HINH) VALUES (? ,? ,? ,?)";
        Object [] values ={
            Entity.getLoaiSP(),
            Entity.getTenSP(),
            Entity.getGiaTien(),
            Entity.getHinh()
        };
        try {
            XJdbc.IUD(sql, values);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SanPhamImple.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<SanPham> getItemsByID(String tensp) {
        String sql = " SELECT * FROM SANPHAM WHERE TENSP LIKE ? ";
        return this.selectBySQl(sql, "%"+tensp+"%");
    }

    @Override
    public String createMaSP() {
        String sql = " EXEC SP_TAOMASP ";
        return XJdbc.getValue(sql);
    }
}
