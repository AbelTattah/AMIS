package com.gp27.amis.models;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String id;
    private String name;
    private String contact;
    private List<Transaction> transactionHistory;

    public Customer(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.transactionHistory = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public List<Transaction> getTransactionHistory() { return transactionHistory; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; }

    // Add transaction to history
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Contact: %s", name, id, contact);
    }
}
