package org.example.BussinessLayer;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
/**
 *
 * @author Grama Malina Bianca
 * @since June 03, 2021
 * Interface of the class that contains all of the necessary data structures and methods for running the application
 *
 */
public interface IDeliveryServiceProcessing {

    ObservableList<MenuItem> importProducts() throws IOException;
    void addMenuItem(MenuItem item);
    void deleteMenuItem(MenuItem item);
    void modifyMenuItem(int index, MenuItem item);
    void addCompositeProduct(CompositeProduct compositeProduct);
    ArrayList<Order> generateTimeIntervalOfOrdersReport(int startHour, int endHour);
    ArrayList<MenuItem> generateProductsOrderedMoreThanReport(int times);
    ArrayList<String> generateClientsReport(int times, int total);
    Map<MenuItem, Integer> generateProductsOrderedDayReport(int day);

    void addOrderToList(Order order);
    void createOrder(Order order, ArrayList<MenuItem> item);
    int computeOrderPrice(Order order);
    void generateBill(String bill);
}
