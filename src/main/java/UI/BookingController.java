package UI;

import Domain.Booking2;
import Service.BookingService2;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingController {

    public TextField txtDay;
    public TextField txtMonth;
    public TextField txtYear;
    public TextField txtHour;
    public TextField txtMinutes;
    public Button btnBookingAdd;
    public Button btnBookingUpdate;
    public Button btnBookingCancel;
    public Spinner spnId;
    public Spinner spnMovieId;
    public Spinner spnClientCardId;

    public BookingService2 bookingService2;

    public void setService(BookingService2 bookingService2) {
        this.bookingService2 = bookingService2;
    }


    public void btnAddClick(javafx.event.ActionEvent actionEvent) {

        try {
            Booking2 booking = upsertClick();
            bookingService2.insert(booking.getId(), booking.getMovieId(), booking.getCardId(), booking.getDate(), booking.getHour());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnCancelClick(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) btnBookingCancel.getScene().getWindow();
        stage.close();
    }

    public void btnUpdateClick(ActionEvent actionEvent) {
        try {
            Booking2 booking = upsertClick();
            bookingService2.update(booking.getId(), booking.getMovieId(), booking.getCardId(), booking.getDate(), booking.getHour());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex){
            Common.showValidationError(rex.getMessage());
        }

    }

        private Booking2 upsertClick(){
            try {
                String id = String.valueOf(spnId.getValue());
                String movieId = String.valueOf(spnMovieId.getValue());
                String clientCardId = String.valueOf(spnClientCardId.getValue());
                LocalDate date = LocalDate.of(Integer.parseInt(txtYear.getText()), Integer.parseInt(txtMonth.getText()), Integer.parseInt(txtDay.getText()));
                LocalTime hour = LocalTime.of(Integer.parseInt(txtHour.getText()), Integer.parseInt(txtMinutes.getText()));

                return new Booking2(id, movieId, clientCardId, date, hour);
            } catch (RuntimeException rex){
                Common.showValidationError(rex.getMessage());
            }
            return null;
        }

    }
