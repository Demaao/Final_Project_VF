package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HostPage {
    static int flag = 0;

    @FXML // fx:id="EnterIP"
    private Button EnterIP; // Value injected by FXMLLoader

    @FXML
    private Button homePageBtn;

    public void initialize() {
        if (flag == 0) {
            homePageBtn.setVisible(false);
        } else
            homePageBtn.setVisible(true);
    }

    @FXML // fx:id="HostIP"
    private TextField HostIP; // Value injected by FXMLLoader

    @FXML
    void enterHostIP(ActionEvent event) throws IOException {
        flag = 1;
        if (!HostIP.getText().startsWith("localhost")) {
            SimpleClient client;// = new SimpleClient(HostIP.getText(), 3000);
            client = SimpleClient.getClient();
            client.setHost(HostIP.getText());
            client.openConnection();
        }
        try {
            switchToHomePage();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }
}

        // try {//////////////////////////////////////////////////////////////////////////////////////
        // SimpleClient.getClient().sendToServer(new NewMessage("movieList"));
        //  } catch (IOException e) {
        //   e.printStackTrace();
        // }
        //  try {///////////////////////////////////////////////////////////
        //    SimpleClient.getClient().sendToServer(new NewMessage("movieListUpdate"));
        //  } catch (IOException e) {
        //     e.printStackTrace();
        // }
        //  }

