/**
 * Book My Stay App - Use Case 7
 * Add-On Service Selection
 *
 * Demonstrates attaching optional services to reservations
 * using Map + List without modifying core booking logic.
 *
 * @author Vishnu
 * @version 7.0
 */

package uc7;

import java.util.*;

// Reservation (from UC6 concept, simplified)
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
}

// Add-On Service
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }
}

// Manager (handles mapping)
class AddOnServiceManager {

    // reservationId → list of services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getServiceName() +
                " to Reservation ID: " + reservationId);
    }

    // Display services
    public void displayServices(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for Reservation ID: " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation ID: " + reservationId);
        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " (₹" + s.getPrice() + ")");
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        double total = 0;
        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }
        return total;
    }
}

// Main Class
public class UC7 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Book My Stay App - v7.0            ");
        System.out.println("======================================");

        // Sample reservation (already confirmed in UC6)
        Reservation reservation = new Reservation("RES123", "Vishnu", "Suite Room");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        manager.addService(reservation.getReservationId(), new AddOnService("Breakfast", 500));
        manager.addService(reservation.getReservationId(), new AddOnService("Airport Pickup", 1200));
        manager.addService(reservation.getReservationId(), new AddOnService("Extra Bed", 800));

        // Display services
        manager.displayServices(reservation.getReservationId());

        // Calculate cost
        double totalCost = manager.calculateTotalCost(reservation.getReservationId());
        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);

        System.out.println("\nCore booking & inventory remain unchanged.");
    }
}