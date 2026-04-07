/**
 * Book My Stay App - Use Case 9
 * Error Handling & Validation
 *
 * Demonstrates input validation, custom exceptions,
 * and fail-fast error handling.
 *
 * @author Vishnu
 * @version 9.0
 */

package uc9;

import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation
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

// Inventory
class RoomInventoryUC9 {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventoryUC9() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) throws InvalidBookingException {
        int available = getAvailability(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No available rooms for: " + roomType);
        }

        inventory.put(roomType, available - 1);
    }
}

// Validator
class BookingValidator {

    public static void validate(Reservation reservation, RoomInventoryUC9 inventory)
            throws InvalidBookingException {

        if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (!inventory.isValidRoomType(reservation.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + reservation.getRoomType());
        }

        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available: " + reservation.getRoomType());
        }
    }
}

// Booking Service
class BookingServiceUC9 {

    private RoomInventoryUC9 inventory;

    public BookingServiceUC9(RoomInventoryUC9 inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation reservation) {
        try {
            // Validate first (Fail-Fast)
            BookingValidator.validate(reservation, inventory);

            // Safe allocation
            inventory.decrement(reservation.getRoomType());

            System.out.println("✅ Booking Confirmed for " + reservation.getGuestName() +
                    " (" + reservation.getRoomType() + ")");

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("❌ Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class UC9 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Book My Stay App - v9.0            ");
        System.out.println("======================================");

        RoomInventoryUC9 inventory = new RoomInventoryUC9();
        BookingServiceUC9 service = new BookingServiceUC9(inventory);

        // Test cases
        service.confirmBooking(new Reservation("Vishnu", "Single Room")); // valid
        service.confirmBooking(new Reservation("", "Single Room"));       // invalid name
        service.confirmBooking(new Reservation("Arun", "Luxury Room"));   // invalid type
        service.confirmBooking(new Reservation("Priya", "Single Room"));  // may fail (no stock)

        System.out.println("\nSystem continues running safely after errors.");
    }
}