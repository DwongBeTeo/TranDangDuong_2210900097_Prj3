<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.constructor.Order" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch sử mua hàng của bạn</title>
    <%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> --%>
    <style>
        .customer-container {
            width: 90%;
            margin: 20px auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .error {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
    <header>
        <h1>Cửa hàng máy móc</h1>
        <div class="user-options" style="position: absolute; top: 10px; right: 100px;">
            <% if (session.getAttribute("maTK") == null) { %>
                <a href="${pageContext.request.contextPath}/Register/login.jsp">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/Register/register.jsp">Đăng ký</a>
            <% } else { %>
                <span>Xin chào, <%= session.getAttribute("username") %></span>
                <a href="${pageContext.request.contextPath}/CustomerOrderServlet">Lịch sử mua hàng</a>
                <a href="${pageContext.request.contextPath}/LogoutServlet">Đăng xuất</a>
            <% } %>
        </div>
    </header>
    <main>
        <div class="customer-container">
            <h2>Lịch sử mua hàng của bạn</h2>
            <%
                List<Order> orders = (List<Order>) request.getAttribute("orders");
                if (orders != null && !orders.isEmpty()) {
            %>
                <table>
                    <thead>
                        <tr>
                            <th>Mã đơn hàng</th>
                            <th>Ngày đặt</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                            <th>Chi tiết</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Order order : orders) {
                        %>
                            <tr>
                                <td><%= order.getMaDH() %></td>
                                <td><%= order.getNgayDat() %></td>
                                <td><%= order.getTongTien() %> VND</td>
                                <td><%= order.getTrangThai() %></td>
                                <td><a href="${pageContext.request.contextPath}/CustomerOrderServlet?action=details&maDH=<%= order.getMaDH() %>">Xem chi tiết</a></td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            <%
                } else {
            %>
                <p>Bạn chưa có đơn hàng nào.</p>
            <%
                }
            %>
            <% if (request.getAttribute("error") != null) { %>
                <p class="error"><%= request.getAttribute("error") %></p>
            <% } %>
            <p><a href="${pageContext.request.contextPath}/products">Tiếp tục mua sắm</a></p>
        </div>
    </main>
    <footer>
        <p>© 2025 Cửa hàng máy móc</p>
    </footer>
</body>
</html>