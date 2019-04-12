package UI;

import Service.MovieReservationVM;
import Service.MovieService2;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DecreasingMovieController {
    public TableView tableViewMovies;
    public TableColumn tableColumnMovieTitle;
    public TableColumn tableColumnBookings;

    private MovieService2 movieService2;

    private ObservableList<MovieReservationVM> movies = FXCollections.observableArrayList();

    public void setService(MovieService2 movieService2){
        this.movieService2 = movieService2;
    }

    private void initialize(){
        Platform.runLater(() -> {
            try {
                List<MovieReservationVM> orderedMovies = movieService2.moviesByBoookings();
                movies.addAll(orderedMovies);
                tableViewMovies.setItems(movies);
            } catch (RuntimeException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window: Movies by bookings.", e);
            }
        });
    }
}
