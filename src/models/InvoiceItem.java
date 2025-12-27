package models;

/**
 * InvoiceItem - Data class representing a line item in an invoice.
 * 
 * This class holds information about a single product purchased including
 * its description, quantity, unit price, and total amount.
 */
public class InvoiceItem {
    public String description;
    public int qty;
    public double unitPrice;
    public double amount;
    
    public InvoiceItem(String description, int qty, double unitPrice, double amount) {
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }
}
