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
import com.example.constructor.MayMoc;
import com.example.constructor.NhanVien;

/**
 * Servlet implementation class AddBaoTriServlet
 */
@WebServlet("/addBaoTri")
public class AddBaoTriServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BaoTriDAO baoTriDAO;

    public void init() {
        baoTriDAO = new BaoTriDAO();
    }

    // Hiển thị form thêm mới
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy danh sách nhân viên và máy móc từ BaoTriDAO
        List<NhanVien> listNhanVien = baoTriDAO.getAllNhanVien();
        List<MayMoc> listMayMoc = baoTriDAO.getAllMayMoc();
        
        request.setAttribute("listNhanVien", listNhanVien);
        request.setAttribute("listMayMoc", listMayMoc);
        
        request.getRequestDispatcher("/Views/BaoTri/addBaoTri.jsp").forward(request, response);
    }

    // Xử lý khi submit form
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form (gửi mã)
            int maMay = Integer.parseInt(request.getParameter("maMay"));
            int maNV = Integer.parseInt(request.getParameter("maNV"));
            String ngayBaoTri = request.getParameter("ngayBaoTri");
            double chiPhi = Double.parseDouble(request.getParameter("chiPhi"));
            String ghiChu = request.getParameter("ghiChu");

            // Tạo đối tượng BaoTri
            BaoTri newBaoTri = new BaoTri(0, maMay, maNV, ngayBaoTri, chiPhi, ghiChu);
            
            // Thêm vào database
            baoTriDAO.addBaoTri(newBaoTri);
            
            // Chuyển hướng về danh sách
            response.sendRedirect("listBaoTri");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu nhập vào không hợp lệ!");
            doGet(request, response); // Hiển thị lại form
        }
    }
}