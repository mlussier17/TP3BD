import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 196128636 on 2016-04-15.
 */
public class Form {
    private JButton BT_Connect;
    private JPanel PAN_Overall;
    private JPanel PAN_Connexion;
    private JButton BT_Disconnect;
    private JPanel PAN_Controls;
    private JButton BT_List;
    private JButton BT_Add;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Form");
        frame.setContentPane(new Form().PAN_Overall);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Form() {
        BT_Connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection.connect();
            }
        });
        BT_Disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection.disconnect();
            }
        });
    }
}
