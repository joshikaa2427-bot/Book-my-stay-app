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

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}
