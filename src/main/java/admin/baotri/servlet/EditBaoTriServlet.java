package admin.baotri.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.example.DAO.BaoTriDAO;
import com.example.constructor.BaoTri;
import com.example.constructor.MayMoc;
import com.example.constructor.NhanVien;

/**
 * Servlet implementation class EditBaoTriServlet
 */
@WebServlet("/editBaoTri")
public class EditBaoTriServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BaoTriDAO baoTriDAO;

    public void init() {
        baoTriDAO = new BaoTriDAO();
    }

    // Hiển thị form chỉnh sửa
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Lấy mã bảo trì từ tham số
            int maBaoTri = Integer.parseInt(request.getParameter("maBaoTri"));
            
            // Lấy thông tin bảo trì hiện tại
            BaoTri baoTri = baoTriDAO.getBaoTriById(maBaoTri); // Thêm phương thức này
            if (baoTri == null) {
                request.setAttribute("error", "Không tìm thấy bản ghi bảo trì!");
                response.sendRedirect("listBaoTri");
                return;
            }

            // Lấy danh sách nhân viên và máy móc
            List<NhanVien> listNhanVien = baoTriDAO.getAllNhanVien();
            List<MayMoc> listMayMoc = baoTriDAO.getAllMayMoc();

            // Truyền dữ liệu vào request
            request.setAttribute("baoTri", baoTri);
            request.setAttribute("listNhanVien", listNhanVien);
            request.setAttribute("listMayMoc", listMayMoc);

            // Chuyển đến JSP
            request.getRequestDispatcher("/Views/BaoTri/editBaoTri.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Mã bảo trì không hợp lệ!");
            response.sendRedirect("listBaoTri");
        }
    }

    // Xử lý cập nhật khi submit form
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            int maBaoTri = Integer.parseInt(request.getParameter("maBaoTri"));
            int maMay = Integer.parseInt(request.getParameter("maMay"));
            int maNV = Integer.parseInt(request.getParameter("maNV"));
            String ngayBaoTri = request.getParameter("ngayBaoTri");
            double chiPhi = Double.parseDouble(request.getParameter("chiPhi"));
            String ghiChu = request.getParameter("ghiChu");

            // Tạo đối tượng BaoTri với thông tin mới
            BaoTri baoTri = new BaoTri(maBaoTri, maMay, maNV, ngayBaoTri, chiPhi, ghiChu);

            // Cập nhật vào database
            baoTriDAO.updateBaoTri(baoTri); // Thêm phương thức này

            // Chuyển hướng về danh sách
            response.sendRedirect("listBaoTri");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu nhập vào không hợp lệ!");
            doGet(request, response); // Hiển thị lại form nếu lỗi
        }
    }
}