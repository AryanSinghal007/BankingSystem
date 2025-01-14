import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {

    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void credit_money(long account_number){
        
        System.out.printf("Enter amount to credit: ");
        double amount = scanner.nextDouble();

        String query = "UPDATE account SET balance = balance + ? WHERE account_no = ?";

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setLong(2, account_number);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Money credited successfully!");
            }else{
                System.out.println("Money credit failed!");
            }

        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void debit_money(long account_number){
        
        System.out.printf("Enter amount to debit: ");
        double amount = scanner.nextDouble();

        // check if account has enough balance
        double userBalance = check_balance(account_number);
        if(userBalance < amount){
            System.out.println("Insufficient balance!");
            return;
        }

        String query = "UPDATE account SET balance = balance - ? WHERE account_no = ?";

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setLong(2, account_number);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Money debited successfully!");
            }else{
                System.out.println("Money debit failed!");
            }

        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }

    }
    
    public void transfer_money(long account_number){
        
        System.out.printf("Enter account number to transfer to: ");
        long account_number_to = scanner.nextLong();

        // check if account number to transfer to exists
        String query = "SELECT * FROM account WHERE account_no = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, account_number_to);

            if(preparedStatement.executeQuery().next()){
                // account exists

                // debit money from account_number
                System.out.printf("Enter amount to transfer: ");
                double amount = scanner.nextDouble();

                String query1 = "UPDATE account SET balance = balance - ? WHERE account_no = ?";

                try{
                    PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                    preparedStatement1.setDouble(1, amount);
                    preparedStatement1.setLong(2, account_number);

                    int rowsAffected = preparedStatement1.executeUpdate();

                    if(rowsAffected > 0){
                        System.out.println("Money debited successfully from your account!");
                    }else{
                        System.out.println("Money debit failed!");
                    }

                } catch (SQLException e){
                    System.out.println("Error: " + e.getMessage());
                }

                // credit money to account_number_to
                
                String query2 = "UPDATE account SET balance = balance + ? WHERE account_no = ?";
                try{

                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement2.setDouble(1, amount);
                    preparedStatement2.setLong(2, account_number_to);
        
                    int rowsAffected = preparedStatement2.executeUpdate();
        
                    if(rowsAffected > 0){
                        System.out.println("Money credited successfully to benificiary!");
                    }else{
                        System.out.println("Money credit failed!");
                        System.out.println("Reverting money back to your account...");

                        String query3 = "UPDATE account SET balance = balance + ? WHERE account_no = ?";
                        try{
                            PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                            preparedStatement3.setDouble(1, amount);
                            preparedStatement3.setLong(2, account_number);
                
                            int rowsAffected2 = preparedStatement3.executeUpdate();
                
                            if(rowsAffected2 > 0){
                                System.out.println("Money reverted successfully!");
                            }else{
                                System.out.println("Money revert failed!");
                                System.out.println("Contact customer care for assistance!");
                            }
                
                        } catch (SQLException e){
                            System.out.println("Error: " + e.getMessage());
                    }
                }
        
            } catch (SQLException e){
                System.out.println("Error: " + e.getMessage());
            }

            }else{
                System.out.println("Account number to transfer to does not exist!");
            }

        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public double check_balance(long account_number){
        
        String query = "SELECT balance FROM account WHERE account_no = ?";

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, account_number);

            ResultSet resultSet = preparedStatement.executeQuery();

            double balance = 0.00;
            if(resultSet.next()){
                balance = resultSet.getDouble("balance");
            }

            System.out.println("Balance: " + balance);
            
            return balance;

        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
            return 0.00;
        }
    }
    
}
