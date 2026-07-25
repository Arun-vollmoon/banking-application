import java.sql.Connection;
import java.sql.DriverManager;

public class Database_connection {
    public static Connection getconnection() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
            return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","Arun@2200");
    }
}
