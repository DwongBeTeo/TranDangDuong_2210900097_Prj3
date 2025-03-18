<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.example.DAO.DAO"%>
<%@ page import="com.example.constructor.MayMoc"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Danh Sách Máy Móc</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container mt-5">
		<h2 class="text-center mb-4">Danh Sách Máy Móc</h2>

		<a href="ThemMayMocServlet" class="btn btn-primary">Thêm Máy Móc</a>
		<a href="/TranDangDuong_2210900097_Prj3/index.jsp" class="btn btn-primary">Quay về trang chủ</a>
		<!-- Hiển thị danh sách máy móc -->
		<div class="row">
			<%
        List<MayMoc> mayMocList = DAO.getAllMayMoc();
        for (MayMoc mayMoc : mayMocList) {
        %>
			<div class="col-md-4 mb-3">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title"><%= mayMoc.getTenMay() %></h5>
						<p class="card-text">
							<strong>Mã Máy:</strong>
							<%= mayMoc.getMaMay() %></p>
						<p class="card-text">
							<strong>Loại Máy:</strong>
							<%= mayMoc.getLoaiMay() %></p>
						<p class="card-text">
							<strong>Ngày Nhập:</strong>
							<%= mayMoc.getNgayNhap() %></p>
						<p class="card-text">
							<strong>Tình Trạng:</strong>
							<%= mayMoc.getTinhTrang() %></p>
						<p class="card-text">
							<strong>Mô Tả:</strong>
							<%= mayMoc.getMoTa() %></p>
						<p class="card-text">
							<strong>Giá bán:</strong>
							<%= mayMoc.getGiaBan() %></p>
						<p class="card-text">
							<strong>Trạng thái hiển thị:</strong>
							<%= mayMoc.getTrangThaiHienThi() %></p>
						<p class="card-text">
							<strong>Số lượng tồn:</strong>
							<%= mayMoc.getSoLuongTon() %></p>

						<!-- Hiển thị ảnh máy móc -->
						<img
							src="<%= request.getContextPath() %>/images/<%= mayMoc.getAnhMayMoc() %>"
							width="100">

						<!-- Nút Sửa -->
						<a
							href="/TranDangDuong_2210900097_Prj3/SuaMayMocServlet?maMay=<%= mayMoc.getMaMay() %>">Sửa</a>

						<!-- Nút Xóa -->
						<form action="/TranDangDuong_2210900097_Prj3/XoaMayMocServlet" method="post"
							style="display: inline;">
							<input type="hidden" name="maMay" value="<%=mayMoc.getMaMay()%>">
							<button type="submit" class="btn btn-danger"
								onclick="return confirm('Bạn có chắc chắn muốn xóa?');">Xóa</button>
						</form>
					</div>
				</div>
			</div>
			<% } %>
		</div>
	</div>
</body>
</html>