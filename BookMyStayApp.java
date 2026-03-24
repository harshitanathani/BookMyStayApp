import java.util.*;

class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    void validate(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type");
        }
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }
    }

    void book(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        String[] requests = {"Single Room", "Suite Room", "Luxury Room"};

        System.out.println("Hotel Booking System v9.1\n");

        for (String roomType : requests) {
            try {
                inventory.validate(roomType);
                inventory.book(roomType);
                System.out.println("Booking confirmed for " + roomType);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        }
    }
}