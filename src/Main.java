import java.util.Scanner;

/***
 *
 */
public class Main {
    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Registration registration= new Registration();
        UserService userService= new UserService();
        AccountCreation accountCreation=new AccountCreation();
        MoneyOpretion moneyOpretion=new MoneyOpretion();
        UserSession session = null;
        //entrance
       while (true) {
           System.out.println("----------Banking Application----------------");
           if (session == null) {
               System.out.print("""
                       1.Registration
                       2.login
                       3.Exit
                       Enter choice:
                       """);
               int choice = sc.nextInt();
               switch (choice) {
                   case 1:
                       System.out.println("------ Account Registration ------");
                       String name;
                       do {
                           System.out.print("Enter Name: ");
                           name = sc.next().trim();
                           if (!UserValidation.ValidateName(name)) {
                               System.out.print("enter valid name:");
                           }
                       } while (!UserValidation.validateDOB(name));
                       String dob;
                       do {
                           System.out.print("Enter DOB (yyyy-MM-dd): ");
                           dob = sc.next();
                           if (!UserValidation.validateDOB(dob)) {
                               System.out.print("Enter valid DOB:");
                           }
                       } while (!UserValidation.validateDOB(dob));
                       String password;
                       do {
                           System.out.print("Enter password: ");
                           password = sc.next();
                           if (!UserValidation.validatePassword(password)) {
                               System.out.print("Enter valid DOB:");
                           }
                       } while (!UserValidation.validatePassword(password));
                       String role;
                       do {
                           System.out.print("Enter role (user/admin/org_admin): ");
                           role = sc.next();
                           if (!UserValidation.validateRole(role)){
                               System.out.println("Enter valid Role:");
                           }
                       } while (!UserValidation.validateRole(role));

                       registration.Register(name, dob, password, role);
                       break;
                   case 2:
//                    login
                       System.out.println("------ Login ------");
                       System.out.print("Enter Username: ");
                       String uname = sc.next();
                       System.out.print("Enter Password: ");
                       String pass = sc.next();
                       session = userService.loginUser(uname, pass);
                       break;
                   case 3:
//                    exit
                       System.out.println("------------Exiting Application-----------");
                       sc.close();
                       return;
                   default:
                       System.out.println("Invalid option. Try again.");
               }
//            admin and org_admin methods
           } else if ("admin".equalsIgnoreCase(session.getRole()) || "org_admin".equalsIgnoreCase(session.getRole())) {
               System.out.println("-------- ADMIN MENU " + session.getUserName() + "------");
               System.out.print("""
                       1. Create Customer Account
                       2. Change Password
                       3. delete Account
                       3. Logout
                       Enter choice: """);

               int choice = sc.nextInt();
               switch (choice) {

                   case 1:
//                    Account creation
                       System.out.println("-------Account creation--------");
                       System.out.print("Enter account number: ");
                       String accNum = sc.next();
                       System.out.print("Enter initial balance: ");
                       double balance = sc.nextDouble();
                       System.out.print("Enter customer user ID: ");
                       int userId = sc.nextInt();
                       accountCreation.createAccount(session.getRole(), accNum, balance, userId);
                       break;
                   case 2:
//                    change password for admin and org_admin
                       System.out.println("-------Change password--------");
                       System.out.print("Enter new password: ");
                       String newPass = sc.next();
                       userService.PasswordChange(session.getUserId(), newPass);
                       break;
                   case 3:
                       System.out.println("-------Delete account--------");
                       System.out.print("Enter your account number: ");
                       String account = sc.next();
                       accountCreation.deleteAccount(account);
                       break;
                   case 4:
//                    logout admin
                       System.out.println("-------Admin logout--------");
                       userService.logout(session.getUserId());
                       System.out.println("Logged out successfuly.");
                       session = null;
                       break;

                   default:
                       System.out.println("Invalid option.");
                       break;
               }
           } else {
               System.out.println("------- USER MENU " + session.getUserName() + "------");
               System.out.println("""
                       1. Deposit Money
                       2. Withdraw Money
                       3. Fund Transfer
                       4. Check Balance
                       5. Change Password
                       6. Logout
                        Enter choice:""");

               int choice = sc.nextInt();
               switch (choice) {
                   case 1:
//               amount deposit
                       System.out.println("-------Deposit--------");
                       System.out.print("Enter amount to deposit: ");
                       double amount = sc.nextDouble();
                       moneyOpretion.depositmoney(session.getUserId(), amount);
                       break;
                   case 2:
//               amount withdraw
                       System.out.println("-------Withdraw--------");
                       System.out.print("Enter amount to deposit: ");
                       double amountWithdrow = sc.nextDouble();
                       moneyOpretion.Withdraw(session.getUserId(), amountWithdrow);
                       break;
                   case 3:
//                       fund trancefer
                       System.out.println("-------Fund trancfer--------");
                       System.out.print("Enter your account number: ");
                       String senderAcc = sc.next();
                       System.out.print("Enter receiver account number: ");
                       String receiverAcc = sc.next();
                       System.out.print("Enter transfer amount: ");
                       double amount1 = sc.nextDouble();
                       moneyOpretion.transferFunds(senderAcc, receiverAcc, amount1);
                       break;
                   case 4:
//                       check balance
                       System.out.println("-------Check balance--------");
                       System.out.print("Enter your account number: ");
                       String accNo = sc.next();
                       moneyOpretion.checkbalance(accNo);
                       break;


                   case 6:
//                       change password
                       System.out.println("-------Change password--------");
                       System.out.print("Enter new password: ");
                       String newPass = sc.next();
                       userService.PasswordChange(session.getUserId(), newPass);
                       break;
                   case 7:
//                       logout
                       System.out.println("-------logout--------");
                       userService.logout(session.getUserId());
                       session = null;
                       System.out.println("Logged out successfully.");
                       break;
                   default:
                       System.out.println("Invalid option.");
                       break;
               }


           }


       }
    }
}