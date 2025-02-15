package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnect {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:mydatabase.db"; // 数据库文件（如果不存在会自动创建）

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("成功连接到 SQLite 数据库！");
            }
        } catch (SQLException e) {
            System.out.println("连接失败: " + e.getMessage());
        }
    }
}
