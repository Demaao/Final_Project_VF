package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class CardsPage {
    private ObservableList<String> ticketType = FXCollections.observableArrayList("Regular", "VIP");

    @FXML
    private Menu cardsMenue;

    @FXML
    private Spinner<Integer> cardsQuantityChooser;

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
    private Button purchaseCardBtn;

    @FXML
    private Label selectProductText;

    @FXML
    private Button signUpBtn;

    @FXML
    private Spinner<String> ticketTypeChooser;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        valueFactory.setValue(1);
        cardsQuantityChooser.setValueFactory(valueFactory);

        SpinnerValueFactory<String> valueFactory2 = new SpinnerValueFactory.ListSpinnerValueFactory<String>(ticketType);
        ticketTypeChooser.setValueFactory(valueFactory2);
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        App.switchScreen("CardsPage");
    }


    @FXML
    private void switchToCheckTicketsPage()  throws IOException  {
        App.switchScreen("CheckTicketsInCardPage");

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
    private void switchToPurchaseProductsPage() throws IOException{
        App.switchScreen("PurchaseProductsPage");
    }
    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        App.switchScreen("PersonalAreaPage");
    }

}


