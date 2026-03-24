import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    String guestName;
    String roomType;
    String roomId;

    Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> history;

    SystemState(Map<String, Integer> inventory, List<Reservation> history) {
        this.inventory = inventory;
        this.history = history;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "data.ser";

    void save(SystemState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(state);
            System.out.println("State saved.");
        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    SystemState load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("State loaded.");
            return (SystemState) in.readObject();
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        PersistenceService service = new PersistenceService();

        SystemState state = service.load();

        Map<String, Integer> inventory;
        List<Reservation> history;

        if (state == null) {
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            history = new ArrayList<>();
            history.add(new Reservation("Amit", "Single Room", "SR1"));
        } else {
            inventory = state.inventory;
            history = state.history;
        }

        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }

        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            System.out.println(r.guestName + " -> " + r.roomType + " (" + r.roomId + ")");
        }

        service.save(new SystemState(inventory, history));
    }
}