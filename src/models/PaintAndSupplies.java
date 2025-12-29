package models;

// Represents a paint item in the inventory system
// Category: Paint (category ID "3")
public class PaintAndSupplies extends InventoryItem {
    private String color;
    
    // Constructor for creating a paint item
    // name: item name, price: selling price, quantity: stock level
    public PaintAndSupplies(String name, double price, int quantity) {
        super(name, price, "3", quantity);
        this.color = "";
    }
    
    // Constructor with color specification
    public PaintAndSupplies(String name, double price, int quantity, String color) {
        super(name, price, "3", quantity);
        this.color = color;
    }
    
    // Returns the paint color
    public String getColor() {
        return color;
    }
    
    // Sets the paint color
    public void setColor(String color) {
        this.color = color;
    }
    
    // Additional method specific to paint items
    // Returns the category name
    public String getCategoryName() {
        return "Paint & Supplies";
    }

}
