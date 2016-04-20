import oracle.jdbc.OracleTypes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 196128636 on 2016-04-20.
 */
public class AddReservationForm {
    private JPanel panel1;
    private JTextField TF_CircuitId;
    private JTextField TF_ClientId;
    private JTextField TF_CurrentDate;
    private JTextField TF_LimitDate;
    private JButton BT_Send;
    public JPanel PAN_Reservation;

    public AddReservationForm() {
        TF_CurrentDate.setText(java.time.LocalDate.now().toString());
        TF_LimitDate.setText(java.time.LocalDate.now().plusDays(7).toString());
        BT_Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TF_CircuitId.getText().trim().length() > 0 && TF_ClientId.getText().trim().length() > 0 && TF_CurrentDate.getText().trim().length() > 0 &&
                        TF_LimitDate.getText().trim().length() > 0) {
                    try {
                        java.sql.Connection connexion = Connection.get();
                        CallableStatement stm = connexion.prepareCall("{call PKGRESERVATIONS.INSERTION(?,?,?,?)}");
                        stm.setInt(1, Integer.parseInt(TF_ClientId.getText()));
                        stm.setInt(2, Integer.parseInt(TF_CircuitId.getText()));
                        stm.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
                        stm.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(7)));
                        stm.execute();
                        System.out.println("Insertion fait");
                        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    } catch (SQLException sqle) {
                        System.err.println(sqle.getMessage());
                    }
                } else {
                    System.out.println("Champs invalides");
                }
            }
        });
    }

    public AddReservationForm(int id){
        setTextField(id);

        TF_CurrentDate.setText(java.time.LocalDate.now().toString());
        TF_LimitDate.setText(java.time.LocalDate.now().plusDays(7).toString());
        BT_Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TF_CircuitId.getText().trim().length() > 0 && TF_ClientId.getText().trim().length() > 0 && TF_CurrentDate.getText().trim().length() > 0 &&
                        TF_LimitDate.getText().trim().length() > 0) {
                    try {
                        java.sql.Connection connexion = Connection.get();
                        CallableStatement stm = connexion.prepareCall("{call PKGRESERVATIONS.MODIFIER(?,?,?,?,?)}");
                        stm.setInt(1,id);
                        stm.setInt(2, Integer.parseInt(TF_ClientId.getText()));
                        stm.setInt(3, Integer.parseInt(TF_CircuitId.getText()));
                        stm.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
                        stm.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(7)));
                        stm.execute();
                        System.out.println("Modification fait");
                        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    } catch (SQLException sqle) {
                        System.err.println(sqle.getMessage());
                    }
                } else {
                    System.out.println("Champs invalides");
                }
            }
        });
    }

    private void setTextField(int id){
        try {
            java.sql.Connection conn = Connection.get();
            CallableStatement stm = conn.prepareCall("{? = call PKGRESERVATIONS.LISTERRESERVATIONS(?)}");
            stm.registerOutParameter(1, OracleTypes.CURSOR);
            stm.setInt(2, id);
            stm.execute();
            ResultSet result = (ResultSet)stm.getObject(1);
            result.next();
            TF_ClientId.setText(result.getObject(1).toString());
            TF_CircuitId.setText(result.getObject(2).toString());
            TF_CurrentDate.setText(result.getObject(3).toString());
            TF_LimitDate.setText(result.getObject(4).toString());
        }
        catch(SQLException sqle){
            System.err.println(sqle.getMessage());
        }
    }
}
