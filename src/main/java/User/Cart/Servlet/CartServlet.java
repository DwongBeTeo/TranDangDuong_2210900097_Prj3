package User.Cart.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

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

        if (maGioHang == null) {
            request.setAttribute("message", "Giỏ hàng trống!");
            request.getRequestDispatcher("/Public/cart.jsp").forward(request, response);
            return;
        }

        try {
            List<CartItem> cartItems = cartDAO.getCartItems(maGioHang);
            request.setAttribute("cartItems", cartItems);
            request.getRequestDispatcher("/Public/cart.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi lấy giỏ hàng: " + e.getMessage());
            request.getRequestDispatcher("/Public/cart.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer maGioHang = (Integer) session.getAttribute("maGioHang");
        String action = request.getParameter("action");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            if ("add".equals(action)) {
                int maMay = Integer.parseInt(request.getParameter("maMay"));
                int soLuong = Integer.parseInt(request.getParameter("soLuong"));

                if (maGioHang == null) {
                    Integer maKH = (Integer) session.getAttribute("maKH");
                    if (maKH == null) {
                        out.print("{\"success\": false, \"message\": \"Vui lòng đăng nhập để thêm vào giỏ hàng!\"}");
                        out.flush();
                        return;
                    }
                    maGioHang = cartDAO.createCart(maKH);
                    session.setAttribute("maGioHang", maGioHang);
                }

                cartDAO.addToCart(maGioHang, maMay, soLuong);
                List<CartItem> cartItems = cartDAO.getCartItems(maGioHang);
                int totalItems = cartItems.stream().mapToInt(CartItem::getSoLuong).sum();

                out.print("{\"success\": true, \"totalItems\": " + totalItems + "}");
                out.flush();
            } else if ("remove".equals(action)) {
                int maMay = Integer.parseInt(request.getParameter("maMay"));

                if (maGioHang == null) {
                    out.print("{\"success\": false, \"message\": \"Giỏ hàng không tồn tại!\"}");
                    out.flush();
                    return;
                }

                cartDAO.removeFromCart(maGioHang, maMay); // Chỉ giảm 1 đơn vị
                List<CartItem> cartItems = cartDAO.getCartItems(maGioHang);
                int totalItems = cartItems.stream().mapToInt(CartItem::getSoLuong).sum();

                out.print("{\"success\": true, \"totalItems\": " + totalItems + "}");
                out.flush();
            } else {
                out.print("{\"success\": false, \"message\": \"Hành động không hợp lệ!\"}");
                out.flush();
            }
        } catch (SQLException e) {
            out.print("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Lỗi hệ thống: " + e.getMessage() + "\"}");
            out.flush();
        }
    }
}