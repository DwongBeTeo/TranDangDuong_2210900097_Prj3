package com.example.constructor;

public class CartItem {
    private int maMay;         // Mã máy từ MAY_MOC
    private String tenMay;     // Tên máy từ MAY_MOC
    private double giaBan;     // Giá bán từ MAY_MOC
    private int soLuong;       // Số lượng từ CHI_TIET_GIO_HANG

    // Constructor
    public CartItem(int maMay, String tenMay, double giaBan, int soLuong) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    // Getters và Setters
    public int getMaMay() { return maMay; }
    public void setMaMay(int maMay) { this.maMay = maMay; }
    public String getTenMay() { return tenMay; }
    public void setTenMay(String tenMay) { this.tenMay = tenMay; }
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}