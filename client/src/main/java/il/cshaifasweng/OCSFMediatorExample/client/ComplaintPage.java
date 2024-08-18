package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ComplaintPage {

    @FXML
    private TextField IDNumText;

    @FXML
    private Menu cardsMenue;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Menu chargebackMenue;

    @FXML
    private Menu comlaintsMenue;

    @FXML
    private TextArea complaintText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField fullNameText;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Menu moviesMenue;

    @FXML
    private TextField phoneText;

    @FXML
    private Button signUpBtn;

    @FXML
    private Button submitComplaintBtn;

    @FXML
    void submitComplaint(ActionEvent event) {

    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        App.switchScreen("LoginPage");
    }

    @FXML
   private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        App.switchScreen("CardsPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        App.switchScreen("MoviesPage");
    }
}


