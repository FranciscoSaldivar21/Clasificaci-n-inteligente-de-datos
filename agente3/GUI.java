package examples.agente3;

import jade.core.AID;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GUI extends JFrame {
    private AgenteReceptor agente;
    private JTextField x1;

    GUI(AgenteReceptor a) {
        agente = a;

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 3));
        p.add(new JLabel("Valor de X:"));
        x1 = new JTextField(15);
        p.add(x1);
        getContentPane().add(p, BorderLayout.CENTER);
        
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String var1 = x1.getText().trim();
                    System.out.println("La predicion para " + var1 + " es: ");
                    System.out.println(agente.predecir(Double.parseDouble(var1)));
                    x1.setText("");
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