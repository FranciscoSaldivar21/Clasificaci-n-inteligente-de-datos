package examples.agente2;

import jade.core.AID;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GUI extends JFrame {
    private AgenteReceptorRM agente;
    private JTextField x1, x2, x3;

    GUI(AgenteReceptorRM a) {
        agente = a;

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 3));
        p.add(new JLabel("TV:"));
        x1 = new JTextField(15);
        p.add(x1);
        p.add(new JLabel("Radio:"));
        x2 = new JTextField(15);
        p.add(x2);
        p.add(new JLabel("News paper:"));
        x3 = new JTextField(15);
        p.add(x3);
        getContentPane().add(p, BorderLayout.CENTER);
        
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String var1 = x1.getText().trim();
                    String var2 = x2.getText().trim();
                    String var3 = x3.getText().trim();
                    agente.predict(Double.parseDouble(var1), Double.parseDouble(var2), Double.parseDouble(var3));;
                    x1.setText("");
                    x2.setText("");
                    x3.setText("");
                } catch (Exception e) {
                    //JOptionPane.showMessageDialog(GUI.this, "Invalid values. " + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        p = new JPanel();
        p.add(addButton);
        getContentPane().add(p, BorderLayout.SOUTH);

        // Make the agent terminate when the user closes
        // the GUI using the button on the upper right corner
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                agente.doDelete();
            }
        });

        setResizable(false);
    }

    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
}