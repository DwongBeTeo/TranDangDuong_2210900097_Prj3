<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.constructor.NhanVien" %>
<%@ page import="com.example.constructor.MayMoc" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Mới Bảo Trì</title>
    <style>
        .error { color: red; }
        .form-container { 
            width: 50%; 
            margin: 20px auto; 
            padding: 20px; 
            border: 1px solid #ccc; 
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Thêm Mới Bảo Trì</h2>
        
        <%
            String error = (String) request.getAttribute("error");
            if (error != null && !error.isEmpty()) {
        %>
            <p class="error"><%= error %></p>
        <%
            }
        %>

        <form action="addBaoTri" method="post">
            <div>
                <label>Tên Máy Móc:</label><br>
                <select name="maMay" required>
                    <option value="">-- Chọn máy móc --</option>
                    <%
                        List<MayMoc> listMayMoc = (List<MayMoc>) request.getAttribute("listMayMoc");
                        if (listMayMoc != null) {
                            for (MayMoc mm : listMayMoc) {
                    %>
                        <option value="<%= mm.getMaMay() %>"><%= mm.getTenMay() %> (Mã: <%= mm.getMaMay() %>)</option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div>
                <label>Tên Nhân Viên:</label><br>
                <select name="maNV" required>
                    <option value="">-- Chọn nhân viên --</option>
                    <%
                        List<NhanVien> listNhanVien = (List<NhanVien>) request.getAttribute("listNhanVien");
                        if (listNhanVien != null) {
                            for (NhanVien nv : listNhanVien) {
                    %>
                        <option value="<%= nv.getMaNV() %>"><%= nv.getHoTen() %> (Mã: <%= nv.getMaNV() %>)</option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div>
                <label>Ngày Bảo Trì:</label><br>
                <input type="date" name="ngayBaoTri" required>
            </div>
            <div>
                <label>Chi Phí:</label><br>
                <input type="number" step="0.01" name="chiPhi" required>
            </div>
            <div>
                <label>Ghi Chú:</label><br>
                <textarea name="ghiChu" rows="4" cols="50"></textarea>
            </div>
            <div>
                <input type="submit" value="Thêm Mới">
                <input type="reset" value="Nhập Lại">
            </div>
        </form>
        <a href="listBaoTri">Quay lại danh sách</a>
    </div>
</body>
</html>