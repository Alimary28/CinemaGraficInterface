package UI;

import Domain.Booking2;
import Domain.ClientCard2;
import Domain.Movie2;
import Service.BookingService2;
import Service.ClientCardService2;
import Service.MovieService2;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    public TableView tableViewMovies;
    public TableColumn tableColumnMovieId;
    public TableColumn tableColumnMovieYear;
    public TableColumn tableColumnMovieTitle;
    public TableColumn tableColumnPrice;
    public TableColumn tableColumnInCinema;

    public TableView tableViewCards;
    public TableColumn tableColumnClientId;
    public TableColumn tableColumnName;
    public TableColumn tableColumnFirstname;
    public TableColumn tableColumnCnp;
    public TableColumn tableColumnBirthDate;
    public TableColumn tableColumnRegistrationDate;
    public TableColumn tableColumnPoints;

    public TableView tableViewBookings;
    public TableColumn tableColumnId;
    public TableColumn tableColumnIdMovie;
    public TableColumn tableColumnClientCardId;
    public TableColumn tableColumnDate;
    public TableColumn tableColumnHour;


    private MovieService2 movieService2;
    private ClientCardService2 clientCardService2;
    private BookingService2 bookingService2;

    private ObservableList<Movie2> movies = FXCollections.observableArrayList();
    private ObservableList<ClientCard2> cards = FXCollections.observableArrayList();
    private ObservableList<Booking2> bookings = FXCollections.observableArrayList();

    public void setServices(MovieService2 movieService2, ClientCardService2 clientCardService2, BookingService2 bookingService2) {
        this.movieService2 = movieService2;
        this.clientCardService2 = clientCardService2;
        this.bookingService2 = bookingService2;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            movies.addAll(movieService2.getAll());
            tableViewMovies.setItems(movies);
            cards.addAll(clientCardService2.getAll());
            tableViewCards.setItems(cards);
            bookings.addAll(bookingService2.getAll());
            tableViewBookings.setItems(bookings);
        });
    }

    public void btnAddMovieClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/movieAdd.fxml"));
        upsertMovie(fxmlLoader, "Movie add");
    }

    public void upsertMovie(FXMLLoader fxmlLoader, String title) {

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 200);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            MovieController controller =  fxmlLoader.getController();
            controller.setService(movieService2);
            stage.showAndWait();
            movies.clear();
            movies.addAll(movieService2.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Movie add.", e);
        }
    }

    public void btnUpdateMovieClick(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/movieUpdate.fxml"));
        upsertMovie(fxmlLoader, "Movie update");

    }

    public void btnDeleteMovieClick(javafx.event.ActionEvent actionEvent) {

        Movie2 selected = (Movie2) tableViewMovies.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                movieService2.remove(selected.getId());
                movies.clear();
                movies.addAll(movieService2.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }
    public void btnAddCardClick(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/cardAdd.fxml"));
        upsertCard(fxmlLoader, "Card add");
    }

    public void upsertCard(FXMLLoader fxmlLoader, String title) {
        try {

            Scene scene = new Scene(fxmlLoader.load(), 600, 200);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            CardController controller =  fxmlLoader.getController();
            controller.setService(clientCardService2);
            stage.showAndWait();
            cards.clear();
            cards.addAll(clientCardService2.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Card add.", e);
        }
    }

    public void btnUpdateCardClick(ActionEvent actionEvent) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/cardUpdate.fxml"));
            upsertCard(fxmlLoader, "Card update");
    }

    public void btnDeleteCardClick(ActionEvent actionEvent) {
        ClientCard2 selected = (ClientCard2) tableViewCards.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                clientCardService2.remove(selected.getId());
                cards.clear();
                cards.addAll(clientCardService2.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }

    public void btnAddBookingClick(ActionEvent actionEvent) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/bookingAdd.fxml"));
            upsertBooking(fxmlLoader, "Booking add");
    }

    public void upsertBooking(FXMLLoader fxmlLoader, String title){

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 200);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            BookingController controller =  fxmlLoader.getController();
            controller.setService(bookingService2);
            stage.showAndWait();
            bookings.clear();
            bookings.addAll(bookingService2.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Booking add.", e);
        }
    }
    public void btnUpdateBookingClick(ActionEvent actionEvent) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/bookingUpdate.fxml"));
            upsertBooking(fxmlLoader, "Booking update");
    }

    public void btnDeleteBookingClick(ActionEvent actionEvent) {
        Booking2 selected = (Booking2) tableViewBookings.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                bookingService2.remove(selected.getId());
                bookings.clear();
                bookings.addAll(bookingService2.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }
    

    public void btnMovieUndoClick(ActionEvent actionEvent) {

        movieService2.undoAble();
        movies.clear();
        movies.addAll(movieService2.getAll());
    }

    public void btnMovieRedoClick(ActionEvent actionEvent) {

        movieService2.redoAble();
        movies.clear();
        movies.addAll(movieService2.getAll());
    }

    public void btnCardUndoClick(ActionEvent actionEvent) {

        clientCardService2.undoAble();
        cards.clear();
        cards.addAll(clientCardService2.getAll());
    }

    public void btnCardRedoClick(ActionEvent actionEvent) {

        clientCardService2.redoAble();
        cards.clear();
        cards.addAll(clientCardService2.getAll());
    }

    public void btnBookingUndoClick(ActionEvent actionEvent) {

        bookingService2.undo();
        bookings.clear();
        bookings.addAll(bookingService2.getAll());
    }

    public void btnCardBookingClick(ActionEvent actionEvent) {

        bookingService2.redo();
        bookings.clear();
        bookings.addAll(bookingService2.getAll());
    }

    public void btnSearchClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/searchText.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            Stage stage = new Stage();
            stage.setTitle("Full Text Search");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            SearchController controller = fxmlLoader.getController();
            controller.setService(movieService2, clientCardService2, bookingService2);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Text search add.", e);
        }
    }

    public void btnBookingSearch(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/bookingSearch.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            Stage stage = new Stage();
            stage.setTitle("Booking search");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            BookingSearchController controller = fxmlLoader.getController();
            controller.setService(bookingService2);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Booking search add.", e);
        }
    }

    public void btnMoviesByBookings(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/decreasingOrderMovies.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            Stage stage = new Stage();
            stage.setTitle("Movies ordered by bookings");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            DecreasingMovieController controller = fxmlLoader.getController();
            controller.setService(movieService2);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Movies ordered by bookings add.", e);
        }
    }

    public void btnCardsByPoints(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/decreasingOrderCards.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setTitle("Cards ordered by points");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            DecreasingCardsController controller = fxmlLoader.getController();
            controller.setService(clientCardService2);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Cards ordered by points add.", e);
        }
    }

    public void btnDeleteBookings(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/deleteBookings.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
            Stage stage = new Stage();
            stage.setTitle("Deleting bookings");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            BookingDeleteController controller = fxmlLoader.getController();
            controller.setService(bookingService2);
            stage.showAndWait();
            bookings.clear();
            bookings.addAll(bookingService2.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Delete bookings add.", e);
        }
    }

    public void btnBirthdayPoints(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/incrementedPoints.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Birthday points");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            IncrementedPointsController controller = fxmlLoader.getController();
            controller.setService(clientCardService2);
            stage.showAndWait();
            //
            cards.clear();
            cards.addAll(clientCardService2.getAll());
            //
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Birthday points add.", e);
        }
    }
}
