package admin.nhanvien.servlet;

import java.io.IOException;
import java.util.List;

import com.example.DAO.NhanVienDAO;
import com.example.constructor.NhanVien;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HienThiNhanVienServlet
 */
@WebServlet("/HienThiNhanVienServlet")
public class HienThiNhanVienServlet extends HttpServlet {
    private NhanVienDAO nhanVienDAO;

    @Override
    public void init() throws ServletException {
        nhanVienDAO = new NhanVienDAO(); // Khởi tạo DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách nhân viên từ DAO
        List<NhanVien> danhSachNhanVien = nhanVienDAO.getAllNhanVien();

        // Đặt danh sách vào request attribute
        request.setAttribute("danhSachNhanVien", danhSachNhanVien);

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher("Views/NhanVien/nhanvien-list.jsp").forward(request, response);
    }
}