import oracle.jdbc.OracleTypes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
    private JPanel PAN_Reservation;
    private JPanel PAN_List;
    private JButton BT_MList;
    private JButton BT_CIRList;
    private JTable TAB_List;
    private JButton BT_SearchCircuits;
    private JTextField TF_CircuitsName;
    private JButton BT_ClientsPerCircuits;
    private JButton BT_RModify;
    private JButton BT_RList;
    private JTextField TF_MName;
    private JTextField TF_MDate;
    private JTextArea TA_MHistoire;
    private JButton BT_Previous;
    private JButton BT_Next;
    private JPanel PAN_Monuments;
    private ResultSet result;
    private CallableStatement stm;

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

        //region Connect/Disconnect

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

        //Disconnect button
        BT_Disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection.disconnect();
            }
        });

        //endregion

        //region client

        //Add client
        BT_CLAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("ADDClientForm");
                frame.setContentPane(new ADDClientForm().PAN_Labels);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        //List client
        BT_CList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel model = (DefaultTableModel) TAB_List.getModel();
                    model.setRowCount(0);

                    String[] tableColumnsName = {"ID","Nom","Prenom"};
                    DefaultTableModel aModel = (DefaultTableModel) TAB_List.getModel();
                    aModel.setColumnIdentifiers(tableColumnsName);

                    java.sql.Connection connexion = Connection.get();
                    stm = connexion.prepareCall("{? = call PKGCLIENTS.LISTER}");
                    stm.registerOutParameter(1, OracleTypes.CURSOR);
                    stm.execute();

                    result = (ResultSet)stm.getObject(1);
                    java.sql.ResultSetMetaData rsmd = result.getMetaData();
                    int colNo = rsmd.getColumnCount();
                    while(result.next()){
                        Object[] objects = new Object[colNo];
                        for(int i=0;i<colNo;i++){
                            objects[i]=result.getObject(i+1);
                        }
                        aModel.addRow(objects);
                    }
                    TAB_List.setModel(aModel);
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });
        //Delete client
        BT_CLDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (TAB_List.getSelectedRowCount() > 0) {
                    int rowIndex = TAB_List.getSelectedRow();

                    java.sql.Connection connexion = Connection.get();
                    CallableStatement stm = connexion.prepareCall("{call PKGCLIENTS.REMOVE(?)}");
                    stm.setInt(1, Integer.parseInt(TAB_List.getModel().getValueAt(rowIndex,0).toString()));
                    stm.execute();
                    System.out.println("Suppression effectuer");
                }
            }
            catch (SQLException sqle){
                System.err.println(sqle.getMessage());
            }
            }
        });

        //endregion

        //region circuit

        //List circuit
        BT_CIRList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel model = (DefaultTableModel) TAB_List.getModel();
                    model.setRowCount(0);

                    String[] tableColumnsName = {"ID","Nom","Depart","Fin","Prix","Duree", "Clients maximum"};
                    DefaultTableModel aModel = (DefaultTableModel) TAB_List.getModel();
                    aModel.setColumnIdentifiers(tableColumnsName);

                    java.sql.Connection connexion = Connection.get();
                    CallableStatement stm = connexion.prepareCall("{? = call PKGCIRCUITS.LISTER}");
                    stm.registerOutParameter(1, OracleTypes.CURSOR);
                    stm.execute();
                    ResultSet result = (ResultSet)stm.getObject(1);
                    java.sql.ResultSetMetaData rsmd = result.getMetaData();
                    int colNo = rsmd.getColumnCount();
                    while(result.next()){
                        Object[] objects = new Object[colNo];
                        for(int i=0;i<colNo;i++){
                            objects[i]=result.getObject(i+1);
                        }
                        aModel.addRow(objects);
                    }
                    TAB_List.setModel(aModel);
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });
        //Add circuit
        BT_CAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("AddCircuitForm");
                frame.setContentPane(new AddCircuitForm().PAN_Circuits);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        //Delete circuits
        BT_CDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (TAB_List.getSelectedRowCount() > 0) {
                        int rowIndex = TAB_List.getSelectedRow();

                        java.sql.Connection connexion = Connection.get();
                        CallableStatement stm = connexion.prepareCall("{call PKGCIRCUITS.SUPPRIMER(?)}");
                        stm.setInt(1, Integer.parseInt(TAB_List.getModel().getValueAt(rowIndex,0).toString()));
                        stm.execute();
                        System.out.println("Suppression effectuer");
                    }
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });
        //Modify circuits
        BT_CModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if (TAB_List.getSelectedRowCount() > 0) {
                        int rowIndex = TAB_List.getSelectedRow();
                        JFrame frame = new JFrame("AddCircuitForm");
                        frame.setContentPane(new AddCircuitForm(Integer.parseInt(TAB_List.getModel().getValueAt(rowIndex,0).toString())).PAN_Circuits);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                        //new AddCircuitForm().setTextField(Integer.parseInt(TAB_List.getModel().getValueAt(rowIndex,0).toString()));

                    }
                }
        });
        //Search circuits per name
        BT_SearchCircuits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel model = (DefaultTableModel) TAB_List.getModel();
                    model.setRowCount(0);

                    String[] tableColumnsName = {"Nom", "Depart", "Fin", "Prix", "Duree", "Clients maximum"};
                    DefaultTableModel aModel = (DefaultTableModel) TAB_List.getModel();
                    aModel.setColumnIdentifiers(tableColumnsName);

                    java.sql.Connection connexion = Connection.get();
                    CallableStatement stm = connexion.prepareCall("{? = call PKGCIRCUITS.RECHERCHER(?)}");
                    stm.registerOutParameter(1, OracleTypes.CURSOR);
                    stm.setString(2, TF_CircuitsName.getText() + "%");
                    stm.execute();
                    ResultSet result = (ResultSet) stm.getObject(1);
                    java.sql.ResultSetMetaData rsmd = result.getMetaData();
                    int colNo = rsmd.getColumnCount();
                    while(result.next()){
                        Object[] objects = new Object[colNo];
                        for(int i=0;i<colNo;i++){
                            objects[i]=result.getObject(i+1);
                        }
                        aModel.addRow(objects);
                    }
                    TAB_List.setModel(aModel);

                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });

        //endregion

        //region Reservations

        //ADD RESERVATIONS
        BT_ClientsPerCircuits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel model = (DefaultTableModel) TAB_List.getModel();
                    model.setRowCount(0);

                    String[] tableColumnsName = {"Nom", "Prenom"};
                    DefaultTableModel aModel = (DefaultTableModel) TAB_List.getModel();
                    aModel.setColumnIdentifiers(tableColumnsName);

                    java.sql.Connection connexion = Connection.get();
                    CallableStatement stm = connexion.prepareCall("{? = call PKGCIRCUITS.LISTS(?)}");
                    stm.registerOutParameter(1, OracleTypes.CURSOR);
                    stm.setString(2, TF_CircuitsName.getText());
                    stm.execute();
                    ResultSet result = (ResultSet) stm.getObject(1);
                    java.sql.ResultSetMetaData rsmd = result.getMetaData();
                    int colNo = rsmd.getColumnCount();
                    while(result.next()){
                        Object[] objects = new Object[colNo];
                        for(int i=0;i<colNo;i++){
                            objects[i]=result.getObject(i+1);
                        }
                        aModel.addRow(objects);
                    }
                    TAB_List.setModel(aModel);

                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });
        BT_RAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("AddReservationForm");
                frame.setContentPane(new AddReservationForm().PAN_Reservation);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        //lISTER RESERVATIONS
        BT_RList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel model = (DefaultTableModel) TAB_List.getModel();
                    model.setRowCount(0);

                    String[] tableColumnsName = {"ID","ID Client","ID Circuit","DateReservation","DateLimite"};
                    DefaultTableModel aModel = (DefaultTableModel) TAB_List.getModel();
                    aModel.setColumnIdentifiers(tableColumnsName);

                    java.sql.Connection connexion = Connection.get();
                    CallableStatement stm = connexion.prepareCall("{? = call PKGRESERVATIONS.LISTER}");
                    stm.registerOutParameter(1, OracleTypes.CURSOR);
                    stm.execute();
                    ResultSet result = (ResultSet)stm.getObject(1);
                    java.sql.ResultSetMetaData rsmd = result.getMetaData();
                    int colNo = rsmd.getColumnCount();
                    while(result.next()){
                        Object[] objects = new Object[colNo];
                        for(int i=0;i<colNo;i++){
                            objects[i]=result.getObject(i+1);
                        }
                        aModel.addRow(objects);
                    }
                    TAB_List.setModel(aModel);
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });
        //MODIFY RESERVATIONS
        BT_RModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TAB_List.getSelectedRowCount() > 0) {
                    int rowIndex = TAB_List.getSelectedRow();
                    JFrame frame = new JFrame("AddCircuitForm");
                    frame.setContentPane(new AddReservationForm(Integer.parseInt(TAB_List.getModel().getValueAt(rowIndex,0).toString())).PAN_Reservation);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);

                }
            }
        });
        //DELETE RESERVATIONS
        BT_RDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (TAB_List.getSelectedRowCount() > 0) {
                        int rowIndex = TAB_List.getSelectedRow();

                        java.sql.Connection connexion = Connection.get();
                        CallableStatement stm = connexion.prepareCall("{call PKGRESERVATIONS.SUPPRIMER(?)}");
                        stm.setInt(1, Integer.parseInt(TAB_List.getModel().getValueAt(rowIndex,0).toString()));
                        stm.execute();
                        System.out.println("Suppression effectuer");
                        Date date = new Date();
                    }
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });

        //endregion

        //region MONUMENTS

        //LIST MONUMENTS
        BT_MList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (TAB_List.getSelectedRowCount() > 0) {
                        int rowIndex = TAB_List.getSelectedRow();

                        java.sql.Connection connexion = Connection.get();
                        stm = connexion.prepareCall("{? = call PKGMONUMENTS.LISTER(?)}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        stm.registerOutParameter(1, OracleTypes.CURSOR);
                        stm.setInt(2, Integer.parseInt(TAB_List.getModel().getValueAt(rowIndex,0).toString()));
                        stm.execute();
                        result = (ResultSet)stm.getObject(1);
                        result.next();
                        TF_MName.setText(result.getObject(1).toString());
                        TF_MDate.setText(result.getObject(2).toString());
                        TA_MHistoire.setText(result.getClob(3).toString());
                    }
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });
        //NEXT MONUMENTS
        BT_Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    result.next();
                    TF_MName.setText(result.getObject(1).toString());
                    TF_MDate.setText(result.getObject(2).toString());
                    TA_MHistoire.setText(result.getClob(3).toString());
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });

        //PREVIOUS MONUMENTS
        BT_Previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    result.previous();
                    TF_MName.setText(result.getObject(1).toString());
                    TF_MDate.setText(result.getObject(2).toString());
                    TA_MHistoire.setText(result.getClob(3).toString());
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });
        //endregion
    }
}
