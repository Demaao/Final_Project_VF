package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Customer;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
//import il.cshaifasweng.OCSFMediatorExample.entities.Purchase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class PersonalDetailsPage {
//
//    @FXML
//    private TableView<Purchase> purchasesTable;
//
//    @FXML
//    private TableColumn<Purchase, String> orderNumberColumn;
//
//    @FXML
//    private TableColumn<Purchase, String> orderPriceColumn;
//
//    @FXML
//    private TableColumn<Purchase, String> orderDateColumn;
//
//    @FXML
//    private TableColumn<Purchase, Double> orderDetailsColumn;

    @FXML
    private Button backBtn;

    private Customer loggedInCustomer;



    private void logOutCustomer() {
        if (loggedInCustomer != null) {
            try {
                NewMessage message = new NewMessage(loggedInCustomer, "logOutCustomer");
                SimpleClient.getClient().sendToServer(message);
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
    private void switchToPersonalArea() throws IOException {
        logOutCustomer();
            App.switchScreen("PersonalAreaPage");
        }


    @FXML
    private void BackToPersonalArea() throws IOException {
        App.switchScreen("PersonalAreaPage");
    }


}
