package User.MayMoc.Servlet;

import java.io.IOException;
import java.util.List;

import com.example.constructor.MayMoc;

import User.DAO.KhachHangMayMocDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private KhachHangMayMocDAO khachHangMayMocDAO; // Đổi tên biến

    @Override
    public void init() throws ServletException {
        khachHangMayMocDAO = new KhachHangMayMocDAO(); // Khởi tạo với tên mới
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("detail")) {
            try {
                int maMay = Integer.parseInt(request.getParameter("id"));
                MayMoc product = khachHangMayMocDAO.getProductById(maMay); // Dùng DAO mới
                request.setAttribute("product", product);
                request.getRequestDispatcher("/Public/product_detail.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm không hợp lệ");
            }
        } else {
            List<MayMoc> products = khachHangMayMocDAO.getDisplayedProducts(); // Dùng DAO mới
            request.setAttribute("products", products);
            request.getRequestDispatcher("/Public/products.jsp").forward(request, response);
        }
    }
    
    
}

