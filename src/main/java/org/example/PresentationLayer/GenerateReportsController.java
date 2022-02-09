package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.BussinessLayer.DeliveryService;
import org.example.BussinessLayer.MenuItem;
import org.example.BussinessLayer.Order;
import org.example.DataLayer.Serializator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
/**
 *
 * @author Grama Malina Bianca
 * @since May 13, 2021
 * Class that controls the window for generating reports by the administrator
 */
public class GenerateReportsController {
    @FXML
    private TextField startHourTextField;
    @FXML
    private TextField endHourTextField;
    @FXML
    private TextField prodOrderedMoreThanTextField;
    @FXML
    private TextField clientsMadeMoreThanTextField;
    @FXML
    private TextField clientsMinimumOrderValueTextField;
    @FXML
    private TextField dayOfOrderTextField;
    @FXML
    private TextArea resultTextArea;

    boolean timeInterval = false;
    boolean productsOrderedMore = false;
    boolean clients = false;
    boolean productsOrderedIn = false;

    DeliveryService del;

    @FXML
    private void initialize() throws IOException {
        del = Serializator.deserializeDeliveryService();
    }

    @FXML
    private void timeIntervalOfOrderPressed() {
        resultTextArea.setText("");
        timeInterval = true;
        productsOrderedMore = false;
        clients = false;
        productsOrderedIn = false;
        startHourTextField.setVisible(true);
        endHourTextField.setVisible(true);
        prodOrderedMoreThanTextField.setVisible(false);
        clientsMadeMoreThanTextField.setVisible(false);
        clientsMinimumOrderValueTextField.setVisible(false);
        dayOfOrderTextField.setVisible(false);
        clearTextFields();
    }

    @FXML
    private void productsOrderedMoreThanPressed() {
        resultTextArea.setText("");
        timeInterval = false;
        productsOrderedMore = true;
        clients = false;
        productsOrderedIn = false;
        startHourTextField.setVisible(false);
        endHourTextField.setVisible(false);
        prodOrderedMoreThanTextField.setVisible(true);
        clientsMadeMoreThanTextField.setVisible(false);
        clientsMinimumOrderValueTextField.setVisible(false);
        dayOfOrderTextField.setVisible(false);
        clearTextFields();
    }

    @FXML
    private void clientsPressed() {
        resultTextArea.setText("");
        timeInterval = false;
        productsOrderedMore = false;
        clients = true;
        productsOrderedIn = false;
        startHourTextField.setVisible(false);
        endHourTextField.setVisible(false);
        prodOrderedMoreThanTextField.setVisible(false);
        clientsMadeMoreThanTextField.setVisible(true);
        clientsMinimumOrderValueTextField.setVisible(true);
        dayOfOrderTextField.setVisible(false);
        clearTextFields();
    }

    @FXML
    private void productsOrderedInDayPressed() {
        resultTextArea.setText("");
        timeInterval = false;
        productsOrderedMore = false;
        clients = false;
        productsOrderedIn = true;
        startHourTextField.setVisible(false);
        endHourTextField.setVisible(false);
        prodOrderedMoreThanTextField.setVisible(false);
        clientsMadeMoreThanTextField.setVisible(false);
        clientsMinimumOrderValueTextField.setVisible(false);
        dayOfOrderTextField.setVisible(true);
        clearTextFields();
    }

    @FXML
    private void clearTextFields() {
        startHourTextField.setText("");
        endHourTextField.setText("");
        prodOrderedMoreThanTextField.setText("");
        clientsMadeMoreThanTextField.setText("");
        clientsMinimumOrderValueTextField.setText("");
        dayOfOrderTextField.setText("");
        resultTextArea.setText("");
    }

    /**
     * Generate time interval report and show the results in the text area
     */
    private void timeIntervalReport() {
        int startHour = Integer.parseInt(startHourTextField.getText());
        int endHour = Integer.parseInt(endHourTextField.getText());
        ArrayList<Order> orders = del.generateTimeIntervalOfOrdersReport(startHour, endHour);
        resultTextArea.appendText("Orders performed between " + startHour + " and " + endHour + " regardless the date: " + "\n\n");
        for (Order order : orders) {
            resultTextArea.appendText("Order ID: ");
            resultTextArea.appendText(order.getOrderID() + "\n");
            resultTextArea.appendText("Client ID: ");
            resultTextArea.appendText(order.getClientID() + "\n");
            resultTextArea.appendText("Order date: ");
            resultTextArea.appendText(order.getOrderDate() + "\n\n");
            System.out.println("Order ID: ");
            System.out.println(order.getOrderID());
            System.out.println("Client ID: ");
            System.out.println(order.getClientID());
            System.out.println("Order date: ");
            System.out.println(order.getOrderDate());
        }
    }

    /**
     * Generate products ordered more than report and show the results in the text area
     */
    private void prodOrderedMoreReport() {
        int times = Integer.parseInt(prodOrderedMoreThanTextField.getText());
        ArrayList<MenuItem> products = del.generateProductsOrderedMoreThanReport(times);
        resultTextArea.appendText("Products ordered more than " + times + " times: " + "\n");
        for (MenuItem product : products) {
            System.out.println(product.getName());
            resultTextArea.appendText(product.getName() + "\n");
        }
    }

    /**
     * Generate clients report and show the results in the text area
     */
    private void clientsReport() {
        int times = Integer.parseInt(clientsMadeMoreThanTextField.getText());
        int total = Integer.parseInt(clientsMinimumOrderValueTextField.getText());
        ArrayList<String> clients = del.generateClientsReport(times, total);
        resultTextArea.appendText("Clients that have ordered more than " + times + " times and the value of the order was higher than " + total + ":\n");
        for (String client : clients) {
            System.out.println(client);
            resultTextArea.appendText(client + "\n");
        }
    }

    /**
     * Generate products ordered more than report and show the results in the text area
     */
    private void prodOrderedInReport() {
        int day = Integer.parseInt(dayOfOrderTextField.getText());
        Map<MenuItem, Integer> map = del.generateProductsOrderedDayReport(day);
        resultTextArea.appendText("Products ordered in day " + day + " with the number of times they have been ordered: \n\n");
        for (Map.Entry<MenuItem, Integer> val : map.entrySet()) {
            if (val.getValue() > 1) {
                resultTextArea.appendText(val.getKey().getName() + ", ordered " + val.getValue() + " times \n");
            } else if (val.getValue() == 1) {
                resultTextArea.appendText(val.getKey().getName() + ", ordered " + val.getValue() + " time \n");
            }
        }
    }

    /**
     * Generate selected report
     */
    @FXML
    private void generateReport() {
        if (timeInterval) {
            resultTextArea.setText("");
            timeIntervalReport();
        } else if (productsOrderedMore) {
            resultTextArea.setText("");
            prodOrderedMoreReport();
        } else if (clients) {
            resultTextArea.setText("");
            clientsReport();
        } else if (productsOrderedIn) {
            resultTextArea.setText("");
            prodOrderedInReport();
        }
    }

    /**
     * Return to the Admin Controller
     * @throws IOException
     */
    @FXML
    private void goBack() throws IOException {
        Serializator.serializeDeliveryService(del);
        App.setRoot("admin");
    }
}
