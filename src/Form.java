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
    private JPanel PAN_Circuit;
    private JButton BT_CModify;
    private JButton BT_CAdd;
    private JButton BT_CDelete;
    private JPanel PAN_Client;
    private JButton BT_CLAdd;
    private JButton BT_CLDelete;
    private JButton BT_RAdd;
    private JButton BT_RDelete;
    private JButton BT_CList;
    private JTextField TA_CName;
    private JPanel PAN_Reservation;
    private JPanel PAN_List;
    private JButton BT_MList;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Form");
        frame.setContentPane(new Form().PAN_Overall);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Form() {
        PAN_Circuit.setVisible(false);
        PAN_Client.setVisible(false);
        PAN_Reservation.setVisible(false);
        PAN_List.setVisible(false);

        // Connection button
        BT_Connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection.connect();
                PAN_Circuit.setVisible(true);
                PAN_Client.setVisible(true);
                PAN_Reservation.setVisible(true);
                PAN_List.setVisible(true);
            }
        });

        // Disconnection button
        BT_Disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection.disconnect();
            }
        });

        //List button to list the client on a given circuit from (TA_CName)
        BT_CList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        //List button to list the monuments on a given circuit from (TA_CName)
        BT_MList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
