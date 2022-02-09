package org.example.BussinessLayer;
/**
 *
 * @author Grama Malina Bianca
 * @since June 03, 2021
 * Class that models the real-life object of a Base Product in the Food Delivery Management System; extends Menu Item
 *
 */
public class BaseProduct extends MenuItem{
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;

    /**
     * Class constructor specifying the name of the product, the rating, the number of calories, protein, fat, sodium and the price
     * @param name specifies the name of the base product
     * @param rating specifies the rating of the base product as a double
     * @param calories specifies the number of calories of the base product as an integer
     * @param protein specifies the number of protein of the base product as an integer
     * @param fat specifies the number of fat of the base product as an integer
     * @param sodium specifies the number of sodium of the base product as an integer
     * @param price specifies the price of the base product as an integer
     */
    public BaseProduct(String name, double rating, int calories, int protein, int fat, int sodium, int price) {
        this.name = name;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    @Override
    public int computePrice() {
        return getPrice();
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
