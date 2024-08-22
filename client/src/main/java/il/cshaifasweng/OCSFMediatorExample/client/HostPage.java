package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;

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
            SimpleClient.client.closeConnection();
            SimpleClient.client.setHost(HostIP.getText());
            SimpleClient.client.openConnection();
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
        if(LoginPage.employee1 != null){
        String position = LoginPage.employee1.getPosition();
            if(Objects.equals(position, "Head Manager")){
                App.switchScreen("HeadManagerPage");
            } else if (Objects.equals(position, "Branch Manager")) {
                App.switchScreen("BranchManagerPage");
            } else if (Objects.equals(position, "Content Manager")) {
                App.switchScreen("ContentManagerPage");
            } else if(Objects.equals(position, "Customer Service Worker")) {
                App.switchScreen("CustomerServiceWorkerPage");
            }
        }
        else
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

