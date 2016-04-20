import oracle.jdbc.OracleTypes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
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
    public AddCircuitForm(){
        BT_Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(TX_Name.getText().trim().length() > 0 && TF_CityStart.getText().trim().length() > 0 && TF_CityEnd.getText().trim().length() > 0 &&
                        TF_Price.getText().trim().length() > 0 && TF_Duree.getText().trim().length() > 0 && TF_MaxClients.getText().trim().length() > 0){
                    try {
                        java.sql.Connection connexion = Connection.get();
                        CallableStatement stm = connexion.prepareCall("{call PKGCIRCUITS.INSERTION(?,?,?,?,?,?)}");
                        stm.setString(1,TX_Name.getText());
                        stm.setString(2,TF_CityStart.getText());
                        stm.setString(3,TF_CityEnd.getText());
                        stm.setInt(4,Integer.parseInt(TF_Price.getText()));
                        stm.setInt(5,Integer.parseInt(TF_Duree.getText()));
                        stm.setInt(6,Integer.parseInt(TF_MaxClients.getText()));
                        stm.execute();
                        System.out.println("Insertion fait");
                        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    }
                    catch (SQLException sqle){
                        System.err.println(sqle.getMessage());
                    }
                }else{
                    System.out.println("Champs invalides");
                }
            }
        });
    }

    public AddCircuitForm(int id) {
        setTextField(id);

        BT_Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(TX_Name.getText().trim().length() > 0 && TF_CityStart.getText().trim().length() > 0 && TF_CityEnd.getText().trim().length() > 0 &&
                        TF_Price.getText().trim().length() > 0 && TF_Duree.getText().trim().length() > 0 && TF_MaxClients.getText().trim().length() > 0){
                        try {
                            java.sql.Connection connexion = Connection.get();
                            CallableStatement stm = connexion.prepareCall("{call PKGCIRCUITS.MODIFIER(?,?,?,?,?,?,?)}");
                            stm.setInt(1,id);
                            stm.setString(2,TX_Name.getText());
                            stm.setString(3,TF_CityStart.getText());
                            stm.setString(4,TF_CityEnd.getText());
                            stm.setInt(5,Integer.parseInt(TF_Price.getText()));
                            stm.setInt(6,Integer.parseInt(TF_Duree.getText()));
                            stm.setInt(7,Integer.parseInt(TF_MaxClients.getText()));
                            stm.execute();
                            System.out.println("Modification fait");
                            //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        }
                        catch (SQLException sqle){
                            System.err.println(sqle.getMessage());
                        }
                    }else{
                        System.out.println("Champs invalides");
                    }
                }
        });

    }
    public void setTextField(int id){
        try {
            java.sql.Connection conn = Connection.get();
            CallableStatement stm = conn.prepareCall("{? = call PKGCIRCUITS.LISTERCIRCUITS(?)}");
            stm.registerOutParameter(1, OracleTypes.CURSOR);
            stm.setInt(2, id);
            stm.execute();
            ResultSet result = (ResultSet)stm.getObject(1);
            result.next();
            TX_Name.setText(result.getObject(1).toString());
            TF_CityStart.setText(result.getObject(2).toString());
            TF_CityEnd.setText(result.getObject(3).toString());
            TF_Price.setText(result.getObject(4).toString());
            TF_Duree.setText(result.getObject(5).toString());
            TF_MaxClients.setText(result.getObject(6).toString());
        }
        catch(SQLException sqle){
            System.err.println(sqle.getMessage());
        }

    }
}
