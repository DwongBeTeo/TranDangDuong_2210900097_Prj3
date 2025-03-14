<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.constructor.MayMoc" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
                document.getElementById('cart-count').innerText = data.totalItems; // Cập nhật số lượng
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
		<div class="cart-icon">
			<a href="${pageContext.request.contextPath}/CartServlet"  style="color: blue;" >Giỏ hàng
				(<span id="cart-count">0</span>)
			</a>
		</div>
		<div class="user-options">
            <% if (session.getAttribute("maTK") == null) { %>
                <a href="${pageContext.request.contextPath}/Register/login.jsp">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/Register/register.jsp">Đăng ký</a>
            <% } else { %>
                <span>Xin chào, <%= session.getAttribute("username") %></span>
                <a href="${pageContext.request.contextPath}/LogoutServlet">Đăng xuất</a>
            <% } %>
        </div>
		
	</header>
    <main>
        <h2>Danh sách sản phẩm</h2>
        <div class="product-container">
            <%
                List<MayMoc> products = (List<MayMoc>) request.getAttribute("products");
                if (products != null && !products.isEmpty()) {
                    for (MayMoc product : products) {
            %>
                <div class="product-item">
                    <img src="${pageContext.request.contextPath}/images/<%= product.getAnhMayMoc() %>" alt="<%= product.getTenMay() %>" class="product-image">
                    <h3><%= product.getTenMay() %></h3>
                    <p>Loại máy: <%= product.getLoaiMay() %></p>
                    <p>Giá bán: <%= product.getGiaBan() %> VND</p>
                    <p><%= product.getMoTa() %></p>
                    <a href="${pageContext.request.contextPath}/products?action=detail&id=<%= product.getMaMay() %>" class="detail-link">Xem chi tiết</a>
                    <button onclick="addToCart(<%= product.getMaMay() %>)">Thêm vào giỏ hàng</button>
                </div>
            <%
                    }
                } else {
            %>
                <p>Không có sản phẩm nào để hiển thị.</p>
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