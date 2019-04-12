package Tests;

import Domain.*;
import Repository.IRepository;
import Repository.JSonRepository;
import Service.BookingService2;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingService2Test {

    private IValidator<Movie2> movie2Validator = new MovieValidatory2();
    private IValidator<ClientCard2> clientCard2Validator = new ClientCardValidatory2();
    private IValidator<Booking2> booking2Validator = new BookingValidatory2();

    private IRepository<Movie2> movieRepo = new JSonRepository<>(movie2Validator);
    private IRepository<ClientCard2> cardRepo = new JSonRepository<>(clientCard2Validator);
    private IRepository<Booking2> bookingRepo = new JSonRepository<>(booking2Validator);

    private BookingService2 bookingService2 = new BookingService2(bookingRepo, movieRepo, cardRepo);

    private Movie2 movie2 = new Movie2("1",2015,"Hitmann",14.0,true);
    private ClientCard2 clientCard2 = new ClientCard2("1","anda","vlaicu","191090912", LocalDate.of(1984,03,12), LocalDate.of(2018,04, 15), 14);


    @org.junit.jupiter.api.Test
    void insert() {

        movieRepo.insert(movie2);
        cardRepo.insert(clientCard2);
        bookingService2.insert("1", "1", "1", LocalDate.of(2007,3,15), LocalTime.of(20,15));

        assertEquals(1, bookingService2.getAll().size());
    }

    @org.junit.jupiter.api.Test
    void update() {

        movieRepo.insert(movie2);
        cardRepo.insert(clientCard2);
        bookingService2.insert("1", "1", "1", LocalDate.of(2007,3,15), LocalTime.of(20,15));
        bookingService2.update("1", "1", "1", LocalDate.of(2007,3,15), LocalTime.of(12,15));


        assertEquals(LocalTime.of(12,0), bookingService2.getAll().get(0).getHour());
    }

    @org.junit.jupiter.api.Test
    void fullTextSearch() {

        movieRepo.insert(movie2);
        cardRepo.insert(clientCard2);
        bookingService2.insert("1", "1", "1", LocalDate.of(2007,3,15), LocalTime.of(20,15));
        String text = "2007";

        bookingService2.fullTextSearch(text);

        assertEquals(1,bookingService2.fullTextSearch(text).size());
    }

    @org.junit.jupiter.api.Test
    void remove() {
        movieRepo.insert(movie2);
        cardRepo.insert(clientCard2);
        bookingService2.insert("1", "1", "1", LocalDate.of(2007,3,15), LocalTime.of(20,15));
        bookingService2.remove("1");

        assertEquals(0,bookingService2.getAll().size());
    }

}