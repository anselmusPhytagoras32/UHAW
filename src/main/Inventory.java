package main;

public class Inventory {
    // Changed to protected so subclasses can access them if needed
    protected String itemName;
    protected int unitPrice;
    protected int itemQuantity;

    public Inventory() {
    }

    public Inventory(String itemName, int unitPrice, int itemQuantity) {
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return unitPrice;
    }
    
    public void setPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getItemAmount() {
        return itemQuantity;
    }
    
    public void setItemAmount(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    
    @Override
    public String toString() {
        return itemName + " | Price: $" + unitPrice + " | Qty: " + itemQuantity;
    }
}