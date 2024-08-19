package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class CustomerServiceWorkerPage {
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
}
