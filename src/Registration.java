import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Registration {

    public void Register(String name, String dob, String password, String role) throws Exception {
        String registerInsert = "INSERT INTO users (name, password, dob) VALUES (?, ?, ?)";
        String getRole = "SELECT role_id FROM roles WHERE role_name = ?";
        String rowAdd = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";

        Connection con = null;

        try {
            con = DatabaseConnection.getconnection();
            con.setAutoCommit(false);

            int userId = -1;
            int roleId = -1;

            try (PreparedStatement psRegister = con.prepareStatement(registerInsert, Statement.RETURN_GENERATED_KEYS)) {
                psRegister.setString(1, name);
                psRegister.setString(2, password);
                psRegister.setString(3, dob);

                int rows = psRegister.executeUpdate();
                if (rows > 0) {
                    try (ResultSet rs = psRegister.getGeneratedKeys()) {
                        if (rs.next()) {
                            userId = rs.getInt(1);
                        }
                    }
                }
            }

            try (PreparedStatement psGetRole = con.prepareStatement(getRole)) {
                psGetRole.setString(1, role.toLowerCase());
                try (ResultSet rs = psGetRole.executeQuery()) {
                    if (rs.next()) {
                        roleId = rs.getInt("role_id");
                    } else {
                        System.out.println("Registration failed: Role '" + role + "' does not exist.");
                        con.rollback();
                        return;
                    }
                }
            }

            if (userId != -1 && roleId != -1) {
                try (PreparedStatement psRowAdd = con.prepareStatement(rowAdd)) {
                    psRowAdd.setInt(1, userId);
                    psRowAdd.setInt(2, roleId);
                    psRowAdd.executeUpdate();
                }

                con.commit();
                System.out.println("Account created successfuly Welcome, " + name + ". Your ID is: " + userId);
            } else {
                con.rollback();
                System.out.println("Failed to create account.");
            }

        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Database error during registration: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (Exception closeEx) {
                    System.err.println("Error closing connection: " + closeEx.getMessage());
                }
            }
        }
    }
}