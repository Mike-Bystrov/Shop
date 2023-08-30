package updates;

import updates.database.ManagerWindow;
import updates.database.PostgresDB;
import updates.database.User;
import updates.database.UsersTable;

import javax.swing.*;
import java.sql.SQLException;

public class newStartMenu extends JFrame{
    private JPanel root;
    private JPanel rolePanel;
    private JButton userButton;
    private JButton managerButton;
    private JPanel upperPanel;
    private JPanel bottomPanel;
    private JPanel loginPanel;
    private JTextField usernameLoginField;
    private JButton createAccountButton;
    private JButton loginButton;
    private JPanel bottomCentralPanel;
    private JPasswordField enterSystemPassword;
    private JPasswordField passwordSignField0;
    private JPasswordField passwordSignField1;
    private JTextField usernameSignField;
    private JPanel signupPanel;
    private JButton signupButton;
    private Roles role;

    private void createUIComponents() {

    }

    public newStartMenu() {
        setContentPane(root);
        setVisible(true);

        setSize(350,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        signupPanel.setVisible(false);
        loginPanel.setVisible(false);

        userButton.addActionListener(e -> {
            role = Roles.USER;
            loginPanel.setVisible(true);
            rolePanel.setVisible(false);
        });
        managerButton.addActionListener(e -> {
            role = Roles.MANAGER;
            loginPanel.setVisible(true);
            rolePanel.setVisible(false);
        });
        loginButton.addActionListener(e -> {
            String login = usernameLoginField.getText();
            String password = String.valueOf(enterSystemPassword.getPassword());
            User user = new User(login, password, role);
            try {
                if (UsersTable.hasTableUser(user)) {
                    JOptionPane.showMessageDialog(null,"good!");
                    new ManagerWindow();
                } else {

                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        signupButton.addActionListener(e -> {
            String login = usernameSignField.getText();
            String password0 = String.valueOf(passwordSignField0.getPassword());
            String password1 = String.valueOf(passwordSignField1.getPassword());
            if (password0.equals(password1)) {
                User user = new User(login, password0, role);
                try {
                    UsersTable.addUserToTable(user);
                    signupPanel.setVisible(false);
                    loginPanel.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
            }
        });
        createAccountButton.addActionListener(e -> {
            loginPanel.setVisible(false);
            signupPanel.setVisible(true);
            update();
        });
        
    }

    public static void main(String[] args) {
        PostgresDB.checkConnection();
        new newStartMenu();
    }
    public void update() {
        pack();
        setSize(350,350);
        setLocationRelativeTo(null);
        revalidate();
    }
}
