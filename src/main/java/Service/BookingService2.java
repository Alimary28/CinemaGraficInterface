package Service;

import Domain.Booking2;
import Domain.ClientCard2;
import Domain.Movie2;
import Repository.IRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BookingService2 {

    private IRepository<Booking2> bookingRepo;
    private IRepository<Movie2> movieRepo;
    private IRepository<ClientCard2> clientCardRepo;

    private Stack<UndoableOperation<Booking2>> undoableOperation = new Stack<>();
    private Stack<UndoableOperation<Booking2>> redoableOperation = new Stack<>();

    public BookingService2(IRepository<Booking2> bookingRepo, IRepository<Movie2> movieRepo, IRepository<ClientCard2> clientCardRepo) {
        this.bookingRepo = bookingRepo;
        this.movieRepo = movieRepo;
        this.clientCardRepo = clientCardRepo;
    }
    public void insert(String id, String movieId, String clientCardId, LocalDate date, LocalTime hour) {

        Movie2 movieSold = movieRepo.findById(movieId);
        if (movieSold == null) {
            throw new BookingServiceException("There is no movie with the given id!");
        }
        if (!movieSold.isInCinema()){
            throw new BookingServiceException("The movie is not in Cinema!");
        }

        Booking2 booking2 = new Booking2(id, movieId, clientCardId, date, hour);
        bookingRepo.insert(booking2);
        Movie2 movie = movieRepo.findById(movieId);

        movieRepo.update(movie);

        ClientCard2 clientCard2 = clientCardRepo.findById(clientCardId);

        if(clientCard2 != null){
            clientCard2.setPoints((int) (clientCard2.getPoints() + (movieSold.getPrice() / 10)));
            clientCardRepo.update(clientCard2);
        }
        undoableOperation.add(new InsertOperation<>(bookingRepo, booking2));
        redoableOperation.clear();
    }

    /**
     *
     * @param id updates the booking id
     * @param movieId updates the booking movieId
     * @param clientCardId updates the booking clientCardId
     * @param date updates the booking date
     * @param hour updates the booking hour
     */
    public void update(String id, String movieId, String clientCardId, LocalDate date, LocalTime hour){
        Booking2 booking = bookingRepo.findById(id);
        Booking2 bookingUpdate = new Booking2(id, movieId, clientCardId, date, hour);
        bookingRepo.update(bookingUpdate);
        undoableOperation.add(new UpdateOperation(movieRepo, bookingUpdate, booking));
        redoableOperation.clear();
    }

    /**
     * Searches bookings whose fields contain a given text.
     * @param text the text searched for
     * @return A list of bookings whose fields contain text.
     */

    public List<Booking2> fullTextSearch(String text) {
        List<Booking2> found = new ArrayList<>();
        for (Booking2 booking : bookingRepo.getAll()) {
            // Might return false positives
            if (booking.getId().contains(text) || booking.getMovieId().contains(text) || booking.getCardId().contains(text) ||
                    booking.getDate().toString().contains(text) || booking.getHour().toString().contains(text) && !found.contains(booking)) {
                found.add(booking);
            }
        }

        return found;
    }
    public void remove(String id){
        Booking2 booking = bookingRepo.findById(id);
        bookingRepo.remove(id);

        ClientCard2 card = clientCardRepo.findById(booking.getCardId());
        Movie2 movie = movieRepo.findById(booking.getMovieId());

        card.setPoints((int) (card.getPoints() - (movie.getPrice() / 10)));
        clientCardRepo.update(card);

        undoableOperation.add(new DeleteOperation<>(bookingRepo, booking));
        redoableOperation.clear();
    }

    public List<Booking2> getAll(){
        return bookingRepo.getAll();
    }

    /**
     *
     * @param start is the start hour
     * @param end is the end hour
     * @return a list with bookings between a start and an end hour
     */
    public List<Booking2> searchByPeriod(LocalTime start, LocalTime end){
        List<Booking2> list = new ArrayList<>();
        for (Booking2 booking : bookingRepo.getAll()){
            if(booking.getHour().isAfter(start) && booking.getHour().isBefore(end)){
                list.add(booking);
            }
        }
        return list;
    }

    /**
     * removes bookings between startDate and endDate
     * @param startDate the start date
     * @param endDate the end date
     */
    public void removeByPeriod(LocalDate startDate, LocalDate endDate){
        List<Booking2> deletedElements = new ArrayList<>();
        for(Booking2 booking : bookingRepo.getAll()){
            if(booking.getDate().isAfter(startDate) && booking.getDate().isBefore(endDate)){
                remove(booking.getId());
                deletedElements.add(booking);
            }
        }
        undoableOperation.add(new DeleteBookings<>(bookingRepo, deletedElements));
        redoableOperation.clear();
    }

    public void undo(){
        if(!undoableOperation.empty()){
            UndoableOperation<Booking2> finishOperation = undoableOperation.pop();
            finishOperation.doUndo();
            redoableOperation.add(finishOperation);
        }
    }

    public void redo(){
        if (!redoableOperation.empty()){
            UndoableOperation<Booking2> finishOperation = redoableOperation.pop();
            finishOperation.doRedo();
            undoableOperation.add(finishOperation);
        }
    }
}
