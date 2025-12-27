package models;

/**
 * InventoryItem - Data class to hold inventory item information.
 * 
 * This class stores details about a product in the inventory including
 * its name, price, category, and available quantity.
 */
public class InventoryItem {
    public final String name;
    public double price;
    public String category;
    public int quantity;
    
    public InventoryItem(String name, double price, String category, int quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }
}
