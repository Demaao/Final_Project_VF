package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class BranchManagerPage {
    @FXML
    private Button changeHostBtn;

    @FXML
    private Button complaintsReportsBtn;

    @FXML
    private Label contentManagerNameLabel;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button ticketsReportsBtn;

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
    private void switchToComplaintsReportPage() throws IOException{
        App.switchScreen("ComplaintsReportPage");
    }
}

