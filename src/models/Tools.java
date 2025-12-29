package models;

// Represents a tool item in the inventory system
// Category: Tools (category ID "1")
public class Tools extends InventoryItem {
    private String powerSource;
    
    // Constructor for creating a tool item
    // name: tool name, price: selling price, quantity: stock level
    public Tools(String name, double price, int quantity) {
        super(name, price, "1", quantity);
        this.powerSource = "";
    }
    
    // Constructor with power source specification
    public Tools(String name, double price, int quantity, String powerSource) {
        super(name, price, "1", quantity);
        this.powerSource = powerSource;
    }
    
    // Returns the power source type
    public String getPowerSource() {
        return powerSource;
    }
    
    // Sets the power source type
    public void setPowerSource(String powerSource) {
        this.powerSource = powerSource;
    }
    
    // Additional method specific to tools
    // Returns the category name
    public String getCategoryName() {
        return "Tools";
    }
}
