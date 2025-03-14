package User.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
