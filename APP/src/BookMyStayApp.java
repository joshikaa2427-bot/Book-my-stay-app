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
    }
}
