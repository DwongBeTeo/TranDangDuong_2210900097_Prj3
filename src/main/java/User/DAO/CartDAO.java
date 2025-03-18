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

 // Sửa phương thức removeFromCart
    public void removeFromCart(int maGioHang, int maMay) throws SQLException {
        String selectSQL = "SELECT SoLuong FROM CHI_TIET_GIO_HANG WHERE MaGioHang = ? AND MaMay = ?";
        String updateSQL = "UPDATE CHI_TIET_GIO_HANG SET SoLuong = SoLuong - 1 WHERE MaGioHang = ? AND MaMay = ?";
        String deleteSQL = "DELETE FROM CHI_TIET_GIO_HANG WHERE MaGioHang = ? AND MaMay = ?";

        try (Connection conn = DBConnection.getConnection()) {
            // Kiểm tra số lượng hiện tại
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
                selectStmt.setInt(1, maGioHang);
                selectStmt.setInt(2, maMay);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    int currentQuantity = rs.getInt("SoLuong");
                    if (currentQuantity > 1) {
                        // Giảm số lượng đi 1 nếu > 1
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                            updateStmt.setInt(1, maGioHang);
                            updateStmt.setInt(2, maMay);
                            updateStmt.executeUpdate();
                        }
                    } else if(currentQuantity == 1) {
                        // Xóa dòng nếu số lượng = 1
                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
                            deleteStmt.setInt(1, maGioHang);
                            deleteStmt.setInt(2, maMay);
                            deleteStmt.executeUpdate();
                        }
                    }
                }
            }
        }
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
        // Kiểm tra tồn kho trước khi thêm
        if (!checkStock(maMay, soLuong)) {
            throw new SQLException("Sản phẩm " + maMay + " không đủ tồn kho!");
        }
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

    public void clearCart(int maGioHang) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtDetails = null;
        PreparedStatement stmtCart = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String deleteDetailsSQL = "DELETE FROM CHI_TIET_GIO_HANG WHERE MaGioHang = ?";
            stmtDetails = conn.prepareStatement(deleteDetailsSQL);
            stmtDetails.setInt(1, maGioHang);
            stmtDetails.executeUpdate();

            String deleteCartSQL = "DELETE FROM GIO_HANG WHERE MaGioHang = ?";
            stmtCart = conn.prepareStatement(deleteCartSQL);
            stmtCart.setInt(1, maGioHang);
            stmtCart.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (stmtDetails != null) stmtDetails.close();
            if (stmtCart != null) stmtCart.close();
            if (conn != null) conn.close();
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