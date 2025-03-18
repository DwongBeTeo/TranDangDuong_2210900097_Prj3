<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.constructor.CartItem" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
    <script>
    function removeFromCart(maMay) {
        if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?')) {
            fetch('${pageContext.request.contextPath}/CartServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'action=remove&maMay=' + maMay
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Đã xóa sản phẩm khỏi giỏ hàng!');
                    location.reload();
                    document.getElementById('cart-count').innerText = data.totalItems;
                } else {
                    alert('Lỗi: ' + data.message);
                }
            })
            .catch(error => console.error('Lỗi:', error));
        }
    }
    </script>
</head>
<body>
    <header>
        <h1>Cửa hàng máy móc</h1>
        <div class="cart-icon">
            <a href="${pageContext.request.contextPath}/CartServlet" style="color: blue;">Giỏ hàng (<span id="cart-count">0</span>)</a>
        </div>
        <div class="user-options" style="position: absolute; top: 10px; right: 100px;">
            <% if (session.getAttribute("maTK") == null) { %>
                <a href="${pageContext.request.contextPath}/Public/login.jsp">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/Public/register.jsp">Đăng ký</a>
            <% } else { %>
                <span>Xin chào, <%= session.getAttribute("username") %></span>
                <a href="${pageContext.request.contextPath}/LogoutServlet">Đăng xuất</a>
            <% } %>
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
                            <th>Hành động</th>
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
                                <td>
                                    <button onclick="removeFromCart(<%= item.getMaMay() %>)" class="remove-button">Xóa</button>
                                </td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="4"><strong>Tổng cộng:</strong></td>
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
                <p style="color: green;"><%= request.getAttribute("message") %></p>
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