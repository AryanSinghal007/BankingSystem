import java.sql.*;
import java.util.Scanner;

public class BankingSystem {

    public BankingSystem(){
        startApp();
    }

    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String username = "root";
    private static final String password = "mysql12345";

    public void startApp(){

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            Connection connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Database connected successfully!");
            
            Scanner scanner = new Scanner(System.in);

            User user = new User(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);

            while(true){
                System.out.printf("WELOME TO BANKING SYSTEM\n");
                System.out.printf("1. Register\n");
                System.out.printf("2. Login\n");
                System.out.printf("3. Exit\n");
                System.out.printf("Enter your choice: ");
                int choice = scanner.nextInt();

                
                String email;
                long account_number;

                switch (choice) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        
                        email = user.login();
                        if(email != null){

                            System.out.println("Login successful!");
                            System.out.println("Welcome " + email);

                            if(accounts.account_exist(email)){
                                // logic

                            }else{
                                System.out.println("Account does not exist. Create account? (y/n)");
                                String createAccChoice = scanner.nextLine();

                                if(createAccChoice == "y" || createAccChoice == "Y"){
                                    account_number = accounts.open_account(email);
                                    System.out.println("Account created successfully! Account number: " + account_number);
                                }else{
                                    System.out.println("Exiting...");
                                    System.exit(0);
                                }
                            }
                        }else{
                            System.out.println("Login failed!");
                        }

                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found." + e.getMessage());
            // e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed." + e.getMessage());
            // e.printStackTrace();
        }
    }
}