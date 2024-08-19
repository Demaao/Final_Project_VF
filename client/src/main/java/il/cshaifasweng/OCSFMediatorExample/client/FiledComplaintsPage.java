package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class FiledComplaintsPage {

    @FXML
    private Button ChargebackBtn;

    @FXML
    private TextField IDNumText;

    @FXML
    private Button OKBtn;

    @FXML
    private Button cardsBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private TableView<?> complaintTable;

    @FXML
    private Button complaintsBtn;

    @FXML
    private Button enterBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Button moviesBtn;

    @FXML
    void showComplaintTable(ActionEvent event) {
        complaintTable.setVisible(true);
        OKBtn.setVisible(true);

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

