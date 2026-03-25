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
    }
}
