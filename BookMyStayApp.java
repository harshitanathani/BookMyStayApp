import java.util.*;

class Service {
    String name;
    double cost;

    Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    double getTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());
        for (Service s : services) {
            total += s.cost;
        }
        return total;
    }

    void displayServices(String reservationId) {
        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());
        System.out.println("Services for " + reservationId + ":");
        for (Service s : services) {
            System.out.println("- " + s.name + " ($" + s.cost + ")");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "SR1";

        manager.addService(reservationId, new Service("Breakfast", 200));
        manager.addService(reservationId, new Service("WiFi", 100));
        manager.addService(reservationId, new Service("Airport Pickup", 500));

        System.out.println("Hotel Booking System v7.1\n");

        manager.displayServices(reservationId);

        double total = manager.getTotalCost(reservationId);
        System.out.println("\nTotal Add-On Cost: $" + total);
    }
}