import Domain.*;
import Repository.IRepository;
import Repository.JSonRepository;
import Service.BookingService2;
import Service.ClientCardService2;
import Service.MovieService2;
import UI.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController();

        IValidator<Movie2> movieValidator = new MovieValidatory2();
        IValidator<ClientCard2> cardValidator = new ClientCardValidatory2();
        IValidator<Booking2> bookingValidator = new BookingValidatory2();

        IRepository<Movie2> movieRepo = new JSonRepository<>(movieValidator, "movie.json", Movie2.class);
        IRepository<ClientCard2> cardRepo = new JSonRepository<>(cardValidator, "clientCard.json", ClientCard2.class);
        IRepository<Booking2> bookingRepo = new JSonRepository<>(bookingValidator, "booking.json", Booking2.class);

        MovieService2 movieService2 = new MovieService2(movieRepo, bookingRepo);
        ClientCardService2 clientCardService2 = new ClientCardService2(cardRepo);

        BookingService2 bookingService2 = new BookingService2(bookingRepo, movieRepo, cardRepo);

        controller.setServices(movieService2, clientCardService2, bookingService2);

        primaryStage.setTitle("Cinema manager");
        primaryStage.setScene(new Scene(root, 1600, 600));

        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}


