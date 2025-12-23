package main;

public class BuildingMaterials extends Inventory {
    
    private String material;
    private String size;
    
    public BuildingMaterials() {
        super();
    }
    
    public BuildingMaterials(String itemName, int unitPrice, int itemQuantity, String material, String size) {
        super(itemName, unitPrice, itemQuantity);
        this.material = material;
        this.size = size;
    }
    
    public String getMaterial() {
        return material;
    }
    
    public void setMaterial(String material) {
        this.material = material;
    }
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    @Override
    public String toString() {
        return "Building Material: " + itemName + " - Material: " + material + " | Size: " + size + 
               " | Price: $" + unitPrice + " | Quantity: " + itemQuantity;
    }
}
