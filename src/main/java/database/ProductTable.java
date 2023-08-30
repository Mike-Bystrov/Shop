package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductTable extends PostgresDB {
    public ProductTable() throws SQLException {
    }

    // add row to table products
    public void addProduct(String name, int number) throws SQLException {
        if (checkConnection()) {
//            String sql = "INSERT INTO products (id, name, number) VALUES (?,?,?)";
//            Product product = new Product(name, number);
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//
//            pstmt.setInt(1, Product.id);
//            pstmt.setString(2, name);
//            pstmt.setInt(3, number);
//
//
//            pstmt.execute();
            if (!isSuchProductInTable(name)) {
                // add
            } else {
                // update
            }
        }
    }

    public boolean updateProduct(String name, int number) throws SQLException{
        // if there is some kind of product we don't need to create another row in our table
        // instead we should update the row we already have
        if (checkConnection()) {
            String sql = "UPDATE products SET number = number + ? WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,number);
            ps.setString(2, name);
            ps.executeUpdate();
        }
        return false;
    }

    public boolean isSuchProductInTable(String name) throws SQLException{
        if (checkConnection()) {
            String sql = "SELECT * FROM products WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        return false;
    }

    public int getRows() throws SQLException {
        String sql = "SELECT COUNT(*) from products";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}
