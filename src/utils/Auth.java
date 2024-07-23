/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entities.NhanVien;

/**
 *
 * @author ndhlt
 */
public class Auth {
    private static NhanVien user;
    // Bỏ tên chức vụ vào đây
    private static String ADMIN = "";
    private static String BAN_HANG = "";
    private static String KHO = "";

    public static NhanVien getUser() {
        return user;
    }

    public static void setUser(NhanVien user) {
        Auth.user = user;
    }
    
    public static void clear(){
        Auth.user = null;
    }
    
    public static boolean isLogin(){
        return Auth.user != null;
    }
    
    public static boolean isAdmin(){
        return isLogin() && Auth.user.getChucVu().equals(ADMIN);
    }
    
    public static boolean isBanHang(){
        return isLogin() && Auth.user.getChucVu().equals(BAN_HANG);
    }
    
    public static boolean isKho(){
        return isLogin() && Auth.user.getChucVu().equals(KHO);
    }
}