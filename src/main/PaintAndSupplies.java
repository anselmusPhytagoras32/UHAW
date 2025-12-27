package main;

public class PaintAndSupplies extends Inventory {
    
    public PaintAndSupplies() {
        super();
    }
    
    // Constructor reduced to only the 3 used parameters
    public PaintAndSupplies(String itemName, int unitPrice, int itemQuantity) {
        super(itemName, unitPrice, itemQuantity);
    }
    
    @Override
    public String toString() {
        return "Paint & Supply: " + itemName + " | Price: $" + unitPrice + " | Quantity: " + itemQuantity;
    }
}