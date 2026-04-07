/**
 * Book My Stay App - Use Case 3
 * Centralized Room Inventory Management
 *
 * Demonstrates use of HashMap for centralized inventory handling.
 *
 * @author Vishnu
 * @version 3.0
 */

import java.util.HashMap;
import java.util.Map;

// Inventory Class (Encapsulates all availability logic)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor - initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:\n");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
    }
}

// Main Class
public class UC3 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Book My Stay App - v3.0            ");
        System.out.println("======================================");

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Display Inventory
        inventory.displayInventory();

        // Example Update
        System.out.println("\nUpdating availability...\n");
        inventory.updateAvailability("Single Room", 4);

        // Fetch specific availability
        int singleAvailable = inventory.getAvailability("Single Room");
        System.out.println("Updated Single Room Availability: " + singleAvailable);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication finished.");
    }
}