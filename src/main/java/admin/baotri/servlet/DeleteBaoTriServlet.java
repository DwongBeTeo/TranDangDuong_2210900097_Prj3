package admin.baotri.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.example.DAO.BaoTriDAO;

/**
 * Servlet implementation class DeleteBaoTriServlet
 */
@WebServlet("/deleteBaoTri")
public class DeleteBaoTriServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BaoTriDAO baoTriDAO;

    public void init() {
        baoTriDAO = new BaoTriDAO();
    }

    // Xử lý xóa
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Lấy mã bảo trì từ tham số
            int maBaoTri = Integer.parseInt(request.getParameter("maBaoTri"));

            // Xóa bản ghi
            baoTriDAO.deleteBaoTri(maBaoTri); // Thêm phương thức này

            // Chuyển hướng về danh sách
            response.sendRedirect("listBaoTri");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Mã bảo trì không hợp lệ!");
            response.sendRedirect("listBaoTri");
        }
    }

    // Nếu cần xử lý POST (ví dụ: từ form xác nhận)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response); // Gọi doGet để xử lý
    }
}