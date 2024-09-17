package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Customer;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.Purchase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class PersonalAreaPage {

    @FXML
    private TextField IDNumText;

    @FXML
    private Button enterBtn;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label menuMsg;

    @FXML
    private Button orderBtn, messagesBtn, MycomplaintBtn, moviesLinksBtn;

    public static Customer loggedInCustomer;

    public void initialize() {
        EventBus.getDefault().register(this);
        if (loggedInCustomer != null) {
            welcomeLabel.setText("Welcome " + loggedInCustomer.getName());
            IDNumText.setVisible(false);
            enterBtn.setVisible(false);

            // Set the buttons to be visible now that the ID has been validated and customer is logged in
            orderBtn.setVisible(true);
            messagesBtn.setVisible(true);
            MycomplaintBtn.setVisible(true);
            moviesLinksBtn.setVisible(true);
            menuMsg.setVisible(true);
        }
    }

    @FXML
    private void showPurchasesTable(ActionEvent event) {
        String idNum = IDNumText.getText();
        if (idNum.isEmpty() || idNum.length() != 9 || !idNum.matches("\\d+")) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a valid 9-digit ID number.");
                alert.show();
            });
        } else {
            try {
                int id = Integer.parseInt(idNum);
                NewMessage message = new NewMessage("loginCustomer", id);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }}
    }

    @Subscribe
    public void handleCustomerLogin(UpdateLoginCustomerEvent event) {
        Platform.runLater(() -> {
            loggedInCustomer = event.getCustomer();
            welcomeLabel.setText("Welcome " + loggedInCustomer.getName());

            IDNumText.setVisible(false);
            enterBtn.setVisible(false);

            orderBtn.setVisible(true);
            messagesBtn.setVisible(true);
            MycomplaintBtn.setVisible(true);
            moviesLinksBtn.setVisible(true);
            menuMsg.setVisible(true);
        });
    }

    public static void logOutCustomer() {
        if (loggedInCustomer != null) {
            try {
                NewMessage message = new NewMessage(loggedInCustomer, "logOutCustomer");
                SimpleClient.getClient().sendToServer(message);
                loggedInCustomer = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void switchToPersonalDetailsPage() throws IOException {
        App.switchScreen("PersonalDetailsPage");
        /*
        if (loggedInCustomer != null) {
            int customerId = loggedInCustomer.getId();
            NewMessage message = new NewMessage("fetchPurchases", customerId);
            try {
                SimpleClient.getClient().sendToServer(message);
                App.switchScreen("PersonalDetailsPage");
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to connect to server.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No customer is logged in.");
            alert.showAndWait();
        } */
    }

    @FXML
    private void switchToHostPage() throws IOException {
        logOutCustomer();
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        logOutCustomer();
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        logOutCustomer();
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        logOutCustomer();
        App.switchScreen("LoginPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        logOutCustomer();
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        logOutCustomer();
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        logOutCustomer();
        App.switchScreen("CardsPage");
    }

    @FXML
    private void switchToMessages() throws IOException {
        App.switchScreen("PersonalMessagesPage");
    }

    @FXML
    public void switchToFiledComplaintsPage() throws IOException {
        App.switchScreen("FiledComplaintsPage");
    }

    @FXML
    private void switchToMoviesLinks() throws IOException {
        logOutCustomer();
        App.switchScreen("MoviesLinksPage");
    }

    @FXML
    private void switchToPersonalArea() throws IOException {
        logOutCustomer();
        App.switchScreen("PersonalAreaPage");
    }
}
