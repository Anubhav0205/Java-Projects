import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HotelReservationSystem {

    static class Room {
        private int roomNumber;
        private String type;
        private double price;
        private boolean isAvailable;

        public Room(int roomNumber, String type, double price) {
            this.roomNumber = roomNumber;
            this.type = type;
            this.price = price;
            this.isAvailable = true;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public String getType() {
            return type;
        }

        public double getPrice() {
            return price;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }

        @Override
        public String toString() {
            return "Room Number: " + roomNumber + ", Type: " + type + ", Price: $" + price + " per night, Available: " + isAvailable;
        }
    }

    static class Reservation {
        private static int idCounter = 1;
        private int reservationId;
        private Room room;
        private Date checkInDate;
        private Date checkOutDate;
        private String guestName;
        private boolean isPaid;

        public Reservation(Room room, Date checkInDate, Date checkOutDate, String guestName) {
            this.reservationId = idCounter++;
            this.room = room;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.guestName = guestName;
            this.isPaid = false;
            room.setAvailable(false);
        }

        public int getReservationId() {
            return reservationId;
        }

        public Room getRoom() {
            return room;
        }

        public Date getCheckInDate() {
            return checkInDate;
        }

        public Date getCheckOutDate() {
            return checkOutDate;
        }

        public String getGuestName() {
            return guestName;
        }

        public void setPaid(boolean isPaid) {
            this.isPaid = isPaid;
        }

        public long getNumberOfDays() {
            long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
            return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        }

        public double calculateTotalAmount() {
            return getNumberOfDays() * room.getPrice();
        }

        @Override
        public String toString() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return "Reservation ID: " + reservationId + "\n" +
                    "Guest Name: " + guestName + "\n" +
                    "Room: " + room.getRoomNumber() + " (" + room.getType() + ")\n" +
                    "Check-In Date: " + sdf.format(checkInDate) + "\n" +
                    "Check-Out Date: " + sdf.format(checkOutDate) + "\n" +
                    "Total Amount: $" + calculateTotalAmount() + "\n" +
                    "Payment Status: " + (isPaid ? "Paid" : "Pending") + "\n";
        }
    }

    static class Hotel {
        private List<Room> rooms;
        private List<Reservation> reservations;

        public Hotel() {
            this.rooms = new ArrayList<>();
            this.reservations = new ArrayList<>();
        }

        public void addRoom(Room room) {
            rooms.add(room);
        }

        public List<Room> searchAvailableRooms(String type) {
            List<Room> availableRooms = new ArrayList<>();
            for (Room room : rooms) {
                if (room.getType().equalsIgnoreCase(type) && room.isAvailable()) {
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        }

        public Room findRoomByNumber(int roomNumber) {
            for (Room room : rooms) {
                if (room.getRoomNumber() == roomNumber) {
                    return room;
                }
            }
            return null;
        }

        public Reservation makeReservation(Room room, Date checkInDate, Date checkOutDate, String guestName) {
            Reservation reservation = new Reservation(room, checkInDate, checkOutDate, guestName);
            reservations.add(reservation);
            return reservation;
        }

        public void processPayment(Reservation reservation, double amount) {
            System.out.println("Processing payment of $" + amount + " for Reservation ID: " + reservation.getReservationId());
            reservation.setPaid(true);
            System.out.println("Payment successful.");
        }

        public List<Reservation> getReservations() {
            return reservations;
        }
    }

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.addRoom(new Room(101, "Single", 100.0));
        hotel.addRoom(new Room(102, "Double", 150.0));
        hotel.addRoom(new Room(103, "Suite", 300.0));

        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        while (true) {
            try {
                System.out.println("\nHotel Reservation System");
                System.out.println("1. Search for Available Rooms");
                System.out.println("2. Make a Reservation");
                System.out.println("3. View Booking Details");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        searchForAvailableRooms(scanner, hotel);
                        break;

                    case 2:
                        makeReservation(scanner, sdf, hotel);
                        break;

                    case 3:
                        viewBookingDetails(hotel);
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option. Please choose a valid option.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void searchForAvailableRooms(Scanner scanner, Hotel hotel) {
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter room type (Single/Double/Suite): ");
                String type = scanner.nextLine();
                List<Room> availableRooms = hotel.searchAvailableRooms(type);
                if (availableRooms.isEmpty()) {
                    System.out.println("No rooms available of type: " + type);
                } else {
                    System.out.println("Available rooms:");
                    for (Room room : availableRooms) {
                        System.out.println(room);
                    }
                }
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private static void makeReservation(Scanner scanner, SimpleDateFormat sdf, Hotel hotel) {
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter room number: ");
                int roomNumber = Integer.parseInt(scanner.nextLine());
                Room selectedRoom = hotel.findRoomByNumber(roomNumber);
                if (selectedRoom == null || !selectedRoom.isAvailable()) {
                    System.out.println("Room is not available.");
                } else {
                    Date checkInDate = null, checkOutDate = null;
                    while (checkInDate == null || checkOutDate == null || checkOutDate.before(checkInDate)) {
                        try {
                            System.out.print("Enter check-in date (yyyy-MM-dd): ");
                            checkInDate = sdf.parse(scanner.nextLine());
                            System.out.print("Enter check-out date (yyyy-MM-dd): ");
                            checkOutDate = sdf.parse(scanner.nextLine());
                            if (checkOutDate.before(checkInDate)) {
                                System.out.println("Check-out date cannot be before check-in date. Please enter valid dates.");
                            }
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                        }
                    }
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.nextLine();

                    Reservation reservation = hotel.makeReservation(selectedRoom, checkInDate, checkOutDate, guestName);
                    System.out.println("Reservation made successfully. Details:\n" + reservation);

                    // Payment Process
                    double totalAmount = reservation.calculateTotalAmount();
                    boolean paymentSuccessful = false;
                    while (!paymentSuccessful) {
                        try {
                            System.out.println("Total amount to be paid: $" + totalAmount);
                            System.out.print("Enter payment amount ($): ");
                            double amount = Double.parseDouble(scanner.nextLine());

                            if (amount == totalAmount) {
                                hotel.processPayment(reservation, amount);
                                paymentSuccessful = true;
                            } else {
                                System.out.println("Payment amount does not match the total amount. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid payment amount.");
                        }
                    }
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void viewBookingDetails(Hotel hotel) {
        List<Reservation> reservations = hotel.getReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("Booking Details:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }
}