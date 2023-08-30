package windows;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// this is the first window that appears
public class ChooseRoleMenu extends JFrame {
    private JButton userRole;
    private JButton managerRole;
    private JButton exit;
    private JPanel panel;
    private JLabel label;
    private UserRegistrationMenu userRegistrationMenu;
    private ManagerRegistrationWindow managerRegistrationMenu;

    public ChooseRoleMenu() {
        super("very important program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,300);
        setVisible(true);
        setLocationRelativeTo(null); // установка окна по центру монитора

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        userRole = new JButton("user");
        managerRole = new JButton("manager");
        label = new JLabel("Enter program as");

        panel.add(label);
        panel.add(userRole);
        panel.add(managerRole);

        add(panel, BorderLayout.CENTER);
        pack();
        revalidate();

        userRole.addActionListener(e -> {
            try {
                userRegistrationMenu = new UserRegistrationMenu();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setState(Frame.ICONIFIED);
        });

        managerRole.addActionListener(e -> {
            try {
                managerRegistrationMenu = new ManagerRegistrationWindow();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setState(Frame.ICONIFIED);
        });
    }
}
