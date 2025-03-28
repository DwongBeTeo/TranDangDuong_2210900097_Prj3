<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Nhân Viên Mới</title>
    <style>
        .form-container {
            width: 50%;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input, .form-group select, .form-group textarea {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Thêm Nhân Viên Mới</h2>
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="AddNhanVienServlet" method="post">
            <div class="form-group">
                <label for="hoTen">Họ Tên:</label>
                <input type="text" id="hoTen" name="hoTen" required>
            </div>
            <div class="form-group">
                <label for="ngaySinh">Ngày Sinh (yyyy-MM-dd):</label>
                <input type="date" id="ngaySinh" name="ngaySinh" required>
            </div>
            <div class="form-group">
                <label for="gioiTinh">Giới Tính:</label>
                <select id="gioiTinh" name="gioiTinh" required>
                    <option value="Nam">Nam</option>
                    <option value="Nữ">Nữ</option>
                </select>
            </div>
            <div class="form-group">
                <label for="sdt">SĐT:</label>
                <input type="text" id="sdt" name="sdt" required>
            </div>
            <div class="form-group">
                <label for="diaChi">Địa Chỉ:</label>
                <textarea id="diaChi" name="diaChi" required></textarea>
            </div>
            <div class="form-group">
                <label for="chucVu">Chức Vụ:</label>
                <input type="text" id="chucVu" name="chucVu" required>
            </div>
            <div class="form-group">
                <label for="luong">Lương:</label>
                <input type="number" id="luong" name="luong" step="0.01" required>
            </div>
            <div class="form-group">
                <input type="submit" value="Thêm Nhân Viên">
                <a href="HienThiNhanVienServlet">Quay lại danh sách</a>
            </div>
        </form>
    </div>
</body>
</html>