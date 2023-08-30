package org.example;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:32768/postgres", "postgres", "postgrespw")) {
            if (conn != null) {
                System.out.println("Connected to the database!");
                Statement st = conn.createStatement();
                st.execute("insert into employee (id, first_name, second_name) VALUES (1,'dava','good')");
                ResultSet rs = st.executeQuery("SELECT name FROM mates");
                while (rs.next()) {
                    System.out.print(rs.getString(1) + ", ");
                    System.out.println();
                }
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}