package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Customer;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class PersonalAreaPage {

    @FXML
    private TextField IDNumText;

    @FXML
    private Button enterBtn;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label  menuMsg;

    @FXML
    private Button orderBtn, messagesBtn, MycomplaintBtn, moviesLinksBtn;

   // private Customer loggedInCustomer; ////////////////////////////////////////////////////////

    public static Customer loggedInCustomer;

    public void initialize() {
        EventBus.getDefault().register(this);
      if(loggedInCustomer != null) {
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

    // Send a request to the server to fetch customer information
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
                int id = Integer.parseInt(idNum);  // Convert ID number to int
                NewMessage message = new NewMessage("loginCustomer", id);  // Send the ID to the server
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Event handler for when the customer login event is triggered
    @Subscribe
    public void handleCustomerLogin(UpdateLoginCustomerEvent event) {
        Platform.runLater(() -> {
            loggedInCustomer = event.getCustomer();
            welcomeLabel.setText("Welcome " + loggedInCustomer.getName());


            IDNumText.setVisible(false);
            enterBtn.setVisible(false);

            // Set the buttons to be visible now that the ID has been validated and customer is logged in
            orderBtn.setVisible(true);
            messagesBtn.setVisible(true);
            MycomplaintBtn.setVisible(true);
            moviesLinksBtn.setVisible(true);
            menuMsg.setVisible(true);
        });
    }


    // Function to log out the customer
    public static void logOutCustomer() {  ///////////////////////
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
    private void switchToMyOrders() throws IOException {
        logOutCustomer();
        App.switchScreen("MyOrdersPage");
    }

    @FXML
    private void switchToMessages() throws IOException {
        logOutCustomer();
        App.switchScreen("MessagesPage");
    }

    @FXML
    public void switchToFiledComplaintsPage() throws IOException {
        App.switchScreen("FiledComplaintsPage");
    }
    /*
    @FXML
    private void switchToComplaintsStatus() throws IOException {
        logOutCustomer();
        App.switchScreen("ComplaintsStatusPage");
    }*/


    @FXML
    public void switchToPersonalDetailsPage() throws IOException {
        App.switchScreen("PersonalDetailsPage");
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
