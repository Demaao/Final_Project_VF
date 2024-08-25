package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.HomeMovie;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.SoonMovie;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoviesPage {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label soon;
    @FXML
    private Label NowInCinema;
    @FXML
    private Label WatchFromHome;
    @FXML
    private ImageView cinemaImageView1;
    @FXML
    private ImageView cinemaImageView2;
    @FXML
    private ImageView cinemaImageView3;
    @FXML
    private ImageView cinemaImageView4;
    @FXML
    private Label cinemaLabel1;
    @FXML
    private Label cinemaLabel2;
    @FXML
    private Label cinemaLabel3;
    @FXML
    private Label cinemaLabel4;
    @FXML
    private ImageView soonImageView1;
    @FXML
    private ImageView soonImageView2;
    @FXML
    private ImageView soonImageView3;

    @FXML
    private Label soonLabel1;
    @FXML
    private Label soonLabel2;
    @FXML
    private Label soonLabel3;

    @FXML
    private ImageView homeImageView1;
    @FXML
    private ImageView homeImageView2;
    @FXML
    private ImageView homeImageView3;
    @FXML
    private ImageView homeImageView4;

    @FXML
    private Label homeLabel1;
    @FXML
    private Label homeLabel2;
    @FXML
    private Label homeLabel3;
    @FXML
    private Label homeLabel4;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
    @FXML
    private Button HomePageB;
    @FXML
    private Button homeLeftButton;
    @FXML
    private Button homeRightButton;
    @FXML
    private ComboBox<String> searchByNameBox;
    @FXML
    private ComboBox<String> searchByGenreBox;
    @FXML
    private ComboBox<String> searchByCinemaBox;
    @FXML
    private DatePicker searchByDatePicker;


    private List<Movie> movies;
    private List<SoonMovie> soonMovies;
    private List<HomeMovie> homeMovies;

    private List<Movie> filteredCinemaMovies;
    private List<HomeMovie> filteredHomeMovies = new ArrayList<>(); ////////////////////////////////////////////

    private int cinemaCurrentIndex = 0;
    private int homeCurrentIndex = 0;

    private int filteredCinemaCurrentIndex = 0;
    private int filteredHomeCurrentIndex = 0;

    public void initialize() {
        EventBus.getDefault().register(this);
        requestMoviesFromServer();
        requestSoonMoviesFromServer();
        requestHomeMoviesFromServer();

        // הוספת אפשרויות לתיבת הז'אנרים
        searchByGenreBox.getItems().addAll("Action", "Adventure", "Comedy", "Drama", "Documentary");


        searchByCinemaBox.getItems().addAll(" Haifa Cinema", "Tel Aviv Cinema", "Eilat Cinema", "Karmiel Cinema", "Jerusalem Cinema");
        searchByDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);  // If the date is before today it will not be available for selection
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });

        searchByNameBox.setOnAction(event -> {        // מאזין לבחירת שם סרט
            String selectedMovieName = searchByNameBox.getValue();
            if (selectedMovieName != null) {
                Movie selectedMovie = findMovieByName(selectedMovieName);
                if (selectedMovie != null) {
                    openMovieDetailsPage(selectedMovie);
                }
            }
        });
        searchByGenreBox.setOnAction(event -> {       // מאזין לבחירת ז'אנר
            String selectedGenre = searchByGenreBox.getValue();

            filterMoviesByGenre(selectedGenre);       //  קריאה לפונקציה לסינון הסרטים לפי הסוג

        });
        searchByCinemaBox.setOnAction(event -> {     //מאיזין לבחירת בית קלנוע
            String selectedCinema = searchByCinemaBox.getValue();
            filterMoviesByCinema(selectedCinema);    // קריאה לפונקציה לסינון  הסרטים לפי בית הקלנוע
        });


        cinemaImageView1.setOnMouseClicked(event -> openMovieDetailsPage(movies.get(cinemaCurrentIndex)));
        cinemaLabel1.setOnMouseClicked(event -> openMovieDetailsPage(movies.get(cinemaCurrentIndex)));


        cinemaImageView2.setOnMouseClicked(event -> openMovieDetailsPage(movies.get(cinemaCurrentIndex + 1)));
        cinemaLabel2.setOnMouseClicked(event -> openMovieDetailsPage(movies.get(cinemaCurrentIndex + 1)));

        cinemaImageView3.setOnMouseClicked(event -> openMovieDetailsPage(movies.get(cinemaCurrentIndex + 2)));
        cinemaLabel3.setOnMouseClicked(event -> openMovieDetailsPage(movies.get(cinemaCurrentIndex + 2)));

        cinemaImageView4.setOnMouseClicked(event -> openMovieDetailsPage(movies.get(cinemaCurrentIndex + 3)));
        cinemaLabel4.setOnMouseClicked(event -> openMovieDetailsPage(movies.get(cinemaCurrentIndex + 3)));

        homeImageView1.setOnMouseClicked(event -> openMovieDetailsPage(homeMovies.get(homeCurrentIndex)));
        homeLabel1.setOnMouseClicked(event -> openMovieDetailsPage(homeMovies.get(homeCurrentIndex)));

        homeImageView2.setOnMouseClicked(event -> openMovieDetailsPage(homeMovies.get(homeCurrentIndex + 1)));
        homeLabel2.setOnMouseClicked(event -> openMovieDetailsPage(homeMovies.get(homeCurrentIndex + 1)));

        homeImageView3.setOnMouseClicked(event -> openMovieDetailsPage(homeMovies.get(homeCurrentIndex + 2)));
        homeLabel3.setOnMouseClicked(event -> openMovieDetailsPage(homeMovies.get(homeCurrentIndex + 2)));

        homeImageView4.setOnMouseClicked(event -> openMovieDetailsPage(homeMovies.get(homeCurrentIndex + 3)));
        homeLabel4.setOnMouseClicked(event -> openMovieDetailsPage(homeMovies.get(homeCurrentIndex + 3)));
    }

    @Subscribe
    public void onUpdateMoviesEvent(UpdateMoviesEvent event) {
        Platform.runLater(() -> updateMovies(event.getMovies()));
    }

    @Subscribe
    public void onUpdateSoonMoviesEvent(UpdateSoonMoviesEvent event) {
        Platform.runLater(() -> {
            this.soonMovies = event.getSoonMovies();
            updateSoonMovies();
        });
    }

    @Subscribe
    public void onUpdateHomeMoviesEvent(UpdateHomeMoviesEvent event) {
        Platform.runLater(() -> {
            this.homeMovies = event.getHomeMovies();
            updateHomeMovies();
        });
    }

    private void requestMoviesFromServer() {
        try {
            NewMessage message = new NewMessage("moviesList");
            SimpleClient.getClient().sendToServer(message);
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

    private void requestHomeMoviesFromServer() {
        try {
            NewMessage message = new NewMessage("homeMoviesList");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateHomeMovies() {
        if (homeMovies != null && homeMovies.size() >= 4) {
            homeImageView1.setImage(new Image(new ByteArrayInputStream(homeMovies.get(homeCurrentIndex).getImageData())));
            homeLabel1.setText(homeMovies.get(homeCurrentIndex).getHebtitle());

            homeImageView2.setImage(new Image(new ByteArrayInputStream(homeMovies.get(homeCurrentIndex + 1).getImageData())));
            homeLabel2.setText(homeMovies.get(homeCurrentIndex + 1).getHebtitle());

            homeImageView3.setImage(new Image(new ByteArrayInputStream(homeMovies.get(homeCurrentIndex + 2).getImageData())));
            homeLabel3.setText(homeMovies.get(homeCurrentIndex + 2).getHebtitle());

            homeImageView4.setImage(new Image(new ByteArrayInputStream(homeMovies.get(homeCurrentIndex + 3).getImageData())));
            homeLabel4.setText(homeMovies.get(homeCurrentIndex + 3).getHebtitle());
        }
    }

    private void updateImages() {
        if (movies != null && movies.size() >= 10) {
            cinemaImageView1.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex).getImageData())));
            cinemaLabel1.setText(movies.get(cinemaCurrentIndex).getHebtitle());

            cinemaImageView2.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex + 1).getImageData())));
            cinemaLabel2.setText(movies.get(cinemaCurrentIndex + 1).getHebtitle());

            cinemaImageView3.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex + 2).getImageData())));
            cinemaLabel3.setText(movies.get(cinemaCurrentIndex + 2).getHebtitle());

            cinemaImageView4.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex + 3).getImageData())));
            cinemaLabel4.setText(movies.get(cinemaCurrentIndex + 3).getHebtitle());
        }
    }

    private void updateMovieView(ImageView imageView, Label label, Movie movie) {
        imageView.setImage(new Image(new ByteArrayInputStream(movie.getImageData())));
        label.setText(movie.getHebtitle());
    }

    public void updateMovies(List<Movie> movies) {
        this.movies = movies;
        updateImages();
        populateSearchByNameBox(); //to add the movies name to the search box
    }
    @FXML
    private void handleLeftButton() {
        if (filteredCinemaMovies != null && filteredCinemaMovies.size() > 0) {
            if (filteredCinemaCurrentIndex > 0) {
                filteredCinemaCurrentIndex--;
                updateFilteredMovies(filteredCinemaMovies, filteredHomeMovies);
            }
        } else {
            if (cinemaCurrentIndex > 0) {
                cinemaCurrentIndex--;
                updateImages();
            }
        }
    }

    @FXML
    private void handleRightButton() {
        if (filteredCinemaMovies != null && filteredCinemaMovies.size() > 0) {
            if (filteredCinemaCurrentIndex < filteredCinemaMovies.size() - 4) {
                filteredCinemaCurrentIndex++;
                updateFilteredMovies(filteredCinemaMovies, filteredHomeMovies);
            }
        } else {
            if (movies != null && cinemaCurrentIndex < movies.size() - 4) {
                cinemaCurrentIndex++;
                updateImages();
            }
        }
    }

    @FXML
    private void handleHomeLeftButton() {
        if (filteredHomeMovies != null && filteredHomeMovies.size() > 0) {
            if (filteredHomeCurrentIndex > 0) {
                filteredHomeCurrentIndex--;
                updateFilteredMovies(filteredCinemaMovies, filteredHomeMovies);
            }
        } else {
            if (homeCurrentIndex > 0) {
                homeCurrentIndex--;
                updateHomeMovies();
            }
        }
    }

    @FXML
    private void handleHomeRightButton() {
        if (filteredHomeMovies != null && filteredHomeMovies.size() > 0) {
            if (filteredHomeCurrentIndex < filteredHomeMovies.size() - 4) {
                filteredHomeCurrentIndex++;
                updateFilteredMovies(filteredCinemaMovies, filteredHomeMovies);
            }
        } else {
            if (homeMovies != null && homeCurrentIndex < homeMovies.size() - 4) {
                homeCurrentIndex++;
                updateHomeMovies();
            }
        }
    }

    private void updateSoonMovies() {
        if (soonMovies != null && soonMovies.size() == 3) {
            soonImageView1.setImage(new Image(new ByteArrayInputStream(soonMovies.get(0).getImageData())));
            soonLabel1.setText(soonMovies.get(0).getHebtitle());

            soonImageView2.setImage(new Image(new ByteArrayInputStream(soonMovies.get(1).getImageData())));
            soonLabel2.setText(soonMovies.get(1).getHebtitle());

            soonImageView3.setImage(new Image(new ByteArrayInputStream(soonMovies.get(2).getImageData())));
            soonLabel3.setText(soonMovies.get(2).getHebtitle());
        }
    }

    private Movie findMovieByName(String movieName) {   //if we want to search by name
        for (Movie movie : movies) {
            if (movie.getEngtitle().equalsIgnoreCase(movieName)) {
                return movie;
            }
        }
        return null;
    }


    // we call this method after the movies updated to add options to  searchByNameBox
    private void populateSearchByNameBox() {
        if (movies != null) {
            searchByNameBox.getItems().clear();  // Clear existing items
            for (Movie movie : movies) {
                searchByNameBox.getItems().add(movie.getEngtitle());
            }
        }
    }

    private void filterMoviesByGenre(String selectedGenre) {
        if (movies != null) {
            // סינון סרטים המוקרנים בקולנוע לפי הז'אנר ושאינם מסוג
            filteredCinemaMovies = movies.stream()
                    .filter(movie -> movie.getGenre().equalsIgnoreCase(selectedGenre))
                    .filter(movie -> !(movie instanceof HomeMovie))
                    .collect(Collectors.toList());
            NowInCinema.setText( selectedGenre+ " Movies in the Cinema" );  //change the text according to the search
        }

        if (homeMovies != null) {

            filteredHomeMovies = homeMovies.stream()
                    .filter(movie -> movie.getGenre().equalsIgnoreCase(selectedGenre))
                    .collect(Collectors.toList());
            WatchFromHome.setText( selectedGenre+ " movies to watch from home" );
        }

        // אפס את האינדקסים אחרי הסינון כדי להראות את הסרטים הראשונים
        filteredCinemaCurrentIndex = 0;
        filteredHomeCurrentIndex = 0;


        updateFilteredMovies(filteredCinemaMovies, filteredHomeMovies);
    }

    private void updateFilteredMovies(List<Movie> filteredCinemaMovies, List<HomeMovie> filteredHomeMovies) {

        clearCinemaDisplay();
        clearHomeDisplay();

        if (!filteredCinemaMovies.isEmpty()) {
            for (int i = 0; i < Math.min(filteredCinemaMovies.size(), 4); i++) {
                updateMovieView(getCinemaImageView(i), getCinemaLabel(i), filteredCinemaMovies.get(i));
            }
        }


        if (!filteredHomeMovies.isEmpty()) {
            for (int i = 0; i < Math.min(filteredHomeMovies.size(), 4); i++) {
                updateMovieView(getHomeImageView(i), getHomeLabel(i), filteredHomeMovies.get(i));
            }
        }
    }

    private ImageView getCinemaImageView(int index) {
        switch (index) {
            case 0: return cinemaImageView1;
            case 1: return cinemaImageView2;
            case 2: return cinemaImageView3;
            case 3: return cinemaImageView4;
            default: return null;
        }
    }

    private Label getCinemaLabel(int index) {
        switch (index) {
            case 0: return cinemaLabel1;
            case 1: return cinemaLabel2;
            case 2: return cinemaLabel3;
            case 3: return cinemaLabel4;
            default: return null;
        }
    }

    private ImageView getHomeImageView(int index) {
        switch (index) {
            case 0: return homeImageView1;
            case 1: return homeImageView2;
            case 2: return homeImageView3;
            case 3: return homeImageView4;
            default: return null;
        }
    }

    private Label getHomeLabel(int index) {
        switch (index) {
            case 0: return homeLabel1;
            case 1: return homeLabel2;
            case 2: return homeLabel3;
            case 3: return homeLabel4;
            default: return null;
        }
    }


    private void clearCinemaDisplay() {
        cinemaImageView1.setImage(null);
        cinemaLabel1.setText("");
        cinemaImageView2.setImage(null);
        cinemaLabel2.setText("");
        cinemaImageView3.setImage(null);
        cinemaLabel3.setText("");
        cinemaImageView4.setImage(null);
        cinemaLabel4.setText("");
    }

    private void clearHomeDisplay() {
        homeImageView1.setImage(null);
        homeLabel1.setText("");
        homeImageView2.setImage(null);
        homeLabel2.setText("");
        homeImageView3.setImage(null);
        homeLabel3.setText("");
        homeImageView4.setImage(null);
        homeLabel4.setText("");
    }

    private void filterMoviesByCinema(String selectedCinema) {
        if (movies != null ) {
            //סינון סרטים המוקרנים בבית הקולנוע שנבחר
            filteredCinemaMovies = movies.stream()
                    .filter(movie -> movie.getBranches().stream()
                            .anyMatch(branch -> branch.getName().equalsIgnoreCase(selectedCinema)))
                    .collect(Collectors.toList());

            // איפוס אינדקס כדי להתחיל מהסרט הראשון
            cinemaCurrentIndex = 0;


            updateFilteredCinemaMovies();
        } else {
            //displayAllMovies(); // הצגת כל הסרטים כאשר בוחרים "ALL MOVIES"
        }
    }

    private void updateFilteredCinemaMovies() {
        clearCinemaDisplay(); // ניקוי התצוגה הקודמת

        if (!filteredCinemaMovies.isEmpty()) {
            for (int i = 0; i < Math.min(filteredCinemaMovies.size(), 4); i++) {
                updateMovieView(getCinemaImageView(i), getCinemaLabel(i), filteredCinemaMovies.get(i));
            }
        }

        NowInCinema.setText("Now in " + searchByCinemaBox.getValue());
    }

    private void openMovieDetailsPage(Movie movie) {
        MovieDetailsPage.setSelectedMovie(movie);  // שמור את הסרט שנבחר

            App.switchScreen("MovieDetailsPage");  // מעבר לדף
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
}
