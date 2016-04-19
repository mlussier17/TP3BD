import oracle.jdbc.OracleTypes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private JTable TB_Lists;
    private JButton BT_CIRList;

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
        BT_CList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] tableColumnsName = {"col 1","col 2","col 3"};
                    DefaultTableModel aModel = (DefaultTableModel) TB_Lists.getModel();
                    aModel.setColumnIdentifiers(tableColumnsName);

                    java.sql.Connection connexion = Connection.get();
                    CallableStatement stm = connexion.prepareCall("{? = call PKGCLIENTS.LISTER}");
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
                    TB_Lists.setModel(aModel);
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });
        BT_CLDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (TB_Lists.getSelectedRowCount() > 0) {
                        //TB_Lists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        int rowIndex = TB_Lists.getSelectedRow();

                        java.sql.Connection connexion = Connection.get();
                        CallableStatement stm = connexion.prepareCall("{call PKGCLIENTS.REMOVE(?)}");
                        stm.setInt(1, Integer.parseInt(TB_Lists.getModel().getValueAt(rowIndex,0).toString()));
                        stm.execute();
                        System.out.println("Suppression effectuer");
                    }
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });
        BT_CIRList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] tableColumnsName = {"col 1","col 2","col 3"};
                    DefaultTableModel aModel = (DefaultTableModel) TB_Lists.getModel();
                    aModel.setColumnIdentifiers(tableColumnsName);

                    java.sql.Connection connexion = Connection.get();
                    CallableStatement stm = connexion.prepareCall("{? = call PKGCIRCUITS.LISTS}");
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
                    TB_Lists.setModel(aModel);
                }
                catch (SQLException sqle){
                    System.err.println(sqle.getMessage());
                }
            }
        });
    }
}
