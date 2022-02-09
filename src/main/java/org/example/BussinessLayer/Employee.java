package org.example.BussinessLayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
/**
 *
 * @author Grama Malina Bianca
 * @since June 03, 2021
 * Class that models the real-life object of an Employee; the employee will be notified when a new order is made
 *
 */
public class Employee implements PropertyChangeListener {

    private Order order = new Order();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setOrder((Order)evt.getNewValue());
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
