/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import entities.KhachHang;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.XJdbc;

/**
 * private String id; private String name; private float price; private Date
 * createDate; private String image;
 *
 * @author Computer
 */
public class KhachHangDAO extends DAOs<KhachHang, String> {

//    @Override
//    public void insert(KhachHang model) {
//        String sql = "INSERT INTO PRODUCT VALUES (? , ?, ?, ?, ?)";
//        XJdbc.update(sql,
//                model.getHoVaTen(),
//                model.getSDT(),
//                model.getMaKH());
//    }
//
//    @Override
//    public void update(KhachHang model) {
//        String sql = "UPDATE KhachHang SET HoVaTen = ?, SDT = ? WHERE MaKH = ?";
//        XJdbc.update(sql,
//                model.getHoVaTen(),
//                model.getSDT(),
//                model.getMaKH());
//    }
//
//    @Override
//    public void delete(String key) {
//        String sql = "DELETE FROM KhachHang WHERE MaKH=?";
//        XJdbc.update(sql, MaKH);
//    }
    @Override
    public List<KhachHang> selectAll() {
        String sql = "SELECT * FROM KhachHang";
        return selectBySql(sql);
    }

    @Override
    public List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = (ResultSet) XJdbc.select(sql, args);
                while (rs.next()) {
                    KhachHang entity = new KhachHang();
                    entity.setMaKH(rs.getString(1));
                    entity.setHoVaTen(rs.getString(2));
                    entity.setSDT(rs.getString(3));
                    list.add(entity);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                rs.getStatement().close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return list;
    }

    @Override
    public KhachHang selectByID(String key) {
        String sql = "SELECT * FROM KhachHang WHERE MaKH = ?";
        List<KhachHang> list = selectBySql(sql, key);
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    public int insert(KhachHang e) {
        int i = -1;
        String sql = "INSERT INTO KhachHang(HoVaTen, SDT) VALUES (? , ?)";
        try {
            i = XJdbc.IUD(
                    sql,
                    e.getHoVaTen(),
                    e.getSDT()
            );
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    @Override
    public int update(KhachHang e) {
        int i = -1;
        String sql = "UPDATE KhachHang SET HoVaTen = ?, SDT = ? WHERE MaKH = ?";
        try {
            i = XJdbc.IUD(sql,
                    e.getHoVaTen(),
                    e.getSDT(),
                    e.getMaKH());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    @Override
    public int delete(String key) {
        int i = -1;
        String sql = "DELETE FROM KhachHang WHERE MaKH=?";
        try {
            i = XJdbc.IUD(sql, key);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
}