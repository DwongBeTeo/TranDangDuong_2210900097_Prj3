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
 * Servlet implementation class SuaMayMocServlet
 */
@WebServlet("/SuaMayMocServlet")
public class SuaMayMocServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String maMayStr = request.getParameter("maMay");
        int maMay = 0;
        try {
            if (maMayStr != null && !maMayStr.isEmpty()) {
                maMay = Integer.parseInt(maMayStr);
            } else {
                request.setAttribute("error", "Lỗi: Mã máy không hợp lệ!");
                request.getRequestDispatcher("Views/MayMoc/error.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Lỗi: Mã máy phải là một số hợp lệ!");
            request.getRequestDispatcher("Views/MayMoc/error.jsp").forward(request, response);
            return;
        }

        MayMoc mayMoc = DAO.getMayMocById(maMay);
        if (mayMoc == null) {
            request.setAttribute("error", "Lỗi: Không tìm thấy thông tin máy móc!");
            request.getRequestDispatcher("Views/MayMoc/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("mayMoc", mayMoc);
        request.getRequestDispatcher("Views/MayMoc/sua-maymoc.jsp").forward(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int maMay = Integer.parseInt(request.getParameter("maMay"));
            String tenMay = request.getParameter("tenMay");
            String loaiMay = request.getParameter("loaiMay");
            String ngayNhap = request.getParameter("ngayNhap");
            String tinhTrang = request.getParameter("tinhTrang");
            String moTa = request.getParameter("moTa");
            String anhMayMoc = request.getParameter("anhMayMoc");
            String giaBanStr = request.getParameter("giaBan");
            Double giaBan = (giaBanStr != null && !giaBanStr.isEmpty()) ? Double.parseDouble(giaBanStr) : 0.0;
            String trangThaiHienThi = request.getParameter("trangThaiHienThi");
            int soLuongTon = Integer.parseInt(request.getParameter("soLuongTon"));

            MayMoc mayMoc = new MayMoc(maMay, tenMay, loaiMay, ngayNhap, tinhTrang, moTa, anhMayMoc, giaBan, trangThaiHienThi, soLuongTon);
            boolean success = DAO.updateMayMoc(mayMoc);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/Views/MayMoc/danh-sach-maymoc.jsp");
            } else {
                request.setAttribute("mayMoc", mayMoc);
                request.setAttribute("error", "Lỗi: Cập nhật không thành công!");
                request.getRequestDispatcher("Views/MayMoc/sua-maymoc.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Lỗi: Dữ liệu nhập vào không hợp lệ!");
            request.getRequestDispatcher("Views/MayMoc/sua-maymoc.jsp").forward(request, response);
        }
    }
}