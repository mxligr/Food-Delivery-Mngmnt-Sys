package org.example.BussinessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author Grama Malina Bianca
 * @since June 03, 2021
 * Class that realizes the connection to the database that holds the login information for each type of user
 *
 */
public class DatabaseConnection {
    public Connection databaseLink;

    public DatabaseConnection() {
    }

    public Connection getConnection() {
        String databaseName = "assignment4";
        String databaseUser = "root";
        String databasePassword = "password";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            return this.databaseLink;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
