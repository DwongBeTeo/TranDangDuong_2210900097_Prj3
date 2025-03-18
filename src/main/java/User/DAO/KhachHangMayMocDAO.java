package User.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.constructor.MayMoc;

import util.DBConnection;

public class KhachHangMayMocDAO {
	public List<MayMoc> getDisplayedProducts() {
        List<MayMoc> products = new ArrayList<>();
        String sql = "SELECT * FROM MAY_MOC WHERE TrangThaiHienThi = 'Hiển thị'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MayMoc mayMoc = new MayMoc();
                mayMoc.setMaMay(rs.getInt("MaMay"));
                mayMoc.setTenMay(rs.getString("TenMay"));
                mayMoc.setLoaiMay(rs.getString("LoaiMay"));
                mayMoc.setNgayNhap(rs.getString("NgayNhap"));
                mayMoc.setTinhTrang(rs.getString("TinhTrang"));
                mayMoc.setAnhMayMoc(rs.getString("AnhMayMoc"));
                mayMoc.setMoTa(rs.getString("MoTa"));
                mayMoc.setGiaBan(rs.getDouble("GiaBan"));
                mayMoc.setTrangThaiHienThi(rs.getString("TrangThaiHienThi"));
                products.add(mayMoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public MayMoc getProductById(int maMay) {
        MayMoc mayMoc = null;
        String sql = "SELECT * FROM MAY_MOC WHERE MaMay = ? AND TrangThaiHienThi = 'Hiển thị'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maMay);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mayMoc = new MayMoc();
                mayMoc.setMaMay(rs.getInt("MaMay"));
                mayMoc.setTenMay(rs.getString("TenMay"));
                mayMoc.setLoaiMay(rs.getString("LoaiMay"));
                mayMoc.setNgayNhap(rs.getString("NgayNhap"));
                mayMoc.setTinhTrang(rs.getString("TinhTrang"));
                mayMoc.setAnhMayMoc(rs.getString("AnhMayMoc"));
                mayMoc.setMoTa(rs.getString("MoTa"));
                mayMoc.setGiaBan(rs.getDouble("GiaBan"));
                mayMoc.setTrangThaiHienThi(rs.getString("TrangThaiHienThi"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mayMoc;
    }
    
 // Thêm phương thức tìm kiếm theo tên
    public List<MayMoc> searchProductsByName(String search) throws SQLException {
        List<MayMoc> products = new ArrayList<>();
        String sql = "SELECT * FROM MAY_MOC WHERE TenMay LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String ngayNhap = rs.getDate("NgayNhap") != null 
                        ? new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("NgayNhap")) 
                        : null;
                    MayMoc product = new MayMoc(
                        rs.getInt("MaMay"),
                        rs.getString("TenMay"),
                        rs.getString("LoaiMay"),
                        ngayNhap,
                        rs.getString("TinhTrang"),
                        rs.getString("MoTa"),
                        rs.getString("AnhMayMoc"),
                        rs.getDouble("GiaBan"),
                        rs.getString("TrangThaiHienThi"),
                        rs.getInt("SoLuongTon")
                    );
                    products.add(product);
                }
            }
        }
        return products;
    }
}
