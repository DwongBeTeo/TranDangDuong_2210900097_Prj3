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
 * Servlet implementation class EditNhanVienServlet
 */
@WebServlet("/EditNhanVienServlet")
public class EditNhanVienServlet extends HttpServlet {
    private NhanVienDAO nhanVienDAO;

    @Override
    public void init() throws ServletException {
        nhanVienDAO = new NhanVienDAO();
    }

    // Hiển thị form sửa
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int maNV = Integer.parseInt(request.getParameter("maNV"));
        NhanVien nv = nhanVienDAO.getNhanVienById(maNV);
        if (nv != null) {
            request.setAttribute("nhanVien", nv);
            request.getRequestDispatcher("Views/NhanVien/edit-nhanvien.jsp").forward(request, response);
        } else {
            response.sendRedirect("HienThiNhanVienServlet");
        }
    }

    // Xử lý cập nhật nhân viên
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // Lấy dữ liệu từ form
            int maNV = Integer.parseInt(request.getParameter("maNV"));
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
            NhanVien nv = new NhanVien(maNV, hoTen, ngaySinh, gioiTinh, sdt, diaChi, chucVu, luong);

            // Cập nhật vào database
            nhanVienDAO.updateNhanVien(nv);

            // Chuyển hướng về danh sách
            response.sendRedirect("HienThiNhanVienServlet");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi sửa nhân viên: " + e.getMessage());
            request.getRequestDispatcher("/edit-nhanvien.jsp").forward(request, response);
        }
    }
}
