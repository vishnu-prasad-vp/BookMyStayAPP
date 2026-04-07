/**
 * Book My Stay App - Use Case 11
 * Concurrent Booking Simulation (Thread Safety)
 *
 * Demonstrates thread-safe booking using synchronized methods
 * to avoid race conditions and double booking.
 *
 * @author Vishnu
 * @version 11.0
 */

package uc11;

import java.util.*;

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

// Thread-safe Inventory
class RoomInventoryUC11 {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventoryUC11() {
        inventory.put("Single Room", 2);
    }

    // synchronized critical section
    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);

        if (available <= 0) {
            return false;
        }

        // simulate processing delay (to expose race condition if unsynchronized)
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        inventory.put(roomType, available - 1);
        return true;
    }

    public void display() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }
    }
}

// Shared Queue
class BookingQueueUC11 {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void add(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getNext() {
        return queue.poll();
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private BookingQueueUC11 queue;
    private RoomInventoryUC11 inventory;

    public BookingProcessor(String name, BookingQueueUC11 queue, RoomInventoryUC11 inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation r;

            // synchronized retrieval
            synchronized (queue) {
                r = queue.getNext();
            }

            if (r == null) break;

            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println("✅ " + getName() + " booked for " + r.getGuestName());
            } else {
                System.out.println("❌ " + getName() + " failed (No rooms) for " + r.getGuestName());
            }
        }
    }
}

// Main Class
public class UC11 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Book My Stay App - v11.0           ");
        System.out.println("======================================");

        BookingQueueUC11 queue = new BookingQueueUC11();
        RoomInventoryUC11 inventory = new RoomInventoryUC11();

        // Simulate concurrent requests
        queue.add(new Reservation("Vishnu", "Single Room"));
        queue.add(new Reservation("Arun", "Single Room"));
        queue.add(new Reservation("Priya", "Single Room")); // one should fail

        // Multiple threads (guests processed concurrently)
        Thread t1 = new BookingProcessor("Thread-1", queue, inventory);
        Thread t2 = new BookingProcessor("Thread-2", queue, inventory);

        t1.start();
        t2.start();

        // wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.display();

        System.out.println("\nAll bookings processed safely under concurrency.");
    }
}