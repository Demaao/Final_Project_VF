package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.SoonMovie;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MoviesPage {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label soon;
    @FXML
    private Label NowInCinema;

    @FXML
    private ImageView cinemaImageView1;
    @FXML
    private ImageView cinemaImageView2;
    @FXML
    private ImageView cinemaImageView3;
    @FXML
    private ImageView cinemaImageView4;
    @FXML
    private ImageView soonImageView1;
    @FXML
    private ImageView soonImageView2;
    @FXML
    private ImageView soonImageView3;
    @FXML
    private ImageView soonImageView4;
    @FXML
    private Label cinemaLabel1;
    @FXML
    private Label cinemaLabel2;
    @FXML
    private Label cinemaLabel3;
    @FXML
    private Label cinemaLabel4;

    @FXML
    private Label soonLabel1;
    @FXML
    private Label soonLabel2;
    @FXML
    private Label soonLabel3;

    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;

    @FXML
    private Button HomePageB;

    @FXML
    private TextField searchByNameField;

    @FXML
    private ComboBox<String> searchByGenreBox;

    @FXML
    private ComboBox<String> searchByCinemaBox;

    @FXML
    private DatePicker searchByDatePicker;

    private List<Movie> movies;
    private List<SoonMovie> soonMovies;
    private int currentIndex = 1;

    public void initialize() {
        EventBus.getDefault().register(this);
        requestMoviesFromServer();
        requestSoonMoviesFromServer();
    }

    @Subscribe
    public void onUpdateMoviesEvent(UpdateMoviesEvent event) {
        Platform.runLater(() -> updateMovies(event.getMovies()));
        System.out.println("Received UpdateMoviesEvent with " + event.getMovies().size() + " movies.");
    }

    @Subscribe
    public void onUpdateSoonMoviesEvent(UpdateSoonMoviesEvent event) {
        Platform.runLater(() -> {
            this.soonMovies = event.getSoonMovies();
            updateSoonMoviesUI();
        });
    }
    private void requestMoviesFromServer() {
        try {
            NewMessage message = new NewMessage("moviesList");  // בקשה לקבלת רשימת הסרטים
            App.getClient().sendToServer(message);
            System.out.println("Request sent to server for movies list.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void requestSoonMoviesFromServer() {
        try {
            NewMessage message = new NewMessage("soonMoviesList");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateImages() {
        if (movies != null && movies.size() >= 4) {
            // טעינת התמונות מתוך ה-Byte Array
            cinemaImageView1.setImage(new Image(new ByteArrayInputStream(movies.get(currentIndex).getImageData())));
            cinemaImageView2.setImage(new Image(new ByteArrayInputStream(movies.get(currentIndex + 1).getImageData())));
            cinemaImageView3.setImage(new Image(new ByteArrayInputStream(movies.get(currentIndex + 2).getImageData())));
            cinemaImageView4.setImage(new Image(new ByteArrayInputStream(movies.get(currentIndex + 3).getImageData())));

            // הצגת הכותרות של הסרטים
            cinemaLabel1.setText(movies.get(currentIndex).getHebtitle());
            cinemaLabel2.setText(movies.get(currentIndex + 1).getHebtitle());
            cinemaLabel3.setText(movies.get(currentIndex + 2).getHebtitle());
            cinemaLabel4.setText(movies.get(currentIndex + 3).getHebtitle());
        }
    }

    public void updateMovies(List<Movie> movies) {
        this.movies = movies;
        updateImages();
    }


    @FXML
    private void handleLeftButton() {
        if (currentIndex > 0) {
            currentIndex--;
            updateImages();
        }
    }

    @FXML
    private void handleRightButton() {
        if (movies != null && currentIndex < movies.size() - 4) {
            currentIndex++;
            updateImages();
        }
    }

    private void updateSoonMoviesUI() {
        if (soonMovies != null && soonMovies.size() >= 3) {
            soonImageView1.setImage(new Image(new ByteArrayInputStream(soonMovies.get(0).getImageData())));
            soonLabel1.setText(soonMovies.get(1).getEngtitle() + "\n" + soonMovies.get(0).getReleaseDate());

            soonImageView2.setImage(new Image(new ByteArrayInputStream(soonMovies.get(1).getImageData())));
            soonLabel2.setText(soonMovies.get(2).getEngtitle() + "\n" + soonMovies.get(1).getReleaseDate());

            soonImageView3.setImage(new Image(new ByteArrayInputStream(soonMovies.get(2).getImageData())));
            soonLabel3.setText(soonMovies.get(2).getEngtitle() + "\n" + soonMovies.get(2).getReleaseDate());
        }
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
    private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
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

    @FXML
    private void handleSearch() {
        String name = searchByNameField.getText();
        String genre = searchByGenreBox.getValue();
        String cinema = searchByCinemaBox.getValue();
        LocalDate date = searchByDatePicker.getValue();
        // Implement search functionality here
    }

    public void handleHomeRightButton(ActionEvent actionEvent) {
    }

    public void handleHomeLeftButton(ActionEvent actionEvent) {

    }
}

