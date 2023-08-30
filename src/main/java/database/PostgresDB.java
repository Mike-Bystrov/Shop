package database;

import java.sql.*;

public class PostgresDB {
    public String url = "jdbc:postgresql://localhost:32768/postgres";
    public String user = "postgres";
    public String password = "postgrespw";
    public Connection conn = DriverManager.getConnection(url,user,password);;

    public PostgresDB() throws SQLException {
    }

    public boolean checkConnection() {
        try (Connection ignored = DriverManager.getConnection(url,user,password)) {
            this.conn = DriverManager.getConnection(url,user,password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
