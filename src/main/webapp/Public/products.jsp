<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.constructor.MayMoc" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ProductAndDetail.css">
    <script src="https://kit.fontawesome.com/1566d9eabd.js" crossorigin="anonymous"></script>
    <script>
    function addToCart(maMay) {
        fetch('${pageContext.request.contextPath}/CartServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'action=add&maMay=' + maMay + '&soLuong=1'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Đã thêm vào giỏ hàng!');
                document.getElementById('cart-count').innerText = data.totalItems;
            } else {
                alert('Lỗi: ' + data.message);
            }
        })
        .catch(error => console.error('Lỗi:', error));
    }
    </script>
</head>
<body>
    <header>
        <h1>Cửa hàng máy móc</h1>
        <div class="user-options">
            <% if (session.getAttribute("maTK") == null) { %>
                <a href="${pageContext.request.contextPath}/Register/login.jsp">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/Register/register.jsp">Đăng ký</a>
            <% } else { %>
                <span>Xin chào, <%= session.getAttribute("username") %></span>
                <a href="${pageContext.request.contextPath}/CartServlet" class="cart-link"><i class="fa-solid fa-cart-shopping"></i>Giỏ hàng<span id="cart-count"></span></a>
                <a href="${pageContext.request.contextPath}/CustomerOrderServlet" class="history-link">Lịch sử mua hàng</a>
                <a href="${pageContext.request.contextPath}/LogoutServlet">Đăng xuất</a>
            <% } %>
        </div>
    </header>
    <main>
        <h2>Danh sách sản phẩm</h2>
        <div class="search-container">
            <form action="${pageContext.request.contextPath}/products" method="get">
                <input type="text" name="search" placeholder="Nhập tên sản phẩm..." value="${param.search}">
                <button type="submit" class="search-button">Tìm kiếm</button>
            </form>
        </div>
        <div class="product-container">
            <%
                List<MayMoc> products = (List<MayMoc>) request.getAttribute("products");
                if (products != null && !products.isEmpty()) {
                    for (MayMoc product : products) {
            %>
                <div class="product-item">
                    <img src="${pageContext.request.contextPath}/images/<%= product.getAnhMayMoc() %>" alt="<%= product.getTenMay() %>" class="product-image">
                    <h3><%= product.getTenMay() %></h3>
                    <p><strong>Loại máy:</strong> <%= product.getLoaiMay() %></p>
                    <p><strong>Giá bán:</strong> <%= product.getGiaBan() %> VND</p>
                    <p><%= product.getMoTa() %></p>
                    <div class="product-actions">
                        <a href="${pageContext.request.contextPath}/products?action=detail&id=<%= product.getMaMay() %>" class="detail-link">Xem chi tiết</a>
                        <button onclick="addToCart(<%= product.getMaMay() %>)" class="add-to-cart-btn">Thêm vào giỏ hàng</button>
                    </div>
                </div>
            <%
                    }
                } else {
            %>
                <p>Không tìm thấy sản phẩm nào.</p>
            <%
                }
            %>
        </div>
    </main>
    <footer>
        <p>© 2025 Cửa hàng máy móc</p>
    </footer>
</body>
</html>