/**
 * Book My Stay App - Use Case 10
 * Booking Cancellation & Inventory Rollback
 *
 * Demonstrates safe cancellation using Stack (LIFO),
 * inventory rollback, and validation.
 *
 * @author Vishnu
 * @version 10.0
 */

package uc10;

import java.util.*;

// Reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
}

// Inventory
class RoomInventoryUC10 {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventoryUC10() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void display() {
        System.out.println("\nUpdated Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }
    }
}

// Booking History
class BookingHistoryUC10 {
    private Map<String, Reservation> bookings = new HashMap<>();

    public void add(Reservation r) {
        bookings.put(r.getReservationId(), r);
    }

    public Reservation get(String id) {
        return bookings.get(id);
    }

    public void remove(String id) {
        bookings.remove(id);
    }
}

// Cancellation Service
class CancellationService {

    private RoomInventoryUC10 inventory;
    private BookingHistoryUC10 history;

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventoryUC10 inventory, BookingHistoryUC10 history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelBooking(String reservationId) {

        Reservation r = history.get(reservationId);

        // Validate existence
        if (r == null) {
            System.out.println("❌ Cancellation Failed: Reservation not found (" + reservationId + ")");
            return;
        }

        // Push room ID to rollback stack
        rollbackStack.push(r.getRoomId());

        // Restore inventory
        inventory.increment(r.getRoomType());

        // Remove booking
        history.remove(reservationId);

        // Confirmation
        System.out.println("✅ Booking Cancelled Successfully!");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent Releases): " + rollbackStack);
    }
}

// Main Class
public class UC10 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Book My Stay App - v10.0           ");
        System.out.println("======================================");

        // Setup
        RoomInventoryUC10 inventory = new RoomInventoryUC10();
        BookingHistoryUC10 history = new BookingHistoryUC10();

        // Simulate confirmed bookings
        history.add(new Reservation("RES201", "Vishnu", "Single Room", "SR-101"));
        history.add(new Reservation("RES202", "Arun", "Double Room", "DR-201"));

        // Cancellation Service
        CancellationService service = new CancellationService(inventory, history);

        // Cancel bookings
        service.cancelBooking("RES201"); // valid
        service.cancelBooking("RES999"); // invalid

        // Show rollback and inventory
        service.showRollbackStack();
        inventory.display();

        System.out.println("\nSystem state restored safely after cancellation.");
    }
}