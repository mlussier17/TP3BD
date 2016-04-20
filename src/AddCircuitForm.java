import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * Created by math on 2016-04-19.
 */
public class AddCircuitForm {
    public static JFrame frame;
    private JTextField TX_Name;
    private JTextField TF_CityStart;
    private JTextField TF_CityEnd;
    private JTextField TF_Price;
    private JTextField TF_Duree;
    private JTextField TF_MaxClients;
    private JButton BT_Send;
    public JPanel PAN_Circuits;

    public AddCircuitForm() {
        BT_Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        BT_Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(TX_Name.getText().trim().length() > 0 && TF_CityStart.getText().trim().length() > 0 && TF_CityEnd.getText().trim().length() > 0 &&
                        TF_Price.getText().trim().length() > 0 && TF_Duree.getText().trim().length() > 0 && TF_MaxClients.getText().trim().length() > 0){
                        try {
                            java.sql.Connection connexion = Connection.get();
                            CallableStatement stm = connexion.prepareCall("{call PKGCircuits.INSERTION(?,?,?,?,?,?)}");
                            stm.setString(1,TX_Name.getText());
                            stm.setString(2,TF_CityStart.getText());
                            stm.setString(3,TF_CityEnd.getText());
                            stm.setInt(4,Integer.parseInt(TF_Price.getText()));
                            stm.setInt(5,Integer.parseInt(TF_Duree.getText()));
                            stm.setInt(6,Integer.parseInt(TF_MaxClients.getText()));
                            stm.execute();
                            System.out.println("Insertion fait");
                            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        }
                        catch (SQLException sqle){
                            System.err.println(sqle.getMessage());
                        }
                    }
                }
        });
    }
}
