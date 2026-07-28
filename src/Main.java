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
        UserProfile userProfile=new UserProfile();
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
                       } while (!UserValidation.ValidateName(name));
                       String dob;
                       do {
                           System.out.print("Enter DOB (yyyy-MM-dd): ");
                           dob = sc.next();
                       } while (!UserValidation.validateDOB(dob));
                       String password;
                       do {
                           System.out.print("Enter password: ");
                           password = sc.next();
                       } while (!UserValidation.validatePassword(password));
                       String role;
                       do {
                           System.out.print("Enter role (user/admin/org_admin): ");
                           role = sc.next();
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
                       4. Logout
                       Enter choice: """);

               int choice = sc.nextInt();
               switch (choice) {

                   case 1:
//                    Account creation
                       System.out.println("-------Account creation--------");
                       String accNum;
                       do {
                           System.out.print("Enter account number: ");
                           accNum = sc.next();
                       } while (!AccountValidation.validateAccountNo(accNum));
                       double balance;
                       do {
                           System.out.print("Enter initial balance: ");
                           balance = sc.nextDouble();
                       } while (!TransactionValidation.validateAmount(balance));

                       System.out.print("Enter customer user ID: ");
                       int userId = sc.nextInt();

                       accountCreation.createAccount(session.getRole(), accNum, balance, userId);
                       break;
                   case 2:
//                    change password for admin and org_admin
                       System.out.println("-------Change password--------");
                       String newPass;
                       do {
                           System.out.print("Enter new password: ");
                          newPass = sc.next();
                       }while (!UserValidation.validatePassword(newPass));
                       userService.PasswordChange(session.getUserId(), newPass);
                            break;
                   case 3:
                       System.out.println("-------Delete account--------");
                       String account ;
                       do {
                           System.out.print("Enter account number: ");
                           account = sc.next();
                       } while (!AccountValidation.validateAccountNo(account));
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
                       6. view User details
                       7. Logout
                        Enter choice:""");

               int choice = sc.nextInt();
               switch (choice) {
                   case 1:
//               amount deposit
                       System.out.println("-------Deposit--------");
                       double amount;
                       do {
                           System.out.print("Enter amount to deposit: ");
                           amount = sc.nextDouble();
                       } while (!TransactionValidation.validateAmount(amount));

                       moneyOpretion.depositmoney(session.getUserId(), amount);
                       break;
                   case 2:
//               amount withdraw
                       System.out.println("-------Withdraw--------");
                       double amountWithdrow;
                       do {
                           System.out.print("Enter amount to deposit: ");
                           amountWithdrow = sc.nextDouble();
                       } while (!TransactionValidation.validateAmount(amountWithdrow));
                       moneyOpretion.Withdraw(session.getUserId(), amountWithdrow);
                       break;
                   case 3:
//                       fund trancefer
                       System.out.println("-------Fund trancfer--------");
                       System.out.print("enter account number(Which account you send): ");
                       String receiverAcc;
                       do {
                           receiverAcc = sc.next();
                       } while (!AccountValidation.validateAccountNo(receiverAcc));

                       double amounttransfer;
                       do {
                           System.out.print("Enter how mush you transfer: ");
                           amounttransfer = sc.nextDouble();
                       } while (!TransactionValidation.validateAmount(amounttransfer));
                       moneyOpretion.transferFunds(session.getUserId(), receiverAcc, amounttransfer);
                       break;
                   case 4:
//                       check balance
                       System.out.println("-------Check balance--------");
                       moneyOpretion.checkbalance(session.getUserId());
                       break;

                   case 5:
//                       change password
                       System.out.println("-------Change password--------");
                       String newPass;
                       do {
                           System.out.print("Enter new password: ");
                            newPass = sc.next();
                       }while (!UserValidation.validatePassword(newPass));

                       userService.PasswordChange(session.getUserId(), newPass);
                       break;
                   case 6:
                       System.out.println("-----------User profile------------");
                       userProfile.UserProfile(session.getUserId());
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