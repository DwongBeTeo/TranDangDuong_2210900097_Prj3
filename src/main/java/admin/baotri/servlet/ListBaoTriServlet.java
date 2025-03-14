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

/**
 * Servlet implementation class ListBaoTriServlet
 */
@WebServlet("/listBaoTri")
public class ListBaoTriServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BaoTriDAO baoTriDAO;

    public void init() {
        baoTriDAO = new BaoTriDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy danh sách bảo trì từ DAO
        List<BaoTri> listBaoTri = baoTriDAO.getAllBaoTri();
        
        // Đặt danh sách vào request attribute
        request.setAttribute("listBaoTri", listBaoTri);
        
        // Chuyển hướng đến JSP
        request.getRequestDispatcher("/Views/BaoTri/listBaoTri.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response); // Nếu có POST, xử lý giống GET
    }
}