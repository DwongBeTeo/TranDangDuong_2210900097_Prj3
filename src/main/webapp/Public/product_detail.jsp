<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="User.MayMoc.Servlet.ProductServlet" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
    function addToCart(maMay) {
        console.log("maMay:", maMay); // Debug
        fetch('${pageContext.request.contextPath}/CartServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'action=add&maMay=' + maMay + '&soLuong=1'
        })
        .then(response => {
            console.log("Response status:", response.status); // Debug
            return response.text(); // Lấy text để kiểm tra
        })
        .then(text => {
            console.log("Response text:", text); // Debug
            const data = JSON.parse(text); // Phân tích JSON
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
        <div class="cart-icon">
            <a href="${pageContext.request.contextPath}/CartServlet" style="color: blue;">Giỏ hàng
                (<span id="cart-count">0</span>)
            </a>
        </div>
    </header>
    <main>
        <h2>${product.tenMay}</h2>
        <div class="product-detail">
            <img src="${pageContext.request.contextPath}/images/${product.anhMayMoc}" alt="${product.tenMay}" class="product-image">
            <p><strong>Giá bán:</strong> ${product.giaBan} VND</p>
            <p><strong>Tình trạng:</strong> ${product.tinhTrang}</p>
            <p><strong>Mô tả:</strong> ${product.moTa}</p>
            <p><strong>Ngày nhập:</strong> ${product.ngayNhap}</p>
            <button onclick="addToCart(${product != null ? product.maMay : -1})">Thêm vào giỏ hàng</button>
            <a href="${pageContext.request.contextPath}/products" class="back-link">Quay lại danh sách</a>
        </div>
    </main>
    <footer>
        <p>© 2025 Cửa hàng máy móc</p>
    </footer>
</body>
</html>