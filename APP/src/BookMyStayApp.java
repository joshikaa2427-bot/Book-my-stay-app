uc11
import java.util.concurrent.*;

public class UseCase11ConcurrentBooking {
    public static void main(String[] args) {
        System.out.println("UC11: Concurrent Booking Simulation");

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Thread pool to simulate multiple guests booking at once
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable bookingTask1 = () -> {
            Reservation r1 = new Reservation("Abhi", "Single");
            allocationService.allocateRoom(r1, inventory);
        };

        Runnable bookingTask2 = () -> {
            Reservation r2 = new Reservation("Subha", "Single");
            allocationService.allocateRoom(r2, inventory);
        };

        Runnable bookingTask3 = () -> {
            Reservation r3 = new Reservation("Vanmathi", "Suite");
            allocationService.allocateRoom(r3, inventory);
        };

        // Submit tasks concurrently
        executor.submit(bookingTask1);
        executor.submit(bookingTask2);
        executor.submit(bookingTask3);

        // Shutdown executor
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Concurrent booking simulation completed.");

uc10
import java.util.*;

class CancellationService {
    private RoomAllocationService allocationService;
    private RoomInventory inventory;

    public CancellationService(RoomAllocationService allocationService, RoomInventory inventory) {
        this.allocationService = allocationService;
        this.inventory = inventory;
    }

    public void cancelReservation(Reservation reservation) {
        String roomType = reservation.getRoomType();
        String roomId = allocationService.getAssignedRoomId(reservation);

        if (roomId != null) {
            allocationService.releaseRoom(roomId, roomType);
            inventory.updateAvailability(roomType + "Room",
                    inventory.getRoomAvailability().get(roomType + "Room") + 1);

            System.out.println("Cancellation confirmed for Guest: " + reservation.getGuestName() +
                    ", Room ID: " + roomId + " has been released.");
        } else {
            System.out.println("No active reservation found for Guest: " + reservation.getGuestName());
        }
    }
}

// Extend RoomAllocationService with releaseRoom and lookup
class RoomAllocationService {
    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> assignedRoomsByType = new HashMap<>();
    private Map<String, String> guestRoomMap = new HashMap<>();

 uc9
import java.util.*;

class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}

class BookingValidator {
    private static final Set<String> validRoomTypes = Set.of("Single", "Double", "Suite");

    public static void validateReservation(Reservation reservation) throws ValidationException {
        if (reservation.getGuestName() == null || reservation.getGuestName().isBlank()) {
            throw new ValidationException("Guest name cannot be empty.");
        }
        if (!validRoomTypes.contains(reservation.getRoomType())) {
            throw new ValidationException("Invalid room type: " + reservation.getRoomType());
        }
    }
}

public class UseCase9ErrorHandling {
    public static void main(String[] args) {
        System.out.println("UC9: Error Handling & Validation");

        List<Reservation> testReservations = Arrays.asList(
                new Reservation("Abhi", "Single"),
                new Reservation("", "Double"),          // Invalid: empty guest name
                new Reservation("Subha", "Triple")      // Invalid: wrong room type
        );

        for (Reservation r : testReservations) {
            try {
                BookingValidator.validateReservation(r);
                System.out.println("Valid reservation for Guest: " + r.getGuestName() +
                        ", Room Type: " + r.getRoomType());
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

 uc8
import java.util.*;

public class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

public class BookingReportService {
    public void generateReport(BookingHistory history) {
        System.out.println("Booking History Report\n");
        for (Reservation reservation : history.getConfirmedReservations()) {
            System.out.println("Guest: " + reservation.getGuestName() +
                    ", Room Type: " + reservation.getRoomType());
        }
    }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();

 uc7
import java.util.*;

public class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

public class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
        servicesByReservation.get(reservationId).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        List<AddOnService> services = servicesByReservation.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;
        for (AddOnService service : services) {
            total += service.getCost();
        }
        return total;
    }
}

public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        System.out.println("Add-On Service Selection");

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "Single-1";

        AddOnService breakfast = new AddOnService("Breakfast", 500.0);
        AddOnService spa = new AddOnService("Spa", 1000.0);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);

 uc6
import java.util.*;
public class RoomAllocationService {
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }
 main

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get(roomType + "Room") != null && availability.get(roomType + "Room") > 0) {
            String roomId = generateRoomId(roomType);

            if (!allocatedRoomIds.contains(roomId)) {
                allocatedRoomIds.add(roomId);

                assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
                assignedRoomsByType.get(roomType).add(roomId);

 uc10
                guestRoomMap.put(reservation.getGuestName(), roomId);

 main
                inventory.updateAvailability(roomType + "Room", availability.get(roomType + "Room") - 1);

                System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() +
                        ", Room ID: " + roomId);
            }
        } else {
            System.out.println("No available rooms for type: " + roomType);
        }
    }

uc10
    public String getAssignedRoomId(Reservation reservation) {
        return guestRoomMap.get(reservation.getGuestName());
    }

    public void releaseRoom(String roomId, String roomType) {
        allocatedRoomIds.remove(roomId);
        if (assignedRoomsByType.containsKey(roomType)) {
            assignedRoomsByType.get(roomType).remove(roomId);
        }
    }

 main
    private String generateRoomId(String roomType) {
        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + count;
    }
}

 uc10
public class UseCase10Cancellation {
    public static void main(String[] args) {
        System.out.println("UC10: Booking Cancellation & Inventory Rollback");

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();
        CancellationService cancellationService = new CancellationService(allocationService, inventory);

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Suite");

        allocationService.allocateRoom(r1, inventory);
        allocationService.allocateRoom(r2, inventory);

        // Cancel one reservation
        cancellationService.cancelReservation(r1);

public class UseCase6RoomAllocation {
    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        while (bookingQueue.hasPendingRequests()) {
            Reservation next = bookingQueue.getNextRequest();

import java.util.LinkedList;
import java.util.Queue;

public class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

public class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        System.out.println("Booking Request Queue");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
 main

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

 uc8
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        while (bookingQueue.hasPendingRequests()) {
            Reservation next = bookingQueue.getNextRequest();
            System.out.println("Processing booking for Guest: "
                    + next.getGuestName() + ", Room Type: " + next.getRoomType());
 main
        }
 main
 main
 main
 main
main
    }
}
