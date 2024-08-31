package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.Screening;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class EditScreeningPage {
    @FXML
    private DatePicker addDateText;

    @FXML
    private TextField addHoursText;

    @FXML
    private Button addMovieBtn;

    @FXML
    private Button addTimeBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private TableView<?> complaintTable;

    @FXML
    private ComboBox<?> editComboBox;

    @FXML
    private DatePicker editNewDateText;

    @FXML
    private TextField editNewHoursText;

    @FXML
    private Button editPricesBtn;

    @FXML
    private Button editScreenigBtn;

    @FXML
    private Button editTimeBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private ComboBox<?> removeComboBox;

    @FXML
    private Button removeMovieBtn;

    @FXML
    private Button removeTimeBtn;

    public EditScreeningPage(List<Screening> screenings) {
    }

    @FXML
    void addTime(ActionEvent event) {

    }

    @FXML
    void editTime(ActionEvent event) {

    }

    @FXML
    void removeTime(ActionEvent event) {

    }


    @FXML
    private void switchToAddMoviePage() throws IOException {
        App.switchScreen("AddMoviePage");
    }

    @FXML
    private void switchToRemoveMoviePage() throws IOException {
        App.switchScreen("RemoveMoviePage");
    }

    @FXML
    private void switchToEditPricesPage() throws IOException {
        App.switchScreen("EditPricesPage");
    }

    @FXML
    private void switchToEditScreeningPage() throws IOException {
        App.switchScreen("EditScreeningPage");
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
    private void switchToContentManagerPage() throws IOException {
        App.switchScreen("ContentManagerPage");
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
