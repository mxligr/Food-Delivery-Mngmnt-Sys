package org.example.BussinessLayer;
/**
 *
 * @author Grama Malina Bianca
 * @since June 03, 2021
 * Class that models the real-life object of a Menu Item in the Food Delivery Management System; a menu item can either be a Base Product or a Composite Product (product made from more Base Products)
 *
 */
public abstract class MenuItem implements java.io.Serializable{

    protected String name;
    protected int price;

    public abstract int computePrice();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
