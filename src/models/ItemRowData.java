package models;

import javax.swing.*;

/**
 * ItemRowData - Data class to track row components efficiently.
 * 
 * This class holds references to UI components for a single item row in the
 * shopping cart, including the item name, quantity spinner, and total label.
 */
public class ItemRowData {
    public final String itemName;
    public final JSpinner spinner;
    public final JLabel totalLabel;
    
    public ItemRowData(String itemName, JSpinner spinner, JLabel totalLabel) {
        this.itemName = itemName;
        this.spinner = spinner;
        this.totalLabel = totalLabel;
    }
}
