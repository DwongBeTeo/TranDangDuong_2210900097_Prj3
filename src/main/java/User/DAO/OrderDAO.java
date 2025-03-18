package User.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.constructor.CartItem;
import com.example.constructor.Order;

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
            String insertOrderSQL = "INSERT INTO DON_HANG (MaKH, NgayDat, TongTien, TrangThai) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertOrderSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, maKH);
            stmt.setDate(2, new Date(System.currentTimeMillis())); // Ngày hiện tại cho NgayDat
            stmt.setDouble(3, tongTien);
            stmt.setString(4, "Chờ xử lý"); // Dùng setString để tránh lỗi cú pháp
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
	
	
	//getAll để lây lịch sử mua hàng cho admin
	public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT dh.MaDH, kh.HoTen, dh.NgayDat, dh.TongTien, dh.TrangThai " +
                     "FROM DON_HANG dh " +
                     "JOIN KHACH_HANG kh ON dh.MaKH = kh.MaKH " +
                     "ORDER BY dh.NgayDat DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("MaDH"),
                    rs.getString("HoTen"),
                    rs.getDate("NgayDat"),
                    rs.getDouble("TongTien"),
                    rs.getString("TrangThai")
                );
                orders.add(order);
            }
        }
        return orders;
    }
	
	
	// Lấy đơn hàng theo MaKH cho khách hàng
    public List<Order> getOrdersByCustomer(int maKH) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT MaDH, NgayDat, TongTien, TrangThai " +
                     "FROM DON_HANG " +
                     "WHERE MaKH = ? " +
                     "ORDER BY NgayDat DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maKH);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                        rs.getInt("MaDH"),
                        null, // Không cần HoTen vì đây là đơn của chính khách hàng
                        rs.getDate("NgayDat"),
                        rs.getDouble("TongTien"),
                        rs.getString("TrangThai")
                    );
                    orders.add(order);
                }
            }
        }
        return orders;
    }
	
}