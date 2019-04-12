package UI;

import Domain.ClientCard2;
import Service.ClientCardService2;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DecreasingCardsController {
    public TableView tableViewOrderdCards;
    public TableColumn tableColumnClientId;
    public TableColumn tableColumnName;
    public TableColumn tableColumnFirstname;
    public TableColumn tableColumnCnp;
    public TableColumn tableColumnBirthDate;
    public TableColumn tableColumnRegistrationDate;
    public TableColumn tableColumnPoints;

    private ClientCardService2 cardService2;

    private ObservableList<ClientCard2> cards = FXCollections.observableArrayList();

    public void setService(ClientCardService2 cardService2){
        this.cardService2 = cardService2;
    }

    public void initialize(){
        Platform.runLater(() -> {
            try {
                List<ClientCard2> orderedCards = cardService2.decreasingOrderedCards();

                cards.addAll(orderedCards);
                tableViewOrderdCards.setItems(cards);
            } catch (RuntimeException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window: Ordered cards add.", e);
            }
        });
    }
}
