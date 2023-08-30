package windows;

import database.ManagersTable;
import database.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class ManagerRegistrationWindow extends JFrame {
    private JButton loginButton;
    private JButton signupButton;
    private JButton exit;
    private JPanel panel0 = new JPanel();
    private ManagersTable managersTable = new ManagersTable();
    private ManagerWindow managerWindow = new ManagerWindow();

    public ManagerRegistrationWindow() throws SQLException {
        super("manager registration window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        panel0.setLayout(new FlowLayout());
        setVisible(true);

        loginButton = new JButton("Log in");
        signupButton = new JButton("Sign up");
        exit = new JButton("Exit");

        // добавление кнопок на форму
        panel0.add(loginButton);
        panel0.add(signupButton);

        JPanel panel = new JPanel();
        panel.add(exit);
        add(panel0, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        revalidate();
        // добавление обработчика событий для кнопки "exit"
        exit.addActionListener(e -> {
            dispose();
        });

        // добавление обработчика событий для кнопки "loginButton"
        loginButton.addActionListener(e -> {
            JTextField login = new JTextField();
            JPasswordField password = new JPasswordField();

            final JComponent[] inputs = new JComponent[] {
                    new JLabel("login"),
                    login,
                    new JLabel("password"),
                    password,
            };

            JOptionPane.showMessageDialog(null, inputs, "df", JOptionPane.PLAIN_MESSAGE);
            String userPassword = String.valueOf(password.getPassword());
            String userLogin = login.getText();

            int i = 0;
            try {
                if (!managersTable.hasManagerInDataBase(userLogin)) {
                    while (!managersTable.hasManagerInDataBase(userLogin) && i<3) {
                        i++;
                        login.setForeground(Color.RED);
                        userLogin = login.getText();

                        JOptionPane.showMessageDialog(null, "no such user in db");
                        JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.DEFAULT_OPTION);
                    }
                } else {
                    int k = 0;
                    login.setForeground(Color.BLACK);
                    while (!userPassword.equals(managersTable.getPassword(userLogin)) && k<3) {
                        password.setForeground(Color.RED);
                        userPassword = String.valueOf(password.getPassword());
                        k++;
                        JOptionPane.showMessageDialog(null, "wrong password");
                        JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.DEFAULT_OPTION);
                    }
                    if (k<3) {
                        managerWindow.setManager(new User(userLogin, userPassword.toCharArray()));
                        managerWindow.setVisible(true);
                        dispose();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


        });

        signupButton.addActionListener(e -> {
            JTextField login = new JTextField();
            JPasswordField password = new JPasswordField();
            JPasswordField repeatPassword = new JPasswordField();
            JLabel errorMessage = new JLabel();

            final JComponent[] inputs = new JComponent[] {
                    new JLabel("login"),
                    login,
                    new JLabel("password"),
                    password,
                    new JLabel("repeat password"),
                    repeatPassword,
                    errorMessage,
            };
            errorMessage.setForeground(Color.RED);
            errorMessage.setVisible(false);

            JOptionPane.showMessageDialog(null, inputs, "df", JOptionPane.PLAIN_MESSAGE);
            char[] userPassword = password.getText().toCharArray();
            char[] userRepeatedPassword = repeatPassword.getText().toCharArray();



            System.out.println("userRepeatedPassword = " + Arrays.toString(userRepeatedPassword));
            System.out.println("userRepeatedPassword = " + Arrays.toString(userPassword));

            while (!Arrays.equals(userPassword, userRepeatedPassword)) {
                userPassword = password.getText().toCharArray();
                userRepeatedPassword = repeatPassword.getText().toCharArray();

                errorMessage.setVisible(true);
                JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.DEFAULT_OPTION);
            }

            String userLogin = login.getText();

            try {
                if (managersTable.hasManagerInDataBase(userLogin)) {
                    System.out.println("user is already registered");
                } else {
                    System.out.println("add user to table");
                    managersTable.addManagerToDataBase(userLogin, String.valueOf(password.getPassword()));
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            //
        });
    }
}
