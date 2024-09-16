package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CheckTicketsInCardPage {
    @FXML
    private TableView<?> CardsTable;

    @FXML
    private TableView<?> allCardsTable;

    @FXML
    private TextField IDNumText;

    @FXML TextField cardNumText;

    @FXML
    private Menu cardsMenue;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Menu chargebackMenue;

    @FXML
    private Menu comlaintsMenue;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Menu moviesMenue;

    @FXML
    private Button showCardsBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    private Button okBtn;

    @FXML
    private Button okBtn2;

    @FXML
    private Button enterBtn;

    @FXML
    private Button forgotCardNumBtn;

    @FXML
    private Label cardNumLabel;

    @FXML
    private Label IDNumLabel;

    @FXML
    private Label forgotCardNumLabel;

    @FXML
    void showCardsTable(ActionEvent event) {
        CardsTable.setVisible(true);
        okBtn.setVisible(true);
        forgotCardNumLabel.setVisible(false);
        forgotCardNumBtn.setVisible(false);
    }

    @FXML
    void showAllCardsTable(ActionEvent event) {
        allCardsTable.setVisible(true);
        okBtn2.setVisible(true);

    }

    @FXML
    void askForIDNum(ActionEvent event) {
        IDNumLabel.setVisible(true);
        IDNumText.setVisible(true);
        enterBtn.setVisible(true);

        forgotCardNumBtn.setVisible(false);
        forgotCardNumLabel.setVisible(false);

        cardNumLabel.setVisible(false);
        cardNumText.setVisible(false);
        showCardsBtn.setVisible(false);
    }


    @FXML
    private void switchToCardsPage() throws IOException {
        App.switchScreen("CardsPage");
    }


    @FXML
    private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        App.switchScreen("ChargebackPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        App.switchScreen("MoviesPage");
    }
    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        App.switchScreen("PersonalAreaPage");
    }
}

