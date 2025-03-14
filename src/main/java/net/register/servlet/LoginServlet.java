package net.register.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            UserInfo userInfo = authenticate(username, password);	
            if (userInfo != null) {
                // Đăng nhập thành công
                HttpSession session = request.getSession();
                session.setAttribute("maTK", userInfo.maTK);
                session.setAttribute("vaiTro", userInfo.vaiTro);
                if (userInfo.maKH != null) {
                    session.setAttribute("maKH", userInfo.maKH); // Lưu maKH cho khách hàng
                }
                session.setAttribute("username", username);

                // Phân quyền dựa trên VaiTro
                String redirectURL = (String) session.getAttribute("redirectURL");
                if (redirectURL != null) {
                    session.removeAttribute("redirectURL");
                    response.sendRedirect(redirectURL); // Quay lại trang trước
                } else {
                    switch (userInfo.vaiTro) {
                        case "Admin":
                            response.sendRedirect(request.getContextPath() + "/index.jsp");
                            break;
                        case "Khách hàng":
                            response.sendRedirect(request.getContextPath() + "/products");
                            break;
                        case "Nhân viên":
                            response.sendRedirect(request.getContextPath() + "/NhanVien/index.jsp"); // Tạm thời chưa dùng đến
                            break;
                        default:
                            response.sendRedirect(request.getContextPath() + "/Public/products.jsp");
                    }
                }
            } else {
                request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("/Register/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/Register/login.jsp").forward(request, response);
        }
    }

    // Class để lưu thông tin tài khoản
    private static class UserInfo {
        int maTK;
        String vaiTro;
        Integer maKH;

        UserInfo(int maTK, String vaiTro, Integer maKH) {
            this.maTK = maTK;
            this.vaiTro = vaiTro;
            this.maKH = maKH;
        }
    }

    private UserInfo authenticate(String username, String password) throws SQLException {
        String sql = "SELECT MaTK, VaiTro, MaKH FROM TAI_KHOAN WHERE TenDangNhap = ? AND MatKhau = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int maTK = rs.getInt("MaTK");
                    String vaiTro = rs.getString("VaiTro");
                    Integer maKH = rs.getObject("MaKH") != null ? rs.getInt("MaKH") : null;
                    return new UserInfo(maTK, vaiTro, maKH);
                }
            }
        }
        return null; // Không tìm thấy tài khoản
    }
}