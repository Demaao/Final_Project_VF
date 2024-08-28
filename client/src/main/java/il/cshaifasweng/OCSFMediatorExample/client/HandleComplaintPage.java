package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;


public class HandleComplaintPage {


        @FXML
        private TextArea answerText;

        @FXML
        private Button changeHostBtn;

        @FXML
        private TableView<?> complaintTable;

        @FXML
        private Button homePageBtn;

        @FXML
        private Button logOutBtn;

        @FXML
        private Button submitAnswerBtn;

        @FXML
        void submitAnswer(ActionEvent event) {

        }


    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToCustomerServiceWorkerPage() throws IOException {
        App.switchScreen("CustomerServiceWorkerPage");
    }


    @FXML
    private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
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
