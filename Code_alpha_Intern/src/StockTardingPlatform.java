import java.io.*;
import java.util.*;
public class StockTardingPlatform {


        static class Room {
            int roomNumber;
            String category;
            boolean isBooked;

            Room(int roomNumber, String category) {
                this.roomNumber = roomNumber;
                this.category = category;
                this.isBooked = false;
            }

            @Override
            public String toString() {
                return "Room " + roomNumber + " (" + category + ") - " + (isBooked ? "Booked" : "Available");
            }
        }

        static class Reservation {
            String guestName;
            int roomNumber;
            String paymentStatus;

            Reservation(String guestName, int roomNumber, String paymentStatus) {
                this.guestName = guestName;
                this.roomNumber = roomNumber;
                this.paymentStatus = paymentStatus;
            }

            @Override
            public String toString() {
                return "Guest: " + guestName + ", Room: " + roomNumber + ", Payment: " + paymentStatus;
            }
        }

        private static final List<Room> rooms = new ArrayList<>();
        private static final List<Reservation> reservations = new ArrayList<>();
        private static final Scanner scanner = new Scanner(System.in);
        private static final String FILE_NAME = "reservations.txt";

        public static void main(String[] args) {
            loadReservations();
            initializeRooms();
            mainMenu();
            saveReservations();
        }

        private static void initializeRooms() {
            for (int i = 1; i <= 3; i++) rooms.add(new Room(i, "Standard"));
            for (int i = 4; i <= 6; i++) rooms.add(new Room(i, "Deluxe"));
            for (int i = 7; i <= 9; i++) rooms.add(new Room(i, "Suite"));

            for (Room room : rooms) {
                for (Reservation res : reservations) {
                    if (room.roomNumber == res.roomNumber) room.isBooked = true;
                }
            }
        }

        private static void mainMenu() {
            while (true) {
                System.out.println("\n--- Hotel Reservation System ---");
                System.out.println("1. View Available Rooms");
                System.out.println("2. Book a Room");
                System.out.println("3. Cancel Reservation");
                System.out.println("4. View All Reservations");
                System.out.println("5. Exit");
                System.out.print("Choose option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> viewAvailableRooms();
                    case 2 -> bookRoom();
                    case 3 -> cancelReservation();
                    case 4 -> viewReservations();
                    case 5 -> {
                        System.out.println("Thank you for using the system.");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            }
        }

        private static void viewAvailableRooms() {
            System.out.println("\n--- Available Rooms ---");
            for (Room room : rooms) {
                if (!room.isBooked) {
                    System.out.println(room);
                }
            }
        }

        private static void bookRoom() {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room category (Standard/Deluxe/Suite): ");
            String category = scanner.nextLine();

            for (Room room : rooms) {
                if (room.category.equalsIgnoreCase(category) && !room.isBooked) {
                    room.isBooked = true;
                    System.out.println("Simulating payment... Payment Successful!");
                    Reservation res = new Reservation(name, room.roomNumber, "Paid");
                    reservations.add(res);
                    System.out.println("Room booked successfully: " + res);
                    return;
                }
            }

            System.out.println("No available rooms in the selected category.");
        }

        private static void cancelReservation() {
            System.out.print("Enter your name to cancel booking: ");
            String name = scanner.nextLine();

            Iterator<Reservation> iterator = reservations.iterator();
            boolean found = false;

            while (iterator.hasNext()) {
                Reservation res = iterator.next();
                if (res.guestName.equalsIgnoreCase(name)) {
                    iterator.remove();
                    for (Room room : rooms) {
                        if (room.roomNumber == res.roomNumber) {
                            room.isBooked = false;
                            break;
                        }
                    }
                    System.out.println("Reservation cancelled for " + name);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("No reservation found under that name.");
            }
        }

        private static void viewReservations() {
            System.out.println("\n--- All Reservations ---");
            if (reservations.isEmpty()) {
                System.out.println("No reservations yet.");
                return;
            }

            for (Reservation res : reservations) {
                System.out.println(res);
            }
        }

        private static void saveReservations() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Reservation res : reservations) {
                    writer.write(res.guestName + "," + res.roomNumber + "," + res.paymentStatus);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving reservations.");
            }
        }

        private static void loadReservations() {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        reservations.add(new Reservation(parts[0], Integer.parseInt(parts[1]), parts[2]));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading reservations.");
            }
        }
    }

