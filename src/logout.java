import java.sql.Connection;
import java.sql.PreparedStatement;

public class logout {
    public boolean logoutuser(int id){
        String sql = "UPDATE Customer_Management SET login = false WHERE id=?";

        try (Connection con = Database_connection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt( 1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("your account is logout");
                return true;
            } else {
                System.out.println("Login failed: Invalid Username or Password.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }
}
