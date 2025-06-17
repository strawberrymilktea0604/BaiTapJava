package qlsach;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        QuanLySach qlSach = new QuanLySach();
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("\n=== QUẢN LÝ SÁCH ===");
            System.out.println("1. Hiển thị tất cả sách");
            System.out.println("2. Tạo file sách mới (từ năm 2020)");
            System.out.println("3. Tìm kiếm sách");
            System.out.println("4. Thêm sách mới");
            System.out.println("5. Xóa sách theo mã");
            System.out.println("6. Sửa số lượng sách");
            System.out.println("7. Tính tổng giá trị theo năm");
            System.out.println("--- QUẢN LÝ CSDL ---");
            System.out.println("8. Kết nối CSDL");
            System.out.println("9. Lưu danh sách vào CSDL");
            System.out.println("10. Xóa sách từ CSDL");
            System.out.println("11. Cập nhật số lượng sau bán hàng");
            System.out.println("12. Thanh lý sách theo năm");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    qlSach.hienThiTatCaSach();
                    break;
                case 2:
                    qlSach.taoFileSachMoi();
                    break;
                case 3:
                    System.out.print("Nhập từ khóa tìm kiếm: ");
                    String tuKhoa = scanner.nextLine();
                    qlSach.timKiemVaHienThi(tuKhoa);
                    break;
                case 4:
                    System.out.print("Nhập mã sách: ");
                    String maSach = scanner.nextLine();
                    System.out.print("Nhập tên sách: ");
                    String tenSach = scanner.nextLine();
                    System.out.print("Nhập năm xuất bản: ");
                    int namXB = scanner.nextInt();
                    System.out.print("Nhập giá: ");
                    double gia = scanner.nextDouble();
                    System.out.print("Nhập số lượng: ");
                    int soLuong = scanner.nextInt();
                    
                    Sach sachMoi = new Sach(maSach, tenSach, namXB, gia, soLuong);
                    qlSach.themSach(sachMoi);
                    break;
                case 5:
                    System.out.print("Nhập mã sách cần xóa: ");
                    String maXoa = scanner.nextLine();
                    qlSach.xoaSachTheoMa(maXoa);
                    break;
                case 6:
                    System.out.print("Nhập mã sách cần sửa: ");
                    String maSua = scanner.nextLine();
                    System.out.print("Nhập số lượng mới: ");
                    int soLuongMoi = scanner.nextInt();
                    qlSach.suaSoLuongSach(maSua, soLuongMoi);
                    break;
                case 7:
                    System.out.print("Nhập năm xuất bản: ");
                    int nam = scanner.nextInt();
                    qlSach.tinhTongGiaTriTheoNam(nam);
                    break;
                case 8:
                    qlSach.ketNoiCSDL();
                    break;
                case 9:
                    qlSach.luuDanhSachTuCSDL();
                    break;
                case 10:
                    System.out.print("Nhập mã sách cần xóa từ CSDL: ");
                    String maXoaCSDL = scanner.nextLine();
                    qlSach.xoaSachTheoMaTuCSDL(maXoaCSDL);
                    break;
                case 11:
                    System.out.print("Nhập mã sách: ");
                    String maBan = scanner.nextLine();
                    System.out.print("Nhập số sách đã bán: ");
                    int soDaBan = scanner.nextInt();
                    qlSach.suaSoLuongSachTrongCSDL(maBan, soDaBan);
                    break;
                case 12:
                    System.out.print("Nhập năm (các sách trước năm này sẽ bị thanh lý): ");
                    int namThanhLy = scanner.nextInt();
                    qlSach.thanhLySachTheoNam(namThanhLy);
                    break;
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 0);
        
        scanner.close();
        DatabaseConnection.closeConnection();
    }
}