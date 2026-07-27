import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection getconnection() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
            return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/BankingApplication","root","Arun@2200");
    }
}
