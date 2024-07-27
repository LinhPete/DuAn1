package daoImpl;

import utils.XJdbc;
import daos.HoaDonDAO;
import entities.HoaDon;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HoaDonImple implements HoaDonDAO {

    @Override
    public void insertHoaDon(HoaDon entity) {
        String sql = " INSERT INTO HOADON (MaKH ,MaNV ,NgayMua ,TONGTIEN ,GiamGia ,TRIGIA) values( ? ,? ,? ,? ,? ,? );";
        Object[] value = {
            entity.getMaKH(),
            entity.getMaNV(),
            entity.getNgayMua(),
            entity.getTongTien(),
            entity.getGiamGia(),
            entity.getTriGia()
        };

        try {
            XJdbc.IUD(sql, value);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(HoaDonImple.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String taoMaHoaDon() {
        String sql = " EXEC SP_TAOMAHD ";
        return XJdbc.getValue(sql);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... values) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = (ResultSet) XJdbc.select(sql, values);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD"));
                hd.setMaKH(rs.getString("maKH"));
                hd.setMaNV(rs.getString("manv"));
                hd.setNgayMua(rs.getDate("ngaymua"));
                hd.setTongTien(rs.getDouble("TongTien"));
                hd.setGiamGia(rs.getDouble("giamgia"));
                hd.setTriGia(rs.getDouble("trigia"));
                list.add(hd);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<HoaDon> selectAll() {
        String sql = "SELECT * FROM HOADON ";
        return this.selectBySql(sql);
    }

    @Override
    public List<HoaDon> selectByID(String maKH) {
        String sql = " SELECT * FROM HOADON WHERE MAKH = ? ";
        return this.selectBySql(sql, maKH);
    }

    @Override
    public Double checkCountBills(String makh) {
        String sql = "SELECT SUM(TRIGIA) FROM HOADON WHERE MAKH = ? AND (NgayMua > (SELECT NgayMua FROM HOADON WHERE GiamGia <> 0 AND MAKH = ?) AND NGAYMUA <= GETDATE())";
        return XJdbc.getValue(sql, makh,makh);
    }

    @Override
    public List<HoaDon> selectHDByDay() {
        String sql = " SELECT * FROM HOADON WHERE  CONVERT(DATE,NGAYMUA) BETWEEN DATEADD( DAY ,-1,CONVERT(date,getdate())) AND CONVERT(DATE,GETDATE()) ";
        return this.selectBySql(sql);
    }

    @Override
    public List<HoaDon> selectHDByMonth() {
        String sql = " SELECT * FROM HOADON WHERE  CONVERT(DATE,NGAYMUA) BETWEEN DATEADD( month ,-1,CONVERT(date,getdate())) AND CONVERT(DATE,GETDATE()) ";
        return this.selectBySql(sql);
    }

    @Override
    public List<HoaDon> selectHDByYear() {
        String sql = " SELECT * FROM HOADON WHERE  CONVERT(DATE,NGAYMUA) BETWEEN DATEADD( year ,-1,CONVERT(date,getdate())) AND CONVERT(DATE,GETDATE()) ";
        return this.selectBySql(sql);
    }

    @Override
    public List<HoaDon> selectByName(String name) {
        String sql = " SELECT * FROM KHACHHANG KH INNER JOIN HOADON HD ON KH.MaKH = HD.MAKH WHERE KH.HOVATEN LIKE ? ";
        return this.selectBySql(sql,"%"+name+"%");
    }

}
