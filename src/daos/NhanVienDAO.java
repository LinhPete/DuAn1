/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import entities.NhanVien;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import utils.XJdbc;

/**
 *
 * @author ndhlt
 */
public class NhanVienDAO extends DAOs<NhanVien, String> {

    @Override
    public int insert(NhanVien e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(NhanVien e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NhanVien> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public NhanVien selectByID(String key) {
        String sql = "select * from NhanVien where MaNV=?";
        return selectBySql(sql,key).isEmpty()?null:selectBySql(sql,key).get(0);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = (ResultSet) XJdbc.select(sql, args);
                while (rs.next()) {
                    NhanVien entity = new NhanVien();
                    entity.setMaNV(rs.getString(1));
                    entity.setMatKhau(rs.getString(2));
                    list.add(entity);
                }
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } finally {
                rs.getStatement().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    
}
