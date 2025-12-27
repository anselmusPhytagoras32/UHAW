package main;

public class Tools extends Inventory {
    
    public Tools() {
        super();
    }
    
    // Constructor reduced to only the 3 used parameters
    public Tools(String itemName, int unitPrice, int itemQuantity) {
        super(itemName, unitPrice, itemQuantity);
    }
    
    @Override
    public String toString() {
        return "Tool: " + itemName + " | Price: $" + unitPrice + " | Quantity: " + itemQuantity;
    }
}