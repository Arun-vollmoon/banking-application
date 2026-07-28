import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserProfile {
    public void UserProfile(int userID) throws Exception {
        String sql = "select u.user_id, u.name,u.password, r.role_name from users u join user_roles ur on u.user_id = ur.user_id join roles r on ur.role_id = r.role_id where u.user_id = ?";
        try (Connection con = DatabaseConnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String role = rs.getString("role_name");
                String password = rs.getString("password");
                String userName = rs.getString("name");

                System.out.println("Your Id:"+userId);
                System.out.println("Your User Name:"+userName);
                System.out.println("Your Password:"+password);
                System.out.println("Your Role:"+role);
            }else {
                System.out.println("Your details not found");
            }
            }catch (Exception e){
            System.out.println("Error :"+e);
        }
        }
    }
