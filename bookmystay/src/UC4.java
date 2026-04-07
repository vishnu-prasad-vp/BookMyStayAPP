/**
 * Book My Stay App - Use Case 4
 * Room Search & Availability Check
 *
 * Demonstrates read-only inventory access and filtering available rooms.
 *
 * @author Vishnu
 * @version 4.0
 */

package uc4;

import java.util.*;

// Abstract Room (UC4 specific)
abstract class RoomUC4 {
    private String type;
    private int beds;
    private double price;

    public RoomUC4(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() { return type; }
    public int getBeds() { return beds; }
    public double getPrice() { return price; }

    public abstract void displayDetails();
}

// Concrete Rooms
class SingleRoomUC4 extends RoomUC4 {
    public SingleRoomUC4() {
        super("Single Room", 1, 2000);
    }

    public void displayDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

class DoubleRoomUC4 extends RoomUC4 {
    public DoubleRoomUC4() {
        super("Double Room", 2, 3500);
    }

    public void displayDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

class SuiteRoomUC4 extends RoomUC4 {
    public SuiteRoomUC4() {
        super("Suite Room", 3, 6000);
    }

    public void displayDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

// Inventory (Read-only usage)
class RoomInventoryUC4 {
    private HashMap<String, Integer> inventory;

    public RoomInventoryUC4() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // unavailable
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Search Service
class RoomSearchServiceUC4 {

    public static void searchAvailableRooms(RoomInventoryUC4 inventory, List<RoomUC4> rooms) {

        System.out.println("\nAvailable Rooms:\n");

        for (RoomUC4 room : rooms) {
            int available = inventory.getAvailability(room.getType());

            // Show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main Class
public class UC4 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Book My Stay App - v4.0            ");
        System.out.println("======================================");

        RoomInventoryUC4 inventory = new RoomInventoryUC4();

        List<RoomUC4> rooms = new ArrayList<>();
        rooms.add(new SingleRoomUC4());
        rooms.add(new DoubleRoomUC4());
        rooms.add(new SuiteRoomUC4());

        RoomSearchServiceUC4.searchAvailableRooms(inventory, rooms);

        System.out.println("Search completed. No inventory modified.");
    }
}