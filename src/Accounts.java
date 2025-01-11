import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Accounts {

    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    
    public int open_account(String email){
        // logic
        return 0;   
    }

    public boolean account_exist(String email){
        // logic
        return false;
    }
}