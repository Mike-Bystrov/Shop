package updates.database;

import java.sql.*;

public class PostgresDB {
    public static String url = "jdbc:postgresql://localhost:32768/postgres";
    public static String user = "postgres";
    public static String password = "postgrespw";
    public static Connection conn;

    public static boolean checkConnection() {
        try (Connection ignored = DriverManager.getConnection(url,user,password)) {
            conn = DriverManager.getConnection(url,user,password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
