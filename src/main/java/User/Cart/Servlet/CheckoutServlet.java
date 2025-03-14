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

import com.example.constructor.CartItem;

import User.DAO.CartDAO;
import User.DAO.OrderDAO;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private CartDAO cartDAO = new CartDAO();
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String vaiTro = (String) session.getAttribute("vaiTro");
        Integer maKH = (Integer) session.getAttribute("maKH");
        Integer maGioHang = (Integer) session.getAttribute("maGioHang");

        // Kiểm tra đăng nhập và vai trò
        if (vaiTro == null || !vaiTro.equals("Khách hàng")) {
            session.setAttribute("redirectURL", request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Kiểm tra maKH
        if (maKH == null) {
            request.setAttribute("error", "Tài khoản khách hàng không hợp lệ!");
            request.getRequestDispatcher("/Public/cart.jsp").forward(request, response);
            return;
        }

        // Kiểm tra giỏ hàng
        if (maGioHang == null) {
            request.setAttribute("message", "Giỏ hàng không tồn tại!");
            request.getRequestDispatcher("/Public/cart.jsp").forward(request, response);
            return;
        }

        try {
            List<CartItem> cartItems = cartDAO.getCartItems(maGioHang);
            if (cartItems.isEmpty()) {
                request.setAttribute("message", "Giỏ hàng trống, không thể thanh toán!");
                request.getRequestDispatcher("/Public/cart.jsp").forward(request, response);
                return;
            }

            // Tạo đơn hàng
            int maDH = orderDAO.createOrder(maKH, cartItems);

            // Xóa giỏ hàng
            cartDAO.clearCart(maGioHang);
            session.removeAttribute("maGioHang");

            // Chuyển đến trang xác nhận
            request.setAttribute("maDH", maDH);
            request.getRequestDispatcher("/Public/order-confirmation.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi thanh toán: " + e.getMessage());
            request.getRequestDispatcher("/Public/cart.jsp").forward(request, response);
        }
    }
}