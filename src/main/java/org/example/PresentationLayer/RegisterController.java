package org.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.BussinessLayer.DatabaseConnection;
/**
 *
 * @author Grama Malina Bianca
 * @since May 13, 2021
 * Class that controls the window for the registration of a client; if the data typed in is correct, the client will be added to the database, and he can log in the application to perform client operations
 */
public class RegisterController {

    @FXML
    private Button exitButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Text passwordText;

    @FXML
    private void exitButtonAction() {
        Stage stage = (Stage)this.exitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    /**
     * Check if the password text field and the confirm password text field contain the same text
     */
    @FXML
    private void registerAction() {
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
            passwordText.setText("");
            registerUser();
        } else {
            passwordText.setText("Passwords do not match.");
        }
    }

    /**
     * check if the username typed in is already in use
     * @return boolean
     */
    private boolean validateUsername() {
        DatabaseConnection connect = new DatabaseConnection();
        Connection connectDB = connect.getConnection();
        String verifyUsername = "SELECT count(1) FROM assignment4.user WHERE username = '" + this.usernameTextField.getText() + "';";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyUsername);
            if (queryResult.next()) {
                return queryResult.getInt(1) != 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Method that adds a client user to the database
     */
    private void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (!this.usernameTextField.getText().isBlank() && !this.passwordField.getText().isBlank() && !this.confirmPasswordField.getText().isBlank()) {
            if (validateUsername()) {
                String username = usernameTextField.getText();
                String password = passwordField.getText();
                String insertFields = "INSERT INTO assignment4.user (username, password, type) VALUES ('";
                String insertValues = username + "', '" + password + "', " + "3" + ");";
                String insertToRegister = insertFields + insertValues;

                try {
                    Statement statement = connectDB.createStatement();
                    statement.executeUpdate(insertToRegister);
                    passwordText.setText("User has been registered successfully! Please log in.");
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getCause();
                }
            } else {
                passwordText.setText("Username taken. Please type in another username and try again.");
            }
        } else {
            passwordText.setText("Please type in every field and try again.");
        }
    }

    /**
     * Go to login window
     * @throws IOException
     */
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}