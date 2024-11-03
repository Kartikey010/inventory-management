package com;
import com.models.Item;
import com.services.InventoryService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            InventoryService inventoryService = new InventoryService();
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\nInventory Management System");
                System.out.println("1. View Item by ID");
                System.out.println("2. Add New Item");
                System.out.println("3. Delete Item");
                System.out.println("4. List All Items");
                System.out.println("5. Update Item Quantity");
                System.out.println("6. Update Item Price");
                System.out.println("7. Check Item Availability");
                System.out.println("8. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Item ID: ");
                        int id = scanner.nextInt();
                        inventoryService.viewItem(id);
                        break;
                    case 2:
                        System.out.print("Enter Item ID: ");
                        int newId = scanner.nextInt();
                        scanner.nextLine(); // Add this line to consume the leftover newline
                        System.out.print("Enter Item Name: ");
                        String name = scanner.nextLine(); // Change from next() to nextLine()
                        System.out.print("Enter Item Quantity: ");
                        int quantity = scanner.nextInt();
                        System.out.print("Enter Item Price: ");
                        double price = scanner.nextDouble();
                        Item newItem = new Item(newId, name, quantity, price);
                        inventoryService.addItem(newItem);
                        break;
                    case 3:
                        System.out.print("Enter Item ID to delete: ");
                        int deleteId = scanner.nextInt();
                        inventoryService.deleteItem(deleteId);
                        break;
                    case 4:
                        inventoryService.listAllItems();
                        break;
                    case 5:
                        System.out.print("Enter Item ID: ");
                        int updateId = scanner.nextInt();
                        System.out.print("Enter new quantity: ");
                        int newQuantity = scanner.nextInt();
                        inventoryService.updateItemQuantity(updateId, newQuantity);
                        break;
                    case 6:
                        System.out.print("Enter Item ID: ");
                        int priceId = scanner.nextInt();
                        System.out.print("Enter new price: ");
                        double newPrice = scanner.nextDouble();
                        inventoryService.updateItemPrice(priceId, newPrice);
                        break;
                    case 7:
                        System.out.print("Enter Item ID to check availability: ");
                        int checkId = scanner.nextInt();
                        inventoryService.checkItemAvailability(checkId);
                        break;
                    case 8:
                        running = false;
                        inventoryService.shutdown();
                        System.out.println("Exiting Inventory Management System.");
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
            
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

