import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Customer_registration cr = new Customer_registration();
        deposit d=new deposit();
        login lo =new login();
        logout log=new logout();
        fund_transfer fun=new fund_transfer();
        changePassword ch=new changePassword();
        while (true){
            System.out.print(""" 
             --------Banking applicaton----------
                    1.Registration
                    2.Login
                    3.deposit
                    4.fund transfer
                    5.check balance
                    6.Change Password
                    7.Logout
                    Enter choice""");
            int n= sc.nextInt();
            switch (n){
                case 1:
                    System.out.println("------your are creating a Account------");
                    System.out.print("Enter Name:");
                    String name=sc.next();
                    System.out.print("Enter DOB (yyyy-MM-dd):");
                    String DOB=sc.next();
                    System.out.print("Enter password:");
                    String password=sc.next();
                    System.out.print("Enter your role(user/admin/org_admin):");
                    String role=sc.next();
                    cr.custom_register(name,DOB,password,role);
                    break;
                case 2:
                    System.out.println("------Login--------");
                    System.out.print("Enter User name:");
                    String Uname=sc.next();
                    System.out.print("Enter password:");
                    String pass= sc.next();
                    lo.loginUser(Uname,pass);
                    break;
                case 3:
                    System.out.println("-------Deposit------");
                    System.out.print("Enter your id:");
                    int iddeposit =sc.nextInt();
                    System.out.print("How mutch you deposit:");
                    double amount= sc.nextDouble();
                    d.depositmoney(iddeposit,amount);
                    break;
                case 4:
                    System.out.println("-------fund transfer-------");
                    System.out.print("Enter your account number:");
                    String account_num= sc.next();
                    System.out.print("Enter which account  you are send money:");
                    String reciveracc_num= sc.next();
                    System.out.print("how mush you transfer..:");
                    double amount_= sc.nextDouble();
                    fun.transferFunds(account_num,reciveracc_num,amount_);
                    break;


                case 5:
                    System.out.println("------check balance-------");
                    System.out.println(" Enter your Account number:");
                    String account_no=sc.next();
                    d.checkbalance(account_no);
                    break;
                case 6:
                    System.out.println("------Change Password-------");
                    System.out.println(" Enter your user id:");
                    int idchange=sc.nextInt();
                    System.out.println("Enter your new password");
                    String newpassword=sc.next();
                    ch.passwordChange(idchange,newpassword);
                    break;
                case 7:
                    System.out.println("------Logout--------");
                    System.out.println(" Enter your user id:");
                    int id=sc.nextInt();
                    log.logoutuser(id);
                    break;

                default:
                    System.out.println("invalid charcter");
                    break;
            }



        }

    }
}