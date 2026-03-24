import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void display() {
        System.out.println("Guest: " + guestName + ", Room: " + roomType);
    }
}

class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    void addRequest(Reservation r) {
        queue.add(r);
    }

    void displayQueue() {
        for (Reservation r : queue) {
            r.display();
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addRequest(new Reservation("Amit", "Single Room"));
        bookingQueue.addRequest(new Reservation("Neha", "Double Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Suite Room"));

        System.out.println("Hotel Booking System v5.1\n");
        System.out.println("Booking Requests (FIFO Order):\n");

        bookingQueue.displayQueue();
    }
}