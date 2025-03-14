package admin.nhanvien.servlet;

import java.io.IOException;

import com.example.DAO.NhanVienDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteNhanVienServlet
 */
@WebServlet("/DeleteNhanVienServlet")
public class DeleteNhanVienServlet extends HttpServlet {
    private NhanVienDAO nhanVienDAO;

    @Override
    public void init() throws ServletException {
        nhanVienDAO = new NhanVienDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy mã nhân viên từ tham số URL
            int maNV = Integer.parseInt(request.getParameter("maNV"));

            // Xóa nhân viên khỏi database
            nhanVienDAO.deleteNhanVien(maNV);

            // Chuyển hướng về danh sách
            response.sendRedirect("HienThiNhanVienServlet");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("HienThiNhanVienServlet?error=InvalidID");
        }
    }

    // Nếu muốn dùng POST để xóa (tùy chọn)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Gọi lại doGet để xử lý
    }
}