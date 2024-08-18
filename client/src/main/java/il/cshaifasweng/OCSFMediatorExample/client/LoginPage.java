package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginPage {

    @FXML
    private Button changeHostBtn;

    @FXML
    private Button changeHostBtn1;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField userNAmeText;

    @FXML
    private void switchToHomePage() throws IOException {
            App.switchScreen("HomePage");
    }

    @FXML
    private void switchToRightHomePage() throws IOException {
            App.switchScreen("HomePage");////////////////////
    }

    @FXML
    void switchToHostPage(ActionEvent event) {
            App.switchScreen("HostPage");

    }

}