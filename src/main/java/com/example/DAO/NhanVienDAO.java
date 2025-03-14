package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.constructor.NhanVien;

public class NhanVienDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/QuanLyMayMoc"; // Thay your_database_name bằng tên database của bạn
    private static final String USER = "root"; // Thay bằng username của bạn
    private static final String PASSWORD = "duong19082004"; // Thay bằng password của bạn

    // Phương thức lấy kết nối
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

 // Lấy tất cả nhân viên
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NHAN_VIEN";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getInt("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setSdt(rs.getString("SDT"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setLuong(rs.getBigDecimal("Luong"));
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

 // Thêm nhân viên mới
    public void addNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NHAN_VIEN (HoTen, NgaySinh, GioiTinh, SDT, DiaChi, ChucVu, Luong) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nv.getHoTen());
            pstmt.setDate(2, new java.sql.Date(nv.getNgaySinh().getTime()));
            pstmt.setString(3, nv.getGioiTinh());
            pstmt.setString(4, nv.getSdt());
            pstmt.setString(5, nv.getDiaChi());
            pstmt.setString(6, nv.getChucVu());
            pstmt.setBigDecimal(7, nv.getLuong());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // Cập nhật thông tin nhân viên
    public void updateNhanVien(NhanVien nv) {
        String sql = "UPDATE NHAN_VIEN SET HoTen=?, NgaySinh=?, GioiTinh=?, SDT=?, DiaChi=?, ChucVu=?, Luong=? WHERE MaNV=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nv.getHoTen());
            pstmt.setDate(2, new java.sql.Date(nv.getNgaySinh().getTime()));
            pstmt.setString(3, nv.getGioiTinh());
            pstmt.setString(4, nv.getSdt());
            pstmt.setString(5, nv.getDiaChi());
            pstmt.setString(6, nv.getChucVu());
            pstmt.setBigDecimal(7, nv.getLuong());
            pstmt.setInt(8, nv.getMaNV());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // Xóa nhân viên
    public void deleteNhanVien(int maNV) {
        String sql = "DELETE FROM NHAN_VIEN WHERE MaNV=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maNV);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


 // Lấy nhân viên theo MaNV
    public NhanVien getNhanVienById(int maNV) {
        String sql = "SELECT * FROM NHAN_VIEN WHERE MaNV=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maNV);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    NhanVien nv = new NhanVien();
                    nv.setMaNV(rs.getInt("MaNV"));
                    nv.setHoTen(rs.getString("HoTen"));
                    nv.setNgaySinh(rs.getDate("NgaySinh"));
                    nv.setGioiTinh(rs.getString("GioiTinh"));
                    nv.setSdt(rs.getString("SDT"));
                    nv.setDiaChi(rs.getString("DiaChi"));
                    nv.setChucVu(rs.getString("ChucVu"));
                    nv.setLuong(rs.getBigDecimal("Luong"));
                    return nv;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
