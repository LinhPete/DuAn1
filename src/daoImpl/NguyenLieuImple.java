
package daoimpl;

import entities.NguyenLieu;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.XJdbc;
import daos.NguyenLieuDao;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NguyenLieuImple extends NguyenLieuDao{

    public List<NguyenLieu> selectBySQl(String sql, Object... agrs) {
        List<NguyenLieu> list = new ArrayList<>();
        try {
            ResultSet rs = (ResultSet) XJdbc.select(sql, agrs);
            while(rs.next()){
                NguyenLieu nl = new NguyenLieu();
                nl.setMaNL(rs.getString("MaNL"));
                nl.setTenNL(rs.getString("TenNL"));
                nl.setGiaTien(rs.getDouble("giaTien"));
                nl.setTonKho(rs.getInt("tonkho"));
                nl.setToiThieu(rs.getInt("toithieu"));
                nl.setDonVi(rs.getString("donVi"));
                nl.setHinh(rs.getString("hinh"));
                list.add(nl);
            }
        } catch (SQLException e){
           throw new RuntimeException(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NguyenLieuImple.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public List<NguyenLieu> selectAll() {
        String sql = " SELECT * FROM NGUYENLIEU ";
        return this.selectBySQl(sql);
    }

    @Override
    public NguyenLieu selectByID(String maNL) {
        String sql = "SELECT * FROM NGUYENLIEU WHERE MANL = ? ";
        List <NguyenLieu> list = this.selectBySQl(sql, maNL);
        return !list.isEmpty()? list.get(0):null;
    }

    public List<NguyenLieu> selectByName(String name) {
        String sql = " SELECT * FROM NGUYENLIEU WHERE TenNL like ? ";
        return this.selectBySQl(sql, "%"+name+"%");
    }

    public Integer checkNLTonKhoByMaNL(String maNL) {
        String sql = "SELECT TONKHO FROM NGUYENLIEU WHERE MANL = ? ";
        return XJdbc.getValue(sql, maNL);
    }

    public void updateTKNguyenLieu(Object [] values) throws SQLException, ClassNotFoundException {
        String sql = " UPDATE NGUYENLIEU SET TONKHO = TONKHO - ? WHERE MANL = ?";
        XJdbc.IUD(sql, values);
    }
}
