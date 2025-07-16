package com.gp27.amis.models;

public class SalesRecord extends Transaction {
    public SalesRecord(String drugCode, int quantity, String buyerId, double totalCost) {
        super(drugCode, quantity, buyerId, totalCost, false);
    }
}
