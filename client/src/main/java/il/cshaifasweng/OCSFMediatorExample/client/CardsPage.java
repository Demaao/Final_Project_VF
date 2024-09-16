package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Card;
import il.cshaifasweng.OCSFMediatorExample.entities.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardsPage {
    private ObservableList<String> ticketType = FXCollections.observableArrayList("Regular", "VIP");

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
        EventBus.getDefault().register(this);
        requestCinemaFromServer();

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        valueFactory.setValue(1);
        cardsQuantityChooser.setValueFactory(valueFactory);

        SpinnerValueFactory<String> valueFactory2 = new SpinnerValueFactory.ListSpinnerValueFactory<String>(ticketType);
        ticketTypeChooser.setValueFactory(valueFactory2);
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        cards.clear();
        App.switchScreen("CardsPage");
    }

    @FXML
    private void switchToCheckTicketsPage()  throws IOException  {
        cards.clear();
        App.switchScreen("CheckTicketsInCardPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        cards.clear();
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        cards.clear();
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        cards.clear();
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        cards.clear();
        App.switchScreen("LoginPage");
    }

    private Cinema cinema = new Cinema();
    private double cardValue;

    @FXML
    private void switchToChargebackPage() throws IOException {
        cards.clear();
        App.switchScreen("ChargebackPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        cards.clear();
        App.switchScreen("MoviesPage");
    }

    public  static List<Card> cards = new ArrayList<Card>();

    @FXML
    private void switchToPurchaseProductsPage() throws IOException{
        if(ticketTypeChooser.getValue().equals("VIP"))
            cardValue = cardValue*1.5;
        for (int i = 0; i < cardsQuantityChooser.getValue(); i++) {
            Card card = new Card("Card", null, "Credit Card", cardValue,
                    null, null,1, null,20, ticketTypeChooser.getValue());
            cards.add(card);
        }
        App.switchScreen("PurchaseProductsPage");
    }

    @FXML
    private void requestCinemaFromServer() {
        try {
            NewMessage message = new NewMessage("cinema");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Subscribe
    public void onCinemaEvent(UpdateCinemaEvent event) {
        Platform.runLater(() -> {
            Cinema cinema = event.getCinema();
            this.cinema = cinema;
            if(cards.isEmpty())
                cardValue = cinema.getCardPrice();
        });
    }

    @Subscribe
    public void onRequestEvent(UpdateRequestEvent event) {}

    @Subscribe
    public void onCardEvent(UpdateCardsEvent event){}
}


