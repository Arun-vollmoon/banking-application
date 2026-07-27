import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService implements LoginInterface{
    @Override
    public UserSession loginUser(String username, String password) throws Exception {
        Connection con=null;
         con = DatabaseConnection.getconnection();
        String sql = "select u.user_id, u.name, r.role_name " +
                "from users u " +
                "join user_roles ur ON u.user_id = ur.user_id " +
                "join roles r ON ur.role_id = r.role_id " +
                "where u.name = ? AND u.password = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String role = rs.getString("role_name");

                PreparedStatement updatePs = con.prepareStatement(
                        "update users set is_logged_in = true where user_id = ?"
                );
                updatePs.setInt(1, userId);
                updatePs.executeUpdate();

                System.out.println("login successful. Welcome, " + username + " " + role );
                return new UserSession(userId, username, role);
            } else {
                System.out.println("login failed: Invalid Username or Password.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("database error during login: " + e.getMessage());
            con.rollback();
            return null;
        }
    }
    @Override
    public void PasswordChange(int id, String nPassword) throws SQLException {
        String sql = "update users set  password = ? WHERE user_id=?";

        try (Connection con = DatabaseConnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,nPassword);
            ps.setInt(2,id);
            int rows = ps.executeUpdate();
            if (rows>0){
                System.out.println("password update successfuly");
            }else {
                System.out.println("Password update is not complted:your account/no is invalid ");
            }
        } catch (Exception e) {
            throw new RuntimeException("Database error"+e.getMessage());
        }
    }

    @Override
    public boolean logout(int UserId) {
        String sql = "update users set is_logged_in = false where user_id=?";

        try (Connection con = DatabaseConnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt( 1, UserId);

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