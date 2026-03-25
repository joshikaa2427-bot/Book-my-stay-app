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

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get(roomType + "Room") != null && availability.get(roomType + "Room") > 0) {
            String roomId = generateRoomId(roomType);

            if (!allocatedRoomIds.contains(roomId)) {
                allocatedRoomIds.add(roomId);

                assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
                assignedRoomsByType.get(roomType).add(roomId);

                guestRoomMap.put(reservation.getGuestName(), roomId);

                inventory.updateAvailability(roomType + "Room", availability.get(roomType + "Room") - 1);

                System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() +
                        ", Room ID: " + roomId);
            }
        } else {
            System.out.println("No available rooms for type: " + roomType);
        }
    }

    public String getAssignedRoomId(Reservation reservation) {
        return guestRoomMap.get(reservation.getGuestName());
    }

    public void releaseRoom(String roomId, String roomType) {
        allocatedRoomIds.remove(roomId);
        if (assignedRoomsByType.containsKey(roomType)) {
            assignedRoomsByType.get(roomType).remove(roomId);
        }
    }

    private String generateRoomId(String roomType) {
        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + count;
    }
}

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
    }
}
