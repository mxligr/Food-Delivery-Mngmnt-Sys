package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.example.BussinessLayer.BaseProduct;
import org.example.BussinessLayer.DeliveryService;
import org.example.BussinessLayer.MenuItem;
import org.example.DataLayer.Serializator;

import java.io.IOException;
/**
 *
 * @author Grama Malina Bianca
 * @since May 13, 2021
 * Class that controls the window for the administrator operations
 */
public class AdminController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField caloriesField;
    @FXML
    private TextField proteinField;
    @FXML
    private TextField fatField;
    @FXML
    private TextField sodiumField;
    @FXML
    private TextField priceField;


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

    ObservableList<MenuItem> listP;
    int index = -1;
    DeliveryService del;

    /**
     * Upon entering a new window, we always deserialize the DeliveryService class so we can use the data contained in there
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException {
        // del = new DeliveryService();
        del = Serializator.deserializeDeliveryService();
        updateTable();
    }

    /**
     * Method that gets the index of the selected row in the BaseProduct table
     */
    @FXML
    private void getSelectedValues() {
        index = productsTable.getSelectionModel().getSelectedIndex();
        if (index > -1) {
            titleField.setText(titleColumn.getCellData(this.index));
            ratingField.setText((ratingColumn.getCellData(this.index)).toString());
            caloriesField.setText((caloriesColumn.getCellData(this.index)).toString());
            proteinField.setText((proteinColumn.getCellData(this.index)).toString());
            fatField.setText((fatColumn.getCellData(this.index)).toString());
            sodiumField.setText((sodiumColumn.getCellData(this.index)).toString());
            priceField.setText((priceColumn.getCellData(this.index)).toString());
        }
    }

    /**
     * Method that writes in the text fields the info regarding the items in the table
     * @return
     */
    private MenuItem returnProductFromTextField() {
        String title = titleField.getText();
        double rating = Double.parseDouble(ratingField.getText());
        int calories = Integer.parseInt(caloriesField.getText());
        int protein = Integer.parseInt(proteinField.getText());
        int fat = Integer.parseInt(fatField.getText());
        int sodium = Integer.parseInt(sodiumField.getText());
        int price = Integer.parseInt(priceField.getText());
        MenuItem item = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
        return item;
    }

    /**
     * Method that adds an item to the table
     */
    @FXML
    private void addButtonAction() {
        MenuItem item = returnProductFromTextField();
        del.addMenuItem(item);
        updateTable();
        messageText.setText("Product Added Successfully!");
    }

    /**
     * Method that deletes the selected item from the table
     */
    @FXML
    private void deleteButtonAction() {
        MenuItem item = returnProductFromTextField();
        del.deleteMenuItem(item);
        updateTable();
        messageText.setText("Product Deleted Successfully!");
    }

    /**
     * Method that modifies the selected item from the table with the input from the text fields
     */
    @FXML
    private void modifyButtonAction() {
        MenuItem item = returnProductFromTextField();
        del.modifyMenuItem(index, item);
        updateTable();
        messageText.setText("Product Modified Successfully!");
    }

    @FXML
    private void clearFieldsAction() {
        titleField.setText("");
        ratingField.setText("");
        caloriesField.setText("");
        proteinField.setText("");
        fatField.setText("");
        sodiumField.setText("");
        priceField.setText("");
    }

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

    @FXML
    private void refreshTable() {
        messageText.setText("");
        del = new DeliveryService();
        updateTable();
    }

    /**
     * Before switching to another window, we always serialize the DeliveryService class; in this way, we ensure that the modifications we made in the class are kept
     * Go to see composite products window
     * @throws IOException
     */
    @FXML
    private void goToCompView() throws IOException {
        Serializator.serializeDeliveryService(del);
        App.setRoot("compositeProductView");
    }

    /**
     * Go to create composite product window
     * @throws IOException
     */
    @FXML
    private void goToCompCreate() throws IOException {
        Serializator.serializeDeliveryService(del);
        App.setRoot("compositeProductCreate");
    }

    /**
     * Go to generate reports window
     * @throws IOException
     */
    @FXML
    private void goToGenerateReports() throws IOException {
        Serializator.serializeDeliveryService(del);
        App.setRoot("generateReports");
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
