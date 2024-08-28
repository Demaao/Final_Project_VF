package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.chart.BarChart;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.chart.XYChart;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class ComplaintsReportPage {

    @FXML
    private Button cardAndLinkReportBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Button complaintsReportBtn;

    @FXML
    private BarChart<?, ?> complaintsReportGraph;

    @FXML
    private Button confirmPricesBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private void switchToHeadManagerPage() throws IOException {
        if(LoginPage.employee1.getPosition().equals("Head Manager"))
            App.switchScreen("HeadManagerPage");
        else
            App.switchScreen("BranchManagerPage");
    }

    public void initialize() {
        if(LoginPage.employee1.getPosition().equals("Branch Manager")){
            cardAndLinkReportBtn.setVisible(false);
            confirmPricesBtn.setVisible(false);
            complaintsReportBtn.setLayoutY(300);
            logOutBtn.setLayoutY(350);
        }
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
    private void switchToTicketsReportPage() throws IOException {
        App.switchScreen("TicketsReportPage");
    }

    @FXML
    private void switchToCardsAndLinksReportPage() throws IOException {
        App.switchScreen("CardsAndLinksReportPage");
    }

    @FXML
    private void switchToComplaintsReportPage() throws IOException {
        App.switchScreen("ComplaintsReportPage");
    }

    @FXML
    private void switchToConfirmPricesUpdatesPage() throws IOException {
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
