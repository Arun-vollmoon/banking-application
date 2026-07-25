import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class login {

    public boolean loginUser(String uname, String password) {

        String sql = "UPDATE Customer_Management SET login = true WHERE name = ? AND password = ?";

        try (Connection con = Database_connection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uname);
            ps.setString(2, password);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Login successful.. Welcome, Mr " + uname);
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