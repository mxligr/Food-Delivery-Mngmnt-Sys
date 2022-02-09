package org.example.BussinessLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.DataLayer.FileWriter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.Observable;
import java.util.stream.Collectors;
/**
 *
 * @author Grama Malina Bianca
 * @since June 03, 2021
 * Class that contains all of the necessary data structures and methods for running the application
 *
 */
public class DeliveryService extends Observable implements IDeliveryServiceProcessing, java.io.Serializable {

    private ArrayList<MenuItem> myMenu;
    private ArrayList<CompositeProduct> compositeItem = new ArrayList<>();

    private HashMap<Order, ArrayList<MenuItem>> orderMenuMap;
    private ArrayList<Order> orderList;

    public PropertyChangeSupport support;

    /**
     * Class constructor that initializes the data structures that hold the list with the menu, the HashMap containing the orders and the products ordered, and the list of the orders
     */
    public DeliveryService() {
        myMenu = new ArrayList<>();
        orderMenuMap = new HashMap<>();
        orderList = new ArrayList<>();
        // importProducts();
    }

    public int getNextOrderID() {
        return orderList.size() + 1;
    }

    public ArrayList<MenuItem> getMyMenu() {
        return myMenu;
    }

    public ArrayList<CompositeProduct> getCompositeItem() {
        return compositeItem;
    }

    public void setCompositeItem(ArrayList<CompositeProduct> compositeItem) {
        this.compositeItem = compositeItem;
    }

    public Map<Order, ArrayList<MenuItem>> getOrderMenuMap() {
        return orderMenuMap;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        support.addPropertyChangeListener(propertyChangeListener);
    }

    /**
     * Admin operation
     * Method that adds an order to the orderList
     * @param order adds this order to the orderList
     */
    @Override
    public void addOrderToList(Order order) {
        orderList.add(order);
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("NEW order created : " + "\n\n");
        sBuilder.append("Order ID : ").append(order.getOrderID()).append("\n");
        sBuilder.append("Client ID: ").append(order.getClientID()).append("\n");
        sBuilder.append("Date : ").append(order.getOrderDate()).append("\n");
        sBuilder.append("Ordered Items : " + "\n");

        Map<Order, ArrayList<MenuItem>> myMap = getOrderMenuMap();
        ArrayList<MenuItem> list = myMap.get(order);
        for (MenuItem menuItem : list) {
            sBuilder.append(menuItem.toString());
            sBuilder.append("\n");
        }
    }

    /**
     * Calls the readProd method from the FileWriter class
     * @return an ArrayList of products read from the CSV file
     */
    private ArrayList<MenuItem> readProductsFromCSV() {
        List<MenuItem> products;
        products = FileWriter.readProd();
        return (ArrayList<MenuItem>) products;
    }

    /**
     * Admin operation
     * Method that imports the products from the CSV file and places them in the myMenu variable
     * @return ObservableList of MenuItems used in displaying the products in the TableViews of the application
     * @throws IOException
     */
    @Override
    public ObservableList<MenuItem> importProducts() {
        myMenu = readProductsFromCSV();
        ObservableList<MenuItem> list = FXCollections.observableArrayList();
        list.addAll(myMenu);
        return list;
    }

    /**
     *
     * @return an ObservableList with the base products in the myMenu list, used for displaying the products in the TableViews of the application
     */
    public ObservableList<MenuItem> getObsList() {
        ObservableList<MenuItem> list = FXCollections.observableArrayList();
        list.addAll(myMenu);
        return list;
    }

    /**
     *
     * @return an ObservableList with the composite products in the compositeItem list, used for displaying the products in the TableViews of the application
     */
    public ObservableList<MenuItem> getObsListComp() {
        ObservableList<MenuItem> list = FXCollections.observableArrayList();
        list.addAll(compositeItem);
        return list;
    }

    /**
     * Admin operation
     * @param item adds the item to the myMenu list
     */
    @Override
    public void addMenuItem(MenuItem item) {
        assert item != null;

        int preSize = myMenu.size();
        myMenu.add(item);
        int postSize = myMenu.size();

        assert preSize + 1 == postSize;
    }

    /**
     * Admin operation
     * @param item looks for the MenuItem item in the myMenu list, finds it and removes it
     */
    @Override
    public void deleteMenuItem(MenuItem item) {
        assert item != null;

        int preSize = myMenu.size();
        for (int i = 0; i < myMenu.size(); i++) {
            if (item.getName().equals(myMenu.get(i).getName())) {
                myMenu.remove(i);
            }
        }
        int postSize = myMenu.size();

        assert preSize == postSize + 1;
    }

    /**
     * Admin operation
     * Method that modifies the product with the specified index
     * @param index of the item to be modified
     * @param item modified item to be added in the table
     */
    @Override
    public void modifyMenuItem(int index, MenuItem item) {
        assert item != null;
        assert index > -1;

        myMenu.set(index, item);
    }

    /**
     * Admin operation
     * @param compositeProduct adds this compositeProduct to the compositeItem list
     */
    @Override
    public void addCompositeProduct(CompositeProduct compositeProduct) {
        assert compositeProduct != null;
        compositeItem.add(compositeProduct);
    }

