package admin.maymoc.servlet;

import java.io.IOException;

import com.example.DAO.DAO;
import com.example.constructor.MayMoc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ThemMayMocServlet
 */
@WebServlet("/ThemMayMocServlet")
public class ThemMayMocServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Views/MayMoc/them-maymoc.jsp").forward(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String tenMay = request.getParameter("tenMay");
            String loaiMay = request.getParameter("loaiMay");
            String ngayNhap = request.getParameter("ngayNhap");
            String tinhTrang = request.getParameter("tinhTrang");
            String moTa = request.getParameter("moTa");
            String anhMayMoc = request.getParameter("anhMayMoc");
            String giaBanStr = request.getParameter("giaBan");
            String trangThaiHienThi = request.getParameter("trangThaiHienThi");
            int soLuongTon = Integer.parseInt(request.getParameter("soLuongTon"));

            // Kiểm tra dữ liệu đầu vào
            if (tenMay == null || tenMay.isEmpty() || loaiMay == null || loaiMay.isEmpty() ||
                ngayNhap == null || ngayNhap.isEmpty() || tinhTrang == null || tinhTrang.isEmpty() ||
                anhMayMoc == null || anhMayMoc.isEmpty() || giaBanStr == null || giaBanStr.isEmpty() ||
                trangThaiHienThi == null || trangThaiHienThi.isEmpty()) {
                request.setAttribute("error", "Vui lòng điền đầy đủ thông tin!");
                request.getRequestDispatcher("/Views/MayMoc/them-maymoc.jsp").forward(request, response);
                return;
            }

            Double giaBan = Double.parseDouble(giaBanStr);
            

            MayMoc mayMoc = new MayMoc(0, tenMay, loaiMay, ngayNhap, tinhTrang, moTa, anhMayMoc, giaBan, trangThaiHienThi, soLuongTon);
            boolean success = DAO.addMayMoc(mayMoc);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/Views/MayMoc/danh-sach-maymoc.jsp?success=true");
            } else {
                request.setAttribute("error", "Không thể thêm máy móc!");
                request.setAttribute("mayMoc", mayMoc); // Giữ lại dữ liệu đã nhập
                request.getRequestDispatcher("/Views/MayMoc/them-maymoc.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Giá bán phải là một số hợp lệ!");
            request.getRequestDispatcher("/Views/MayMoc/them-maymoc.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/Views/MayMoc/them-maymoc.jsp").forward(request, response);
        }
    }
}