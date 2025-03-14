package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.constructor.MayMoc;

public class DAO {
	private static final String URL = "jdbc:mysql://localhost:3306/QuanLyMayMoc";
    private static final String USER = "root";
    private static final String PASSWORD = "duong19082004";
	//phương thức kết nối db


  //Phương thức layDanhSachMayMoc trong DAO
        public static List<MayMoc> getAllMayMoc() {
            List<MayMoc> mayMocList = new ArrayList<>();
            String sql = "SELECT * FROM MAY_MOC";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    MayMoc mayMoc = new MayMoc(
                        rs.getInt("MaMay"),
                        rs.getString("TenMay"),
                        rs.getString("LoaiMay"),
                        rs.getString("NgayNhap"),
                        rs.getString("TinhTrang"),
                        rs.getString("MoTa"),
                        rs.getString("AnhMayMoc"),
                        rs.getDouble("GiaBan"),
                        rs.getString("TrangThaiHienThi"),
                        rs.getInt("SoLuongTon")
                    );
                    mayMocList.add(mayMoc);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mayMocList;
        }


    //phương thức thêm mới máy móc
        public static boolean addMayMoc(MayMoc mayMoc) {
            String sql = "INSERT INTO MAY_MOC (TenMay, LoaiMay, NgayNhap, TinhTrang, MoTa, AnhMayMoc, GiaBan, TrangThaiHienThi, SoLuongTon) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, mayMoc.getTenMay());
                stmt.setString(2, mayMoc.getLoaiMay());
                stmt.setDate(3, java.sql.Date.valueOf(mayMoc.getNgayNhap())); // Sửa thành setDate
                stmt.setString(4, mayMoc.getTinhTrang());
                stmt.setString(5, mayMoc.getMoTa());
                stmt.setString(6, mayMoc.getAnhMayMoc());
                stmt.setDouble(7, mayMoc.getGiaBan());
                stmt.setString(8, mayMoc.getTrangThaiHienThi());
                stmt.setInt(9, mayMoc.getSoLuongTon());
                return stmt.executeUpdate() > 0;
            } catch (IllegalArgumentException e) {
                System.err.println("Lỗi định dạng ngày: " + e.getMessage());
                return false;
            } catch (SQLException e) {
                System.err.println("Lỗi cơ sở dữ liệu: " + e.getMessage());
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


    //lấy máy móc theo mã máy để sửa
    public static MayMoc getMayMocById(int maMay) {
        String sql = "SELECT * FROM MAY_MOC WHERE MaMay=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maMay);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MayMoc(
                    rs.getInt("MaMay"),
                    rs.getString("TenMay"),
                    rs.getString("LoaiMay"),
                    rs.getString("NgayNhap"),
                    rs.getString("TinhTrang"),
                    rs.getString("MoTa"),
                    rs.getString("AnhMayMoc"),
                    rs.getDouble("GiaBan"),
                    rs.getString("TrangThaiHienThi"),
                    rs.getInt("SoLuongTon")
                );
            }
        } catch (Exception e) {
            e.printStackTrace(); // Nên thay bằng logging framework như SLF4J trong ứng dụng thực tế
        }
        return null;
    }

    //cập nhật máy móc sau khi sửa
    public static boolean updateMayMoc(MayMoc mayMoc) {
        String sql = "UPDATE MAY_MOC SET TenMay=?, LoaiMay=?, NgayNhap=?, TinhTrang=?, MoTa=?, AnhMayMoc=?, GiaBan=?, TrangThaiHienThi=?, SoLuongTon=? WHERE MaMay=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mayMoc.getTenMay());
            stmt.setString(2, mayMoc.getLoaiMay());
            stmt.setDate(3, java.sql.Date.valueOf(mayMoc.getNgayNhap()));
            stmt.setString(4, mayMoc.getTinhTrang());
            stmt.setString(5, mayMoc.getMoTa());
            stmt.setString(6, mayMoc.getAnhMayMoc());
            stmt.setDouble(7, mayMoc.getGiaBan());
            stmt.setString(8, mayMoc.getTrangThaiHienThi());
            stmt.setInt(9, mayMoc.getSoLuongTon());
            stmt.setInt(10, mayMoc.getMaMay());
            return stmt.executeUpdate() > 0;
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi định dạng ngày: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //xóa máy móc
    public static boolean deleteMayMoc(int maMay) {
        String sql = "DELETE FROM MAY_MOC WHERE MaMay=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maMay);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}
