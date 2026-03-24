import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingQueue {
    Queue<Reservation> queue = new LinkedList<>();

    void addRequest(Reservation r) {
        queue.add(r);
    }

    Reservation getNext() {
        return queue.poll();
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    void reduce(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

class BookingService {
    private Set<String> usedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> allocated = new HashMap<>();
    private int counter = 1;

    void process(BookingQueue queue, RoomInventory inventory) {
        while (!queue.isEmpty()) {
            Reservation r = queue.getNext();

            if (inventory.getAvailability(r.roomType) > 0) {
                String roomId = r.roomType.substring(0, 2).toUpperCase() + counter++;

                usedRoomIds.add(roomId);
                allocated.putIfAbsent(r.roomType, new HashSet<>());
                allocated.get(r.roomType).add(roomId);

                inventory.reduce(r.roomType);

                System.out.println("Confirmed: " + r.guestName + " -> " + roomId);
            } else {
                System.out.println("Failed: " + r.guestName + " (No rooms available)");
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingQueue queue = new BookingQueue();
        queue.addRequest(new Reservation("Amit", "Single Room"));
        queue.addRequest(new Reservation("Neha", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        System.out.println("Hotel Booking System v6.1\n");

        service.process(queue, inventory);
    }
}