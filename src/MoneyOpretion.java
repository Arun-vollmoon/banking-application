import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyOpretion {
    public void depositmoney(int user_id, double depositAmount) {

        String uSql = "UPDATE account SET balance = balance + ? WHERE user_id = ?";
        String sSql = "SELECT balance FROM account WHERE user_id = ?";

        try (Connection con = DatabaseConnection.getconnection();
             PreparedStatement uPs = con.prepareStatement(uSql)) {

            uPs.setDouble(1, depositAmount);
            uPs.setInt(2, user_id);

            int rows = uPs.executeUpdate();

            if (rows > 0) {
                try (PreparedStatement sPs = con.prepareStatement(sSql)) {
                    sPs.setInt(1, user_id);
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
                System.out.println("Deposit failed: Account ID " + user_id + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Database error during deposit: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void Withdraw(int user_id, double WithdrawAmount) {

        String uSql = "UPDATE account SET balance = balance - ? WHERE user_id = ?";
        String sSql = "SELECT balance FROM account WHERE user_id = ?";

        try (Connection con = DatabaseConnection.getconnection();
             PreparedStatement uPs = con.prepareStatement(uSql)) {

            uPs.setDouble(1, WithdrawAmount);
            uPs.setInt(2, user_id);

            int rows = uPs.executeUpdate();

            if (rows > 0) {
                try (PreparedStatement sPs = con.prepareStatement(sSql)) {
                    sPs.setInt(1, user_id);
                    try (ResultSet rs = sPs.executeQuery()) {
                        if (rs.next()) {
                            double nBalance = rs.getDouble("balance");
                            System.out.println(" your amount deposit Successfuly");
                            System.out.println("Withdraw amount: " + WithdrawAmount);
                            System.out.println("Current balance: " + nBalance);
                        }
                    }
                }
            } else {
                System.out.println("Withdraw failed: Account ID " + user_id + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Database error during Withdraw: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void transferFunds(int senderAccNo, String receiverAccNo, double amount) {
        String checkBalanceSql = "SELECT balance FROM account WHERE user_id = ?";
        String deductSql = "UPDATE account SET balance = balance - ? WHERE user_id = ?";
        String addSql = "UPDATE account SET balance = balance + ? WHERE account_no = ?";
        Connection con=null;
        try {
            con = DatabaseConnection.getconnection();
            con.setAutoCommit(false);
            try (PreparedStatement checkPs = con.prepareStatement(checkBalanceSql)) {
                checkPs.setInt(1, senderAccNo);
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
                deductPs.setInt(2, senderAccNo);
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
            System.out.println("Transfer Successful " + amount + " to " + receiverAccNo);

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
    void checkbalance(int user_id) throws Exception {
        String checkBalanceSql = "SELECT balance FROM account WHERE user_id = ?";

        try (Connection con = DatabaseConnection.getconnection();
             PreparedStatement uPs = con.prepareStatement(checkBalanceSql)) {
            uPs.setInt(1,user_id);
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
