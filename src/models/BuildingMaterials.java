package models;

// Represents a building material item in the inventory system
// Category: Building Materials (category ID "2")
public class BuildingMaterials extends InventoryItem {
    private String material;
    
    // Constructor for creating a building material item
    // name: material name, price: selling price, quantity: stock level
    public BuildingMaterials(String name, double price, int quantity) {
        super(name, price, "2", quantity);
        this.material = "";
    }
    
    // Constructor with material specification
    public BuildingMaterials(String name, double price, int quantity, String material) {
        super(name, price, "2", quantity);
        this.material = material;
    }
    
    // Returns the material type
    public String getMaterial() {
        return material;
    }
    
    // Sets the material type
    public void setMaterial(String material) {
        this.material = material;
    }
    
    // Additional method specific to building materials
    // Returns the category name
    public String getCategoryName() {
        return "Building Materials";
    }
}