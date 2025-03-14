<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ page import="com.example.constructor.DonHang" %> --%>
<!DOCTYPE html>
<html>
<head>
    <title>Xác nhận đơn hàng</title>
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
        <h2>Đơn hàng đã được đặt thành công!</h2>
        <p>Mã đơn hàng: ${maDH}</p>
        <p>Cảm ơn bạn đã mua sắm. Đơn hàng sẽ được xử lý sớm nhất!</p>
        <a href="${pageContext.request.contextPath}/products" class="back-link">Quay lại danh sách sản phẩm</a>
    </main>
    <footer>
        <p>© 2025 Cửa hàng máy móc</p>
    </footer>
</body>
</html>