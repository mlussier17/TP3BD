import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by 196128636 on 2016-04-15.
 */
public class Connection {
    static java.sql.Connection conn = null;
    public static void connect(){

        String bd = "jdbc:oracle:thin:@mercure.clg.qc.ca:1521:orcl";
        String user = "STJACQUE";
        String pass = "oracle2";

        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(bd, user, pass);
            System.out.println("Connected mofo");
        }
        catch (SQLException sqle){
            System.err.println(sqle.getMessage());
        }
    }

    public static void disconnect(){
        try{
            conn.close();
            System.out.println("Disconnected mofo");
        }
        catch (NullPointerException npe){
            npe.printStackTrace();
        }
        catch(SQLException sqle){
            sqle.getMessage();
        }
    }
}
