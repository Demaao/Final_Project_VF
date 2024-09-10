package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;

import java.io.IOException;

public class PurchaseLink {

    @FXML
    private Button cancelBtn;

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
    private Button nextBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    public void switchToMoviesPage() throws IOException {
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void switchToPaymentPage() throws IOException {
        App.switchScreen("PaymentLink");
    }


    @FXML
    private void switchToCardsPage() throws IOException {
        App.switchScreen("CardsPage");
    }
    @FXML
    private void switchToMovieDetailsPage() throws IOException {
        App.switchScreen("MovieLinkDetailsPage");
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
}

