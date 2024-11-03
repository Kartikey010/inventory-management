package com.services;

import com.models.Item;
import com.utils.PerformanceTracker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InventoryService {

    private final CacheManager cacheManager = new CacheManager(20);
    private final DatabaseManager databaseManager;
    private final PerformanceTracker performanceTracker = new PerformanceTracker();
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public InventoryService() throws Exception {
        databaseManager = new DatabaseManager();
    }

    public void viewItem(int itemId) {
        executorService.submit(() -> {
            long startTime = System.nanoTime();
            Item item = cacheManager.getFromCache(itemId);
            if (item == null) {
                item = databaseManager.fetchItemFromDB(itemId);
                if (item != null) {
                    cacheManager.addToCache(item);
                }
            }
            long endTime = System.nanoTime();
            System.out.println(item);
            performanceTracker.logRetrievalTime("L1/L2 Cache or DB", endTime - startTime);
        });
    }

    public void addItem(Item item) {
        executorService.submit(() -> {
            databaseManager.addItemToDB(item);
            cacheManager.addToCache(item);
        });
    }

    public void deleteItem(int itemId) {
        executorService.submit(() -> {
            databaseManager.deleteItemFromDB(itemId);
            // Remove from cache if exists
            cacheManager.addToCache(null); // or implement a more specific cache removal method
        });
    }

    public void listAllItems() {
        executorService.submit(() -> {
            List<Item> items = databaseManager.listAllItems();
            items.forEach(System.out::println);
        });
    }

    public void updateItemQuantity(int itemId, int newQuantity) {
        executorService.submit(() -> {
            databaseManager.updateItemQuantityInDB(itemId, newQuantity);
            executorService.submit(() -> {
                Item updatedItem = databaseManager.fetchItemFromDB(itemId);
                if (updatedItem != null) {
                    cacheManager.addToCache(updatedItem);
                }
            });
        });
    }

    public void updateItemPrice(int itemId, double newPrice) {
        executorService.submit(() -> {
            databaseManager.updateItemPriceInDB(itemId, newPrice);
            executorService.submit(() -> {
                Item updatedItem = databaseManager.fetchItemFromDB(itemId);
                if (updatedItem != null) {
                    cacheManager.addToCache(updatedItem);
                }
            });
        });
    }

    public void checkItemAvailability(int itemId) {
        executorService.submit(() -> {
            boolean isAvailable = databaseManager.checkItemAvailability(itemId);
            System.out.println("Item availability for ID " + itemId + ": " + (isAvailable ? "In Stock" : "Out of Stock"));
        });
    }

    public void shutdown() {
        executorService.shutdown();
        databaseManager.close();
    }
}
