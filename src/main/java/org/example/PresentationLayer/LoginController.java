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
import org.example.BussinessLayer.DeliveryService;
import org.example.DataLayer.Serializator;
/**
 *
 * @author Grama Malina Bianca
 * @since May 13, 2021
 * Class that controls the window for the login of a user; depending on the user that logs in, a new window will appear
 */
public class LoginController {

    private static int clientId;
    private static String clientUsername;

    @FXML
    private Button exitButton;
    @FXML
    private Text loginMessageText;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    DeliveryService del;

    @FXML
    private void initialize() throws IOException {
        del = Serializator.deserializeDeliveryService();
    }

    /**
     * Exit Application
     */
    @FXML
    private void exitButtonAction() {
        Serializator.serializeDeliveryService(del);
        Stage stage = (Stage)this.exitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    @FXML
    private void loginButtonAction() {
        if (!this.usernameTextField.getText().isBlank() && !this.passwordTextField.getText().isBlank()) {
            this.validateLogin();
        } else {
            this.loginMessageText.setText("Please enter username and password");
        }
    }

    /**
     * Method that takes the text from the login text fields and checks if the user credentials are in the database
     * If the username and password match with an entry in the database, check the type of the user and open the specific window (admin, client, employee)
     */
    private void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String var10000 = this.usernameTextField.getText();
        String verifyLogin = "SELECT * FROM assignment4.user WHERE username = '" + var10000 + "' AND password = '" + this.passwordTextField.getText() + "';";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) != 0) {
                    this.loginMessageText.setText("Successfully logged in.");
                    if (queryResult.getInt(4) == 1) {
                        goToAdmin();
                    } else if (queryResult.getInt(4) == 2) {
                        goToEmployee();
                    } else if (queryResult.getInt(4) == 3) {
                        clientUsername = queryResult.getString(2);
                        clientId = queryResult.getInt(1);
                        goToClient();
                    } else {
                        loginMessageText.setText("Incorrect username/password. Please Try again.");
                    }
                } else {
                    this.loginMessageText.setText("Invalid Login. Please Try Again.");
                }
            }
            loginMessageText.setText("Incorrect username/password. Please Try again.");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     *
     * @return clientID to the client controller
     */
    public static int getClientId() {
        return clientId;
    }

    /**
     *
     * @return client's username to the client controller
     */
    public static String getClientUsername() {
        return clientUsername;
    }

    private void goToAdmin() throws IOException {
        App.setRoot("admin");
    }

    private void goToEmployee() throws IOException {
        App.setRoot("employee");
    }

    private void goToClient() throws IOException {
        App.setRoot("client");
    }

    /**
     * Go to register window
     * @throws IOException
     */
    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("register");
    }
}
