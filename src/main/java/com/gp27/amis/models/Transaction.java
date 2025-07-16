package com.gp27.amis.models;

import java.time.LocalDateTime;

public class Transaction {
    private String drugCode;
    private int quantity;
    private LocalDateTime timestamp;
    private String buyerId;
    private double totalCost;
    private boolean isPurchase;

    public Transaction(String drugCode, int quantity, String buyerId, double totalCost, boolean isPurchase) {
        this.drugCode = drugCode;
        this.quantity = quantity;
        this.buyerId = buyerId;
        this.totalCost = totalCost;
        this.isPurchase = isPurchase;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getDrugCode() { return drugCode; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getBuyerId() { return buyerId; }
    public double getTotalCost() { return totalCost; }
    public boolean isPurchase() { return isPurchase; }

    // Setters
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setDrugCode(String drugCode) { this.drugCode = drugCode; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    @Override
    public String toString() {
        String type = isPurchase ? "Purchase" : "Sale";
        return String.format("%s - %s: %d units of %s for %.2f", 
            timestamp, type, quantity, drugCode, totalCost);
    }
}
