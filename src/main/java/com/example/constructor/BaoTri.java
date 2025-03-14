package com.example.constructor;

public class BaoTri {
	private int maBaoTri;
    private int maMay;
    private int maNV;
    private String ngayBaoTri;
    private double chiPhi;
    private String ghiChu;
    private String tenMay;     // Thêm thuộc tính
    private String hoTenNV;    // Thêm thuộc tính

    // Constructor
    public BaoTri(int maBaoTri, int maMay, int maNV, String ngayBaoTri, double chiPhi, String ghiChu) {
        this.maBaoTri = maBaoTri;
        this.maMay = maMay;
        this.maNV = maNV;
        this.ngayBaoTri = ngayBaoTri;
        this.chiPhi = chiPhi;
        this.ghiChu = ghiChu;
    }
    
    public BaoTri() {}
    
    public int getMaBaoTri() {return maBaoTri;}
    public void setMaBaoTri(int maBaoTri) {this.maBaoTri=maBaoTri;}
    
    public int getMaMay() {return maMay;}
    public void setMaMay(int maMay) {this.maMay=maMay;}
    

    public int getMaNV() {return maNV;}
    public void setMaNV(int maNV) {this.maNV=maNV;}
    
    public String getNgayBaoTri( ) {return ngayBaoTri;}
    public void setNgayBaoTri(String ngayBaoTri) {this.ngayBaoTri=ngayBaoTri;}
    
    public double getChiPhi() {return chiPhi;}
    public void setChiPhi(double chiPhi) {this.chiPhi=chiPhi;}

    public String getGhiChu() {return ghiChu;}
    public void setGhiChu(String ghiChu) {this.ghiChu=ghiChu;}
    
    
    public String getTenMay() {return tenMay;}
    public void setTenMay(String tenMay) { this.tenMay = tenMay; }
    
    public String getHoTenNV() {return hoTenNV;}
    public void setHoTenNV(String hoTenNV) { this.hoTenNV = hoTenNV; }
}
