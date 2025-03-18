<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - Quản Lý</title>
    <style>
		.container {
			width: 50%;
			margin: 50px auto;
			text-align: center;
		}
		
		h1 {
			color: #333;
		}
		
		.menu {
			margin-top: 20px;
		}
		
		.menu a {
			display: inline-block;
			padding: 10px 20px;
			margin: 10px;
			text-decoration: none;
			background-color: #4CAF50;
			color: white;
			border-radius: 5px;
			font-size: 18px;
		}
		
		.menu a:hover {
			background-color: #45a049;
		}
		
		.user-options {
			position: absolute;
			top: 10px;
			right: 100px;
		}
		
		.user-options a {
			margin-left: 10px;
			text-decoration: none;
			color: blue;
		}
		
		.user-options a:hover {
			text-decoration: underline;
		}
	</style>
</head>
<body>
    <div class="container">
        <h1>Trang Chủ - Admin</h1>
        <div class="user-options">
            <%
            if (session.getAttribute("maTK") == null) {
            %>
                <a href="${pageContext.request.contextPath}/Register/login.jsp">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/Register/register.jsp">Đăng ký</a>
            <% } else { %>
                <span>Xin chào, <%= session.getAttribute("username") %></span>
                <a href="${pageContext.request.contextPath}/LogoutServlet">Đăng xuất</a>
            <% } %>
        </div>
        <div class="menu">
            <a href="HienThiNhanVienServlet">Quản Lý Nhân Viên</a>
            <a href="HienThiMayMocServlet">Quản Lý Máy Móc</a>
            <a href="listBaoTri">Quản Lý Danh Sách Bảo Trì</a>
            <a href="AdminOrderServlet">Quản Lý Lịch Sử Mua Hàng</a>
            
        </div>
    </div>
</body>
</html>
</html>