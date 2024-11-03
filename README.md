
# Console based Inventory management system

The Inventory Management System is a Java-based console application designed to help users manage and keep track of inventory items effectively. With features to add, update, view, and delete items, this system provides a straightforward interface for managing stock and ensuring that items are readily available.

Uses concepts of multithreading , L1 and L2 cache implementation

## Features

- **View Item by ID:** Retrieve and display details of a specific item using its unique identifier.
- **Add New Item:** Insert new items into the inventory with details such as ID, name, quantity, and price.
- **Delete Item:** Remove items from the inventory based on their ID.
- **List All Items:** Display a complete list of all items currently in the inventory.
- **Update Item Quantity:** Modify the quantity of an existing item.
- **Update Item Price:** Change the price of a specific item.
- **Check Item Availability:** Verify if a specific item is available in stock.


## Installation


```bash
    git clone <repository-url>
    cd inventory-management

```
In main folder add resources with own application.properties file

```bash
    db.url= <your databse url>
    db.user=<your mysql username>
    db.password= <your mysql password>
```

do ```mvn clean install``` and then run the Main.java file

create database in mysql and the create table in it 

```bash
    create table items(
    item_id int primary key,
    name varchar(70),
    quantity int ,
    price double 
    );
```
## Technology used

-  Java
- SQL (MySQL)
- Caching Libraries (Guava for L2 caching)
- JDBC (Java Database Connectivity)
## Main Components

 **Model Layer:**

  -  Item: Represents an item in the inventory.
 **Service Layer:**

-  InventoryService: Handles business logic and interacts with CacheManager and DatabaseManager.
- CacheManager: Manages two levels of caching (L1 and L2).
- DatabaseManager: Responsible for all database interactions (CRUD operations).
 **Utils:**
- PerformanceTracker: Logs the retrieval times for performance analysis.
 **Main Class:**

- Main: Contains the main method and user interface to interact with the system.
## DEMO

```bash
Inventory Management System
1. View Item by ID        
2. Add New Item
3. Delete Item
4. List All Items
5. Update Item Quantity   
6. Update Item Price      
7. Check Item Availability
8. Exit
Choose an option:         

```

lets say we already have some data in database then 
```bash
8. Exit
Choose an option: 4

Inventory Management System
1. View Item by ID
2. Add New Item
3. Delete Item
4. List All Items
5. Update Item Quantity
6. Update Item Price
7. Check Item Availability
8. Exit
Choose an option: Item{id=1, name='shampoo', quantity=3, price=900.0}
Item{id=2, name='hair oil', quantity=1, price=300.0}
```

So this is how we can use each functionality of our project
## Future Optimizations needed

When adding a product we should not pass id instead it should auto increment and we can add more exeptional handlings for more readability of error.

