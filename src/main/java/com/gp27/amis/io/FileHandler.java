package com.gp27.amis.io;

import com.gp27.amis.models.*;
import com.gp27.amis.datastructures.HashMap;
import com.gp27.amis.datastructures.Stack;
import com.gp27.amis.datastructures.Queue;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FileHandler {
    private static final String DRUGS_FILE = "data/drugs.txt";
    private static final String SUPPLIERS_FILE = "data/suppliers.txt";
    private static final String CUSTOMERS_FILE = "data/customers.txt";
    private static final String SALES_LOG_FILE = "data/sales_log.txt";
    private static final String PURCHASE_HISTORY_FILE = "data/purchase_history.txt";

    public static void saveDrugs(HashMap<String, Drug> drugs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DRUGS_FILE))) {
            for (Drug drug : drugs.values()) {
                writer.write(String.format("%s|%s|%s|%s|%f|%d", 
                    drug.getCode(), drug.getName(),
                    String.join(",", drug.getSuppliers()),
                    drug.getExpirationDate(), drug.getPrice(), drug.getStockLevel()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving drugs: " + e.getMessage());
        }
    }

    public static HashMap<String, Drug> loadDrugs() {
        HashMap<String, Drug> drugs = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DRUGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                Drug drug = new Drug(parts[1], parts[0], Double.parseDouble(parts[4]));
                drug.setSuppliers(Arrays.asList(parts[2].split(",")));
                drug.setExpirationDate(LocalDate.parse(parts[3]));
                drug.setStockLevel(Integer.parseInt(parts[5]));
                drugs.put(drug.getCode(), drug);
            }
        } catch (IOException e) {
            System.err.println("Error loading drugs: " + e.getMessage());
        }
        return drugs;
    }

    public static void saveSuppliers(HashMap<String, Supplier> suppliers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SUPPLIERS_FILE))) {
            for (Supplier supplier : suppliers.values()) {
                writer.write(String.format("%s|%s|%s|%d|%s", 
                    supplier.getId(), supplier.getName(),
                    supplier.getLocation(), supplier.getDeliveryTime(),
                    String.join(",", supplier.getDrugCodes())));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving suppliers: " + e.getMessage());
        }
    }

    public static HashMap<String, Supplier> loadSuppliers() {
        HashMap<String, Supplier> suppliers = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SUPPLIERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                Supplier supplier = new Supplier(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
                supplier.setDrugCodes(Arrays.asList(parts[4].split(",")));
                suppliers.put(supplier.getId(), supplier);
            }
        } catch (IOException e) {
            System.err.println("Error loading suppliers: " + e.getMessage());
        }
        return suppliers;
    }

    public static void saveCustomers(HashMap<String, Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer customer : customers.values()) {
                writer.write(String.format("%s|%s|%s", 
                    customer.getId(), customer.getName(), customer.getContact()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }

    public static HashMap<String, Customer> loadCustomers() {
        HashMap<String, Customer> customers = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                Customer customer = new Customer(parts[0], parts[1], parts[2]);
                customers.put(customer.getId(), customer);
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
        return customers;
    }

    public static void saveSalesLog(Stack<Transaction> salesLog) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SALES_LOG_FILE))) {
            Stack<Transaction> temp = new Stack<>();
            while (!salesLog.isEmpty()) {
                Transaction transaction = salesLog.pop();
                writer.write(String.format("%s|%d|%s|%s|%.2f", 
                    transaction.getDrugCode(), transaction.getQuantity(),
                    transaction.getBuyerId(), transaction.getTimestamp(),
                    transaction.getTotalCost()));
                writer.newLine();
                temp.push(transaction);
            }
            // Restore original stack
            while (!temp.isEmpty()) {
                salesLog.push(temp.pop());
            }
        } catch (IOException e) {
            System.err.println("Error saving sales log: " + e.getMessage());
        }
    }

    public static Stack<Transaction> loadSalesLog() {
        Stack<Transaction> salesLog = new Stack<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SALES_LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                Transaction transaction = new Transaction(
                    parts[0], Integer.parseInt(parts[1]), parts[2],
                    Double.parseDouble(parts[4]), false
                );
                transaction.setTimestamp(LocalDateTime.parse(parts[3]));
                salesLog.push(transaction);
            }
        } catch (IOException e) {
            System.err.println("Error loading sales log: " + e.getMessage());
        }
        return salesLog;
    }

    public static void savePurchaseHistory(Queue<Transaction> purchaseHistory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PURCHASE_HISTORY_FILE))) {
            for (Transaction transaction : purchaseHistory) {
                writer.write(String.format("%s|%d|%s|%s|%.2f", 
                    transaction.getDrugCode(), transaction.getQuantity(),
                    transaction.getBuyerId(), transaction.getTimestamp(),
                    transaction.getTotalCost()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving purchase history: " + e.getMessage());
        }
    }

    public static Queue<Transaction> loadPurchaseHistory() {
        Queue<Transaction> purchaseHistory = new com.gp27.amis.datastructures.Queue<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PURCHASE_HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                Transaction transaction = new Transaction(
                    parts[0], Integer.parseInt(parts[1]), parts[2],
                    Double.parseDouble(parts[4]), true
                );
                transaction.setTimestamp(LocalDateTime.parse(parts[3]));
                purchaseHistory.add(transaction);
            }
        } catch (IOException e) {
            System.err.println("Error loading purchase history: " + e.getMessage());
        }
        return purchaseHistory;
    }

    public static void ensureDataDirectory() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }
}
