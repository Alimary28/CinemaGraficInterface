package Service;

import Domain.Booking2;
import Domain.Movie2;
import Repository.IRepository;

import java.util.*;

public class MovieService2 {

    private IRepository<Movie2> movieRepo;
    private IRepository<Booking2> bookingRepo;

    private Stack<UndoableOperation<Movie2>> undoableOperations = new Stack<>();
    private Stack<UndoableOperation<Movie2>> redoableOperations = new Stack<>();

    public MovieService2(IRepository<Movie2> movieRepo, IRepository<Booking2> bookingRepo) {
        this.movieRepo = movieRepo;
        this.bookingRepo = bookingRepo;
    }

    /**
     * @param id       add a movie with the specified id
     * @param year     add a movie with the specified year
     * @param title    add a movie with the specified title
     * @param price    add a movie with the specified price
     * @param inCinema add a movie with the specified boolean value
     */
    public void insert(String id, int year, String title, double price, boolean inCinema) {

        Movie2 movie2 = new Movie2(id, year, title, price, inCinema);
        movieRepo.insert(movie2);
        undoableOperations.add(new InsertOperation<>(movieRepo, movie2));
        redoableOperations.clear();
    }

    /**
     * @param id       updates a movie with the specified id
     * @param year     updates a movie with the specified year
     * @param title    updates a movie with the specified title
     * @param price    updates a movie with the specified price
     * @param inCinema updates a movie with the specified boolean value
     */
    public void update(String id, int year, String title, double price, boolean inCinema) {

        Movie2 movie = movieRepo.findById(id);
        Movie2 secondMovie = new Movie2(id, year, title, price, inCinema);
        movieRepo.update(secondMovie);
        undoableOperations.add(new UpdateOperation(movieRepo, secondMovie, movie));
        redoableOperations.clear();
    }

    /**
     * Searches movies whose fields contain a given text.
     *
     * @param text the text searched for
     * @return A list of movies whose fields contain text.
     */
    public List<Movie2> fullTextSearch(String text) {
        List<Movie2> found = new ArrayList<>();
        for (Movie2 movies : movieRepo.getAll()) {
            // Might return false positives
            if (movies.getId().contains(text) || Integer.toString(movies.getYear()).contains(text) || movies.getTitle().contains(text) ||
                    Double.toString(movies.getPrice()).contains(text) ||
                    Boolean.toString(movies.isInCinema()).contains(text) && !found.contains(movies)) {

                found.add(movies);
            }
        }

        return found;
    }

    /**
     * @param id removes a movie with specified id
     */
    public void remove(String id) {
        Movie2 movie2 = movieRepo.findById(id);
        movieRepo.remove(id);
        undoableOperations.add(new DeleteOperation<>(movieRepo, movie2));
        redoableOperations.clear();
    }

    /**
     * @return a list with all movies
     */
    public List<Movie2> getAll() {

        return movieRepo.getAll();
    }

    public void undoAble() {
        if (!undoableOperations.empty()) {
            UndoableOperation<Movie2> endOperation = undoableOperations.pop();
            endOperation.doUndo();
            redoableOperations.add(endOperation);
        }
    }

    public void redoAble() {
        if (!redoableOperations.empty()) {
            UndoableOperation<Movie2> endOperation = redoableOperations.pop();
            endOperation.doRedo();
            undoableOperations.add(endOperation);
        }
    }

    public List<MovieReservationVM> moviesByBookings() {
        Map<String, Integer> frequencies = new HashMap<>();
        for (Booking2 res : bookingRepo.getAll()) {
            String movieId = res.getMovieId();
            if (frequencies.containsKey(movieId)) {
                frequencies.put(movieId, frequencies.get(movieId) + 1);
            } else {
                frequencies.put(movieId, 1);
            }
        }

        List<MovieReservationVM> results = new ArrayList<>();
        for (String movieId : frequencies.keySet()) {
            Movie2 movie2 = movieRepo.findById(movieId);
            results.add(new MovieReservationVM(movie2, frequencies.get(movieId)));
        }
        results.sort((f1, f2) -> Integer.compare(f2.getBookings(), f1.getBookings()));
        return results;
    }

}
