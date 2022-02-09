package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.example.BussinessLayer.CompositeProduct;
import org.example.BussinessLayer.DeliveryService;
import org.example.BussinessLayer.MenuItem;
import org.example.DataLayer.Serializator;

import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Grama Malina Bianca
 * @since May 13, 2021
 * Class that controls the window for the creation of a composite product by the administrator
 */
public class CompositeProductCreateController {

    DeliveryService del = Serializator.deserializeDeliveryService();

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
    private Text messageText;
    @FXML
    private TextField nameTextField;

    ObservableList<MenuItem> listP;
    ArrayList<MenuItem> products = new ArrayList<>();

    public CompositeProductCreateController() throws IOException {
    }

    @FXML
    private void initialize() throws IOException {
        // del = new DeliveryService();
        del = Serializator.deserializeDeliveryService();
        updateTable();
    }

    /**
     * Show a table with the BaseProducts from which the admin can select items that will compose a CompositeProduct
     */
    private void updateTable() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        sodiumColumn.setCellValueFactory(new PropertyValueFactory<>("sodium"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        listP = del.getObsList();
        productsTable.setItems(listP);
    }

    /**
     * Add selected BaseProduct to CompositeProduct
     */
    @FXML
    private void addToCompProd() {
        MenuItem prod = productsTable.getSelectionModel().getSelectedItem();
        products.add(prod);
        messageText.setText("BaseProduct added to CompositeProduct");
    }

    /**
     * Type in a name fomxligrr the CompositeProduct and add that composite product to the list in DeliveryService
     */
    @FXML
    private void finalizeCompProduct() {
        if (nameTextField.getText().equals("")) {
            messageText.setText("Please type in a name for the Composite Product.");
        } else {
            String name = nameTextField.getText();
            CompositeProduct compProd = new CompositeProduct(name, products);
            del.addCompositeProduct(compProd);
            messageText.setText("Composite Product added successfully!");
        }
    }

    @FXML
    private void switchToLogin() throws IOException {
        Serializator.serializeDeliveryService(del);
        App.setRoot("admin");
    }
}
