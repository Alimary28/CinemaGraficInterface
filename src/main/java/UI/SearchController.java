package UI;

import Domain.Booking2;
import Domain.ClientCard2;
import Domain.Movie2;
import Service.BookingService2;
import Service.ClientCardService2;
import Service.MovieService2;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchController {

    public Label search;
    public TextField searchText;
    public Button btnSearch;
    public Button btnCancel;

    private MovieService2 movieService;
    private ClientCardService2 cardService;
    private BookingService2 bookingService;

    public void setService(MovieService2 movieService, ClientCardService2 cardService, BookingService2 bookingService){
        this.movieService = movieService;
        this.cardService = cardService;
        this.bookingService = bookingService;
    }

    public void btnClickCancel(ActionEvent actionEvent) {

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private String movieSearch(String text){

        String movieText = "";
        for(Movie2 movie2 : movieService.fullTextSearch(text)){
            movieText += movie2 + "\n";
        }
        return movieText;
    }
    private String cardSearch(String text){

        String cardText = "";
        for(ClientCard2 clients : cardService.fullTextSearch(text)){
            cardText += clients + "\n";
        }
        return cardText;
    }
    private String bookingSearch(String text){

        String bookingText = "";
        for(Booking2 booking : bookingService.fullTextSearch(text)){
            bookingText += booking + "\n";
        }
        return bookingText;
    }

    public void btnSearch(ActionEvent actionEvent) {

        String text = searchText.getText();
        String found = text + " found in:\n" + movieSearch(text) + "\n" + cardSearch(text) + "\n" + bookingSearch(text);
        if(text.length()>=1)
            search.setText(found);
    }
}
