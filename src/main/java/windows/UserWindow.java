package windows;

import database.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Comparator;
import java.util.List;

public class UserWindow extends JFrame {
    private JTable table;
    private ProductTable productTable;
    private User user;

    protected JPanel bottomPanel = new JPanel();
    protected JPanel topPanel = new JPanel();
    protected JPanel generalPanel = new JPanel();

    protected JButton updateButton = new JButton("update");
    protected JButton checkButton = new JButton("check");
    protected JButton makeOrderButton = new JButton("make order");
    protected JTextField nameField = new JTextField("      ");
    protected JTextField amountField = new JTextField("      ");

    private UsersOrders usersOrders = new UsersOrders();

    public void setUser(User user) {
        this.user = user;
    }

    public UserWindow() throws SQLException {
        setTitle("product info window");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Массив с заголовками-кнопками
        String[] columnButtons = {
                "name", "amount"
        };

        productTable = new ProductTable();
        Statement st = productTable.conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * from products");
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Данные для таблицы
        Object[][] data = new Object[productTable.getRows()][columnCount];

        int row = 0;
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                data[row][0] = rs.getString("name");
                data[row][1] = rs.getString("number");
            }
            row++;
        }

        // Создание модели таблицы с данными и заголовками-кнопками
        DefaultTableModel model = new DefaultTableModel(data, columnButtons) {
            @Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass(); // Возврат типа класса для ячейки таблицы
            }
        };

        // Создание таблицы с моделью
        table = new JTable(model);


        TableRowSorter<DefaultTableModel> nameSorter = new TableRowSorter<>(model);
        TableRowSorter<DefaultTableModel> amountSorter = new TableRowSorter<>(model);

        nameSorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        Comparator<Integer> numberComparator = Comparator.naturalOrder();
        amountSorter.setComparator(1, numberComparator);

        // Установка обработчика нажатия кнопок в заголовках столбцов
// Установка обработчика нажатия кнопок в заголовках столбцов
        JTableHeader header = table.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = header.columnAtPoint(e.getPoint());
                Object headerValue = header.getColumnModel().getColumn(column).getHeaderValue();

                // Проверка, что нажата кнопка "ID"
                if (headerValue.equals("name")) {
                    table.setRowSorter(nameSorter);
                }
                if (headerValue.equals("amount")) {
                    table.setRowSorter(amountSorter);
                }
            }
        });


        // Создание верхней панели
        topPanel.setLayout(new FlowLayout());
        topPanel.add(updateButton);
        topPanel.add(checkButton);

        // Создание нижней панели
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(makeOrderButton);
        bottomPanel.add(new JLabel("name of product:"));
        bottomPanel.add(nameField);
        bottomPanel.add(new JLabel("amount:"));
        bottomPanel.add(amountField);



        // Добавление таблицы в окно
        getContentPane().add(new JScrollPane(table));

        // Добавление панелей в главную панель
        generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.PAGE_AXIS));
        generalPanel.add(topPanel, BorderLayout.NORTH);
        generalPanel.add(bottomPanel, BorderLayout.SOUTH);

// Добавление главной панели в окно
        //getContentPane().add(generalPanel);

        add(generalPanel, BorderLayout.SOUTH);

        makeOrderButton.addActionListener(e -> {
            Product product = new Product(nameField.getText());
            try {
                usersOrders.addOrder(this.user,product,Integer.parseInt(amountField.getText()));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        checkButton.addActionListener(e -> {
            // TODO: нужно добавить таблицу заказов подобно той, где представлены товар
            try {
                OrdersTable ordersTable = new OrdersTable();
                ordersTable.setVisible(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        //exitButton.addActionListener(e -> {
        //    this.dispose();
        //});
        pack();
        revalidate();

    }
    class OrdersTable extends JFrame {
        private ProductTable productTable = new ProductTable();
        public OrdersTable() throws SQLException {
            setTitle("product info window");
            setSize(600, 600);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            String[] columnNames = {"user_name", "product_name", "product_amount", "id","status" };

            String sql = "SELECT * FROM users_orders WHERE user_name = ?";
            PreparedStatement ps = usersOrders.conn.prepareStatement(sql);
            ps.setString(1,user.login);
            ResultSet rs = ps.executeQuery();
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
                    String sql1 = "SELECT * FROM users_orders WHERE user_name = ?";
                    PreparedStatement ps1 = usersOrders.conn.prepareStatement(sql);
                    ps1.setString(1,user.login);
                    ResultSet rs1 = ps1.executeQuery();
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
                    revalidate();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            });
            JPanel buttonsPanel = new JPanel();

            JButton exit = new JButton("exit");
            exit.addActionListener(e -> {dispose();});
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
    public static void main(String[] args) throws SQLException {
        UserWindow window = new UserWindow();
        window.setVisible(true);
        window.revalidate();

    }
}
