package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;

import java.io.IOException;

public class AddMoviePage {
    @FXML
    private Button addBtn;

    @FXML
    private Button addMovieBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private ComboBox<?> cinemaComboBox;

    @FXML
    private Label cinemaLabel;

    @FXML
    private TableView<?> complaintTable;

    @FXML
    private Button editPricesBtn;

    @FXML
    private Button editScreenigBtn;

    @FXML
    private TextField fullNameText1;

    @FXML
    private TextField fullNameText11;

    @FXML
    private TextField fullNameText111;

    @FXML
    private TextField fullNameText2;

    @FXML
    private TextField fullNameText21;

    @FXML
    private Button homePageBtn;

    @FXML
    private Label linkLabel;

    @FXML
    private TextField linkText;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button removeMovieBtn;

    @FXML
    private Spinner<String> screeningTypeSpinner;

    private ObservableList<String> ScreeningType =
            FXCollections.observableArrayList("In The Cinema", "Link");

    @FXML
    public void initialize() {
        SpinnerValueFactory<String> valueFactory2 =
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(ScreeningType);
        screeningTypeSpinner.setValueFactory(valueFactory2);
    }

    public void changeScreeningType() {
        if(screeningTypeSpinner.getValue() == "In The Cinema") {
            cinemaLabel.setVisible(true);
            cinemaComboBox.setVisible(true);
            linkLabel.setVisible(false);
            linkText.setVisible(false);
        }
        else if(screeningTypeSpinner.getValue() == "Link") {
            cinemaLabel.setVisible(false);
            cinemaComboBox.setVisible(false);
            linkLabel.setVisible(true);
            linkText.setVisible(true);
        }
    }

    @FXML
    void AddMovie(ActionEvent event) {

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


}
