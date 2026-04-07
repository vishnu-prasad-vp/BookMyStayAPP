/**
 * Book My Stay App - Use Case 6
 * Reservation Confirmation & Room Allocation
 *
 * Demonstrates safe room allocation using Queue, Set, and HashMap
 * to prevent double-booking and maintain inventory consistency.
 *
 * @author Vishnu
 * @version 6.0
 */

package uc6;

import java.util.*;

// Reservation (from UC5 concept)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// Inventory Service
class RoomInventoryUC6 {
    private HashMap<String, Integer> inventory;

    public RoomInventoryUC6() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, getAvailability(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nUpdated Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }
    }
}

// Booking Queue (FIFO)
class BookingRequestQueueUC6 {
    private Queue<Reservation> queue = new LinkedList<>();

    public void add(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNext() {
        return queue.poll(); // FIFO removal
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Service (Core Allocation Logic)
class BookingServiceUC6 {

    private RoomInventoryUC6 inventory;

    // Track all allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type → assigned room IDs
    private HashMap<String, Set<String>> allocationMap = new HashMap<>();

    public BookingServiceUC6(RoomInventoryUC6 inventory) {
        this.inventory = inventory;
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.replace(" ", "").toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
    }

    public void processBooking(Reservation reservation) {

        String roomType = reservation.getRoomType();

        // Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("❌ No rooms available for " + roomType + " (Guest: " + reservation.getGuestName() + ")");
            return;
        }

        // Generate unique ID
        String roomId;
        do {
            roomId = generateRoomId(roomType);
        } while (allocatedRoomIds.contains(roomId)); // ensure uniqueness

        // Store ID
        allocatedRoomIds.add(roomId);

        allocationMap.putIfAbsent(roomType, new HashSet<>());
        allocationMap.get(roomType).add(roomId);

        // Update inventory (atomic step)
        inventory.decrement(roomType);

        // Confirm booking
        System.out.println("✅ Booking Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Allocated Room ID: " + roomId + "\n");
    }

    public void displayAllocations() {
        System.out.println("\nAll Allocations:");
        for (Map.Entry<String, Set<String>> e : allocationMap.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }
    }
}

// Main Class
public class UC6 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Book My Stay App - v6.0            ");
        System.out.println("======================================");

        // Setup
        RoomInventoryUC6 inventory = new RoomInventoryUC6();
        BookingRequestQueueUC6 queue = new BookingRequestQueueUC6();
        BookingServiceUC6 service = new BookingServiceUC6(inventory);

        // Add booking requests
        queue.add(new Reservation("Vishnu", "Single Room"));
        queue.add(new Reservation("Arun", "Single Room"));
        queue.add(new Reservation("Priya", "Single Room")); // should fail (only 2 available)

        // Process queue (FIFO)
        while (!queue.isEmpty()) {
            Reservation r = queue.getNext();
            service.processBooking(r);
        }

        // Show results
        service.displayAllocations();
        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely.");
    }
}