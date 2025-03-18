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

import com.example.constructor.Order;

import User.DAO.OrderDAO;

/**
 * Servlet implementation class CustomerOrderServlet
 */
@WebServlet("/CustomerOrderServlet")
public class CustomerOrderServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer maKH = (Integer) session.getAttribute("maKH");

        // Kiểm tra đăng nhập
        if (maKH == null) {
            response.sendRedirect(request.getContextPath() + "/Register/login.jsp");
            return;
        }

        try {
            List<Order> orders = orderDAO.getOrdersByCustomer(maKH);
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/Public/customer-order-history.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi lấy lịch sử đơn hàng: " + e.getMessage());
            request.getRequestDispatcher("/Public/customer-order-history.jsp").forward(request, response);
        }
    }
}