package com.services;

import com.models.Item;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CacheManager {
    private final LRUCache l1Cache; // L1 Cache using LRU strategy
    private final Cache<Integer, Item> l2Cache; // L2 Cache using Guava

    // Constructor
    public CacheManager(int l1MaxSize) {
        l1Cache = new LRUCache(l1MaxSize); // Initialize L1 Cache with max size
        l2Cache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, java.util.concurrent.TimeUnit.MINUTES) // L2 Cache expiration
                .maximumSize(500) // Limit the number of items in the L2 cache
                .build();
    }

    // Get item from cache
    public Item getFromCache(int itemId) {
        // First check L1 cache
        Item item = l1Cache.get(itemId);
        if (item != null) {
            System.out.println("Item fetched from L1 Cache");
            return item; // Return item if found in L1 cache
        }

        // If not found in L1, check L2 cache
        item = l2Cache.getIfPresent(itemId);
        if (item != null) {
            System.out.println("Item fetched from L2 Cache");
            // Move it to L1 cache for faster access next time
            l1Cache.put(itemId, item);
            return item; // Return item if found in L2 cache
        }

        // Item not found in both caches
        System.out.println("Item not found in Cache");
        return null; // Return null if item is not found
    }

    // Add item to both caches
    public void addToCache(Item item) {
        if (item != null) {
            // Add to L1 cache
            l1Cache.put(item.getId(), item);
            // Add to L2 cache
            l2Cache.put(item.getId(), item);
            System.out.println("Item added to L1 and L2 Cache");
        } else {
            System.out.println("Null item cannot be added to Cache");
        }
    }

    // Inner LRUCache class
    private static class LRUCache {
        private final Map<Integer, Item> cache; // The actual cache
        private final int maxSize; // Maximum size of the cache

        public LRUCache(int maxSize) {
            this.maxSize = maxSize;

            // Using a synchronized block to make LinkedHashMap thread-safe
            cache = Collections.synchronizedMap(new LinkedHashMap<Integer, Item>(maxSize, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<Integer, Item> eldest) {
                    return size() > maxSize; // Remove the eldest entry if size exceeds maxSize
                }
            });
        }

        public synchronized Item get(int key) {
            return cache.get(key);
        }

        public synchronized void put(int key, Item value) {
            cache.put(key, value);
        }
    }
}


// package com.services;

// import com.models.Item;
// import com.google.common.cache.Cache;
// import com.google.common.cache.CacheBuilder;

// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;

// public class CacheManager {
//     private final Map<Integer, Item> l1Cache; // L1 Cache using ConcurrentHashMap
//     private final Cache<Integer, Item> l2Cache; // L2 Cache using Guava

//     public CacheManager() {
//         l1Cache = new ConcurrentHashMap<>(); // Initialize L1 Cache
//         l2Cache = CacheBuilder.newBuilder()
//                 .expireAfterWrite(10, java.util.concurrent.TimeUnit.MINUTES) // L2 Cache expiration
//                 .maximumSize(500) // Limit the number of items in the L2 cache
//                 .build();
//     }

//     // Get item from cache
//     public Item getFromCache(int itemId) {
//         // First check L1 cache
//         Item item = l1Cache.get(itemId);
//         if (item != null) {
//             System.out.println("Item fetched from L1 Cache");
//             return item; // Return item if found in L1 cache
//         }

//         // If not found in L1, check L2 cache
//         item = l2Cache.getIfPresent(itemId);
//         if (item != null) {
//             System.out.println("Item fetched from L2 Cache");
//             // Move it to L1 cache for faster access next time
//             l1Cache.put(itemId, item);
//             return item; // Return item if found in L2 cache
//         }

//         // Item not found in both caches
//         System.out.println("Item not found in Cache");
//         return null; // Return null if item is not found
//     }

//     // Add item to both caches
//     public void addToCache(Item item) {
//         if (item != null) {
//             // Add to L1 cache
//             l1Cache.put(item.getId(), item);
//             // Add to L2 cache
//             l2Cache.put(item.getId(), item);
//             System.out.println("Item added to L1 and L2 Cache");
//         } else {
//             System.out.println("Null item cannot be added to Cache");
//         }
//     }
// }
