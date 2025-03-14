<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.constructor.BaoTri" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Bảo Trì</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"> 	
    <style>
        .table-container {
            width: 80%;
            margin: 20px auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
    </style>
    <script>
        function confirmDelete(maBaoTri) {
            if (confirm("Bạn có chắc chắn muốn xóa bản ghi này?")) {
                window.location.href = "deleteBaoTri?maBaoTri=" + maBaoTri;
            }
        }
    </script>
</head>
<body>
    <div class="table-container">
        <h2>Danh Sách Bảo Trì</h2>
        <a href="addBaoTri" class="btn btn-primary">Thêm mới bảo trì</a>
		<a href="/TranDangDuong_2210900097_Prj3/" class="btn btn-primary">Quay về trang chủ</a>
        <br><br>
        
        <table>
            <tr>
                <th>Mã Bảo Trì</th>
                <th>Tên Máy Móc</th>
                <th>Tên Nhân Viên</th>
                <th>Ngày Bảo Trì</th>
                <th>Chi Phí</th>
                <th>Ghi Chú</th>
                <th>Hành Động</th>
            </tr>
            <%
                List<BaoTri> listBaoTri = (List<BaoTri>) request.getAttribute("listBaoTri");
                if (listBaoTri != null && !listBaoTri.isEmpty()) {
                    for (BaoTri bt : listBaoTri) {
            %>
                <tr>
                    <td><%= bt.getMaBaoTri() %></td>
                    <td><%= bt.getTenMay() != null ? bt.getTenMay() : "Không xác định" %></td>
                    <td><%= bt.getHoTenNV() != null ? bt.getHoTenNV() : "Không xác định" %></td>
                    <td><%= bt.getNgayBaoTri() %></td>
                    <td><%= String.format("%.2f", bt.getChiPhi()) %></td>
                    <td><%= bt.getGhiChu() != null ? bt.getGhiChu() : "" %></td>
                    <td>
                        <a href="editBaoTri?maBaoTri=<%= bt.getMaBaoTri() %>">Sửa</a>
                        <a href="javascript:void(0)" onclick="confirmDelete(<%= bt.getMaBaoTri() %>)">Xóa</a>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="7">Không có dữ liệu bảo trì nào.</td>
                </tr>
            <%
                }
            %>
        </table>
    </div>
</body>
</html>