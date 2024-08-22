package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import javafx.scene.control.Label;

import java.io.IOException;

public class CustomerServiceWorkerPage {
    @FXML
    private Label customerServiceWorkerNameLabel;

    @FXML
    private void switchToCustomerServiceWorkerPage() throws IOException {
        App.switchScreen("CustomerServiceWorkerPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    void switchToHostPage(ActionEvent event) {
        App.switchScreen("HostPage");
    }

    @FXML
    void switchToHandleComplaintPage(ActionEvent event) {
        App.switchScreen("HandleComplaintPage");
    }

    public void initialize() {
        customerServiceWorkerNameLabel.setText(LoginPage.employee1.getFullName());
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
