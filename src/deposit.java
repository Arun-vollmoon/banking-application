import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class deposit {

    public void depositmoney(int accNumber, double depositAmount) {

        String uSql = "UPDATE account SET balance = balance + ? WHERE acc_id = ?";
        String sSql = "SELECT balance FROM account WHERE acc_id = ?";

        try (Connection con = Database_connection.getconnection();
             PreparedStatement uPs = con.prepareStatement(uSql)) {

            uPs.setDouble(1, depositAmount);
            uPs.setInt(2, accNumber);

            int rows = uPs.executeUpdate();

            if (rows > 0) {
                try (PreparedStatement sPs = con.prepareStatement(sSql)) {
                    sPs.setInt(1, accNumber);
                    try (ResultSet rs = sPs.executeQuery()) {
                        if (rs.next()) {
                            double nBalance = rs.getDouble("balance");
                            System.out.println(" your amount deposit Successfuly");
                            System.out.println("Deposited amount: " + depositAmount);
                            System.out.println("Current balance: " + nBalance);
                        }
                    }
                }
            } else {
                System.out.println("Deposit failed: Account ID " + accNumber + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Database error during deposit: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    void checkbalance(String account_no) throws Exception {
        String checkBalanceSql = "SELECT balance FROM account WHERE account_no = ?";

        try (Connection con = Database_connection.getconnection();
             PreparedStatement uPs = con.prepareStatement(checkBalanceSql)) {
            uPs.setString(1,account_no);
            ResultSet rs =uPs.executeQuery();

            if (!rs.next()) {
                System.out.println(" this account does not exist.");
                con.rollback();
                return;
            }

            double currentBalance = rs.getDouble("balance");
            System.out.println("Your account balance is :"+currentBalance);
        }
    }
}