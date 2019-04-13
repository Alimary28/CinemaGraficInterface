package UI;

import Domain.Booking2;
import Service.BookingService2;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class BookingDeleteController {


    public TableView tableViewBookings;
    public TableColumn tableColumnId;
    public TableColumn tableColumnIdMovie;
    public TableColumn tableColumnDate;
    public TableColumn tableColumnHour;
    public TextField beginDay;
    public TextField beginMonth;
    public TextField beginYear;
    public TextField finishDay;
    public TextField finishMonth;
    public TextField finishYear;
    public Button deleteBooking;
    public Button cancelBooking;
    public TableColumn tableColumnCardId;

    private BookingService2 bookingService;

    public void setService(BookingService2 bookingService){
        this.bookingService = bookingService;
    }
    ObservableList<Booking2> bookings = FXCollections.observableArrayList();

    public void initialize(){
        Platform.runLater(() -> {
            bookings.addAll(bookingService.getAll());
            tableViewBookings.setItems(bookings);
        });
    }
    public void deleteBookingClick(ActionEvent actionEvent) {
        try {
            LocalDate start = LocalDate.of(Integer.parseInt(beginYear.getText()), Integer.parseInt(beginMonth.getText()), Integer.parseInt(beginDay.getText()));
            LocalDate end = LocalDate.of(Integer.parseInt(finishYear.getText()), Integer.parseInt(finishMonth.getText()), Integer.parseInt(finishDay.getText()));
            bookingService.removeByPeriod(start, end);
            bookings.clear();
            bookings.addAll(bookingService.getAll());
        } catch (RuntimeException rex){
            Common.showValidationError(rex.getMessage());
        }
    }

    public void cancelBookingClick(ActionEvent actionEvent) {

        Stage stage = (Stage) cancelBooking.getScene().getWindow();
        stage.close();
    }

    public void btnDeleteUndoClick(ActionEvent actionEvent) {

        bookingService.undo();
        bookings.clear();
        bookings.addAll(bookingService.getAll());
    }

    public void btnDeleteRedoClick(ActionEvent actionEvent) {

        bookingService.redo();
        bookings.clear();;
        bookings.addAll(bookingService.getAll());
    }
}
