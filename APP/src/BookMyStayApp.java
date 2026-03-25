public class UseCase12EndToEnd {
    public static void main(String[] args) {
        System.out.println("UC12: End-to-End Booking Flow");

        // Initialize core services
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();
        CancellationService cancellationService = new CancellationService(allocationService, inventory);
        ServiceSelection serviceSelection = new ServiceSelection();

        // Step 1: Guest makes reservation
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Suite");

        try {
            BookingValidator.validateReservation(r1);
            BookingValidator.validateReservation(r2);

            // Step 2: Allocate rooms
            allocationService.allocateRoom(r1, inventory);
            allocationService.allocateRoom(r2, inventory);

            // Step 3: Add-on services
            serviceSelection.addService(new AddOnService("Breakfast", 500));
            serviceSelection.addService(new AddOnService("Airport Pickup", 1000));
            serviceSelection.showSelectedServices();

            // Step 4: Payment simulation
            PaymentProcessor paymentProcessor = new PaymentProcessor();
            paymentProcessor.processPayment(r1.getGuestName(), serviceSelection.calculateTotal());

            // Step 5: Cancellation flow
            cancellationService.cancelReservation(r2);

            // Step 6: Booking history reporting
            BookingHistory history = new BookingHistory();
            history.addRecord(r1.getGuestName(), r1.getRoomType(), "Confirmed");
            history.addRecord(r2.getGuestName(), r2.getRoomType(), "Cancelled");
            history.showHistory();

        } catch (ValidationException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }

        System.out.println("End-to-End booking flow completed.");
    }
}
