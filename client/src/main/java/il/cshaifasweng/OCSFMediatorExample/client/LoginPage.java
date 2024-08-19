package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        //////////////////////////  NEED TO CHANGE ... THIS IS ONLY TO SEE THE WORKERS PAGES
        if(userNAmeText.getText().equals("head manager")){
            App.switchScreen("HomePage");
        }
        else if(userNAmeText.getText().equals("branch manager")){
            App.switchScreen("HomePage");
        }
        else if(userNAmeText.getText().equals("content manager")){
            App.switchScreen("HomePage");
        }
        else if(userNAmeText.getText().equals("customer service worker")){
            App.switchScreen("CustomerServiceWorkerPage");
        }
        else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        String.format("%s\n",
                                "User name is incorrect.")
                );
                alert.show();
            });
        }
    }

    @FXML
    void switchToHostPage(ActionEvent event) {
        App.switchScreen("HostPage");
    }

}