package admin.maymoc.servlet;

import java.io.IOException;
import java.util.List;

import com.example.DAO.DAO;
import com.example.constructor.MayMoc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HienThiMayMocServlet
 */
@WebServlet("/HienThiMayMocServlet")
public class HienThiMayMocServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<MayMoc> danhSachMayMoc = DAO.getAllMayMoc();
        request.setAttribute("danhSachMayMoc", danhSachMayMoc);
        request.getRequestDispatcher("Views/MayMoc/danh-sach-maymoc.jsp").forward(request, response);
    }
}