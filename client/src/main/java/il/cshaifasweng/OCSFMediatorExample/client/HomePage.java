package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

    @FXML // fx:id="cardsBtn"
    private Button cardsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="changeHostBtn"
    private Button changeHostBtn; // Value injected by FXMLLoader

    @FXML // fx:id="chargebackBtn"
    private Button chargebackBtn; // Value injected by FXMLLoader

    @FXML // fx:id="complaintBtn"
    private Button complaintBtn; // Value injected by FXMLLoader

    @FXML // fx:id="homePageBtn"
    private Button homePageBtn; // Value injected by FXMLLoader

    @FXML // fx:id="loginBtn"
    private Button loginBtn; // Value injected by FXMLLoader

    @FXML // fx:id="moviesBtn"
    private Button moviesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="signUpBtn"
    private Button signUpBtn; // Value injected by FXMLLoader

    @FXML
    private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        App.switchScreen("LoginPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        App.switchScreen("CardsPage");
    }

    /*

    @FXML // fx:id="soonInCinemaList"
    private ListView<String> soonInCinemaList; // Value injected by FXMLLoader

    private ObservableList<String> movieList;

    static List<Movie> movieL2 = new ArrayList<Movie>();

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        movieList = FXCollections.observableArrayList();
        movieList.clear();
        for (Movie movie : movieL2) {
            movieList.add(movie.toString());
        }
        soonInCinemaList.setItems(movieList);
    }

    @Subscribe
    public void onMessage(MessageEvent event) {
        Platform.runLater(() -> {
            if(event.getMessage().equals("movies")) {
                List<Movie> movies = event.getMovieList();
                movieList.clear();
                for(Movie movie : movies) {
                    movieList.add(movie.toString());
                }
                soonInCinemaList.setItems(movieList);
            }
        });
    }

     */


}