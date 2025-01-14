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
            Accounts accounts = new Accounts(connection);
            AccountManager accountManager = new AccountManager(connection, scanner);
            
            String email;
            long account_number;

            while(true){
                System.out.printf("WELCOME TO BANKING SYSTEM\n");
                System.out.printf("1. Register\n");
                System.out.printf("2. Login\n");
                System.out.printf("3. Exit\n");
                System.out.printf("Enter your choice: ");
                int choice = scanner.nextInt();


                switch (choice) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        
                        email = user.login();
                        if(email != null){

                            // System.out.println("Login successful!");
                            System.out.println("Welcome " + email);

                            if(accounts.account_exist(email)){
                                account_number = accounts.get_account_number(email);
                                System.out.println("Account number: " + account_number);

                                while(true){
                                    System.out.println("===================================");
                                    System.out.println("1. Credit money");
                                    System.out.println("2. Debit money");
                                    System.out.println("3. Transfer money");
                                    System.out.println("4. Check balance");
                                    System.out.println("5. Logout");
                                    System.out.println("===================================");


                                    System.out.printf("Enter your choice: ");
                                    int choice2 = scanner.nextInt();

                                    switch (choice2) {
                                        case 1:
                                            accountManager.credit_money(account_number);
                                            break;
                                        case 2:
                                            accountManager.debit_money(account_number);
                                            break;
                                        case 3:
                                            accountManager.transfer_money(account_number);
                                            break;
                                        case 4:
                                            accountManager.check_balance(account_number);
                                            break;
                                        case 5:
                                            System.out.println("Logging out...");
                                            System.out.println("Thank you for using our Banking system!");
                                            System.exit(0);
                                            break;
                                        default:
                                            System.out.println("Invalid choice");
                                            break;
                                    }

                                }

                            }else{
                                System.out.println("Account does not exist. Create account? (y/n)");
                                Character createAccChoice = scanner.next().charAt(0);

                                if(createAccChoice == 'y' || createAccChoice == 'Y'){
                                    account_number = accounts.open_account(email);
                                    System.out.println("Account created successfully! Account number: " + account_number);


                                    // get user details
                                    String query = "SELECT full_name FROM user WHERE email = ?";
                                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                                    preparedStatement.setString(1, email);

                                    ResultSet resultSet = preparedStatement.executeQuery();
                                    resultSet.next();
                                    String full_name = resultSet.getString("full_name");

                                    // insert into account table
                                    String query2 = "INSERT INTO account(account_no, full_name, email, balance, security_pin) VALUES(?, ?, ?, ?, ?)";
                                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                                    preparedStatement2.setLong(1, account_number);
                                    preparedStatement2.setString(2, full_name);
                                    preparedStatement2.setString(3, email);
                                    preparedStatement2.setDouble(4, 0.0);

                                    System.out.printf("Generate 4 digit security pin (XXXX): ");
                                    int security_pin = scanner.nextInt();

                                    if(security_pin < 1000 || security_pin > 9999){
                                        System.out.println("Invalid security pin");
                                        System.exit(0);
                                    }

                                    System.out.printf("Add initial deposit: ");
                                    double balance = scanner.nextDouble();
                                    preparedStatement2.setDouble(4, balance);

                                    preparedStatement2.setInt(5, security_pin);

                                    int rowsAffected = preparedStatement2.executeUpdate();

                                    if(rowsAffected > 0){
                                        System.out.println("Account created successfully!");

                                    }else{
                                        System.out.println("Account creation failed!");
                                    }

                                }else if (createAccChoice == 'n' || createAccChoice == 'N'){
                                    System.out.println("Exiting...");
                                    System.exit(0);
                                }else{
                                    System.out.println("Invalid choice");
                                }
                            }
                        }else{
                            // if email is null, login failed
                            System.out.println("Login failed!");
                        }

                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.out.println("Thank you for using our Banking system!");
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