<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
    <header>
		<h1 style="width: 19%; margin: 0 auto 0 auto;">Cửa hàng máy móc</h1>
	</header>
    <main>
        <div class="register-container">
            <h2>Đăng ký tài khoản</h2>
            <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
                <div class="form-group">
                    <label for="hoTen">Họ và tên:</label>
                    <input type="text" id="hoTen" name="hoTen" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="sdt">Số điện thoại:</label>
                    <input type="text" id="sdt" name="sdt" pattern="[0-9]{10}" title="Số điện thoại phải là 10 chữ số" required>
                </div>
                <div class="form-group">
                    <label for="diaChi">Địa chỉ:</label>
                    <input type="text" id="diaChi" name="diaChi" required>
                </div>
                <div class="form-group">
                    <label for="username">Tên đăng nhập:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <input type="submit" value="Đăng ký">
                </div>
            </form>
            <% if (request.getAttribute("error") != null) { %>
                <p class="error"><%= request.getAttribute("error") %></p>
            <% } %>
            <% if (request.getAttribute("message") != null) { %>
                <p class="success"><%= request.getAttribute("message") %></p>
            <% } %>
            <p style="text-align: center;">Đã có tài khoản? <a href="${pageContext.request.contextPath}/Register/login.jsp">Đăng nhập</a></p>
        </div>
    </main>
    <footer>
        <p>© 2025 Cửa hàng máy móc</p>
    </footer>
</body>
</html>