package main;

public class BuildingMaterials extends Inventory {
    
    public BuildingMaterials() {
        super();
    }
    
    // Constructor reduced to only the 3 used parameters
    public BuildingMaterials(String itemName, int unitPrice, int itemQuantity) {
        super(itemName, unitPrice, itemQuantity);
    }
    
    @Override
    public String toString() {
        return "Building Material: " + itemName + " | Price: $" + unitPrice + " | Quantity: " + itemQuantity;
    }
}