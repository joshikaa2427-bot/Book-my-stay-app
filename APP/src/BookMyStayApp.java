 uc6
import java.util.*;
public class RoomAllocationService {
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get(roomType + "Room") != null && availability.get(roomType + "Room") > 0) {
            String roomId = generateRoomId(roomType);

            if (!allocatedRoomIds.contains(roomId)) {
                allocatedRoomIds.add(roomId);

                assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
                assignedRoomsByType.get(roomType).add(roomId);

                inventory.updateAvailability(roomType + "Room", availability.get(roomType + "Room") - 1);

                System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() +
                        ", Room ID: " + roomId);
            }
        } else {
            System.out.println("No available rooms for type: " + roomType);
        }
    }

    private String generateRoomId(String roomType) {
        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + count;
    }
}

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

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        while (bookingQueue.hasPendingRequests()) {
            Reservation next = bookingQueue.getNextRequest();
            System.out.println("Processing booking for Guest: "
                    + next.getGuestName() + ", Room Type: " + next.getRoomType());
 main
        }
    }
}
