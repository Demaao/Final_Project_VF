package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class EditPricesPage {
    @FXML
    private Button addMovieBtn;

    @FXML
    private Label cardPriceLabel;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Button editCardPriceBtn;

    @FXML
    private Button editLinkTicketPriceBtn;

    @FXML
    private Button editPricesBtn;

    @FXML
    private Button editScreenigBtn;

    @FXML
    private Button editTicketPriceBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Label linkTicketPriceLabel;

    @FXML
    private Button logOutBtn;

    @FXML
    private TextField newCardPriceText;

    @FXML
    private TextField newLinkTicketPriceText;

    @FXML
    private TextField newTicketPriceText;

    @FXML
    private Button removeMovieBtn;

    @FXML
    private Button saveCardPriceBtn;

    @FXML
    private Button saveLinkTicketPriceBtn;

    @FXML
    private Button saveTicketPriceBtn;

    @FXML
    private Button cancelCardBtn;

    @FXML
    private Button cancelLinkTicketBtn;

    @FXML
    private Button cancelTicketBtn;

    @FXML
    private Label ticketPriceLabel;
    @FXML
    private void switchToAddMoviePage() throws IOException {
        App.switchScreen("AddMoviePage");
    }

    @FXML
    private void switchToRemoveMoviePage() throws IOException {
        App.switchScreen("RemoveMoviePage");
    }

    @FXML
    private void switchToEditPricesPage() throws IOException {
        App.switchScreen("EditPricesPage");
    }

    @FXML
    private void switchToEditScreeningPage() throws IOException {
        App.switchScreen("EditScreeningPage");
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
    private void switchToContentManagerPage() throws IOException {
        App.switchScreen("ContentManagerPage");
    }

    public void editTicketPrice(ActionEvent actionEvent) {
        editTicketPriceBtn.setVisible(false);
        newTicketPriceText.setVisible(true);
        saveTicketPriceBtn.setVisible(true);
        cancelTicketBtn.setVisible(true);
    }

    public void editLinkTicketPrice(ActionEvent actionEvent) {
        editLinkTicketPriceBtn.setVisible(false);
        newLinkTicketPriceText.setVisible(true);
        saveLinkTicketPriceBtn.setVisible(true);
        cancelLinkTicketBtn.setVisible(true);
    }

    public void editCardPrice(ActionEvent actionEvent) {
        editCardPriceBtn.setVisible(false);
        newCardPriceText.setVisible(true);
        saveCardPriceBtn.setVisible(true);
        cancelCardBtn.setVisible(true);
    }

    public void saveCardPrice(ActionEvent actionEvent) {
    }

    public void saveLinkTicketPrice(ActionEvent actionEvent) {
    }

    public void saveTicketPrice(ActionEvent actionEvent) {
    }

    public void cancelTicket(ActionEvent actionEvent) {
        editTicketPriceBtn.setVisible(true);
        newTicketPriceText.setVisible(false);
        saveTicketPriceBtn.setVisible(false);
        cancelTicketBtn.setVisible(false);
    }

    public void cancelLinkTicket(ActionEvent actionEvent) {
        editLinkTicketPriceBtn.setVisible(true);
        newLinkTicketPriceText.setVisible(false);
        saveLinkTicketPriceBtn.setVisible(false);
        cancelLinkTicketBtn.setVisible(false);
    }

    public void cancelCard(ActionEvent actionEvent) {
        editCardPriceBtn.setVisible(true);
        newCardPriceText.setVisible(false);
        saveCardPriceBtn.setVisible(false);
        cancelCardBtn.setVisible(false);
    }



    @FXML
    private void requestLogoutFromServer() {
        try { ///////////////////////////////////////////////////////////////
            NewMessage message = new NewMessage("logOut", LoginPage.employee1);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

/*
  public void initialize() {//////////////////////////////////////
        EventBus.getDefault().register(this);
    }
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        Platform.runLater(() -> {
            LoginPage.employee1 = null;
            try {
                switchToHomePage();
            }
            catch (IOException e) {}
        });
    }*/
}
