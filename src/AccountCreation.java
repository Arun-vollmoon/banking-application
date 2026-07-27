import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountCreation {

    public boolean createAccount(String loggedInRole, String accountNo, double initialBalance, int userId) {

        if (initialBalance < 0) {
            System.out.println("Account creation failed: Initial balance cannot be negative.");
            return false;
        }

        String checkUserSql = "SELECT user_id FROM users WHERE user_id = ?";
        String insertAccountSql = "INSERT INTO account (account_no, balance, user_id) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getconnection()) {

            try (PreparedStatement checkPs = con.prepareStatement(checkUserSql)) {
                checkPs.setInt(1, userId);
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Account creation failed: User ID " + userId + " does not exist.");
                        return false;
                    }
                }
            }

            try (PreparedStatement insertPs = con.prepareStatement(insertAccountSql)) {
                insertPs.setString(1, accountNo);
                insertPs.setDouble(2, initialBalance);
                insertPs.setInt(3, userId);

                int rowsInserted = insertPs.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("--------Account created successfully----------");
                    System.out.println("Account Number: " + accountNo);
                    System.out.println("User ID: " + userId);
                    System.out.println("Initial Balance: " + initialBalance);
                    return true;
                } else {
                    System.out.println("Account creation failed.");
                    return false;
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error during account creation: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteAccount(String accountNo) {

        String sql = "DELETE FROM account WHERE account_no = ?";

        try (Connection con = DatabaseConnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accountNo);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Account deleted successfully.");
                return true;
            } else {
                System.out.println("Account not found.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            return false;
        }
    }
}