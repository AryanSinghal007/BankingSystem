# Banking System

## Overview
This is a Java-based console application that simulates a basic banking system. It allows users to manage accounts, perform transactions, and handle user registrations using a MySQL database. The system is structured to ensure modularity and maintainability through a combination of JDBC (Java Database Connectivity) and a well-organized backend.

## Collaborators
- Aryan Singhal : [AryanSinghal007](https://github.com/AryanSinghal007)
- Ansh Bathla : [anshbathla](https://github.com/anshbathla)

## Features

1. **Account Management**:
   - Open new accounts.
   - Generate and retrieve account numbers.
   - Check if an account exists.

2. **Transaction Management**:
   - Credit money into an account.
   - Debit money from an account.
   - Transfer funds between accounts.
   - Check account balances.

3. **User Management**:
   - Register new users.
   - Login existing users.
   - Verify user existence.

4. **User Interaction**:
   - Display a menu-driven interface for system operations.
   - Allow easy navigation and interaction with the application.

## How to Run

1. Clone the repository to your local system.

2. Ensure you have **Java JDK** installed (version 8 or higher).

3. Set up the MySQL database:
   - Create a database named `banking_db`.
   - Create tables for `Accounts` and `User` as defined in the schema.

4. Install the MySQL Connector JAR file:
   - Download the `mysql-connector-java` JAR file from the [official MySQL website](https://dev.mysql.com/downloads/connector/j/).
   - Add the JAR file to your project classpath.

5. Configure the database connection in the Java application:
   - Update the JDBC URL, username, and password in the application code to match your MySQL configuration.

6. Compile the Java files using the following command:
   ```bash
   javac App.java
   
7. Run the program

   ```bash
   java App

## Future Enhancements

1. **Add Support for Multi-Factor Authentication**: 
   - Implement multi-factor authentication to enhance security during user login and critical transactions.

2. **Implement a GUI**:
   - Create a graphical user interface for a more user-friendly and intuitive experience compared to the console-based application.

3. **Add Reporting Features**:
   - Include functionalities to generate account statements and transaction history for users.

4. **Introduce Interest Calculation**:
   - Develop interest calculation mechanisms for savings accounts to automatically calculate and credit interest periodically.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request for review.

## License
This project is licensed under the MIT License.
