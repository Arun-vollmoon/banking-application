import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class fund_transfer {
    public void transferFunds(String senderAccNo, String receiverAccNo, double amount) {


        String checkBalanceSql = "SELECT balance FROM account WHERE account_no = ?";
        String deductSql = "UPDATE account SET balance = balance - ? WHERE account_no = ?";
        String addSql = "UPDATE account SET balance = balance + ? WHERE account_no = ?";

        Connection con=null;

        try {
            con = Database_connection.getconnection();

            con.setAutoCommit(false);

            try (PreparedStatement checkPs = con.prepareStatement(checkBalanceSql)) {
                checkPs.setString(1, senderAccNo);
                ResultSet rs = checkPs.executeQuery();

                if (!rs.next()) {
                    System.out.println(" Sender account does not exist.");
                    con.rollback();
                    return;
                }

                double currentBalance = rs.getDouble("balance");
                if (currentBalance < amount) {
                    System.out.println("insuficient balance Available balance: " + currentBalance);
                    con.rollback();
                    return;
                }
            }

            try (PreparedStatement deductPs = con.prepareStatement(deductSql)) {
                deductPs.setDouble(1, amount);
                deductPs.setString(2, senderAccNo);
                deductPs.executeUpdate();
            }

            int receiverCount;
            try (PreparedStatement addPs = con.prepareStatement(addSql)) {
                addPs.setDouble(1, amount);
                addPs.setString(2, receiverAccNo);
                receiverCount = addPs.executeUpdate();
            }

            if (receiverCount == 0) {
                System.out.println("reciver  account not found. rolling back transaction");
                con.rollback();
                return;
            }

            con.commit();
            System.out.println("Transfer Successful! $" + amount + " transferred from " + senderAccNo + " to " + receiverAccNo);

        } catch (SQLException e) {
            System.out.println("transaction failed due to database error: " + e.getMessage());
            if (con != null) {
                try {
                    System.out.println("Rolling back database changes...");
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
