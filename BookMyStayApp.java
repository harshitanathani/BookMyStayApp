import java.util.HashMap;

class RoomInventory {
    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    void displayInventory() {
        for (String type : inventory.keySet()) {
            System.out.println(type + " Available: " + inventory.get(type));
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        System.out.println("Hotel Booking System v3.1\n");

        inventory.displayInventory();

        System.out.println("\nUpdating Single Room availability...\n");
        inventory.updateAvailability("Single Room", 4);

        inventory.displayInventory();
    }
}