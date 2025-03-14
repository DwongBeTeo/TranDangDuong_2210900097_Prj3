package User.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.constructor.CartItem;

import util.DBConnection;

public class CartDAO {

    public boolean checkStock(int maMay, int soLuong) throws SQLException {
        String sql = "SELECT SoLuongTon FROM MAY_MOC WHERE MaMay = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maMay);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("SoLuongTon") >= soLuong;
                }
            }
        }
        return false;
    }

    public int createCart(Integer maKH) throws SQLException {
        String sql = "INSERT INTO GIO_HANG (MaKH) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            if (maKH == null) {
                stmt.setNull(1, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(1, maKH);
            }
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Không thể tạo giỏ hàng!");
    }

    public void addToCart(int maGioHang, int maMay, int soLuong) throws SQLException {
        String sql = "INSERT INTO CHI_TIET_GIO_HANG (MaGioHang, MaMay, SoLuong) VALUES (?, ?, ?)"
                   + " ON DUPLICATE KEY UPDATE SoLuong = SoLuong + ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maGioHang);
            stmt.setInt(2, maMay);
            stmt.setInt(3, soLuong);
            stmt.setInt(4, soLuong);
            stmt.executeUpdate();
        }
    }
    
    //để xóa giỏ hàng sau khi thanh toán
    public void clearCart(int maGioHang) throws SQLException {
        String sql = "DELETE FROM CHI_TIET_GIO_HANG WHERE MaGioHang = ?; DELETE FROM GIO_HANG WHERE MaGioHang = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maGioHang);
            stmt.setInt(2, maGioHang);
            stmt.executeUpdate();
        }
    }

    public List<CartItem> getCartItems(int maGioHang) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT m.MaMay, m.TenMay, m.GiaBan, ct.SoLuong " +
                     "FROM CHI_TIET_GIO_HANG ct " +
                     "JOIN MAY_MOC m ON ct.MaMay = m.MaMay " +
                     "WHERE ct.MaGioHang = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maGioHang);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem(
                        rs.getInt("MaMay"),
                        rs.getString("TenMay"),
                        rs.getDouble("GiaBan"),
                        rs.getInt("SoLuong")
                    );
                    cartItems.add(item);
                }
            }
        }
        return cartItems;
    }
}
