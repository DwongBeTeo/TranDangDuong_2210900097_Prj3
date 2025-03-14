<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.constructor.CartItem" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
    <header>
        <h1>Cửa hàng máy móc</h1>
        <div class="cart-icon">
            <a href="${pageContext.request.contextPath}/CartServlet" style="color: blue;">Giỏ hàng (<span id="cart-count">0</span>)</a>
        </div>
    </header>
    <main>
        <h2>Giỏ hàng của bạn</h2>
        <div class="cart-container">
            <%
                List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
                if (cartItems != null && !cartItems.isEmpty()) {
                    double total = 0;
            %>
                <table>
                    <thead>
                        <tr>
                            <th>Tên máy</th>
                            <th>Giá bán</th>
                            <th>Số lượng</th>
                            <th>Tổng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (CartItem item : cartItems) {
                                double itemTotal = item.getGiaBan() * item.getSoLuong();
                                total += itemTotal;
                        %>
                            <tr>
                                <td><%= item.getTenMay() %></td>
                                <td><%= item.getGiaBan() %> VND</td>
                                <td><%= item.getSoLuong() %></td>
                                <td><%= itemTotal %> VND</td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="3"><strong>Tổng cộng:</strong></td>
                            <td><%= total %> VND</td>
                        </tr>
                    </tfoot>
                </table>
                <a href="${pageContext.request.contextPath}/CheckoutServlet" class="checkout-button">Thanh toán</a>
                <a href="${pageContext.request.contextPath}/products" class="back-link">Tiếp tục mua sắm</a>
            <%
                } else {
            %>
                <p>Giỏ hàng của bạn đang trống.</p>
                <a href="${pageContext.request.contextPath}/products" class="back-link">Quay lại danh sách sản phẩm</a>
            <%
                }
            %>
            <% if (request.getAttribute("message") != null) { %>
                <p style="color: red;"><%= request.getAttribute("message") %></p>
            <% } %>
            <% if (request.getAttribute("error") != null) { %>
                <p style="color: red;"><%= request.getAttribute("error") %></p>
            <% } %>
        </div>
    </main>
    <footer>
        <p>© 2025 Cửa hàng máy móc</p>
    </footer>
</body>
</html>