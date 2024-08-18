package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class PaymentPage {

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
    private TextField creditCardTxt;

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
    private Button payBtn;

    @FXML
    private TextField phoneText;

    @FXML
    private Button signUpBtn;

    @FXML
    private Label totalPriceLabel;

    @FXML
    void payForProduct(ActionEvent event) {
        if(IDNumText.getText().isEmpty()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        String.format("Message: %s\n",
                                "Payment completed successfully")
                );
                alert.show();
            });
        }

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

}
