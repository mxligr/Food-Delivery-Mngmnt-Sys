package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.example.BussinessLayer.*;
import org.example.BussinessLayer.MenuItem;
import org.example.DataLayer.Serializator;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
/**
 *
 * @author Grama Malina Bianca
 * @since May 13, 2021
 * Class that controls the window for the client operations
 */
public class ClientController {
    @FXML
    private TableView<MenuItem> productsTable;
    @FXML
    private TableColumn<MenuItem, String> titleColumn;
    @FXML
    private TableColumn<MenuItem, Double> ratingColumn;
    @FXML
    private TableColumn<MenuItem, Integer> caloriesColumn;
    @FXML
    private TableColumn<MenuItem, Integer> proteinColumn;
    @FXML
    private TableColumn<MenuItem, Integer> fatColumn;
    @FXML
    private TableColumn<MenuItem, Integer> sodiumColumn;
    @FXML
    private TableColumn<MenuItem, Integer> priceColumn;
    @FXML
    private TableView<MenuItem> compositeProductsTable;
    @FXML
    private TableColumn<MenuItem, String> titleColumnComp;
    @FXML
    private TableColumn<MenuItem, Integer> priceColumnComp;
    @FXML
    private TextArea productsOrderTextArea;
    @FXML
    private Button selectBProductButton;
    @FXML
    private Button selectCProductButton;
    @FXML
    private Button placeOrderButton;
    @FXML
    private Text totalText;
    @FXML
    private Button openBillButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private ChoiceBox<String> searchChoiceBox;

    ObservableList<MenuItem> listB;
    ObservableList<MenuItem> listC;
    int clientId = LoginController.getClientId();
    String clientUser = LoginController.getClientUsername();
    ArrayList<MenuItem> items;
    int total = 0;
    ArrayList<String> searchFields = new ArrayList<>();
    ObservableList<String> srcFields;

    private static Employee employee = new Employee();
    public static StringBuilder employeeInfo = new StringBuilder();

    boolean baseProdPressed = false;
    boolean comProdPressed = false;

    DeliveryService del;

    @FXML
    private void initialize() throws IOException {
        del = Serializator.deserializeDeliveryService();
        items = new ArrayList<>();
    }

