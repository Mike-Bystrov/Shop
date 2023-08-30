package updates.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersTable extends PostgresDB{
    private static User user;
    private static final String tableName = "all_users";

    public static void setUser(User user) {
        UsersTable.user = user;
    }

    // maybe I should make it boolean
    public static void addUserToTable(User userToAdd) throws SQLException {
        String sql = "INSERT INTO all_users (login, password, role) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        //ps.setString(1, tableName);
        ps.setString(1, userToAdd.getLogin());
        ps.setString(2, userToAdd.getPassword());
        ps.setString(3, userToAdd.getRole());
        ps.execute();
    }

    public static boolean hasTableUser(User userToCheck) throws SQLException{
        String sql = "SELECT * FROM all_users WHERE login = ? AND ROLE = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, userToCheck.getLogin());
        ps.setString(2, userToCheck.getRole());

        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static String getPassword(User user) throws SQLException {
        String sql = "SELECT password from ? WHERE login = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,tableName);
        ps.setString(2,user.getLogin());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("password");
        }
        return "";
    }
}
