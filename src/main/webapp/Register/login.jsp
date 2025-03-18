<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <header>
        <h1 style="margin: 41px auto 0 auto; width: 19%;">Cửa hàng máy móc</h1>
    </header>
    <main>
        <div class="login-container">
            <h2>Đăng nhập</h2>
            <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                <input type="text" name="username" placeholder="Tên đăng nhập" required>
                <input type="password" name="password" placeholder="Mật khẩu" required>
                <button type="submit">Đăng nhập</button>
            </form>
            <% if (request.getAttribute("error") != null) { %>
                <p class="error"><%= request.getAttribute("error") %></p>
            <% } %>
            <p style="text-align: center;">Chưa có tài khoản? <a href="${pageContext.request.contextPath}/Register/register.jsp">Đăng ký</a></p>
        </div>
    </main>
    <footer>
    </footer>
</body>
</html>