package admin.order.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.constructor.Order;

import User.DAO.OrderDAO;

/**
 * Servlet implementation class AdminOrderServlet
 */
@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String vaiTro = (String) session.getAttribute("vaiTro");

        // Kiểm tra quyền admin
        if (vaiTro == null || !"Admin".equals(vaiTro)) {
            response.sendRedirect(request.getContextPath() + "/Public/login.jsp");
            return;
        }

        try {
            List<Order> orders = orderDAO.getAllOrders();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/Views/OrderHistory/order-history.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi lấy lịch sử đơn hàng: " + e.getMessage());
            request.getRequestDispatcher("/Views/OrderHistory/order-history.jsp").forward(request, response);
        }
    }
}