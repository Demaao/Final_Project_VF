package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SecondaryController {
    @FXML
    private Button backBtn;

    @FXML
    private Button movieListBtn;

    @FXML // Updated unique fx:id for this button
    private Button updateListBtn;

    @FXML
    private void switchToMovieList(ActionEvent event) throws IOException {
        try {
            SimpleClient.getClient().sendToServer(new NewMessage("movieList"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        App.switchScreen("movieList");
    }

    @FXML
    private void switchToWelcomePage() throws IOException {
        App.switchScreen("welcomepage");
    }

    @FXML
    private void switchToUpdateList() throws IOException {
        try {
            SimpleClient.getClient().sendToServer(new NewMessage("movieListUpdate"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        App.switchScreen("updateList");
    }
}