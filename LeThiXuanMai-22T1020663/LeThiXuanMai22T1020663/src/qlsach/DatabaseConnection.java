package qlsach;

import java.sql.*;

public class DatabaseConnection {
    private static final String SERVER = "localhost";
    private static final String PORT = "1433";
    private static final String DATABASE = "QuanLySach";
    private static final String USERNAME = "sa"; 
    private static final String PASSWORD = "123456"; 
    
    private static final String URL = "jdbc:sqlserver://" + SERVER + ":" + PORT + 
                                    ";databaseName=" + DATABASE + 
                                    ";encrypt=false;trustServerCertificate=true";
    
    private static Connection connection = null;
    

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load driver
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                

                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Kết nối CSDL thành công!");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy JDBC Driver!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối CSDL: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Đã đóng kết nối CSDL!");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi đóng kết nối: " + e.getMessage());
        }
    }
    
    
    public static boolean testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT 1");
                if (rs.next()) {
                    System.out.println("Test kết nối thành công!");
                    return true;
                }
            } catch (SQLException e) {
                System.err.println("Lỗi test kết nối: " + e.getMessage());
            }
        }
        return false;
    }
}
