<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.constructor.Order" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch sử mua hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order.css">
    <link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
    <header>
        <h1>Quản lý lịch sử mua máy móc</h1>
        <div class="admin-options" style="position: absolute; top: 10px; right: 100px;">
            <a href="/TranDangDuong_2210900097_Prj3/index.jsp" class="btn btn-primary">Quay về trang chủ</a>
            <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-primary" >Đăng xuất</a>
        </div>
    </header>
    <main>
        <div class="admin-container">
            <h2>Lịch sử mua hàng của khách hàng</h2>
            <%
                List<Order> orders = (List<Order>) request.getAttribute("orders");
                if (orders != null && !orders.isEmpty()) {
            %>
                <table>
                    <thead>
                        <tr>
                            <th>Mã đơn hàng</th>
                            <th>Tên khách hàng</th>
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
                                <td><%= order.getHoTenKH() %></td>
                                <td><%= order.getNgayDat() %></td>
                                <td><%= order.getTongTien() %> VND</td>
                                <td><%= order.getTrangThai() %></td>
                                <td><a href="${pageContext.request.contextPath}/AdminOrderServlet?action=details&maDH=<%= order.getMaDH() %>">Xem chi tiết</a></td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            <%
                } else {
            %>
                <p>Chưa có đơn hàng nào.</p>
            <%
                }
            %>
            <% if (request.getAttribute("error") != null) { %>
                <p class="error"><%= request.getAttribute("error") %></p>
            <% } %>
        </div>
    </main>
    <footer>
        <p>© 2025 Cửa hàng máy móc</p>
    </footer>
</body>
</html>