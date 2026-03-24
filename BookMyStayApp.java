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
    private Queue<Reservation> queue = new LinkedList<>();

    synchronized void add(Reservation r) {
        queue.add(r);
    }

    synchronized Reservation get() {
        return queue.poll();
    }

    synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    synchronized boolean allocate(String type) {
        int available = inventory.getOrDefault(type, 0);
        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }
}

class BookingProcessor extends Thread {
    private BookingQueue queue;
    private RoomInventory inventory;

    BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;

            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.get();
            }

            if (r != null) {
                synchronized (inventory) {
                    if (inventory.allocate(r.roomType)) {
                        System.out.println(Thread.currentThread().getName() +
                                " confirmed for " + r.guestName + " (" + r.roomType + ")");
                    } else {
                        System.out.println(Thread.currentThread().getName() +
                                " failed for " + r.guestName);
                    }
                }
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {
        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        queue.add(new Reservation("Amit", "Single Room"));
        queue.add(new Reservation("Neha", "Single Room"));
        queue.add(new Reservation("Rahul", "Single Room"));
        queue.add(new Reservation("Sneha", "Double Room"));

        System.out.println("Hotel Booking System v11.1\n");

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}