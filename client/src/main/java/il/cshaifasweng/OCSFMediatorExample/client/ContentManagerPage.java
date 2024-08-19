package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ContentManagerPage {

    @FXML
    private Button addMovieBtn;

    @FXML
    private Button bigAddMovieBtn;

    @FXML
    private Button bigEditPricesBtn;

    @FXML
    private Button bigEditScreenigBtn;

    @FXML
    private Button bigRemoveMovieBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Label contentManagerNameLabel;

    @FXML
    private Button editPricesBtn;

    @FXML
    private Button editScreenigBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button removeMovieBtn;

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



}