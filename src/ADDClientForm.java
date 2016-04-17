import javax.swing.*;

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
        frame = new JFrame("ADDClientForm");
    }

    public static void main(String[] args) {
        //JFrame frame = new JFrame("ADDClientForm");
        frame.setContentPane(new ADDClientForm().PAN_Labels);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
