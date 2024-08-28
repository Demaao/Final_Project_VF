package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class CardsAndLinksReportPage {
    @FXML
    private Button changeHostBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private ComboBox<?> monthComboBox;

    @FXML
    private TableView<?> cardsAndLinksReportTable;

    @FXML
    private void switchToHeadManagerPage() throws IOException {
        App.switchScreen("HeadManagerPage");
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
    private void switchToTicketsReportPage() throws IOException{
        App.switchScreen("TicketsReportPage");
    }

    @FXML
    private void switchToCardsAndLinksReportPage() throws IOException{
        App.switchScreen("CardsAndLinksReportPage");
    }

    @FXML
    private void switchToComplaintsReportPage() throws IOException{
        App.switchScreen("ComplaintsReportPage");
    }

    @FXML
    private void switchToConfirmPricesUpdatesPage() throws IOException{
        App.switchScreen("ConfirmPricesUpdatesPage");
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
