import java.util.*;

class Reservation {
    String guestName;
    String roomType;
    String roomId;

    Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    void increase(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    void display() {
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

class CancellationService {
    private Map<String, Reservation> bookings = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    void addBooking(Reservation r) {
        bookings.put(r.roomId, r);
    }

    void cancel(String roomId, RoomInventory inventory) {
        if (!bookings.containsKey(roomId)) {
            System.out.println("Cancellation failed: Invalid reservation ID");
            return;
        }

        Reservation r = bookings.remove(roomId);
        rollbackStack.push(roomId);

        inventory.increase(r.roomType);

        System.out.println("Cancelled: " + roomId + " (" + r.roomType + ")");
    }

    void showRollbackStack() {
        System.out.println("\nRollback Stack:");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        Reservation r1 = new Reservation("Amit", "Single Room", "SR1");
        Reservation r2 = new Reservation("Neha", "Double Room", "DR1");

        service.addBooking(r1);
        service.addBooking(r2);

        System.out.println("Hotel Booking System v10.1\n");

        service.cancel("SR1", inventory);
        service.cancel("XX1", inventory); // invalid case

        System.out.println("\nUpdated Inventory:");
        inventory.display();

        service.showRollbackStack();
    }
}