package UI;

import Domain.Booking2;
import Domain.ClientCard2;
import Domain.Movie2;
import Service.BookingService2;
import Service.ClientCardService2;
import Service.MovieService2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Console {

    private MovieService2 movieService2;
    private ClientCardService2 clientCardService2;
    private BookingService2 bookingService2;

    private Scanner scanner;

    public Console(MovieService2 movieService2, ClientCardService2 clientCardService2, BookingService2 bookingService2) {
        this.movieService2 = movieService2;
        this.clientCardService2 = clientCardService2;
        this.bookingService2 = bookingService2;

        this.scanner = new Scanner(System.in);
    }
    private LocalDate dateScanner(){
       try {
           Scanner sc = new Scanner(System.in);
           System.out.print("Enter year: ");
           int year = Integer.parseInt(sc.nextLine());
           System.out.print("Enter month: ");
           int month = Integer.parseInt(sc.nextLine());
           System.out.print("Enter day: ");
           int day = Integer.parseInt(sc.nextLine());
           return LocalDate.of(year, month, day);
       } catch (RuntimeException rex){
           throw new RuntimeException("Invalid date!");
       }
    }

    private LocalTime timeScanner(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter hour: ");
            int hour = Integer.parseInt(sc.nextLine());
            System.out.print("Enter minutes: ");
            int minutes = Integer.parseInt(sc.nextLine());
            return LocalTime.of(hour, minutes);
        } catch (RuntimeException rex){
            throw new RuntimeException("Invalid time!");
        }
    }

    private LocalDate converts(String date){
        try {
            return LocalDate.of(Integer.parseInt(date.substring(6, 10)), Integer.parseInt(date.substring(3, 5)), Integer.parseInt(date.substring(0, 2)));
        } catch (RuntimeException rex) {
            throw new RuntimeException("Invalid date!");
        }
    }

    private void showMenu() {
        System.out.println("1. Movie CRUD");
        System.out.println("2. ClientCard CRUD");
        System.out.println("3. Booking CRUD");
        System.out.println("4. Search Clients");
        System.out.println("x. Exit");
    }

    public void run() {
        while (true) {
            showMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    runMovieCrud();
                    break;
                case "2":
                    runClientCardCrud();
                    break;
                case "3":
                    runBookingCrud();
                    break;
                case "4":
                    runClientSearch();
                    break;
                case "x":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }
    private void runClientSearch(){
        System.out.println("Search by: ");
        String search = scanner.nextLine();
        System.out.println("Search results are: ");
        for(ClientCard2 clientCard2 : clientCardService2.fullTextSearch(search)){
            System.out.println(clientCard2);
        }
    }

    private void runBookingCrud() {
        while (true) {
            System.out.println("1. Add booking");
            System.out.println("2. Update booking");
            System.out.println("3. Remove a booking");
            System.out.println("4. View all bookings");
            System.out.println("5. Back");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    handleAddBooking();
                    break;
                case "2":
                    handleUpdateBooking();
                    break;
                case "3":
                    handleRemoveBooking();
                    break;
                case "4":
                    handleViewBookings();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void handleViewBookings() {
        for (Booking2 booking2 : bookingService2.getAll()) {
            System.out.println(booking2);
        }
    }

    private void handleRemoveBooking() {
        try {
            System.out.print("Enter the id to remove:");
            String id = scanner.nextLine();
            bookingService2.remove(id);

            System.out.println("Transaction removed!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void handleAddBooking() {
        try {
            System.out.print("Enter id: ");
            String id = scanner.nextLine();
            System.out.print("Enter movie id (empty to not change for update): ");
            String movieId = scanner.nextLine();
            System.out.print("Enter client card (empty to not change for update): ");
            String cardId = scanner.nextLine();
            System.out.print("Enter date (empty to not change for update): ");
            scanner.nextLine();
            LocalDate date = dateScanner();
            System.out.print("Enter time (empty to not change for update): ");
            LocalTime hour = timeScanner();

            Booking2 booking2 = new Booking2(id, movieId, cardId, date, hour);

            bookingService2.insert(id, movieId, cardId, date, hour);

            if(clientCardService2.getCardRepo().findById(booking2.getCardId()) != null){
                System.out.println("Points: " + clientCardService2.getCardRepo().findById(booking2.getCardId()).getPoints());
            }
            System.out.println("Booking added!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void handleUpdateBooking(){

        try {
            System.out.print("Enter id: ");
            String id = scanner.nextLine();
            System.out.print("Enter movie id (empty to not change for update): ");
            String movieId = scanner.nextLine();
            System.out.print("Enter client card (empty to not change for update): ");
            String cardId = scanner.nextLine();
            System.out.print("Enter date (empty to not change for update): ");
            scanner.nextLine();
            LocalDate date = dateScanner();
            System.out.print("Enter time (empty to not change for update): ");
            LocalTime hour = timeScanner();

            bookingService2.update(id, movieId, cardId, date, hour);
            System.out.println("Booking updated!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }

    }

    private void runClientCardCrud() {
        while (true) {
            System.out.println("1. Add a client card");
            System.out.println("2. Update a client card");
            System.out.println("3. Remove a client card");
            System.out.println("4. View all clients");
            System.out.println("5. Back");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    handleAddClientCard();
                    break;
                case "2":
                    handleUpdateClientCard();
                    break;
                case "3":
                    handleRemoveClientCard();
                    break;
                case "4":
                    handleViewClients();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void handleViewClients() {
        for (ClientCard2 clientCard2 : clientCardService2.getAll()) {
            System.out.println(clientCard2);
        }
    }

    private void handleRemoveClientCard() {
        try {
            System.out.print("Enter the card id to remove:");
            String id = scanner.nextLine();
            clientCardService2.remove(id);

            System.out.println("Client card removed!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void handleAddClientCard() {
        try {
            System.out.print("Enter id: ");
            String id = scanner.nextLine();
            System.out.print("Enter last name (empty to not change for update): ");
            scanner.nextLine();
            String name = scanner.nextLine();
            System.out.print("Enter first name (empty to not change for update): ");
            String firstName = scanner.nextLine();
            System.out.print("Enter CNP (empty to not change for update): ");
            String cnp = scanner.nextLine();
            System.out.print("Enter date of birth (empty to not change for update): ");
            LocalDate birthDate = dateScanner();
            System.out.print("Enter date of registration (empty to not change for update): ");
            LocalDate registrationDate = dateScanner();
            System.out.println("Enter points: ");
            int points = Integer.parseInt(scanner.nextLine());
            clientCardService2.insert(id, name, firstName, cnp, birthDate, registrationDate, points);

            System.out.println("Client card added!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void handleUpdateClientCard(){

        try {
            System.out.print("Enter id: ");
            String id = scanner.nextLine();
            System.out.print("Enter last name (empty to not change for update): ");
            scanner.nextLine();
            String name = scanner.nextLine();
            System.out.print("Enter first name (empty to not change for update): ");
            String firstName = scanner.nextLine();
            System.out.print("Enter CNP (empty to not change for update): ");
            String cnp = scanner.nextLine();
            System.out.print("Enter date of birth (empty to not change for update): ");
            LocalDate birthDate = dateScanner();
            System.out.print("Enter date of registration (empty to not change for update): ");
            LocalDate registrationDate = dateScanner();
            System.out.println("Enter points: ");
            int points = Integer.parseInt(scanner.nextLine());
            clientCardService2.update(id, name, firstName, cnp, birthDate, registrationDate, points);

            System.out.println("Client card updated!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }
    private void runMovieCrud() {
        while (true) {
            System.out.println("1. Add a movie");
            System.out.println("2. Update a movie");
            System.out.println("3. Remove a movie");
            System.out.println("4. View all movies");
            System.out.println("5. Back");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    handleAddMovie();
                    break;
                case "2":
                    handleUpdateMovie();
                    break;
                case "3":
                    handleRemoveMovie();
                    break;
                case "4":
                    handleViewMovies();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void handleViewMovies() {
        for (Movie2 movie2 : movieService2.getAll()) {
            System.out.println(movie2);
        }
    }

    private void handleRemoveMovie() {
        try {
            System.out.print("Enter the movie id to remove:");
            String id = scanner.nextLine();
            movieService2.remove(id);

            System.out.println("Movie removed!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void handleAddMovie() {

        try {
            System.out.print("Enter id: ");
            String id = scanner.nextLine();
            System.out.print("Enter year: ");
            int year = scanner.nextInt();
            System.out.print("Enter title (empty to not change for update): ");
            scanner.nextLine();
            String title = scanner.nextLine();
            System.out.print("Enter price (0 to not change for update): ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter in Cinema (true / false): ");
            boolean inCinema = Boolean.parseBoolean(scanner.nextLine());

            movieService2.insert(id, year, title, price, inCinema);

            System.out.println("Movie added!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void handleUpdateMovie(){

        try {
            System.out.print("Enter id: ");
            String id = scanner.nextLine();
            System.out.print("Enter year: ");
            int year = scanner.nextInt();
            System.out.print("Enter title (empty to not change for update): ");
            scanner.nextLine();
            String title = scanner.nextLine();
            System.out.print("Enter price (0 to not change for update): ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter in Cinema (true / false): ");
            boolean inCinema = Boolean.parseBoolean(scanner.nextLine());

            movieService2.update(id, year, title, price, inCinema);

            System.out.println("Movie updated!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

}
