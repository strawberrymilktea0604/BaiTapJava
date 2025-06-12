package qlsach;

public class Sach {
    private String maSach;
    private String tenSach;
    private int namXB;
    private double gia;
    private int soLuong;
    
    public Sach() {}
    
    public Sach(String maSach, String tenSach, int namXB, double gia, int soLuong) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.namXB = namXB;
        this.gia = gia;
        this.soLuong = soLuong;
    }
    public String getMaSach() { return maSach; }
    public void setMaSach(String maSach) { this.maSach = maSach; }
    
    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }
    
    public int getNamXB() { return namXB; }
    public void setNamXB(int namXB) { this.namXB = namXB; }
    
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    
    @Override
    public String toString() {
        return maSach + "," + tenSach + "," + namXB + "," + gia + "," + soLuong;
    }
}