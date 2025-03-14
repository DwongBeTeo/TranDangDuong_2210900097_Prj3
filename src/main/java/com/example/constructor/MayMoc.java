package com.example.constructor;

public class MayMoc {
    private int maMay;
    private String tenMay;
    private String loaiMay;
    private String ngayNhap;
    private String tinhTrang;
    private String moTa;
    private String anhMayMoc;
    private double giaBan;            // Thêm GiaBan
    private String trangThaiHienThi;  // Thêm TrangThaiHienThi
    private int soLuongTon;

    public MayMoc(int maMay, String tenMay, String loaiMay, String ngayNhap, String tinhTrang, String moTa, String anhMayMoc, double giaBan, String trangThaiHienThi, int soLuongTon) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.loaiMay = loaiMay;
        this.ngayNhap = ngayNhap;
        this.tinhTrang = tinhTrang;
        this.moTa = moTa;
        this.anhMayMoc = anhMayMoc;
        this.giaBan = giaBan;
        this.trangThaiHienThi = trangThaiHienThi;
        this.soLuongTon = soLuongTon;
    }

    public MayMoc() {}

    public int getMaMay() { return maMay; }
    public void setMaMay(int maMay) { this.maMay = maMay; }

    public String getTenMay() { return tenMay; }
    public void setTenMay(String tenMay) { this.tenMay = tenMay; }

    public String getLoaiMay() { return loaiMay; }
    public void setLoaiMay(String loaiMay) { this.loaiMay = loaiMay; }

    public String getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(String ngayNhap) { this.ngayNhap = ngayNhap; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getAnhMayMoc() { return anhMayMoc; }
    public void setAnhMayMoc(String anhMayMoc) { this.anhMayMoc = anhMayMoc; }

    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }

    public String getTrangThaiHienThi() { return trangThaiHienThi; }
    public void setTrangThaiHienThi(String trangThaiHienThi) { this.trangThaiHienThi = trangThaiHienThi; }
    
    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }
}

