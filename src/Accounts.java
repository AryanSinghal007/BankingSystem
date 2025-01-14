import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Accounts {

    private Connection connection;

    public Accounts(Connection connection){
        this.connection = connection;
    }

    public int get_account_number(String email){

        String query = "SELECT account_no FROM account WHERE email = ?";

        try{
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("account_no");
            }else{
                System.out.println("Account does not exist!");
            }

        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }

        return 0;
    }
    
    public long open_account(String email){
       
        try{

            String query = "SELECT COUNT(*) FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int totalRecords = resultSet.getInt(1);
            long accountNumber = totalRecords + 1;
            return 1000000000L + accountNumber;

        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
     
    }

    public boolean account_exist(String email){
         try{
            String query = "SELECT * FROM account WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                // System.out.println("Account Exists");
                return true;
            }else{
                // System.out.println("Account does not exist!");
                return false;
            }
           
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage()); 
            return false; 
        }
    }
}