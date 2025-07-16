package com.gp27.amis.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Drug implements Comparable<Drug> {
    private String name;
    private String code;
    private List<String> suppliers;
    private LocalDate expirationDate;
    private double price;
    private int stockLevel;

    public Drug(String name, String code, double price) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.suppliers = new ArrayList<>();
        this.stockLevel = 0;
    }

    // Getters
    public String getName() { return name; }
    public String getCode() { return code; }
    public List<String> getSuppliers() { return suppliers; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public double getPrice() { return price; }
    public int getStockLevel() { return stockLevel; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
    public void setSuppliers(List<String> suppliers) { this.suppliers = suppliers; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    public void setPrice(double price) { this.price = price; }
    public void setStockLevel(int stockLevel) { this.stockLevel = stockLevel; }

    // Add supplier
    public void addSupplier(String supplierId) {
        if (!suppliers.contains(supplierId)) {
            suppliers.add(supplierId);
        }
    }

    // Remove supplier
    public void removeSupplier(String supplierId) {
        suppliers.remove(supplierId);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Stock: %d, Price: %.2f", 
            name, code, stockLevel, price);
    }

    @Override
    public int compareTo(Drug other) {
        return Integer.compare(this.stockLevel, other.stockLevel);
    }
}
