package windows;

import javax.swing.*;
import java.awt.*;

public class RegistrationWindow extends JFrame {
    private JButton exit;
    public RegistrationWindow() {
        super("signup");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null); // установка окна по центру монитора
        setVisible(true);
        setLayout(new FlowLayout());
        exit = new JButton("Exit");
        add(exit, BorderLayout.EAST);
        exit.addActionListener(e -> {
            dispose();
        });
    }
}
