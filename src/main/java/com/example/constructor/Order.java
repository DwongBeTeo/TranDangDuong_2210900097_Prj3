package com.example.constructor;

import java.sql.Date;

public class Order {
	private int maDH;
    private String hoTenKH;
    private Date ngayDat;
    private double tongTien;
    private String trangThai;

    public Order(int maDH, String hoTenKH, Date ngayDat, double tongTien, String trangThai) {
        this.maDH = maDH;
        this.hoTenKH = hoTenKH;
        this.ngayDat = ngayDat;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public int getMaDH() { return maDH; }
    public String getHoTenKH() { return hoTenKH; }
    public Date getNgayDat() { return ngayDat; }
    public double getTongTien() { return tongTien; }
    public String getTrangThai() { return trangThai; }
}
