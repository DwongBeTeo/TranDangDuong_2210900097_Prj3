<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="User.DAO.CartDAO" %>
<%@ page import="java.util.List" %>

<%@ page import="com.example.constructor.CartItem" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - Quản Lý</title>
    <style>
        .container {
            width: 50%;
            margin: 50px auto;
            text-align: center;
        }
        h1 {
            color: #333;
        }
        .menu {
            margin-top: 20px;
        }
        .menu a {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px;
            text-decoration: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 5px;
            font-size: 18px;
        }
        .menu a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Trang Chủ - Admin</h1>
        <div class="menu">
            <a href="HienThiNhanVienServlet">Quản Lý Nhân Viên</a>
            <a href="HienThiMayMocServlet">Quản Lý Máy Móc</a>
            <a href="listBaoTri">Quản Lý Danh Sách Bảo Trì</a>
            <a href="products">Sản phẩm cho khách hàng</a>
            
            <a href="${pageContext.request.contextPath}/CartServlet">Giỏ hàng (
                <span id="cart-count">
                    <%
                        HttpSession sess = request.getSession();
                        Integer maGioHang = (Integer) sess.getAttribute("maGioHang");
                        int totalItems = 0;
                        if (maGioHang != null) {
                            CartDAO cartDAO = new CartDAO();
                            try {
                                List<CartItem> cartItems = cartDAO.getCartItems(maGioHang);
                                for (CartItem item : cartItems) {
                                    totalItems += item.getSoLuong();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        out.print(totalItems);
                    %>
                </span>)
            </a>
        </div>
    </div>
</body>
</html>
</html>