package admin.maymoc.servlet;

import java.io.IOException;

import com.example.DAO.DAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class XoaMayMocServlet
 */
@WebServlet("/XoaMayMocServlet")
public class XoaMayMocServlet extends HttpServlet {
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int maMay = Integer.parseInt(request.getParameter("maMay"));

        boolean success = DAO.deleteMayMoc(maMay);

        if (success) {
            response.sendRedirect("Views/MayMoc/danh-sach-maymoc.jsp?delete=success");
        } else {
            response.sendRedirect("Xóa thất bại");
        }
    }
}