package Service;

import Domain.Movie2;

public class MovieReservationVM {

    private Movie2 movie2;
    int bookings;

    public MovieReservationVM(Movie2 movie2, int bookings) {
        this.movie2 = movie2;
        this.bookings = bookings;
    }

    public Movie2 getMovie2(){
        return movie2;
    }

    public int getBookings(){
        return bookings;
    }
}
