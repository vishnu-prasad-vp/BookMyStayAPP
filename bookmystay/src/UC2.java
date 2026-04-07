/**
 * Book My Stay App - Use Case 2
 * Basic Room Types & Static Availability
 *
 * Demonstrates abstraction, inheritance, polymorphism,
 * and simple availability handling.
 *
 * @author Vishnu
 * @version 2.0
 */

// Abstract Class
abstract class Room {
    private int beds;
    private double price;
    private String type;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    // Abstract method
    public abstract void displayDetails();
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }

    @Override
    public void displayDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    @Override
    public void displayDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }

    @Override
    public void displayDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

// Main Class
public class UC2 {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("     Book My Stay App - v2.0          ");
        System.out.println("======================================");

        // Polymorphism
        Room single = new SingleRoom();
        Room doub = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display Details
        System.out.println("\nRoom Details & Availability:\n");

        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        doub.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("Application finished.");
    }
}