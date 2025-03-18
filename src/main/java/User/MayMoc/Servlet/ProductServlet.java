package User.MayMoc.Servlet;

import java.io.IOException;
import java.sql.SQLException;
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
    private KhachHangMayMocDAO khachHangMayMocDAO = new KhachHangMayMocDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String search = request.getParameter("search");

        try {
            if ("detail".equals(action)) {
                int maMay = Integer.parseInt(request.getParameter("id"));
                MayMoc product = khachHangMayMocDAO.getProductById(maMay);
                request.setAttribute("product", product);
                request.getRequestDispatcher("/Public/product-detail.jsp").forward(request, response);
            } else {
                List<MayMoc> products;
                if (search != null && !search.trim().isEmpty()) {
                    products = khachHangMayMocDAO.searchProductsByName(search); // Tìm kiếm theo tên
                } else {
                    products = khachHangMayMocDAO.getDisplayedProducts(); // Lấy tất cả nếu không tìm kiếm
                }
                request.setAttribute("products", products);
                request.getRequestDispatcher("/Public/products.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("/Public/products.jsp").forward(request, response);
        }
    }
}



