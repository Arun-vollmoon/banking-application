public class AccountValidation {

    public static boolean validateAccountNo(String accNo) {

        if (!accNo.matches("\\d{10}")) {
            System.out.println("Account number must contain exactly 10 digits.");
            return false;
        }

        return true;
    }

}
