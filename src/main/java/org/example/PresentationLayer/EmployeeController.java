package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.example.BussinessLayer.DeliveryService;
import org.example.DataLayer.Serializator;

import java.io.IOException;
/**
 *
 * @author Grama Malina Bianca
 * @since May 13, 2021
 * Class that controls the window for the employee operations
 */
public class EmployeeController {
    @FXML
    private TextArea employeeTextArea;

    StringBuilder str = ClientController.employeeInfo;
    DeliveryService del;

    @FXML
    private void initialize() throws IOException {
        del = Serializator.deserializeDeliveryService();
    }

    /**
     * Method that shows the information regarding the new orders that have been placed
     */
    @FXML
    private void seeOrdersAction() {
        employeeTextArea.appendText(str.toString());
    }

    @FXML
    private void switchToLogin() throws IOException {
        Serializator.serializeDeliveryService(del);
        App.setRoot("login");
    }
}
