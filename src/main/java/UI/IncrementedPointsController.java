package UI;

import Domain.ClientCard2;
import Service.ClientCardService2;
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

public class IncrementedPointsController {

    public TableView tableViewCards;
    public TableColumn tableColumnClientId;
    public TableColumn tableColumnName;
    public TableColumn tableColumnFirstname;
    public TableColumn tableColumnCnp;
    public TableColumn tableColumnBirthDate;
    public TableColumn tableColumnRegistrationDate;
    public TableColumn tableColumnPoints;
    public TextField incrementedPoints;
    public TextField finishMonth;
    public TextField finishDay;
    public TextField beginMonth;
    public TextField beginDay;

    public Button btnCancelPoints;
    public Button btnIncrementedPoints;

    private ClientCardService2 cardService2;

    private ObservableList<ClientCard2> clients = FXCollections.observableArrayList();

   public void setService(ClientCardService2 cardService2){
       this.cardService2 = cardService2;
   }

   public void initialize(){
       Platform.runLater(() -> {
           clients.addAll(cardService2.getAll());
           tableViewCards.setItems(clients);
       });
   }

    public void btnPointsRedoClick(ActionEvent actionEvent) {

        cardService2.redoAble();
        clients.clear();
        clients.addAll(cardService2.getAll());
    }

    public void btnPointsUndoClick(ActionEvent actionEvent) {

        cardService2.undoAble();
        clients.clear();
        clients.addAll(cardService2.getAll());
    }

    public void btnCancelClick(ActionEvent actionEvent) {

        Stage stage = (Stage) btnCancelPoints.getScene().getWindow();
        stage.close();
    }

    public void btnPointsClick(ActionEvent actionEvent) {
       try{
           LocalDate start = LocalDate.of(1900, Integer.parseInt(beginMonth.getText()), Integer.parseInt(beginDay.getText()));
           LocalDate end = LocalDate.of(1900, Integer.parseInt(finishMonth.getText()), Integer.parseInt(finishDay.getText()));
           int points = Integer.parseInt(incrementedPoints.getText());
           cardService2.incrementPoints(start, end, points);
           clients.clear();
           clients.addAll(cardService2.getAll());
       } catch (RuntimeException r){
           Common.showValidationError(r.getMessage());
       }
    }
}
