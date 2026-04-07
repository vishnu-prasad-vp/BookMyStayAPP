/**
 * Book My Stay App - Use Case 8
 * Booking History & Reporting
 *
 * Demonstrates storing confirmed bookings and generating reports
 * using List (ordered storage) without modifying data.
 *
 * @author Vishnu
 * @version 8.0
 */

package uc8;

import java.util.*;

// Reservation (Confirmed Booking)
class Reservation {
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

// Booking History (Ordered List)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Booking stored: " + reservation.getReservationId());
    }

    // Get all bookings (read-only usage)
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public static void showAllBookings(List<Reservation> reservations) {
        System.out.println("\n📋 Booking History:\n");
        for (Reservation r : reservations) {
            r.display();
        }
    }

    // Summary report
    public static void generateSummary(List<Reservation> reservations) {
        System.out.println("\n📊 Booking Summary Report:\n");

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : reservations) {
            roomCount.put(r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            System.out.println(entry.getKey() + " → Total Bookings: " + entry.getValue());
        }

        System.out.println("Total Reservations: " + reservations.size());
    }
}

// Main Class
public class UC8 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Book My Stay App - v8.0            ");
        System.out.println("======================================");

        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from UC6)
        history.addReservation(new Reservation("RES101", "Vishnu", "Single Room"));
        history.addReservation(new Reservation("RES102", "Arun", "Suite Room"));
        history.addReservation(new Reservation("RES103", "Priya", "Single Room"));

        // Admin views booking history
        List<Reservation> allBookings = history.getAllReservations();

        BookingReportService.showAllBookings(allBookings);
        BookingReportService.generateSummary(allBookings);

        System.out.println("\nReporting completed. No data modified.");
    }
}