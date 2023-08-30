package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersOrders extends PostgresDB {

    public UsersOrders() throws SQLException {
        super();
    }

    public void addOrder(User user, Product product, int number) throws SQLException {
        String sql = "INSERT INTO users_orders (user_name, product_name, product_amount, id, status) " +
                "VALUES (?, ?, ?, ?, 'waiting')";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user.login);
        ps.setString(2, product.name);
        ps.setInt(3, number);
        ps.setInt(4, product.hashCode() + number);
        ps.execute();
    }

    public void deleteOrder(String id) throws SQLException{
        String sql = "DELETE FROM user_orders WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,id);
        ps.execute();
    }

    public int getRows() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users_orders";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

     public void changeOrderStatus(int id, String status) throws SQLException {
        String sql = "UPDATE users_orders SET status = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, id);
        ps.execute();
     }
}
