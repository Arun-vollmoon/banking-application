import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class account_creation {

    public boolean createAccount(String loggedInRole, String accountNo, double initialBalance, int customerId) {
        
        if (!"admin".equalsIgnoreCase(loggedInRole) && !"org_admin".equalsIgnoreCase(loggedInRole)) {
            System.out.println("Access Denied: Only Admin or Org Admin can create accounts.");
            return false;
        }

        if (initialBalance < 0) {
            System.out.println("Account creation failed: Initial balance cannot be negative.");
            return false;
        }

        String checkCustomerSql = "SELECT id FROM Customer_Management WHERE id = ?";
        String insertAccountSql = "INSERT INTO account (account_no, balance, id) VALUES (?, ?, ?)";

        try (Connection con = Database_connection.getconnection()) {

            // 3. Verify Customer ID exists before creating account
            try (PreparedStatement checkPs = con.prepareStatement(checkCustomerSql)) {
                checkPs.setInt(1, customerId);
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Account creation failed: Customer ID " + customerId + " does not exist.");
                        return false;
                    }
                }
            }

            // 4. Insert new account record
            try (PreparedStatement insertPs = con.prepareStatement(insertAccountSql)) {
                insertPs.setString(1, accountNo);
                insertPs.setDouble(2, initialBalance);
                insertPs.setInt(3, customerId);

                int rowsInserted = insertPs.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Account created successfully!");
                    System.out.println("Account Number: " + accountNo);
                    System.out.println("Customer ID: " + customerId);
                    System.out.println("Initial Balance: $" + initialBalance);
                    return true;
                } else {
                    System.out.println("Account creation failed.");
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during account creation: " + e.getMessage());
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}