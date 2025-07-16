package com.gp27.amis.models;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private String id;
    private String name;
    private String location;
    private int deliveryTime;
    private List<String> drugCodes;

    public Supplier(String id, String name, String location, int deliveryTime) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.deliveryTime = deliveryTime;
        this.drugCodes = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getDeliveryTime() { return deliveryTime; }
    public List<String> getDrugCodes() { return drugCodes; }

    // Setters
    public void setDrugCodes(List<String> drugCodes) {
        this.drugCodes.clear();
        this.drugCodes.addAll(drugCodes);
    }
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setDeliveryTime(int deliveryTime) { this.deliveryTime = deliveryTime; }

    // Add drug
    public void addDrug(String drugCode) {
        if (!drugCodes.contains(drugCode)) {
            drugCodes.add(drugCode);
        }
    }

    // Remove drug
    public void removeDrug(String drugCode) {
        drugCodes.remove(drugCode);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Location: %s, Delivery Time: %d days", 
            name, id, location, deliveryTime);
    }
}
