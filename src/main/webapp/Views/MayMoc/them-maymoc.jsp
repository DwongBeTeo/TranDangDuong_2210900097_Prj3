<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.constructor.MayMoc" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm Máy Móc</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Thêm Máy Móc</h2>
    
    <% 
        String error = (String) request.getAttribute("error");
        MayMoc mayMoc = (MayMoc) request.getAttribute("mayMoc");
        if (error != null) { 
    %>
        <p class="text-danger"><%= error %></p>
    <% } %>
    
    <% if ("true".equals(request.getParameter("success"))) { %>
        <p class="text-success">Thêm máy móc thành công!</p>
    <% } %>

    <form action="/TranDangDuong_2210900097_Prj3/ThemMayMocServlet" method="post">
        <div class="mb-3">
            <label class="form-label">Tên Máy:</label>
            <input type="text" name="tenMay" value="<%= mayMoc != null ? mayMoc.getTenMay() : "" %>" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Loại Máy:</label>
            <input type="text" name="loaiMay" value="<%= mayMoc != null ? mayMoc.getLoaiMay() : "" %>" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Ngày Nhập:</label>
            <input type="date" name="ngayNhap" value="<%= mayMoc != null ? mayMoc.getNgayNhap() : "" %>" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Tình Trạng:</label>
            <select name="tinhTrang" class="form-control" required>
                <option value="Hoạt động" <%= mayMoc != null && "Hoạt động".equals(mayMoc.getTinhTrang()) ? "selected" : "" %>>Hoạt động</option>
                <option value="Bảo trì" <%= mayMoc != null && "Bảo trì".equals(mayMoc.getTinhTrang()) ? "selected" : "" %>>Bảo trì</option>
                <option value="Hỏng" <%= mayMoc != null && "Hỏng".equals(mayMoc.getTinhTrang()) ? "selected" : "" %>>Hỏng</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Mô Tả:</label>
            <textarea name="moTa" class="form-control"><%= mayMoc != null ? mayMoc.getMoTa() : "" %></textarea>
        </div>

        <div class="mb-3">
            <label class="form-label">Giá Bán:</label>
            <input type="number" name="giaBan" value="<%= mayMoc != null ? mayMoc.getGiaBan() : "" %>" class="form-control" step="0.01" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Trạng thái hiển thị:</label>
            <select name="trangThaiHienThi" class="form-control" required>
                <option value="Hiển thị" <%= mayMoc != null && "Hiển thị".equals(mayMoc.getTrangThaiHienThi()) ? "selected" : "" %>>Hiển thị</option>
                <option value="Ẩn" <%= mayMoc != null && "Ẩn".equals(mayMoc.getTrangThaiHienThi()) ? "selected" : "" %>>Ẩn</option>
            </select>
        </div>
        
        <div class="mb-3">
            <label class="form-label">Số lượng tồn:</label>
            <input type="text" name="soLuongTon" value="<%= mayMoc != null ? mayMoc.getSoLuongTon() : "" %>" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Ảnh Máy Móc:</label>
            <input type="text" name="anhMayMoc" value="<%= mayMoc != null ? mayMoc.getAnhMayMoc() : "" %>" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary">Thêm Máy Móc</button>
        <a href="/TranDangDuong_2210900097_Prj3/Views/MayMoc/danh-sach-maymoc.jsp" class="btn btn-secondary">Hủy</a>
    </form>
</div>
</body>
</html>