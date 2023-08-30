package updates.database;

import javax.swing.*;

public class ManagerWindow extends JFrame{
    private JPanel root;
    private JPanel rolePanel;
    private JPanel upperPanel;
    private JPanel signupPanel;
    private JTable table1;
    private JButton acceptButton;
    private JButton rejectButton;
    private JButton removeButton;

    public ManagerWindow() {
        setContentPane(root);
        setSize(1000,400);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
