package qlsach;

import java.io.*;
import java.util.*;
import java.sql.*;

public class QuanLySach {
    private ArrayList<Sach> danhSachSach;
    private final String FILE_NAME = "DanhMucSach.txt";
    
    public QuanLySach() {
        danhSachSach = new ArrayList<>();
        docDuLieuTuFile();
    }
    
    public void docDuLieuTuFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Sach sach = new Sach(parts[0], parts[1], 
                                       Integer.parseInt(parts[2]), 
                                       Double.parseDouble(parts[3]), 
                                       Integer.parseInt(parts[4]));
                    danhSachSach.add(sach);
                }
            }
            System.out.println("Đã đọc dữ liệu từ file thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
    }
    
    public void taoFileSachMoi() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("SachMoi.txt"))) {
            for (Sach sach : danhSachSach) {
                if (sach.getNamXB() >= 2020) {
                    pw.println(sach.toString());
                }
            }
            System.out.println("Đã tạo file SachMoi.txt thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi tạo file: " + e.getMessage());
        }
    }
    
    public void luuDuLieuVaoFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Sach sach : danhSachSach) {
                pw.println(sach.toString());
            }
            System.out.println("Đã lưu dữ liệu vào file thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi lưu file: " + e.getMessage());
        }
    }
    
    public void timKiemVaHienThi(String tuKhoa) {
        boolean timThay = false;
        System.out.println("Kết quả tìm kiếm cho: " + tuKhoa);
        for (Sach sach : danhSachSach) {
            if (sach.getTenSach().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                sach.getMaSach().toLowerCase().contains(tuKhoa.toLowerCase())) {
                System.out.println(sach.toString());
                timThay = true;
            }
        }
        if (!timThay) {
            System.out.println("Không tìm thấy sách nào!");
        }
    }
    

    public void ketNoiCSDL() {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("Đã kết nối vào CSDL QuanLySach thành công!");
        } else {
            System.out.println("Kết nối CSDL thất bại!");
        }
    }
    

    public void luuDanhSachTuCSDL() {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Không thể kết nối CSDL!");
            return;
        }
        
        try {

            String deleteSql = "DELETE FROM DMSach";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.executeUpdate();
            deleteStmt.close();
            

            String insertSql = "INSERT INTO DMSach (MaSach, TenSach, NamXB, Gia, SoLuong) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            
            for (Sach sach : danhSachSach) {
                insertStmt.setString(1, sach.getMaSach());
                insertStmt.setString(2, sach.getTenSach());
                insertStmt.setInt(3, sach.getNamXB());
                insertStmt.setDouble(4, sach.getGia());
                insertStmt.setInt(5, sach.getSoLuong());
                insertStmt.executeUpdate();
            }
            
            insertStmt.close();
            System.out.println("Đã lưu danh sách sách từ ArrayList vào bảng DMSach thành công!");
            
        } catch (SQLException e) {
            System.err.println("Lỗi lưu dữ liệu vào CSDL: " + e.getMessage());
        }
    }
    

    public void xoaSachTheoMaTuCSDL(String maSach) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Không thể kết nối CSDL!");
            return;
        }
        
        try {

            String checkSql = "SELECT COUNT(*) FROM DMSach WHERE MaSach = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, maSach);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {

                String deleteSql = "DELETE FROM DMSach WHERE MaSach = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setString(1, maSach);
                int rowsAffected = deleteStmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Đã xóa sách có mã " + maSach + " khỏi CSDL thành công!");

                    capNhatArrayListTuCSDL();
                } else {
                    System.out.println("Không thể xóa sách!");
                }
                deleteStmt.close();
            } else {
                System.out.println("Mã sách " + maSach + " không tồn tại trong CSDL!");
            }
            
            rs.close();
            checkStmt.close();
            
        } catch (SQLException e) {
            System.err.println("Lỗi xóa sách từ CSDL: " + e.getMessage());
        }
    }
    

    public void suaSoLuongSachTrongCSDL(String maSach, int soSachDaBan) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Không thể kết nối CSDL!");
            return;
        }
        
        try {

            String selectSql = "SELECT SoLuong FROM DMSach WHERE MaSach = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, maSach);
            ResultSet rs = selectStmt.executeQuery();
            
            if (rs.next()) {
                int soLuongHienTai = rs.getInt("SoLuong");
                int soLuongConLai = soLuongHienTai - soSachDaBan;
                
                if (soLuongConLai < 0) {
                    System.out.println("Số sách đã bán vượt quá số lượng hiện có!");
                    rs.close();
                    selectStmt.close();
                    return;
                }
                

                String updateSql = "UPDATE DMSach SET SoLuong = ? WHERE MaSach = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, soLuongConLai);
                updateStmt.setString(2, maSach);
                
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Đã cập nhật số lượng sách có mã " + maSach);
                    System.out.println("Số lượng trước: " + soLuongHienTai + ", sau khi bán: " + soLuongConLai);

                    capNhatArrayListTuCSDL();
                } else {
                    System.out.println("Không thể cập nhật số lượng sách!");
                }
                updateStmt.close();
            } else {
                System.out.println("Mã sách " + maSach + " không tồn tại trong CSDL!");
            }
            
            rs.close();
            selectStmt.close();
            
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật số lượng sách: " + e.getMessage());
        }
    }
    

    public void thanhLySachTheoNam(int nam) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Không thể kết nối CSDL!");
            return;
        }
        
        try {

            String selectSql = "SELECT * FROM DMSach WHERE NamXB < ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, nam);
            ResultSet rs = selectStmt.executeQuery();
            
            System.out.println("Danh sách sách cần thanh lý (xuất bản trước năm " + nam + "):");
            boolean coSachThanhLy = false;
            double tongGiaTriThanhLy = 0;
            
            while (rs.next()) {
                coSachThanhLy = true;
                String maSach = rs.getString("MaSach");
                String tenSach = rs.getString("TenSach");
                int namXB = rs.getInt("NamXB");
                double gia = rs.getDouble("Gia");
                int soLuong = rs.getInt("SoLuong");
                double giaTriSach = gia * soLuong;
                tongGiaTriThanhLy += giaTriSach;
                
                System.out.println("Mã: " + maSach + ", Tên: " + tenSach + 
                                 ", Năm XB: " + namXB + ", Giá trị: " + giaTriSach + " VNĐ");
            }
            
            if (coSachThanhLy) {
                System.out.println("Tổng giá trị thanh lý: " + tongGiaTriThanhLy + " VNĐ");
                

                String deleteSql = "DELETE FROM DMSach WHERE NamXB < ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setInt(1, nam);
                int rowsDeleted = deleteStmt.executeUpdate();
                
                System.out.println("Đã thanh lý " + rowsDeleted + " loại sách.");
                deleteStmt.close();
                

                capNhatArrayListTuCSDL();
            } else {
                System.out.println("Không có sách nào cần thanh lý.");
            }
            
            rs.close();
            selectStmt.close();
            
        } catch (SQLException e) {
            System.err.println("Lỗi thanh lý sách: " + e.getMessage());
        }
    }
    

    private void capNhatArrayListTuCSDL() {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return;
        
        try {
            danhSachSach.clear();
            String sql = "SELECT * FROM DMSach";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Sach sach = new Sach(
                    rs.getString("MaSach"),
                    rs.getString("TenSach"),
                    rs.getInt("NamXB"),
                    rs.getDouble("Gia"),
                    rs.getInt("SoLuong")
                );
                danhSachSach.add(sach);
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật ArrayList từ CSDL: " + e.getMessage());
        }
    }
    

    public void xoaSachTheoMa(String maSach) {
        boolean daXoa = false;
        Iterator<Sach> iterator = danhSachSach.iterator();
        while (iterator.hasNext()) {
            Sach sach = iterator.next();
            if (sach.getMaSach().equals(maSach)) {
                iterator.remove();
                daXoa = true;
                break;
            }
        }
        if (daXoa) {
            System.out.println("Đã xóa sách có mã: " + maSach);
            luuDuLieuVaoFile();
        } else {
            System.out.println("Mã sách không tồn tại!");
        }
    }
    
    public void suaSoLuongSach(String maSach, int soLuongMoi) {
        boolean daSua = false;
        for (Sach sach : danhSachSach) {
            if (sach.getMaSach().equals(maSach)) {
                sach.setSoLuong(soLuongMoi);
                daSua = true;
                break;
            }
        }
        if (daSua) {
            System.out.println("Đã sửa số lượng sách có mã: " + maSach);
            luuDuLieuVaoFile();
        } else {
            System.out.println("Mã sách không tồn tại!");
        }
    }
    
    public void tinhTongGiaTriTheoNam(int nam) {
        double tongGiaTri = 0;
        int soLuongSach = 0;
        
        for (Sach sach : danhSachSach) {
            if (sach.getNamXB() == nam) {
                tongGiaTri += sach.getGia() * sach.getSoLuong();
                soLuongSach += sach.getSoLuong();
            }
        }
        
        System.out.println("Năm " + nam + ":");
        System.out.println("Tổng số lượng sách: " + soLuongSach);
        System.out.println("Tổng giá trị: " + tongGiaTri + " VNĐ");
    }
    
    public void hienThiTatCaSach() {
        System.out.println("Danh sách tất cả sách:");
        for (Sach sach : danhSachSach) {
            System.out.println(sach.toString());
        }
    }
    
    public void themSach(Sach sach) {
        danhSachSach.add(sach);
        luuDuLieuVaoFile();
        System.out.println("Đã thêm sách mới thành công!");
    }
}