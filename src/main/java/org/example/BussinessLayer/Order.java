package org.example.BussinessLayer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
/**
 *
 * @author Grama Malina Bianca
 * @since June 03, 2021
 * Class that models the real-life object of an Order made by a client in the Food Delivery Management System
 *
 */
public class Order implements java.io.Serializable {

    private int orderID;
    private int clientID;
    private String orderDateAndTime;

    private int orderYear;
    private int orderMonth;
    private int orderDay;

    private int orderHour;
    private int orderMinute;
    private int orderSecond;

    private int orderTotal;

    /**
     * Empty class constructor
     */
    public Order() {

    }

    /**
     * Class constructor that gets the orderID, clientID and total of the order as parameters, and computes the order date and time
     * @param orderID the ID of the order
     * @param clientID the ID of the client placing the order
     * @param total the total sum of the order, computed from all of the products ordered
     */
    public Order(int orderID, int clientID, int total) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderTotal = total;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.orderDateAndTime = formatter.format(date);
        LocalDate date1 = LocalDate.now(); // Gets the current date
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd");
        this.orderDay = Integer.parseInt(date1.format(formatter1));
        formatter1 = DateTimeFormatter.ofPattern("MM");
        this.orderMonth = Integer.parseInt(date1.format(formatter1));
        formatter1 = DateTimeFormatter.ofPattern("yyyy");
        this.orderYear = Integer.parseInt(date1.format(formatter1));
        formatter = new SimpleDateFormat("HH");
        this.orderHour = Integer.parseInt(formatter.format(date));
        formatter = new SimpleDateFormat("mm");
        this.orderMinute = Integer.parseInt(formatter.format(date));
        formatter = new SimpleDateFormat("ss");
        this.orderSecond = Integer.parseInt(formatter.format(date));
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public String getOrderDate() {
        return orderDateAndTime;
    }

    public int getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(int orderDay) {
        this.orderDay = orderDay;
    }

    public int getOrderHour() {
        return orderHour;
    }

    public int getOrderMinute() {
        return orderMinute;
    }

    public void setOrderMinute(int orderMinute) {
        this.orderMinute = orderMinute;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    @Override
    public int hashCode() {
        int hashcode = 11;
        hashcode += hashcode * 7 + 31 * orderID + 3 * orderDay + 5 * clientID;
        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        Order other = (Order) obj;
        if (this.orderID != other.orderID) return false;
        if (!this.orderDateAndTime.equals(other.orderDateAndTime)) return false;
        if (this.clientID != other.clientID) return false;
        return true;
    }
}
