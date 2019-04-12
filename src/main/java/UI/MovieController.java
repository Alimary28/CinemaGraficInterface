package UI;

import Domain.Movie2;
import Service.MovieService2;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MovieController {

    public TextField txtMovieYear;
    public TextField txtMovieTitle;
    public TextField txtPrice;
    public CheckBox chkInCinema;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnCancel;
    public Spinner spnId;

    private MovieService2 movieService2;

    public void setService(MovieService2 movieService2) {
        this.movieService2 = movieService2;
    }

    public void btnCancelClick(ActionEvent actionEvent){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }


    public void btnAddClick(ActionEvent actionEvent) {
        try {
            Movie2 movie = upsertClick();
            movieService2.insert(movie.getId(), movie.getYear(), movie.getTitle(), movie.getPrice(), movie.isInCinema());
            btnCancelClick(actionEvent);
        } catch ( RuntimeException rex ){
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnUpdateClick(ActionEvent actionEvent) {
        try {
            Movie2 movie = upsertClick();
            movieService2.update(movie.getId(), movie.getYear(), movie.getTitle(), movie.getPrice(),movie.isInCinema());
            btnCancelClick(actionEvent);
        } catch ( RuntimeException rex ){
            Common.showValidationError(rex.getMessage());
        }
    }

    private Movie2 upsertClick(){

        try {
            String id = String.valueOf(spnId.getValue());
            int year = Integer.parseInt(txtMovieYear.getText());
            String title = txtMovieTitle.getText();
            double price = Double.parseDouble(txtPrice.getText());
            boolean inCinema = chkInCinema.isSelected();

            return new Movie2(id, year, title, price, inCinema);
        } catch (RuntimeException r){
            Common.showValidationError(r.getMessage());
        }
        return null;
    }

}
