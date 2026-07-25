import java.sql.Connection;
import java.sql.PreparedStatement;

public class Customer_registration {

    public void custom_register(String name, String dob,String password,String role) {
        String sql = "INSERT INTO Customer_Management (name,password,dob,ROLE) VALUES (?,?,?,?)";

        try (Connection con = (Connection) Database_connection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(3, dob);
            ps.setString(2, password);
            ps.setString(4, role);

//            ps.executeUpdate();
            int row=ps.executeUpdate();
            if (row > 0){
                System.out.println("Your account created successfully MR."+name);
            }else {
                System.out.println("filed to create account");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}