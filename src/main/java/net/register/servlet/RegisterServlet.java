package net.register.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String sdt = request.getParameter("sdt");
        String diaChi = request.getParameter("diaChi");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Kiểm tra xem username hoặc email đã tồn tại chưa
            String checkSQL = "SELECT COUNT(*) FROM TAI_KHOAN WHERE TenDangNhap = ? UNION SELECT COUNT(*) FROM KHACH_HANG WHERE Email = ?";
            stmt = conn.prepareStatement(checkSQL);
            stmt.setString(1, username);
            stmt.setString(2, email);
            rs = stmt.executeQuery();
            int usernameCount = 0;
            int emailCount = 0;
            if (rs.next()) {
                usernameCount = rs.getInt(1);
            }
            if (rs.next()) {
                emailCount = rs.getInt(1);
            }
            if (usernameCount > 0) {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
                request.getRequestDispatcher("/Public/register.jsp").forward(request, response);
                return;
            }
            if (emailCount > 0) {
                request.setAttribute("error", "Email đã được sử dụng!");
                request.getRequestDispatcher("/Public/register.jsp").forward(request, response);
                return;
            }

            // Thêm vào KHACH_HANG
            String insertCustomerSQL = "INSERT INTO KHACH_HANG (HoTen, SDT, DiaChi, Email) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertCustomerSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, hoTen);
            stmt.setString(2, sdt);
            stmt.setString(3, diaChi);
            stmt.setString(4, email);
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            int maKH;
            if (rs.next()) {
                maKH = rs.getInt(1);
            } else {
                throw new SQLException("Không thể tạo khách hàng!");
            }

            // Thêm vào TAI_KHOAN
            String insertAccountSQL = "INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, VaiTro, MaKH) VALUES (?, ?, 'Khách hàng', ?)";
            stmt = conn.prepareStatement(insertAccountSQL);
            stmt.setString(1, username);
            stmt.setString(2, password); // Lưu ý: Nên mã hóa mật khẩu trong thực tế
            stmt.setInt(3, maKH);
            stmt.executeUpdate();

            conn.commit();

            request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("/Register/login.jsp").forward(request, response);

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            request.setAttribute("error", "Lỗi đăng ký: " + e.getMessage());
            request.getRequestDispatcher("/Resgister/register.jsp").forward(request, response);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}