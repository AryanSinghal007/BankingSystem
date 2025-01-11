import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {

    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    // register new user
    public void register(){
        System.out.printf("Enter your name: ");
        String name = scanner.nextLine();

        System.out.printf("Enter your email: ");
        String email = scanner.nextLine();

        System.out.printf("Enter your password: ");
        String password = scanner.nextLine();


        if(user_exists(email)){
            System.out.println("User already exists");
        }else{
            // insert user into database
            String query = "INSERT INTO user(full_name, email, password) VALUES(? ? ?)";

            try{
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                int rowsAffected = preparedStatement.executeUpdate();

                if(rowsAffected > 0){
                    System.out.println("User registered successfully!");
                }else{
                    System.out.println("User registration failed!");
                }

            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("User registration failed!" + e.getMessage());
            }
        }
    }

    // login user
    public String login(){
        System.out.printf("Enter your email: ");
        String email = scanner.nextLine();

        System.out.printf("Enter your password: ");
        String password = scanner.nextLine();

        if(user_exists(email)){
            // check password
            String query = "SELECT * FROM user WHERE email = ? AND password = ?";
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    System.out.println("User logged in successfully!");
                    return email;
                }else{
                    System.out.println("Invalid email or password!");
                    return null;
                }

            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("User login failed!");
                return null;
            }
        }else{
            System.out.println("User does not exist!");
            return null;
        }
    }

    // check user exists
    public boolean user_exists(String email){
        
        String query = "SELECT * FROM user WHERE email = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){   // if user exists
                return true;
            }else{
                return false;
            }
    
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}