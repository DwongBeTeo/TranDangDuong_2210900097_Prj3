package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.constructor.BaoTri;
import com.example.constructor.MayMoc;
import com.example.constructor.NhanVien;

import util.DBConnection;

public class BaoTriDAO {
	public void addBaoTri(BaoTri bt) {
        String sql = "INSERT INTO BAO_TRI (MaMay, MaNV, NgayBaoTri, ChiPhi, GhiChu) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bt.getMaMay());
            ps.setInt(2, bt.getMaNV());
            ps.setString(3, bt.getNgayBaoTri());
            ps.setDouble(4, bt.getChiPhi());
            ps.setString(5, bt.getGhiChu());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	// Lấy tất cả nhân viên
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT MaNV, HoTen, NgaySinh, GioiTinh, SDT, DiaChi, ChucVu, Luong FROM NHAN_VIEN";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new NhanVien(
                    rs.getInt("MaNV"),
                    rs.getString("HoTen"),
                    rs.getDate("NgaySinh"),       
                    rs.getString("GioiTinh"),     
                    rs.getString("SDT"),         
                    rs.getString("DiaChi"),       
                    rs.getString("ChucVu"),         
                    rs.getBigDecimal("Luong")       
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

 // Lấy tất cả máy móc
    public List<MayMoc> getAllMayMoc() {
        List<MayMoc> list = new ArrayList<>();
        String sql = "SELECT MaMay, TenMay, LoaiMay, NgayNhap, TinhTrang, MoTa, AnhMayMoc, GiaBan, TrangThaiHienThi, SoLuongTon FROM MAY_MOC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new MayMoc(
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
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Sửa
 // Lấy bảo trì theo mã
    public BaoTri getBaoTriById(int maBaoTri) {
        String sql = "SELECT * FROM BAO_TRI WHERE MaBaoTri = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maBaoTri);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new BaoTri(
                    rs.getInt("MaBaoTri"),
                    rs.getInt("MaMay"),
                    rs.getInt("MaNV"),
                    rs.getString("NgayBaoTri"),
                    rs.getDouble("ChiPhi"),
                    rs.getString("GhiChu")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật bảo trì
    public void updateBaoTri(BaoTri bt) {
        String sql = "UPDATE BAO_TRI SET MaMay = ?, MaNV = ?, NgayBaoTri = ?, ChiPhi = ?, GhiChu = ? WHERE MaBaoTri = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bt.getMaMay());
            ps.setInt(2, bt.getMaNV());
            ps.setString(3, bt.getNgayBaoTri());
            ps.setDouble(4, bt.getChiPhi());
            ps.setString(5, bt.getGhiChu());
            ps.setInt(6, bt.getMaBaoTri());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa
    public void deleteBaoTri(int maBaoTri) {
        String sql = "DELETE FROM BAO_TRI WHERE MaBaoTri=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maBaoTri);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả
    public List<BaoTri> getAllBaoTri() {
        List<BaoTri> list = new ArrayList<>();
        String sql = "SELECT bt.MaBaoTri, bt.MaMay, bt.MaNV, bt.NgayBaoTri, bt.ChiPhi, bt.GhiChu, " +
                     "mm.TenMay, nv.HoTen " +
                     "FROM BAO_TRI bt " +
                     "JOIN MAY_MOC mm ON bt.MaMay = mm.MaMay " +
                     "JOIN NHAN_VIEN nv ON bt.MaNV = nv.MaNV";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BaoTri bt = new BaoTri(
                    rs.getInt("MaBaoTri"),
                    rs.getInt("MaMay"),
                    rs.getInt("MaNV"),
                    rs.getString("NgayBaoTri"),
                    rs.getDouble("ChiPhi"),
                    rs.getString("GhiChu")
                );
                bt.setTenMay(rs.getString("TenMay"));
                bt.setHoTenNV(rs.getString("HoTen"));
                list.add(bt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
