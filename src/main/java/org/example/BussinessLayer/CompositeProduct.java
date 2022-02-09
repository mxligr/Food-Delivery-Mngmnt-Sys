package org.example.BussinessLayer;

import java.util.ArrayList;
/**
 *
 * @author Grama Malina Bianca
 * @since June 03, 2021
 * Class that models the real-life object of a Composite Product (product made from more Base Products) in the Food Delivery Management System; extends Menu Item
 *
 */
public class CompositeProduct extends MenuItem {

    private final ArrayList<MenuItem> compositeProduct;

    /**
     * Class constructor specifying the name of the composite product, and a list of base products that make the composite product
     * @param name specifies the name of the composite product
     * @param compositeProduct an ArrayList of MenuItems that compose the composite product
     */
    public CompositeProduct(String name, ArrayList<MenuItem> compositeProduct) {
        this.name = name;
        this.compositeProduct = compositeProduct;
        this.price = computePrice();
    }

    public ArrayList<MenuItem> getCompositeProduct() {
        return compositeProduct;
    }

    /**
     *
     * @return the total price of the composite product by adding all the prices of the base products that compose the composite product
     */
    @Override
    public int computePrice() {
        int price = 0;
        for (MenuItem menuItem : compositeProduct) {
            price += menuItem.getPrice();
        }
        return price;
    }
}
