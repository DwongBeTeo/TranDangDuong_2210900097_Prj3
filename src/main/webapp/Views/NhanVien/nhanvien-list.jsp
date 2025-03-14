<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.constructor.NhanVien" %>
<%@ page import="com.example.DAO.NhanVienDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Nhân Viên</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
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
    <link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
    <h1>Danh Sách Nhân Viên</h1>
    <a href="AddNhanVienServlet" class="btn btn-primary">Thêm Nhân Viên Mới</a>
    <a href="/TranDangDuong_2210900097_Prj3/" class="btn btn-primary">Quay về trang chủ</a>
    <% if (request.getParameter("error") != null) { %>
        <p class="error">Lỗi: <%= request.getParameter("error") %></p>
    <% } %>
    <table>
        <thead>
            <tr>
                <th>Mã NV</th>
                <th>Họ Tên</th>
                <th>Ngày Sinh</th>
                <th>Giới Tính</th>
                <th>SĐT</th>
                <th>Địa Chỉ</th>
                <th>Chức Vụ</th>
                <th>Lương</th>
                <th>Hành Động</th>
            </tr>
        </thead>
        <tbody>
            <%
                Object danhSachObj = request.getAttribute("danhSachNhanVien");
                if (danhSachObj instanceof List) {
                    List<NhanVien> danhSachNhanVien = (List<NhanVien>) danhSachObj;
                    for (NhanVien nv : danhSachNhanVien) {
            %>
                <tr>
                    <td><%= nv.getMaNV() %></td>
                    <td><%= nv.getHoTen() %></td>
                    <td><%= nv.getNgaySinh() %></td>
                    <td><%= nv.getGioiTinh() %></td>
                    <td><%= nv.getSdt() %></td>
                    <td><%= nv.getDiaChi() %></td>
                    <td><%= nv.getChucVu() %></td>
                    <td><%= nv.getLuong() %></td>
                    <td>
                        <a href="EditNhanVienServlet?maNV=<%= nv.getMaNV() %>">Sửa</a>
                        <a href="DeleteNhanVienServlet?maNV=<%= nv.getMaNV() %>" 
                           onclick="return confirm('Bạn có chắc muốn xóa nhân viên này?');">Xóa</a>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="9">Không có dữ liệu nhân viên</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>