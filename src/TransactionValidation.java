public class TransactionValidation {

    public static boolean validateAmount(double amount) {

        if (amount <= 0) {
            System.out.println("negative method not accept.");
            return false;
        }

        return true;
    }

}