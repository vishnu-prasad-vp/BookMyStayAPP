/**
 * Book My Stay App - Use Case 12
 * Data Persistence & System Recovery
 *
 * Demonstrates serialization, deserialization,
 * and restoring system state from file.
 *
 * @author Vishnu
 * @version 12.0
 */

package uc12;

import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// System State (Serializable)
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("✅ System state saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("✅ System state loaded successfully.");
            return (SystemState) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ No saved state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading state. Starting safe default.");
        }
        return null;
    }
}

// Main Class
public class UC12 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Book My Stay App - v12.0           ");
        System.out.println("======================================");

        // Try to restore state
        SystemState state = PersistenceService.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;

            System.out.println("\nRecovered Inventory: " + inventory);

            System.out.println("\nRecovered Bookings:");
            for (Reservation r : bookings) {
                r.display();
            }

        } else {
            // Initialize fresh state
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            bookings = new ArrayList<>();
            bookings.add(new Reservation("RES301", "Vishnu", "Single Room"));
            bookings.add(new Reservation("RES302", "Arun", "Double Room"));

            System.out.println("\nInitialized fresh system state.");
        }

        // Save state before shutdown
        SystemState newState = new SystemState(inventory, bookings);
        PersistenceService.save(newState);

        System.out.println("\nSystem ready with persistent state.");
    }
}