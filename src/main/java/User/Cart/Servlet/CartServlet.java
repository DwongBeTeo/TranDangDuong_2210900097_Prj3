package User.Cart.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

import com.example.constructor.CartItem;

import User.DAO.CartDAO;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer maGioHang = (Integer) session.getAttribute("maGioHang");

        if (maGioHang != null) {
            try {
                List<CartItem> cartItems = cartDAO.getCartItems(maGioHang);
                request.setAttribute("cartItems", cartItems);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Lỗi khi lấy giỏ hàng: " + e.getMessage());
            }
        }
        request.getRequestDispatcher("/Public/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Integer maGioHang = (Integer) session.getAttribute("maGioHang");
        response.setContentType("application/json");
        JSONObject json = new JSONObject();

        try {
            if ("add".equals(action)) {
                int maMay = Integer.parseInt(request.getParameter("maMay"));
                int soLuong = Integer.parseInt(request.getParameter("soLuong"));

                if (!cartDAO.checkStock(maMay, soLuong)) {
                    json.put("success", false);
                    json.put("message", "Số lượng tồn kho không đủ!");
                } else {
                    if (maGioHang == null) {
                        maGioHang = cartDAO.createCart(null); // Khách vãng lai
                        session.setAttribute("maGioHang", maGioHang);
                    }
                    cartDAO.addToCart(maGioHang, maMay, soLuong);

                    // Lấy danh sách giỏ hàng để đếm số lượng
                    List<CartItem> cartItems = cartDAO.getCartItems(maGioHang);
                    int totalItems = 0;
                    for (CartItem item : cartItems) {
                        totalItems += item.getSoLuong();
                    }

                    json.put("success", true);
                    json.put("totalItems", totalItems); // Trả về tổng số lượng sản phẩm
                }
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("message", "Lỗi server: " + e.getMessage());
        }
        response.getWriter().write(json.toString());
    }
}