package UI;

import Domain.Booking2;
import Service.BookingService2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingSearchController {

    public TableView tableViewBookings;
    public TableColumn tableColumnId;
    public TableColumn tableColumnIdMovie;
    public TableColumn tableColumnClientCardId;
    public TableColumn tableColumnDate;
    public TableColumn tableColumnHour;
    public TextField firstHour;
    public TextField firstMinutes;
    public TextField finishHour;
    public TextField finishMinutes;
    public Button btnCancel;
    public Button bookingSearch;

    private BookingService2 bookingService;

    private ObservableList<Booking2> bookings = FXCollections.observableArrayList();

    public void setService(BookingService2 bookingService){
        this.bookingService = bookingService;
    }

    public void btnSearchClick(ActionEvent actionEvent) {
        try{
            bookings.clear();
            LocalTime start = LocalTime.of(Integer.parseInt(firstHour.getText()), Integer.parseInt(firstMinutes.getText()));
            LocalTime end = LocalTime.of(Integer.parseInt(finishHour.getText()), Integer.parseInt(finishMinutes.getText()));
            bookings.addAll(bookingService.searchByPeriod(start, end));
            tableViewBookings.setItems(bookings);
        } catch (RuntimeException rex){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Movie update.", rex);
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnCancelClick(ActionEvent actionEvent) {

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
