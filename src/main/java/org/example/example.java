package org.example;

import javax.swing.*;
import java.awt.*;

public class example extends JFrame {
    private JPanel rootPanel;
    private JButton button1;
    private JButton button2;

    public example() {
        setContentPane(rootPanel);
        setVisible(true);

        setSize(100,100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        button1.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "hi");
        });
    }

    public static void main(String[] args) {
        new example();
    }

}
