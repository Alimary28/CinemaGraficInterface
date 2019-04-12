package UI;

import Domain.ClientCard2;
import Service.ClientCardService2;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CardController {

    public TextField txtName;
    public TextField txtFirstname;
    public TextField txtCNP;
    public TextField txtDayB;
    public TextField txtMonthB;
    public TextField txtYearB;
    public TextField txtDayR;
    public TextField txtMonthR;
    public TextField txtYearR;
    public TextField txtPoints;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnCancel;
    public Spinner spnId;

    private ClientCardService2 clientCardService2;

    public void setService(ClientCardService2 clientCardService2) {
        this.clientCardService2 = clientCardService2;
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }


    public void btnAddClick(ActionEvent actionEvent) {
        try {
            ClientCard2 card = upsertClick();
            clientCardService2.insert(card.getId(), card.getName(), card.getFirstname(), card.getCnp(), card.getBirthDate(), card.getRegistrationDate(), card.getPoints());
            btnCancelClick(actionEvent);
        } catch ( RuntimeException rex ){
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnUpdateClick(ActionEvent actionEvent){

        try {
            ClientCard2 card = upsertClick();
            clientCardService2.update(card.getId(), card.getName(), card.getFirstname(), card.getCnp(), card.getBirthDate(), card.getRegistrationDate(), card.getPoints());
            btnCancelClick(actionEvent);
        } catch ( RuntimeException rex ){
            Common.showValidationError(rex.getMessage());
        }
    }

    private ClientCard2 upsertClick(){
        try {
            String id = String.valueOf(spnId.getValue());
            String name = txtName.getText();
            String firstName = txtFirstname.getText();
            String cnp = txtCNP.getText();
            LocalDate birthDate = LocalDate.of(Integer.parseInt(txtYearB.getText()), Integer.parseInt(txtMonthB.getText()), Integer.parseInt(txtDayB.getText()));
            LocalDate registrationDate = LocalDate.of(Integer.parseInt(txtYearR.getText()), Integer.parseInt(txtMonthR.getText()), Integer.parseInt(txtDayR.getText()));
            int points = Integer.parseInt(txtPoints.getText());
            return  new ClientCard2(id, name, firstName, cnp, birthDate, registrationDate, points);
        } catch ( RuntimeException rex ){
            Common.showValidationError(rex.getMessage());
        }
        return null;
    }
}
