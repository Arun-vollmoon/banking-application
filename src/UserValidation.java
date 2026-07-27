import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UserValidation{
    public static boolean ValidateName(String name){
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }
        if (!name.matches("[A-Za-z ]+")) {
            System.out.println("Name should contain only alphabets.");
            return false;
        }
        return true;
    }
    public static boolean validateDOB(String dob) {

        try {
            LocalDate.parse(dob);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid DOB. Enter in yyyy-MM-dd format.");
            return false;
        }
    }
    public static boolean validatePassword(String password) {

        if (password.length() < 8) {
            System.out.println("Password must contain at least 8 characters.");
            return false;
        }

        return true;
    }
    public static boolean validateRole(String role) {

        if (role.equalsIgnoreCase("user") ||
                role.equalsIgnoreCase("admin") ||
                role.equalsIgnoreCase("org_admin")) {

            return true;
        }

        System.out.println("Invalid Role.");
        System.out.println("Allowed roles are:");
        System.out.println("user");
        System.out.println("admin");
        System.out.println("org_admin");

        return false;
    }


}