    /**
     * Admin operation
     * Method that generates a report containing the orders that have been made in a given time interval
     * @param startHour beginning of time interval
     * @param endHour end of time interval
     * @return a list of orders made in the given time interval
     */
    @Override
    public ArrayList<Order> generateTimeIntervalOfOrdersReport(int startHour, int endHour) {
        ArrayList<Order> result;
        result = orderList.stream().filter(o -> ((o.getOrderHour() >= startHour) && (o.getOrderHour() < endHour)))
                .collect(Collectors.toCollection(ArrayList::new));
        return result;
    }

    /**
     * Admin operation
     * Method that generates a report containing the products that have been ordered more than a specified number of times
     * @param times specified number of times that the products have been ordered
     * @return a list with products ordered more than the specified number of times
     */
    @Override
    public ArrayList<MenuItem> generateProductsOrderedMoreThanReport(int times) {
        ArrayList<MenuItem> result = new ArrayList<>();
        ArrayList<MenuItem> items = new ArrayList<>();

        // get all of the items ordered and place them in an ArrayList
        for (Order order : orderList) {
            items.addAll(orderMenuMap.get(order));
        }

        // count occurrences of products ordered
        Map<MenuItem, Integer> hm = new HashMap<>();
        for (MenuItem i : items) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }

        // if the number of occurrences is bigger than the given parameter, add the product to the result list that will be returned
        for (Map.Entry<MenuItem, Integer> val : hm.entrySet()) {
            if (val.getValue() > times) {
                result.add(val.getKey());
            }
//            System.out.println("Element " + val.getKey().getName() + " "
//                    + "occurs"
//                    + ": " + val.getValue() + " times");
        }

        return result;
    }

    /**
     * Admin operation
     * Method that generates a report containing the clients that ordered more than a specified number of times, and the value of the order was higher than a specified amount
     * @param times specified number of times
     * @param total specified amount
     * @return a list of strings containing the username of the clients that fit the criteria
     */
    @Override
    public ArrayList<String> generateClientsReport(int times, int total) {
        ArrayList<String> clients = new ArrayList<>();
        ArrayList<Integer> clientIDs = new ArrayList<>();
        HashSet<Integer> ids = new HashSet<>();

        for (Order order : orderList) {
            if (order.getOrderTotal() > total) {
                clientIDs.add(order.getClientID());
            }
        }

        Map<Integer, Integer> hm = new HashMap<>();
        for (Integer i : clientIDs) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }

        hm.entrySet().removeIf(val -> val.getValue() <= times);

        for (Map.Entry<Integer, Integer> val : hm.entrySet()) {
            ids.add(val.getKey());
        }

        for (Integer cl : ids) {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String verifyLogin = "SELECT username FROM assignment4.user WHERE userID = " + cl +";";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);
                while (queryResult.next()) {
                    clients.add(queryResult.getString(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return clients;
    }

    /**
     * Admin operation
     * Method that generates a report containing the products ordered within a specified day with the number of times they have been ordered
     * @param day specified day
     * @return a HashMap containing the products ordered in the specified day, with the number of times they have been ordered
     */
    @Override
    public Map<MenuItem, Integer> generateProductsOrderedDayReport(int day) {

        ArrayList<MenuItem> products = new ArrayList<>();
        for (Map.Entry<Order, ArrayList<MenuItem>> val : orderMenuMap.entrySet()) {
            if(val.getKey().getOrderDay() == day) {
                products.addAll(val.getValue());
            }
        }

        Map<MenuItem, Integer> hm = new HashMap<>();
        for (MenuItem i : products) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }

        for (Map.Entry<MenuItem, Integer> val : hm.entrySet()) {
            System.out.println("Element " + val.getKey().getName() + " "
                    + "occurs"
                    + ": " + val.getValue() + " times");
        }

        return hm;
    }

    /**
     * Client operation
     * Method that creates an order and adds it to the HashMap of orders (orderMenuMap)
     * @param order the order to be created
     * @param item the list of products to be linked to the order (products that have been ordered)
     */
    @Override
    public void createOrder(Order order, ArrayList<MenuItem> item) {
        assert order != null;
        assert item != null;

        System.out.println(order.getOrderID() + " " + item);

        orderMenuMap.putIfAbsent(order, item);

        support.firePropertyChange("New Order", null, order);

        assert order.equals(order);
    }

    /**
     * Client operation
     * Method that computes the total value of an order
     * @param order for which the price will be computed
     * @return price of the order
     */
    @Override
    public int computeOrderPrice(Order order) {
        assert order != null;

        int price = 0;
        if (orderList.contains(order)) {
            ArrayList<MenuItem> orderedItems = orderMenuMap.get(order);
            for (MenuItem orderedItem : orderedItems) {
                price += orderedItem.getPrice();
            }
        }
        return price;
    }

    /**
     * Client operation
     * Method that generates a bill for the client after they placed an order
     * @param bill string with the information to be written in the .txt file
     */
    @Override
    public void generateBill(String bill) {
        assert bill != null;

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bill.txt")));
            bw.write(bill);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
