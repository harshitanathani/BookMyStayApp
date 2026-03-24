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

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    void add(Reservation r) {
        history.add(r);
    }

    List<Reservation> getAll() {
        return history;
    }
}

class BookingReportService {
    void displayAll(List<Reservation> history) {
        for (Reservation r : history) {
            System.out.println(r.guestName + " -> " + r.roomType + " (" + r.roomId + ")");
        }
    }

    void summary(List<Reservation> history) {
        Map<String, Integer> count = new HashMap<>();

        for (Reservation r : history) {
            count.put(r.roomType, count.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("\nSummary:");
        for (String type : count.keySet()) {
            System.out.println(type + ": " + count.get(type));
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();

        history.add(new Reservation("Amit", "Single Room", "SR1"));
        history.add(new Reservation("Neha", "Double Room", "DR1"));
        history.add(new Reservation("Rahul", "Single Room", "SR2"));

        BookingReportService report = new BookingReportService();

        System.out.println("Hotel Booking System v8.1\n");

        report.displayAll(history.getAll());
        report.summary(history.getAll());
    }
}