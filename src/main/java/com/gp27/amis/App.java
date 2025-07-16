package com.gp27.amis;

import com.gp27.amis.datastructures.Stack;
import com.gp27.amis.datastructures.Queue;
import com.gp27.amis.datastructures.MinHeap;
import com.gp27.amis.datastructures.HashMap;
import com.gp27.amis.io.FileHandler;
import com.gp27.amis.models.Drug;
import com.gp27.amis.models.Supplier;
import com.gp27.amis.models.Customer;
import com.gp27.amis.models.Transaction;

import java.util.*;

/**
 * Main application class for Atinka Meds Inventory Management System (AMIS)
 */
public class App {
    private static Scanner scanner = new Scanner(System.in);
    
    // Core data structures
    private static HashMap<String, Drug> drugs = new HashMap<>();
    private static HashMap<String, Supplier> suppliers = new HashMap<>();
    private static HashMap<String, Customer> customers = new HashMap<>();
    private static Queue<Transaction> purchaseHistory = new Queue<>();
    private static Stack<Transaction> salesLog = new Stack<>();
    private static MinHeap<Drug> lowStockAlerts = new MinHeap<>();
    private static int stockThreshold = 10;

    public static void main(String[] args) {
        // Initialize data structures and load from files
        initializeSystem();
        
        // Main menu loop
        while (true) {
            displayMainMenu();
            String choice = scanner.nextLine();
            
            try {
                handleMenuChoice(Integer.parseInt(choice));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void initializeSystem() {
        // Ensure data directory exists
        FileHandler.ensureDataDirectory();
        
        // Initialize data structures
        drugs = FileHandler.loadDrugs();
        suppliers = FileHandler.loadSuppliers();
        customers = FileHandler.loadCustomers();
        salesLog = FileHandler.loadSalesLog();
        purchaseHistory = FileHandler.loadPurchaseHistory();
        lowStockAlerts = new MinHeap<>();
        
        // Initialize low stock alerts
        updateLowStockAlerts();
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Atinka Meds Inventory Management System ===");
        System.out.println("1. Manage Drugs");
        System.out.println("2. Manage Suppliers and Customers");
        System.out.println("3. Log Purchases and Sales");
        System.out.println("4. Monitor Stock Levels");
        System.out.println("5. Generate Reports");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                manageDrugs();
                break;
            case 2:
                manageSuppliersAndCustomers();
                break;
            case 3:
                manageTransactions();
                break;
            case 4:
                monitorStock();
                break;
            case 5:
                generateReports();
                break;
            case 6:
                saveAndExit();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void manageSuppliersAndCustomers() {
        System.out.println("\n=== Supplier and Customer Management ===");
        System.out.println("1. Manage Suppliers");
        System.out.println("2. Manage Customers");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    manageSuppliers();
                    break;
                case 2:
                    manageCustomers();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void manageSuppliers() {
        System.out.println("\n=== Supplier Management ===");
        System.out.println("1. Add Supplier");
        System.out.println("2. Update Supplier");
        System.out.println("3. Remove Supplier");
        System.out.println("4. List Suppliers");
        System.out.println("5. Back to Previous Menu");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addSupplier();
                    break;
                case 2:
                    updateSupplier();
                    break;
                case 3:
                    removeSupplier();
                    break;
                case 4:
                    listSuppliers();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void addSupplier() {
        System.out.print("Enter supplier ID: ");
        String id = scanner.nextLine();
        
        if (suppliers.containsKey(id)) {
            System.out.println("Error: Supplier ID already exists.");
            return;
        }
        
        System.out.print("Enter supplier name: ");
        String name = scanner.nextLine();
        System.out.print("Enter supplier location: ");
        String location = scanner.nextLine();
        System.out.print("Enter delivery time (in days): ");
        int deliveryTime = Integer.parseInt(scanner.nextLine());
        
        Supplier supplier = new Supplier(id, name, location, deliveryTime);
        suppliers.put(id, supplier);
        System.out.println("Supplier added successfully!");
    }

    private static void updateSupplier() {
        System.out.print("Enter supplier ID to update: ");
        String id = scanner.nextLine();
        
        Supplier supplier = suppliers.get(id);
        if (supplier == null) {
            System.out.println("Error: Supplier not found.");
            return;
        }
        
        System.out.println("Current supplier details:");
        System.out.println(supplier);
        
        System.out.print("Enter new name (or press Enter to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            supplier.setName(name);
        }
        
        System.out.print("Enter new location (or press Enter to keep current): ");
        String location = scanner.nextLine();
        if (!location.isEmpty()) {
            supplier.setLocation(location);
        }
        
        System.out.print("Enter new delivery time (or press Enter to keep current): ");
        String deliveryTimeStr = scanner.nextLine();
        if (!deliveryTimeStr.isEmpty()) {
            supplier.setDeliveryTime(Integer.parseInt(deliveryTimeStr));
        }
    }

    private static void removeSupplier() {
        System.out.print("Enter supplier ID to remove: ");
        String id = scanner.nextLine();
        
        if (suppliers.remove(id) != null) {
            System.out.println("Supplier removed successfully!");
        } else {
            System.out.println("Error: Supplier not found.");
        }
    }

    private static void listSuppliers() {
        System.out.println("\n=== Supplier List ===");
        for (Supplier supplier : suppliers.values()) {
            System.out.println(supplier);
        }
    }

    private static void manageCustomers() {
        System.out.println("\n=== Customer Management ===");
        System.out.println("1. Add Customer");
        System.out.println("2. Update Customer");
        System.out.println("3. Remove Customer");
        System.out.println("4. List Customers");
        System.out.println("5. Back to Previous Menu");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    updateCustomer();
                    break;
                case 3:
                    removeCustomer();
                    break;
                case 4:
                    listCustomers();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void addCustomer() {
        System.out.print("Enter customer ID: ");
        String id = scanner.nextLine();
        
        if (customers.containsKey(id)) {
            System.out.println("Error: Customer ID already exists.");
            return;
        }
        
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer contact info: ");
        String contact = scanner.nextLine();
        
        Customer customer = new Customer(id, name, contact);
        customers.put(id, customer);
        System.out.println("Customer added successfully!");
    }

    private static void updateCustomer() {
        System.out.print("Enter customer ID to update: ");
        String id = scanner.nextLine();
        
        Customer customer = customers.get(id);
        if (customer == null) {
            System.out.println("Error: Customer not found.");
            return;
        }
        
        System.out.println("Current customer details:");
        System.out.println(customer);
        
        System.out.print("Enter new name (or press Enter to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            customer.setName(name);
        }
        
        System.out.print("Enter new contact info (or press Enter to keep current): ");
        String contact = scanner.nextLine();
        if (!contact.isEmpty()) {
            customer.setContact(contact);
        }
    }

    private static void removeCustomer() {
        System.out.print("Enter customer ID to remove: ");
        String id = scanner.nextLine();
        
        if (customers.remove(id) != null) {
            System.out.println("Customer removed successfully!");
        } else {
            System.out.println("Error: Customer not found.");
        }
    }

    private static void listCustomers() {
        System.out.println("\n=== Customer List ===");
        for (Customer customer : customers.values()) {
            System.out.println(customer);
        }
    }

    private static void manageTransactions() {
        System.out.println("\n=== Transaction Management ===");
        System.out.println("1. Log Purchase");
        System.out.println("2. Log Sale");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    logPurchase();
                    break;
                case 2:
                    logSale();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void logPurchase() {
        System.out.print("Enter drug code: ");
        String drugCode = scanner.nextLine();
        Drug drug = drugs.get(drugCode);
        
        if (drug == null) {
            System.out.println("Error: Drug not found.");
            return;
        }
        
        System.out.print("Enter supplier ID: ");
        String supplierId = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        
        Transaction transaction = new Transaction(drugCode, quantity, supplierId, drug.getPrice() * quantity, true);
        purchaseHistory.enqueue(transaction);
        drug.setStockLevel(drug.getStockLevel() + quantity);
        updateLowStockAlerts();
        System.out.println("Purchase logged successfully!");
    }

    private static void logSale() {
        System.out.print("Enter drug code: ");
        String drugCode = scanner.nextLine();
        Drug drug = drugs.get(drugCode);
        
        if (drug == null) {
            System.out.println("Error: Drug not found.");
            return;
        }
        
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        
        if (drug.getStockLevel() < quantity) {
            System.out.println("Error: Insufficient stock.");
            return;
        }
        
        Transaction transaction = new Transaction(drugCode, quantity, customerId, drug.getPrice() * quantity, false);
        salesLog.push(transaction);
        drug.setStockLevel(drug.getStockLevel() - quantity);
        updateLowStockAlerts();
        System.out.println("Sale logged successfully!");
    }

    private static void monitorStock() {
        System.out.println("\n=== Stock Monitoring ===");
        System.out.println("1. View All Drugs");
        System.out.println("2. View Low Stock Drugs");
        System.out.println("3. Set Stock Threshold");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    listDrugs();
                    break;
                case 2:
                    viewLowStockDrugs();
                    break;
                case 3:
                    setStockThreshold();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void saveAndExit() {
        // Save all data to files
        FileHandler.saveDrugs(drugs);
        FileHandler.saveSuppliers(suppliers);
        FileHandler.saveCustomers(customers);
        FileHandler.saveSalesLog(salesLog);
        FileHandler.savePurchaseHistory(purchaseHistory);
        
        System.out.println("Data saved successfully. Goodbye!");
        scanner.close();
        System.exit(0);
    }

    private static void manageDrugs() {
        System.out.println("\n=== Drug Management ===");
        System.out.println("1. Add Drug");
        System.out.println("2. Update Drug");
        System.out.println("3. Remove Drug");
        System.out.println("4. List Drugs");
        System.out.println("5. Search Drugs");
        System.out.println("6. Sort Drugs");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addDrug();
                    break;
                case 2:
                    updateDrug();
                    break;
                case 3:
                    removeDrug();
                    break;
                case 4:
                    listDrugs();
                    break;
                case 5:
                    searchDrugs();
                    break;
                case 6:
                    sortDrugs();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void addDrug() {
        System.out.print("Enter drug name: ");
        String name = scanner.nextLine();
        System.out.print("Enter drug code: ");
        String code = scanner.nextLine();
        
        // Check if drug code already exists
        if (drugs.containsKey(code)) {
            System.out.println("Error: Drug code already exists.");
            return;
        }
        
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter initial stock level: ");
        int stockLevel = Integer.parseInt(scanner.nextLine());
        
        Drug newDrug = new Drug(name, code, price);
        newDrug.setStockLevel(stockLevel);
        
        // Add suppliers
        while (true) {
            System.out.print("Enter supplier ID (or 'done' to finish): ");
            String supplierId = scanner.nextLine();
            if (supplierId.equalsIgnoreCase("done")) {
                break;
            }
            if (suppliers.containsKey(supplierId)) {
                newDrug.addSupplier(supplierId);
            } else {
                System.out.println("Warning: Supplier not found. Adding anyway.");
                newDrug.addSupplier(supplierId);
            }
        }
        
        drugs.put(code, newDrug);
        updateLowStockAlerts();
        System.out.println("Drug added successfully!");
    }

    private static void updateDrug() {
        System.out.print("Enter drug code to update: ");
        String code = scanner.nextLine();
        
        Drug drug = drugs.get(code);
        if (drug == null) {
            System.out.println("Error: Drug not found.");
            return;
        }
        
        System.out.println("Current drug details:");
        System.out.println(drug);
        
        System.out.print("Enter new name (or press Enter to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            drug.setName(name);
        }
        
        System.out.print("Enter new price (or press Enter to keep current): ");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) {
            drug.setPrice(Double.parseDouble(priceInput));
        }
        
        System.out.print("Enter new stock level (or press Enter to keep current): ");
        String stockInput = scanner.nextLine();
        if (!stockInput.isEmpty()) {
            drug.setStockLevel(Integer.parseInt(stockInput));
            // Update low stock alerts if stock changed
            updateLowStockAlerts();
        }
        
        System.out.println("Drug updated successfully!");
    }

    private static void removeDrug() {
        System.out.print("Enter drug code to remove: ");
        String code = scanner.nextLine();
        
        if (drugs.remove(code) != null) {
            System.out.println("Drug removed successfully!");
            // Update low stock alerts
            updateLowStockAlerts();
        } else {
            System.out.println("Error: Drug not found.");
        }
    }

    private static void listDrugs() {
        System.out.println("\n=== Drug List ===");
        for (Drug drug : drugs.values()) {
            System.out.println(drug);
        }
    }

    private static void searchDrugs() {
        System.out.println("\n=== Search Drugs ===");
        System.out.println("1. Search by Code");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Supplier");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    searchByCode();
                    break;
                case 2:
                    searchByName();
                    break;
                case 3:
                    searchBySupplier();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void searchByCode() {
        System.out.print("Enter drug code: ");
        String code = scanner.nextLine();
        Drug drug = drugs.get(code);
        if (drug != null) {
            System.out.println("\nFound Drug:");
            System.out.println(drug);
            // Show suppliers
            if (!drug.getSuppliers().isEmpty()) {
                System.out.println("\nSuppliers:");
                for (String supplierId : drug.getSuppliers()) {
                    Supplier supplier = suppliers.get(supplierId);
                    if (supplier != null) {
                        System.out.println("- " + supplier.getName());
                    }
                }
            }
        } else {
            System.out.println("No drug found with code: " + code);
        }
    }

    private static void searchByName() {
        System.out.print("Enter drug name (partial or full): ");
        String name = scanner.nextLine().toLowerCase();
        
        System.out.println("\nSearch Results:");
        boolean found = false;
        
        // Linear search through all drugs
        for (Drug drug : drugs.values()) {
            if (drug.getName().toLowerCase().contains(name)) {
                found = true;
                System.out.println(drug);
            }
        }
        
        if (!found) {
            System.out.println("No drugs found matching: " + name);
        }
    }

    private static void searchBySupplier() {
        System.out.print("Enter supplier ID: ");
        String supplierId = scanner.nextLine();
        Supplier supplier = suppliers.get(supplierId);
        
        if (supplier == null) {
            System.out.println("Supplier not found.");
            return;
        }
        
        System.out.println("\nDrugs supplied by " + supplier.getName() + ":");
        boolean found = false;
        
        // Linear search through all drugs
        for (Drug drug : drugs.values()) {
            if (drug.getSuppliers().contains(supplierId)) {
                found = true;
                System.out.println(drug);
            }
        }
        
        if (!found) {
            System.out.println("No drugs found for this supplier.");
        }
    }

    private static void sortDrugs() {
        System.out.println("\n=== Sort Drugs ===");
        System.out.println("1. Sort by Name (Insertion Sort)");
        System.out.println("2. Sort by Price (Merge Sort)");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    sortByName();
                    break;
                case 2:
                    sortByPrice();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void sortByName() {
        System.out.println("\n=== Sorting Drugs by Name (Insertion Sort) ===");
        List<Drug> drugList = new ArrayList<>(drugs.values());
        
        // Insertion Sort
        for (int i = 1; i < drugList.size(); i++) {
            Drug key = drugList.get(i);
            int j = i - 1;
            
            while (j >= 0 && drugList.get(j).getName().compareTo(key.getName()) > 0) {
                drugList.set(j + 1, drugList.get(j));
                j--;
            }
            
            drugList.set(j + 1, key);
        }
        
        System.out.println("\nSorted Drug List:");
        for (Drug drug : drugList) {
            System.out.println(drug);
        }
    }

    private static void sortByPrice() {
        System.out.println("\n=== Sorting Drugs by Price (Merge Sort) ===");
        List<Drug> drugList = new ArrayList<>(drugs.values());
        
        // Merge Sort
        mergeSort(drugList, 0, drugList.size() - 1);
        
        System.out.println("\nSorted Drug List:");
        for (Drug drug : drugList) {
            System.out.println(drug);
        }
    }

    private static void mergeSort(List<Drug> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);
            
            merge(list, left, mid, right);
        }
    }

    private static void merge(List<Drug> list, int left, int mid, int right) {
        List<Drug> leftList = new ArrayList<>(list.subList(left, mid + 1));
        List<Drug> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));
        
        int i = 0, j = 0, k = left;
        
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).getPrice() <= rightList.get(j).getPrice()) {
                list.set(k, leftList.get(i));
                i++;
            } else {
                list.set(k, rightList.get(j));
                j++;
            }
            k++;
        }
        
        while (i < leftList.size()) {
            list.set(k, leftList.get(i));
            i++;
            k++;
        }
        
        while (j < rightList.size()) {
            list.set(k, rightList.get(j));
            j++;
            k++;
        }
    }

    private static void updateLowStockAlerts() {
        // Create temporary heap
        MinHeap<Drug> tempHeap = new MinHeap<>();
        
        // Add all low-stock drugs to heap
        for (Drug drug : drugs.values()) {
            if (drug.getStockLevel() <= stockThreshold) {
                tempHeap.insert(drug);
            }
        }
        
        // Replace existing alerts
        lowStockAlerts = tempHeap;
    }

    private static void viewLowStockDrugs() {
        System.out.println("\n=== Low Stock Drugs ===");
        System.out.println("Stock Threshold: " + stockThreshold + " units");
        
        if (lowStockAlerts.isEmpty()) {
            System.out.println("No drugs below stock threshold.");
            return;
        }
        
        // Create a temporary heap to store the drugs
        MinHeap<Drug> tempHeap = new MinHeap<>();
        
        // Extract all drugs from the original heap and insert them into the temp heap
        while (!lowStockAlerts.isEmpty()) {
            Drug drug = lowStockAlerts.extractMin();
            tempHeap.insert(drug);
            // Also re-insert the drug back into the original heap
            lowStockAlerts.insert(drug);
        }
        
        System.out.println("\nLow Stock Drugs:");
        System.out.println("----------------");
        while (!tempHeap.isEmpty()) {
            Drug drug = tempHeap.extractMin();
            System.out.printf("Code: %s, Name: %s, Stock: %d, Threshold: %d\n",
                drug.getCode(), drug.getName(), drug.getStockLevel(), stockThreshold);
        }
    }

    private static void setStockThreshold() {
        System.out.print("Enter new stock threshold (current: " + stockThreshold + "): ");
        try {
            int newThreshold = Integer.parseInt(scanner.nextLine());
            if (newThreshold < 0) {
                System.out.println("Threshold must be a positive number.");
                return;
            }
            
            stockThreshold = newThreshold;
            System.out.println("Stock threshold updated to: " + stockThreshold);
            
            // Update heap with new threshold
            updateLowStockAlerts();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void generateReports() {
        System.out.println("\n=== Report Generation ===");
        System.out.println("1. Generate Sales Report");
        System.out.println("2. Generate Purchase Report");
        System.out.println("3. Generate Inventory Report");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    generateSalesReport();
                    break;
                case 2:
                    generatePurchaseReport();
                    break;
                case 3:
                    generateInventoryReport();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void generateSalesReport() {
        System.out.println("\n=== Sales Report ===");
        System.out.println("Generated on: " + new Date());
        
        // Calculate statistics
        double totalRevenue = 0;
        com.gp27.amis.datastructures.HashMap<String, Integer> drugSalesCount = new com.gp27.amis.datastructures.HashMap<>();
        com.gp27.amis.datastructures.HashMap<String, Double> drugRevenue = new com.gp27.amis.datastructures.HashMap<>();
        
        // Clone sales log to avoid modifying original
        Stack<Transaction> tempStack = new Stack<>();
        Stack<Transaction> cloneStack = new Stack<>();
        while (!salesLog.isEmpty()) {
            Transaction sale = salesLog.pop();
            tempStack.push(sale);
            cloneStack.push(sale);
        }
        
        // Restore original stack
        while (!tempStack.isEmpty()) {
            salesLog.push(tempStack.pop());
        }
        
        // Process cloned stack
        while (!cloneStack.isEmpty()) {
            Transaction sale = cloneStack.pop();
            totalRevenue += sale.getTotalCost();
            
            // Update drug statistics
            String drugCode = sale.getDrugCode();
            drugSalesCount.put(drugCode, 
                drugSalesCount.getOrDefault(drugCode, 0) + sale.getQuantity());
            drugRevenue.put(drugCode, 
                drugRevenue.getOrDefault(drugCode, 0.0) + sale.getTotalCost());
        }
        
        // Generate report
        System.out.println("\nSales Statistics:");
        System.out.println("----------------");
        System.out.println("Total Sales: " + drugSalesCount.size());
        System.out.printf("Total Revenue: $%.2f\n", totalRevenue);
        
        System.out.println("\nDrug Sales Breakdown:");
        System.out.println("-----------------");
        System.out.println("Code | Name | Units Sold | Revenue");
        System.out.println("-----------------------------------");
        
        for (String drugCode : drugSalesCount.keySet()) {
            Drug drug = drugs.get(drugCode);
            if (drug != null) {
                System.out.printf("%s | %s | %d | $%.2f\n",
                    drugCode,
                    drug.getName(),
                    drugSalesCount.get(drugCode),
                    drugRevenue.get(drugCode));
            }
        }
    }

    private static void generatePurchaseReport() {
        System.out.println("\n=== Purchase Report ===");
        System.out.println("Generated on: " + new Date());
        
        // Calculate statistics
        double totalCost = 0;
        java.util.Map<String, Integer> supplierPurchases = new java.util.HashMap<String, Integer>();
        java.util.Map<String, Double> supplierCost = new java.util.HashMap<String, Double>();
        
        // Clone purchase history to avoid modifying original
        Queue<Transaction> tempQueue = new Queue<>();
        Queue<Transaction> cloneQueue = new Queue<>();
        while (!purchaseHistory.isEmpty()) {
            Transaction purchase = purchaseHistory.dequeue();
            tempQueue.enqueue(purchase);
            cloneQueue.enqueue(purchase);
        }
        
        // Restore original queue
        while (!tempQueue.isEmpty()) {
            purchaseHistory.enqueue(tempQueue.dequeue());
        }
        
        // Process cloned queue
        while (!cloneQueue.isEmpty()) {
            Transaction purchase = cloneQueue.dequeue();
            totalCost += purchase.getTotalCost();
            
            // Update supplier statistics
            String supplierId = purchase.getBuyerId();
            supplierPurchases.put(supplierId, 
                supplierPurchases.getOrDefault(supplierId, 0) + purchase.getQuantity());
            supplierCost.put(supplierId, 
                supplierCost.getOrDefault(supplierId, 0.0) + purchase.getTotalCost());
        }
        
        // Generate report
        System.out.println("\nPurchasing Statistics:");
        System.out.println("---------------------");
        System.out.printf("Total Cost: $%.2f\n", totalCost);
        
        System.out.println("\nSupplier Breakdown:");
        System.out.println("-----------------");
        System.out.println("ID | Name | Units Purchased | Cost");
        System.out.println("-----------------------------------");
        
        for (String supplierId : supplierPurchases.keySet()) {
            Supplier supplier = suppliers.get(supplierId);
            if (supplier != null) {
                System.out.printf("%s | %s | %d | $%.2f\n",
                    supplierId,
                    supplier.getName(),
                    supplierPurchases.get(supplierId),
                    supplierCost.get(supplierId));
            }
        }
    }

    private static void generateInventoryReport() {
        System.out.println("\n=== Inventory Report ===");
        System.out.println("Generated on: " + new Date());
        
        // Calculate statistics
        int totalDrugs = drugs.size();
        int totalStock = 0;
        double totalValue = 0;
        int lowStockCount = 0;
        
        // Process inventory
        for (Drug drug : drugs.values()) {
            totalStock += drug.getStockLevel();
            totalValue += drug.getPrice() * drug.getStockLevel();
            if (drug.getStockLevel() <= stockThreshold) {
                lowStockCount++;
            }
        }
        
        // Generate report
        System.out.println("\nInventory Statistics:");
        System.out.println("---------------------");
        System.out.println("Total Drugs: " + totalDrugs);
        System.out.println("Total Stock: " + totalStock);
        System.out.printf("Total Value: $%.2f\n", totalValue);
        System.out.printf("Low Stock Items: %d (%.2f%%)\n", 
            lowStockCount, (double) lowStockCount / totalDrugs * 100);
        
        // List all drugs with stock levels
        System.out.println("\nCurrent Inventory:");
        System.out.println("-----------------");
        System.out.println("Code | Name | Stock | Price | Value | Status");
        System.out.println("------------------------------------------------");
        
        for (Object obj : drugs.values()) {
            Drug drug = (Drug) obj;
            String status = drug.getStockLevel() <= stockThreshold ? "[LOW STOCK]" : "[OK]";
            System.out.printf("%s | %s | %d | $%.2f | $%.2f | %s\n",
                drug.getCode(),
                drug.getName(),
                drug.getStockLevel(),
                drug.getPrice(),
                drug.getPrice() * drug.getStockLevel(),
                status);
        }
    }
}