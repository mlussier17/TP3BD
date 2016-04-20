import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * Created by math on 2016-04-17.
 */
public class ADDClientForm {
    public static JFrame frame;
    public JPanel PAN_Labels;
    private JTextField TF_Name;
    private JTextField TF_Firstname;
    private JButton BT_Submit;
    private JPanel PAN_Insert;

    public ADDClientForm() {
        BT_Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(TF_Firstname.getText().trim().length() > 0){
                    if(TF_Name.getText().trim().length() > 0){
                        try {
                            java.sql.Connection connexion = Connection.get();
                            CallableStatement stm = connexion.prepareCall("{call PKGCLIENTS.INSERTION(?,?)}");
                            stm.setString(1,TF_Name.getText());
                            stm.setString(2,TF_Firstname.getText());
                            stm.execute();
                            System.out.println("Insertion fait");
                            frame.dispose();
                        }
                        catch (SQLException sqle){
                            System.err.println(sqle.getMessage());
                        }
                    }
                }
            }
        });
    }
}
