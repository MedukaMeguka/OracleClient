package sample;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;

public class DBConnection {
    public Connection Connect(String user, String password) {

        Locale.setDefault(Locale.ENGLISH);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", user, password);

            return conn;
        }
        catch (Exception ex) { return null;}
    }
}
