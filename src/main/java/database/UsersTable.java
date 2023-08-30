package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersTable extends PostgresDB {

    public UsersTable() throws SQLException {
    }

    public boolean hasUserInDataBase(String login) throws SQLException {
        if (checkConnection()) {
            String sql = "SELECT * FROM users WHERE login = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,login);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
        return false;
    }

    public void addUserToDataBase (String login, String password) throws SQLException{
        if (checkConnection()) {
            String sql = "INSERT INTO users (login, password) VALUES (?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,login);
            pstmt.setString(2,password);
            pstmt.execute();
        }
    }

    public String getPassword(String login) throws SQLException {
        if (checkConnection()) {
            String sql = "SELECT password from users WHERE login = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               return rs.getString("password");
            }
        }
        return "";
    }
}
