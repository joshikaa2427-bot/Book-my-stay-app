import java.io.*;
import java.util.*;

class BookingHistory implements Serializable {
    private List<String> records = new ArrayList<>();

    public void addRecord(String guestName, String roomType, String status) {
        records.add("Guest: " + guestName + ", Room: " + roomType + ", Status: " + status);
    }

    public void showHistory() {
        System.out.println("Booking History:");
        for (String record : records) {
            System.out.println(record);
        }
    }

    public List<String> getRecords() {
        return records;
    }
}

class PersistenceManager {
    private static final String FILE_NAME = "booking_history.ser";

    public static void saveHistory(BookingHistory history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(history);
            System.out.println("Booking history saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving booking history: " + e.getMessage());
        }
    }

    public static BookingHistory loadHistory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            BookingHistory history = (BookingHistory) ois.readObject();
            System.out.println("Booking history loaded successfully.");
            return history;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous history found. Starting fresh.");
            return new BookingHistory();
        }
    }
}

public class UseCase13PersistenceRecovery {
    public static void main(String[] args) {
        System.out.println("UC13: Data Persistence & System Recovery");

        // Step 1: Load existing history
        BookingHistory history = PersistenceManager.loadHistory();

        // Step 2: Add new records
        history.addRecord("Abhi", "Single", "Confirmed");
        history.addRecord("Subha", "Suite", "Cancelled");

        // Step 3: Show history
        history.showHistory();

        // Step 4: Save history back to disk
        PersistenceManager.saveHistory(history);
    }
}
