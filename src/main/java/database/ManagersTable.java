package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagersTable extends PostgresDB {

    public ManagersTable() throws SQLException {
    }

    public boolean hasManagerInDataBase(String login) throws SQLException {
        if (checkConnection()) {
            String sql = "SELECT * FROM managers WHERE login = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,login);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
        return false;
    }

    public void addManagerToDataBase (String login, String password) throws SQLException{
        if (checkConnection()) {
            String sql = "INSERT INTO managers (login, password) VALUES (?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,login);
            pstmt.setString(2,password);
            pstmt.execute();
        }
    }

    public String getPassword(String login) throws SQLException {
        if (checkConnection()) {
            String sql = "SELECT password from managers WHERE login = ?";
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
