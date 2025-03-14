package com.example.constructor;

import java.math.BigDecimal;
import java.util.Date;

public class NhanVien {
    private int maNV;
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh; // Sử dụng String thay vì enum trực tiếp
    private String sdt;
    private String diaChi;
    private String chucVu;
    private BigDecimal luong;

    // Constructor mặc định
    public NhanVien() {
    }

    // Constructor đầy đủ tham số
    public NhanVien(int maNV, String hoTen, Date ngaySinh, String gioiTinh,
                   String sdt, String diaChi, String chucVu, BigDecimal luong) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.luong = luong;
    }

    // Getter và Setter
    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        // Có thể thêm kiểm tra giá trị hợp lệ
        if (gioiTinh != null && (gioiTinh.equals("Nam") || gioiTinh.equals("Nữ"))) {
            this.gioiTinh = gioiTinh;
        }
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public BigDecimal getLuong() {
        return luong;
    }

    public void setLuong(BigDecimal luong) {
        this.luong = luong;
    }

    // Phương thức toString để hiển thị thông tin
    @Override
    public String toString() {
        return "NhanVien{" +
                "maNV=" + maNV +
                ", hoTen='" + hoTen + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", sdt='" + sdt + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", luong=" + luong +
                '}';
    }
}
