/**
 * Book My Stay App - Use Case 5
 * Booking Request (First-Come-First-Served)
 *
 * Demonstrates Queue (FIFO) for fair booking request handling.
 *
 * @author Vishnu
 * @version 5.0
 */

package uc5;

import java.util.*;

// Reservation (Represents booking intent)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// Booking Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void displayQueue() {
        System.out.println("\nCurrent Booking Requests (FIFO Order):\n");
        for (Reservation r : queue) {
            r.display();
        }
    }

    // Get next request (for future UC6 processing)
    public Reservation peekNext() {
        return queue.peek();
    }
}

// Main Class
public class UC5 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Book My Stay App - v5.0            ");
        System.out.println("======================================");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulating booking requests
        bookingQueue.addRequest(new Reservation("Vishnu", "Single Room"));
        bookingQueue.addRequest(new Reservation("Arun", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Double Room"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();

        System.out.println("\nAll requests stored. No allocation done yet.");
    }
}