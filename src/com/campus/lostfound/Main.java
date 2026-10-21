package com.campus.lostfound;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ItemDAO itemDAO = new ItemDAO();

    public static void main(String[] args) {
        System.out.println("\n>>> Starting Campus Lost & Found Tracker System...");

        if (!DBConnection.testConnection()) {
            System.out.println("\n[WARNING] Please verify your MySQL server connection before proceeding.");
            System.out.println("You can configure database credentials in DBConnection.java.\n");
        }

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    reportLostItem();
                    break;
                case 2:
                    reportFoundItem();
                    break;
                case 3:
                    viewAllItems();
                    break;
                case 4:
                    searchItemById();
                    break;
                case 5:
                    searchItemsByName();
                    break;
                case 6:
                    viewLostItems();
                    break;
                case 7:
                    viewFoundItems();
                    break;
                case 8:
                    updateItemDetails();
                    break;
                case 9:
                    markItemAsReturned();
                    break;
                case 10:
                    deleteItem();
                    break;
                case 11:
                    System.out.println("\n=======================================================");
                    System.out.println(" Thank you for using Campus Lost & Found Tracker!");
                    System.out.println(" Have a productive and safe day on campus. Goodbye!");
                    System.out.println("=======================================================\n");
                    running = false;
                    break;
                default:
                    System.out.println("\n[ERROR] Invalid choice (" + choice + "). Please enter a number between 1 and 11.\n");
            }
        }

        scanner.close();
    }
    private static void displayMenu() {
        System.out.println("========================================");
        System.out.println("       CAMPUS LOST & FOUND TRACKER      ");
        System.out.println("========================================");
        System.out.println("1. Report Lost Item");
        System.out.println("2. Report Found Item");
        System.out.println("3. View All Items");
        System.out.println("4. Search Item by ID");
        System.out.println("5. Search Items by Name");
        System.out.println("6. View Lost Items");
        System.out.println("7. View Found Items");
        System.out.println("8. Update Item Details");
        System.out.println("9. Mark Item as Returned");
        System.out.println("10. Delete Item");
        System.out.println("11. Exit");
        System.out.println("========================================");
    }
    private static void reportLostItem() {
        System.out.println("\n--- [REPORT A LOST ITEM] ---");
        Item item = collectItemInput("LOST");
        boolean success = itemDAO.addItem(item);

        if (success) {
            System.out.println("\n[SUCCESS] Lost item reported successfully!");
        } else {
            System.out.println("\n[FAILURE] Could not report lost item. Please check the database connection.");
        }
        pressEnterToContinue();
    }
    private static void reportFoundItem() {
        System.out.println("\n--- [REPORT A FOUND ITEM] ---");
        Item item = collectItemInput("FOUND");
        boolean success = itemDAO.addItem(item);

        if (success) {
            System.out.println("\n[SUCCESS] Found item reported successfully! Thank you for your honesty.");
        } else {
            System.out.println("\n[FAILURE] Could not report found item. Please check the database connection.");
        }
        pressEnterToContinue();
    }

    private static void viewAllItems() {
        System.out.println("\n--- [ALL REPORTED ITEMS] ---");
        List<Item> items = itemDAO.getAllItems();
        printItemsTable(items);
        pressEnterToContinue();
    }
    private static void searchItemById() {
        System.out.println("\n--- [SEARCH ITEM BY ID] ---");
        int id = readInt("Enter Item ID to search: ");
        Item item = itemDAO.getItemById(id);

        if (item != null) {
            System.out.println("\n[ITEM FOUND DETAILS]");
            printSingleItem(item);
        } else {
            System.out.println("\n[!] No item found with ID: " + id);
        }
        pressEnterToContinue();
    }
    private static void searchItemsByName() {
        System.out.println("\n--- [SEARCH ITEMS BY NAME] ---");
        String name = readNonEmptyString("Enter item name or keyword to search: ");
        List<Item> items = itemDAO.getItemsByName(name);

        if (items.isEmpty()) {
            System.out.println("\n[!] No items found matching keyword: \"" + name + "\"");
        } else {
            System.out.println("\n[SUCCESS] Found " + items.size() + " matching record(s):");
            printItemsTable(items);
        }
        pressEnterToContinue();
    }

   
    private static void viewLostItems() {
        System.out.println("\n--- [VIEW LOST ITEMS] ---");
        List<Item> items = itemDAO.getItemsByType("LOST");
        printItemsTable(items);
        pressEnterToContinue();
    }
    private static void viewFoundItems() {
        System.out.println("\n--- [VIEW FOUND ITEMS] ---");
        List<Item> items = itemDAO.getItemsByType("FOUND");
        printItemsTable(items);
        pressEnterToContinue();
    }

   
    private static void updateItemDetails() {
        System.out.println("\n--- [UPDATE ITEM DETAILS] ---");
        int id = readInt("Enter the Item ID to update: ");
        Item existing = itemDAO.getItemById(id);

        if (existing == null) {
            System.out.println("\n[!] Cannot update. No item found with ID: " + id);
            pressEnterToContinue();
            return;
        }

        System.out.println("\nCurrent item information:");
        printSingleItem(existing);
        System.out.println("(Press [ENTER] without typing anything to keep the current value)\n");

        System.out.print("New Item Name [" + existing.getItemName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            existing.setItemName(name);
        }

        System.out.print("New Category [" + existing.getCategory() + "]: ");
        String category = scanner.nextLine().trim();
        if (!category.isEmpty()) {
            existing.setCategory(category);
        }

        System.out.print("New Description [" + existing.getDescription() + "]: ");
        String description = scanner.nextLine().trim();
        if (!description.isEmpty()) {
            existing.setDescription(description);
        }

        System.out.print("New Location [" + existing.getLocation() + "]: ");
        String location = scanner.nextLine().trim();
        if (!location.isEmpty()) {
            existing.setLocation(location);
        }

        System.out.print("New Reported By [" + existing.getReportedBy() + "]: ");
        String reportedBy = scanner.nextLine().trim();
        if (!reportedBy.isEmpty()) {
            existing.setReportedBy(reportedBy);
        }

        System.out.print("New Contact Number [" + existing.getContactNumber() + "]: ");
        String contact = scanner.nextLine().trim();
        if (!contact.isEmpty()) {
            existing.setContactNumber(contact);
        }

        System.out.print("New Status (LOST / FOUND / RETURNED) [" + existing.getStatus() + "]: ");
        String status = scanner.nextLine().trim().toUpperCase();
        if (!status.isEmpty()) {
            if (status.equals("LOST") || status.equals("FOUND") || status.equals("RETURNED")) {
                existing.setStatus(status);
            } else {
                System.out.println("[Notice] Invalid status entered. Retaining previous status: " + existing.getStatus());
            }
        }

        boolean success = itemDAO.updateItem(existing);
        if (success) {
            System.out.println("\n[SUCCESS] Item ID #" + id + " updated successfully!");
        } else {
            System.out.println("\n[FAILURE] Failed to update item details in the database.");
        }
        pressEnterToContinue();
    }
    private static void markItemAsReturned() {
        System.out.println("\n--- [MARK ITEM AS RETURNED] ---");
        int id = readInt("Enter the Item ID to mark as returned: ");
        Item item = itemDAO.getItemById(id);

        if (item == null) {
            System.out.println("\n[!] No item found with ID: " + id);
            pressEnterToContinue();
            return;
        }

        if ("RETURNED".equalsIgnoreCase(item.getStatus())) {
            System.out.println("\n[NOTICE] Item ID #" + id + " is already marked as 'RETURNED'.");
            pressEnterToContinue();
            return;
        }

        System.out.println("\nSelected Item:");
        System.out.println("  ID     : " + item.getId());
        System.out.println("  Name   : " + item.getItemName());
        System.out.println("  Type   : " + item.getReportType());
        System.out.println("  Current: " + item.getStatus());

        System.out.print("\nConfirm marking this item as RETURNED? (Y/N): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            boolean success = itemDAO.updateItemStatus(id, "RETURNED");
            if (success) {
                System.out.println("\n[SUCCESS] Item ID #" + id + " has been successfully marked as RETURNED!");
            } else {
                System.out.println("\n[FAILURE] Could not update item status.");
            }
        } else {
            System.out.println("\n[INFO] Operation cancelled. Status unchanged.");
        }
        pressEnterToContinue();
    }

 
    private static void deleteItem() {
        System.out.println("\n--- [DELETE AN ITEM] ---");
        int id = readInt("Enter the Item ID to delete: ");
        Item item = itemDAO.getItemById(id);

        if (item == null) {
            System.out.println("\n[!] Cannot delete. No item found with ID: " + id);
            pressEnterToContinue();
            return;
        }

        System.out.println("\nItem to be permanently deleted:");
        printSingleItem(item);

        System.out.print("Are you sure you want to delete this record? (Y/N): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            boolean success = itemDAO.deleteItem(id);
            if (success) {
                System.out.println("\n[SUCCESS] Item ID #" + id + " was deleted successfully!");
            } else {
                System.out.println("\n[FAILURE] Failed to delete the item from database.");
            }
        } else {
            System.out.println("\n[INFO] Deletion cancelled by user.");
        }
        pressEnterToContinue();
    }

    private static Item collectItemInput(String reportType) {
        String itemName = readNonEmptyString("Enter Item Name (required): ");
        
        System.out.print("Enter Category (e.g. Electronics, Documents, Stationery, Personal): ");
        String category = scanner.nextLine().trim();
        if (category.isEmpty()) category = "General";

        System.out.print("Enter Description (color, brand, distinguishing marks): ");
        String description = scanner.nextLine().trim();
        if (description.isEmpty()) description = "No description provided";

        System.out.print("Enter Location (" + (reportType.equals("LOST") ? "where it was lost" : "where it was found") + "): ");
        String location = scanner.nextLine().trim();
        if (location.isEmpty()) location = "Campus premises";

        System.out.print("Enter Reporter's Name: ");
        String reportedBy = scanner.nextLine().trim();
        if (reportedBy.isEmpty()) reportedBy = "Anonymous";

        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine().trim();
        if (contactNumber.isEmpty()) contactNumber = "N/A";

        Date reportedDate = readDateInput();
        String initialStatus = reportType.toUpperCase(); // Defaults to "LOST" or "FOUND"

        return new Item(itemName, category, description, location, reportType,
                        reportedBy, contactNumber, reportedDate, initialStatus);
    }

   
    private static Date readDateInput() {
        while (true) {
            System.out.print("Enter Date (YYYY-MM-DD) [Press ENTER for Today]: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return Date.valueOf(LocalDate.now());
            }

            try {
                LocalDate parsedDate = LocalDate.parse(input);
                return Date.valueOf(parsedDate);
            } catch (DateTimeParseException e) {
                System.out.println("[!] Invalid date format. Please use YYYY-MM-DD (e.g., 2026-10-19).");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[!] This field cannot be empty. Please enter a value.");
        }
    }


    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid whole number.");
            }
        }
    }
    private static void printItemsTable(List<Item> items) {
        if (items == null || items.isEmpty()) {
            System.out.println("\n[INFO] No records found.");
            return;
        }

        String format = "| %-4s | %-18s | %-14s | %-6s | %-9s | %-20s | %-15s | %-12s | %-10s |\n";
        String line   = "+------+--------------------+----------------+--------+-----------+----------------------+-----------------+--------------+------------+";

        System.out.println(line);
        System.out.printf(format, "ID", "Item Name", "Category", "Type", "Status", "Location", "Reported By", "Contact", "Date");
        System.out.println(line);

        for (Item item : items) {
            System.out.printf(format,
                truncate(String.valueOf(item.getId()), 4),
                truncate(item.getItemName(), 18),
                truncate(item.getCategory(), 14),
                truncate(item.getReportType(), 6),
                truncate(item.getStatus(), 9),
                truncate(item.getLocation(), 20),
                truncate(item.getReportedBy(), 15),
                truncate(item.getContactNumber(), 12),
                item.getReportedDate().toString()
            );
        }
        System.out.println(line);
        System.out.println("Total records displayed: " + items.size());
    }


    private static void printSingleItem(Item item) {
     //   System.out.println("+------------------------------------------------------------+");
        System.out.printf("| %-15s : %-40s |\n", "Item ID", item.getId());
        System.out.printf("| %-15s : %-40s |\n", "Item Name", item.getItemName());
        System.out.printf("| %-15s : %-40s |\n", "Category", item.getCategory());
        System.out.printf("| %-15s : %-40s |\n", "Description", item.getDescription());
        System.out.printf("| %-15s : %-40s |\n", "Location", item.getLocation());
        System.out.printf("| %-15s : %-40s |\n", "Report Type", item.getReportType());
        System.out.printf("| %-15s : %-40s |\n", "Current Status", item.getStatus());
        System.out.printf("| %-15s : %-40s |\n", "Reported By", item.getReportedBy());
        System.out.printf("| %-15s : %-40s |\n", "Contact Number", item.getContactNumber());
        System.out.printf("| %-15s : %-40s |\n", "Reported Date", item.getReportedDate());
     //   System.out.println("+------------------------------------------------------------+");
    }
    private static String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 2) + "..";
    }
    private static void pressEnterToContinue() {
        System.out.print("\nPress [ENTER] to return to the menu...");
        scanner.nextLine();
        System.out.println();
    }
}
