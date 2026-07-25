import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class changePassword {
    public void passwordChange(int id,String nPassword) throws SQLException {
        String sql = "UPDATE Customer_Management SET password = ? WHERE id=?";

        try (Connection con = Database_connection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,nPassword);
            ps.setInt(2,id);
            int rows = ps.executeUpdate();
            if (rows<0){
                System.out.println("password update successfuly");
            }else {
                System.out.println("Password update is not complted:your account/no is invalid ");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
