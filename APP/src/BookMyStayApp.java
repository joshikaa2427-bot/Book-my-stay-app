public abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sq.ft");
        System.out.println("Price per Night: ₹" + pricePerNight);
    }
}

public class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

public class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

public class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        SingleRoom single = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suite = new SuiteRoom();

        System.out.println("Single Room Details:");
        single.displayRoomDetails();

        System.out.println("\nDouble Room Details:");
        doubleRoom.displayRoomDetails();

        System.out.println("\nSuite Room Details:");
        suite.displayRoomDetails();
    }
}
