package admin.nhanvien.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.DAO.NhanVienDAO;
import com.example.constructor.NhanVien;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddNhanVienServlet
 */
@WebServlet("/AddNhanVienServlet")
public class AddNhanVienServlet extends HttpServlet {
    private NhanVienDAO nhanVienDAO;

    @Override
    public void init() throws ServletException {
        nhanVienDAO = new NhanVienDAO();
    }

    // Hiển thị form thêm mới
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Views/NhanVien/add-nhanvien.jsp").forward(request, response);
    }

    // Xử lý dữ liệu từ form
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Đảm bảo nhận đúng tiếng Việt

        try {
            // Lấy dữ liệu từ form
            String hoTen = request.getParameter("hoTen");
            String ngaySinhStr = request.getParameter("ngaySinh");
            String gioiTinh = request.getParameter("gioiTinh");
            String sdt = request.getParameter("sdt");
            String diaChi = request.getParameter("diaChi");
            String chucVu = request.getParameter("chucVu");
            String luongStr = request.getParameter("luong");

            // Chuyển đổi dữ liệu
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ngaySinh = sdf.parse(ngaySinhStr);
            BigDecimal luong = new BigDecimal(luongStr);

            // Tạo đối tượng NhanVien
            NhanVien nv = new NhanVien(0, hoTen, ngaySinh, gioiTinh, sdt, diaChi, chucVu, luong);

            // Thêm vào database
            nhanVienDAO.addNhanVien(nv);

            // Chuyển hướng về danh sách
            response.sendRedirect("HienThiNhanVienServlet");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi thêm nhân viên: " + e.getMessage());
            request.getRequestDispatcher("/add-nhanvien.jsp").forward(request, response);
        }
    }
}