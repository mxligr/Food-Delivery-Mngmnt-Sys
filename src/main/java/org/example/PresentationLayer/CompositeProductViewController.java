package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
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
 * Class that controls the window for the view of the composite products table by the administrator
 */
public class CompositeProductViewController {
    @FXML
    private  TableView<MenuItem> compositeProductsTable;
    @FXML
    private TableColumn<MenuItem, String> titleColumn;
    @FXML
    private TableColumn<MenuItem, Integer> priceColumn;
    @FXML
    private TextArea textArea;

    DeliveryService del = Serializator.deserializeDeliveryService();
    ObservableList<MenuItem> listP;
    int index = -1;

    public CompositeProductViewController() throws IOException {
    }

    @FXML
    private void initialize() throws IOException {
        // del = new DeliveryService();
        del = Serializator.deserializeDeliveryService();
        updateTable();
    }

    /**
     * Show the CompositeProducts in a table
     */
    private void updateTable() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        listP = del.getObsListComp();
        compositeProductsTable.setItems(listP);
    }

    /**
     * Method that shows the BaseProducts that compose the selected CompositeProduct
     */
    @FXML
    private void showProducts() {
        index = compositeProductsTable.getSelectionModel().getSelectedIndex();
        if (index > -1) {
            ArrayList<CompositeProduct> compProducts = del.getCompositeItem();
            CompositeProduct comp = compProducts.get(index);
            ArrayList<MenuItem> products = comp.getCompositeProduct();
            StringBuilder str = new StringBuilder();
            for (MenuItem product : products) {
                str.append(product.getName()).append("\n");
            }
            textArea.setText(str.toString());
        }
    }

    @FXML
    private void switchToLogin() throws IOException {
        Serializator.serializeDeliveryService(del);
        App.setRoot("admin");
    }
}
