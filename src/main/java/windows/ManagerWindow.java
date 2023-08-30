package windows;

import database.ProductTable;
import database.User;
import database.UsersOrders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ManagerWindow extends UserWindow {

    private User manager;
    private UsersOrders usersOrders = new UsersOrders();
    public void setManager(User user) {
        super.setUser(user);
    }

    public ManagerWindow() throws SQLException {
        super();
        JButton addProduct = new JButton("addProduct");
        JButton checkOrders = new JButton("checkOrders");
        JButton deleteProduct = new JButton("deleteProduct");


        topPanel.add(checkOrders);
        topPanel.add(addProduct);
        topPanel.add(deleteProduct);

        hideButtons();

        checkOrders.addActionListener(e -> {
            try {
                OrdersTable ordersTable = new OrdersTable();
                ordersTable.setVisible(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


        });
        revalidate();
    }

    public static void main(String[] args) throws SQLException {
        ManagerWindow managerWindow = new ManagerWindow();
        managerWindow.setVisible(true);
    }

    public void hideButtons() {
        bottomPanel.setVisible(false);
        nameField.setVisible(false);
        amountField.setVisible(false);
        makeOrderButton.setVisible(false);
        checkButton.setVisible(false);
    }

    class OrdersTable extends JFrame {
        private ProductTable productTable = new ProductTable();
        public OrdersTable() throws SQLException {
            setTitle("product info window");
            setSize(600, 600);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            String[] columnNames = {"user_name", "product_name", "product_amount", "id","status" };

            Statement st = usersOrders.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from users_orders WHERE status = 'waiting'");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            Object[][] data = new Object[usersOrders.getRows()][columnCount];

            int row = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    data[row][0] = rs.getString("user_name");
                    data[row][1] = rs.getString("product_name");
                    data[row][2] = rs.getString("product_amount");
                    data[row][3] = rs.getString("id");
                    data[row][4] = rs.getString("status");
                }
                row++;
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public Class getColumnClass(int column) {
                    return getValueAt(0, column).getClass(); // Возврат типа класса для ячейки таблицы
                }
            };

            JTable table = new JTable(model);
            JTableHeader header = table.getTableHeader();

            JButton updateButton = new JButton("Update");
            updateButton.addActionListener(e -> {
                try {
                    // Получаем данные из базы данных
                    Statement st1 = usersOrders.conn.createStatement();
                    ResultSet rs1 = st1.executeQuery("SELECT * from users_orders WHERE status = 'waiting'");
                    ResultSetMetaData metaData1 = rs1.getMetaData();
                    int columnCount1 = metaData1.getColumnCount();

                    // Заполняем массив с данными
                    Object[][] data1 = new Object[usersOrders.getRows()][columnCount1];

                    int row1 = 0;
                    while (rs1.next()) {
                        for (int i = 1; i <= columnCount1; i++) {
                            data1[row1][0] = rs1.getString("user_name");
                            data1[row1][1] = rs1.getString("product_name");
                            data1[row1][2] = rs1.getString("product_amount");
                            data1[row1][3] = rs1.getString("id");
                            data1[row1][4] = rs1.getString("status");
                        }
                        row1++;
                    }

                    // Создаем новую модель таблицы и устанавливаем ее в таблицу
                    DefaultTableModel model1 = new DefaultTableModel(data1, columnNames) {
                        @Override
                        public Class getColumnClass(int column) {
                            return getValueAt(0, column).getClass();
                        }
                    };
                    table.setModel(model1);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            });

            JButton rejectButton = new JButton("reject");
            rejectButton.addActionListener(e -> {
                // Обработчик нажатия на кнопку reject
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String orderId = table.getValueAt(selectedRow, 3).toString();
                    try {
                        usersOrders.changeOrderStatus(Integer.parseInt(orderId), "rejected");
                        JOptionPane.showMessageDialog(null, "Order " + orderId + " has been rejected!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                revalidate();
            });

            JButton acceptButton = new JButton("accept");
            acceptButton.addActionListener(e -> {
                // Обработчик нажатия на кнопку accept
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String orderId = table.getValueAt(selectedRow, 3).toString();
                    String productName = table.getValueAt(selectedRow, 1).toString();
                    int productAmount = Integer.parseInt(table.getValueAt(selectedRow, 2).toString());
                    try {
                        usersOrders.changeOrderStatus(Integer.parseInt(orderId), "accepted");
                        JOptionPane.showMessageDialog(null, "Order " + orderId + " has been accepted!");
                        productTable.updateProduct(productName, -productAmount);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    }
                }
                updateButton.doClick();
                revalidate();
            });

            JPanel buttonsPanel = new JPanel();

            JButton exit = new JButton("exit");
            exit.addActionListener(e -> {dispose();});
            buttonsPanel.add(rejectButton);
            buttonsPanel.add(acceptButton);
            buttonsPanel.add(updateButton);
            buttonsPanel.add(exit);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(header, BorderLayout.NORTH);
            mainPanel.add(table, BorderLayout.CENTER);
            mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

            getContentPane().add(mainPanel);
        }
    }
}
