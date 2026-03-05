import java.util.HashMap;
import java.util.Map;

abstract class Room {
    protected String roomNumber;
    protected int numberOfBeds;
    protected double roomSize;
    protected double pricePerNight;

    public Room(String roomNumber, int numberOfBeds, double roomSize, double pricePerNight) {
        this.roomNumber    = roomNumber;
        this.numberOfBeds  = numberOfBeds;
        this.roomSize      = roomSize;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomNumber()    { return roomNumber; }
    public int getNumberOfBeds()     { return numberOfBeds; }
    public double getRoomSize()      { return roomSize; }
    public double getPricePerNight() { return pricePerNight; }
    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("  Room Number   : " + roomNumber);
        System.out.println("  Room Type     : " + getRoomType());
        System.out.println("  Number of Beds: " + numberOfBeds);
        System.out.println("  Room Size     : " + roomSize + " sq ft");
        System.out.println("  Price/Night   : $" + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom(String roomNumber) { super(roomNumber, 1, 200.0, 99.99); }

    @Override
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom(String roomNumber) { super(roomNumber, 2, 350.0, 149.99); }

    @Override
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom(String roomNumber) { super(roomNumber, 3, 750.0, 349.99); }

    @Override
    public String getRoomType() { return "Suite Room"; }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room",  7);
        inventory.put("Suite Room",   3);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int updatedCount) {
        if (!inventory.containsKey(roomType)) {
            System.out.println("  [WARNING] Room type not found: " + roomType);
            return;
        }
        if (updatedCount < 0) {
            System.out.println("  [WARNING] Availability cannot be negative. Update rejected.");
            return;
        }
        inventory.put(roomType, updatedCount);
        System.out.println("  [UPDATE]  " + roomType + " availability updated to " + updatedCount);
    }

    public void addRoomType(String roomType, int initialCount) {
        if (inventory.containsKey(roomType)) {
            System.out.println("  [INFO] Room type already exists: " + roomType);
            return;
        }
        inventory.put(roomType, initialCount);
        System.out.println("  [ADDED]   New room type registered: " + roomType
                + " (count: " + initialCount + ")");
    }

    public void displayInventory() {
        System.out.println("\n  ── Current Room Inventory ──────────────────────────");
        System.out.printf("  %-20s  %s%n", "Room Type", "Available Rooms");
        System.out.println("  " + "-".repeat(42));
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.printf("  %-20s  %d%n", entry.getKey(), entry.getValue());
        }
        System.out.println("  " + "-".repeat(42));
    }
}

class UseCase3HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("        Welcome to Book My Stay App                        ");
        System.out.println("        Hotel Booking Management System  v3.0              ");
        System.out.println("============================================================");

        System.out.println("\n[STEP 1] Initializing Room Inventory...");
        RoomInventory inventory = new RoomInventory();
        inventory.displayInventory();

        System.out.println("\n[STEP 2] Checking Room Availability...");
        System.out.println("  Single Room available : " + inventory.getAvailability("Single Room"));
        System.out.println("  Double Room available : " + inventory.getAvailability("Double Room"));
        System.out.println("  Suite  Room available : " + inventory.getAvailability("Suite Room"));

        System.out.println("\n[STEP 3] Simulating a Booking (Double Room booked by a guest)...");
        int updatedDoubleCount = inventory.getAvailability("Double Room") - 1;
        inventory.updateAvailability("Double Room", updatedDoubleCount);

        System.out.println("\n[STEP 4] Adding a New Room Type (Penthouse)...");
        inventory.addRoomType("Penthouse", 2);

        System.out.println("\n[STEP 5] Final Inventory State:");
        inventory.displayInventory();

        System.out.println("\n[STEP 6] Room Details:");
        Room[] rooms = {
                new SingleRoom("R101"),
                new DoubleRoom("R201"),
                new SuiteRoom("R301")
        };
        for (Room room : rooms) {
            System.out.println("\n  ----------------------------------------------------");
            room.displayRoomDetails();
            System.out.println("  Availability  : "
                    + (inventory.getAvailability(room.getRoomType()) > 0 ? "Available" : "Not Available"));
        }

        System.out.println("\n============================================================");
        System.out.println("  HashMap replaces scattered variables with a single,       ");
        System.out.println("  centralized source of truth. O(1) access ensures          ");
        System.out.println("  consistent and scalable inventory management.             ");
        System.out.println("============================================================");
    }
}