    /**
     * Method that "searches" (filters the entries in the table using streams) for products based on one criterion
     */
    @FXML
    private void searchProducts() {
        if (baseProdPressed) {
            String choice = this.searchChoiceBox.getSelectionModel().getSelectedItem();
            updateBaseProdTable();
            ArrayList<MenuItem> list = del.getMyMenu();

            ArrayList<BaseProduct> items = new ArrayList<>();
            for (MenuItem value : list) {
                items.add((BaseProduct) value);
            }

            ArrayList<MenuItem> listClone;
            switch (choice) {
                case "Name": {
                    listClone = items.stream().filter(o -> (o.getName().toLowerCase(Locale.ROOT).contains(searchTextField.getText())) || (o.getName().contains(searchTextField.getText())))
                            .collect(Collectors.toCollection(ArrayList::new));
                    ObservableList<MenuItem> observableList = FXCollections.observableList(listClone);
                    productsTable.setItems(observableList);
                    break;
                }
                case "Rating": {
                    listClone = items.stream().filter(o -> o.getRating() == Double.parseDouble(searchTextField.getText()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    ObservableList<MenuItem> observableList = FXCollections.observableList(listClone);
                    productsTable.setItems(observableList);
                    break;
                }
                case "Calories": {
                    listClone = items.stream().filter(o -> o.getCalories() == Integer.parseInt(searchTextField.getText()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    ObservableList<MenuItem> observableList = FXCollections.observableList(listClone);
                    productsTable.setItems(observableList);
                    break;
                }
                case "Protein": {
                    listClone = items.stream().filter(o -> o.getProtein() == Integer.parseInt(searchTextField.getText()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    ObservableList<MenuItem> observableList = FXCollections.observableList(listClone);
                    productsTable.setItems(observableList);
                    break;
                }
                case "Fat": {
                    listClone = items.stream().filter(o -> o.getFat() == Integer.parseInt(searchTextField.getText()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    ObservableList<MenuItem> observableList = FXCollections.observableList(listClone);
                    productsTable.setItems(observableList);
                    break;
                }
                case "Sodium": {
                    listClone = items.stream().filter(o -> o.getSodium() == Integer.parseInt(searchTextField.getText()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    ObservableList<MenuItem> observableList = FXCollections.observableList(listClone);
                    productsTable.setItems(observableList);
                    break;
                }
                case "Price": {
                    listClone = items.stream().filter(o -> o.getPrice() == Integer.parseInt(searchTextField.getText()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    ObservableList<MenuItem> observableList = FXCollections.observableList(listClone);
                    productsTable.setItems(observableList);
                    break;
                }
            }
        } else if (comProdPressed) {
            String choice = this.searchChoiceBox.getSelectionModel().getSelectedItem();
            updateCompProdTable();
            ArrayList<CompositeProduct> list = del.getCompositeItem();
            ArrayList<CompositeProduct> listClone;
            if (choice.equals("Name")) {
                listClone = list.stream().filter(o -> (o.getName().toLowerCase(Locale.ROOT).contains(searchTextField.getText())) || (o.getName().contains(searchTextField.getText())))
                        .collect(Collectors.toCollection(ArrayList::new));
                ObservableList<MenuItem> observableList = FXCollections.observableArrayList();
                observableList.addAll(listClone);
                compositeProductsTable.setItems(observableList);
            } else if (choice.equals("Price")) {
                listClone = list.stream().filter(o -> o.getPrice() == Integer.parseInt(searchTextField.getText()))
                        .collect(Collectors.toCollection(ArrayList::new));
                ObservableList<MenuItem> observableList = FXCollections.observableArrayList();
                observableList.addAll(listClone);
                compositeProductsTable.setItems(observableList);
            }
        }
    }

    /**
     * When button is pressed, a table with the base products will appear
     */
    @FXML
    private void showBaseProducts() {
        baseProdPressed = true;
        comProdPressed = false;
        searchFields.clear();
        searchFields.add("Name");
        searchFields.add("Rating");
        searchFields.add("Calories");
        searchFields.add("Protein");
        searchFields.add("Fat");
        searchFields.add("Sodium");
        searchFields.add("Price");
        srcFields = FXCollections.observableArrayList(searchFields);
        searchChoiceBox.setItems(srcFields);

        productsTable.setVisible(true);
        compositeProductsTable.setVisible(false);
        selectBProductButton.setVisible(true);
        selectCProductButton.setVisible(false);
        updateBaseProdTable();
    }

    /**
     * When button is pressed, a table with the composite products will appear
     */
    @FXML
    private void showCompProducts() {
        baseProdPressed = false;
        comProdPressed = true;
        searchFields.clear();
        searchFields.add("Name");
        searchFields.add("Price");
        srcFields = FXCollections.observableArrayList(searchFields);
        searchChoiceBox.setItems(srcFields);

        productsTable.setVisible(false);
        compositeProductsTable.setVisible(true);
        selectBProductButton.setVisible(false);
        selectCProductButton.setVisible(true);
        updateCompProdTable();
    }

    private void updateBaseProdTable() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        sodiumColumn.setCellValueFactory(new PropertyValueFactory<>("sodium"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        listB = del.getObsList();
        productsTable.setItems(listB);
    }

    private void updateCompProdTable() {
        titleColumnComp.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumnComp.setCellValueFactory(new PropertyValueFactory<>("price"));
        listC = del.getObsListComp();
        compositeProductsTable.setItems(listC);
    }

    /**
     * Add selected base product to the order
     */
    @FXML
    private void selectBProductForOrder() {
        MenuItem product = productsTable.getSelectionModel().getSelectedItem();
        items.add(product);
        total += product.getPrice();
        totalText.setText("Total: " + total);
        productsOrderTextArea.appendText(product.getName() + "\n");
        placeOrderButton.setVisible(true);
    }

    /**
     * Add selected composite product to the order
     */
    @FXML
    private void selectCProductForOrder() {
        MenuItem product = compositeProductsTable.getSelectionModel().getSelectedItem();
        items.add(product);
        total += product.getPrice();
        totalText.setText("Total: " + total);
        productsOrderTextArea.appendText(product.getName() + "\n");
        placeOrderButton.setVisible(true);
    }

    /**
     * After selecting the products wanted, place order
     * A bill with information regarding the order will be generated, and it can be opened from the application
     */
    @FXML
    private void placeOrder() {
        totalText.setText("Order Placed Successfully!");
        productsOrderTextArea.setText("");
        Order order = new Order(del.getNextOrderID(), clientId, total);
        del.support = new PropertyChangeSupport(del);
        del.addPropertyChangeListener(employee);
        del.createOrder(order, items);
        del.addOrderToList(order);

        employeeInfo.append("Order ID: ").append(order.getOrderID()).append("\n");
        employeeInfo.append("Client's ID: ").append(order.getClientID()).append("\n");
        employeeInfo.append("Order Date and Time: ").append(order.getOrderDate()).append("\n");
        employeeInfo.append("Products Ordered: ").append("\n");
        Map<Order, ArrayList<MenuItem>> myMap = del.getOrderMenuMap();
        ArrayList<MenuItem> list = myMap.get(order);
        for (MenuItem item : list) {
            employeeInfo.append("Product's name: ").append(item.getName()).append("; Price: ").append(item.getPrice()).append("\n");
        }

        StringBuilder bill = new StringBuilder();
        bill.append("Bill for the Order placed" + "\n");
        bill.append("Order ID: ").append(order.getOrderID()).append("\n");
        bill.append("Client's ID: ").append(order.getClientID()).append("\n");
        bill.append("Client's Username: ").append(clientUser).append("\n");
        bill.append("Order Date and Time: ").append(order.getOrderDate()).append("\n");
        bill.append("\n");
        bill.append("Products Ordered: ").append("\n");
        for (MenuItem item : list) {
            bill.append("Product's name: ").append(item.getName()).append("; Price: ").append(item.getPrice()).append("\n");
        }
        bill.append("Total price of the order: ").append(total);
        String billString = String.valueOf(bill);

        del.generateBill(billString);
        openBillButton.setVisible(true);

        ArrayList<Order> orders = del.getOrderList();
        for (Order value : orders) {
            System.out.println(value.getOrderDate());
        }
    }

    @FXML
    private void openBill() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("C:\\Users\\Stela\\IdeaProjects\\FudPandaFake\\bill.txt");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Delete all products selected for the order
     */
    @FXML
    private void clearProducts() {
        totalText.setText("");
        productsOrderTextArea.setText("");
        total = 0;
        items.clear();
    }

    /**
     * Go to login window
     * @throws IOException
     */
    @FXML
    private void switchToLogin() throws IOException {
        Serializator.serializeDeliveryService(del);
        App.setRoot("login");
    }
}
