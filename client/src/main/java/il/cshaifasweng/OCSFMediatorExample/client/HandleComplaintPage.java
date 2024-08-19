package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

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
}
