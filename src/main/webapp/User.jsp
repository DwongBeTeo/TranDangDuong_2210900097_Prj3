<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ page import="java.util.List" %>
<%@ page import="User.DAO.CartDAO" %>
<%@ page import="com.example.constructor.CartItem" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
        <h1>Trang Chủ - User</h1>
        <div class="user-options">
            <% if (session.getAttribute("maTK") == null) { %>
                <a href="${pageContext.request.contextPath}/Register/login.jsp">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/Register/register.jsp">Đăng ký</a>
            <% } else { %>
                <span>Xin chào, <%= session.getAttribute("username") %></span>
                <a href="${pageContext.request.contextPath}/LogoutServlet">Đăng xuất</a>
            <% } %>
        </div>
        <div class="menu">
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
</html> --%>