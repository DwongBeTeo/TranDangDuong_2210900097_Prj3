package User.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.constructor.CartItem;

import util.DBConnection;

public class OrderDAO {
	public int createOrder(int maKH, List<CartItem> cartItems) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Tính tổng tiền
            double tongTien = 0;
            for (CartItem item : cartItems) {
                tongTien += item.getGiaBan() * item.getSoLuong();
            }

            // Tạo đơn hàng trong DON_HANG
            String insertOrderSQL = "INSERT INTO DON_HANG (MaKH, TongTien, TrangThai) VALUES (?, ?, 'Chờ xử lý')";
            stmt = conn.prepareStatement(insertOrderSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, maKH);
            stmt.setDouble(2, tongTien);
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            int maDH;
            if (rs.next()) {
                maDH = rs.getInt(1);
            } else {
                throw new SQLException("Không thể tạo đơn hàng!");
            }

            // Thêm chi tiết đơn hàng vào CHI_TIET_DON_HANG
            String insertDetailSQL = "INSERT INTO CHI_TIET_DON_HANG (MaDH, MaMay, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertDetailSQL);
            for (CartItem item : cartItems) {
                stmt.setInt(1, maDH);
                stmt.setInt(2, item.getMaMay());
                stmt.setInt(3, item.getSoLuong());
                stmt.setDouble(4, item.getGiaBan());
                stmt.addBatch();
            }
            stmt.executeBatch();

            // Cập nhật số lượng tồn kho trong MAY_MOC
            String updateStockSQL = "UPDATE MAY_MOC SET SoLuongTon = SoLuongTon - ? WHERE MaMay = ?";
            stmt = conn.prepareStatement(updateStockSQL);
            for (CartItem item : cartItems) {
                stmt.setInt(1, item.getSoLuong());
                stmt.setInt(2, item.getMaMay());
                stmt.addBatch();
            }
            stmt.executeBatch();

            conn.commit(); // Hoàn tất transaction
            return maDH;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback nếu có lỗi
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}