package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.bytebuddy.asm.Advice;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MovieDetailsPage {

    public static Movie selectedMovie;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label movieTitleLabel;

    @FXML
    private ImageView moviePosterImageView;

    @FXML
    private TextArea movieDescriptionTextArea;

    @FXML
    private Label directorLabel;

    @FXML
    private Label actorsLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label lengthLabel;

    @FXML
    private Label hebtitle;

    @FXML
    private Label engtitle;

    @FXML
    private ComboBox<String> cinemaComboBox;

    @FXML
    private DatePicker chooseDatePicker;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private Label bookNowLabel;
    @FXML
    private Button purchaseCardBtn;


    public static void setSelectedMovie(Movie movie) {
        selectedMovie = movie;
    }

    @FXML
    public void initialize() {
        if (selectedMovie != null) {
            EventBus.getDefault().register(this);

            movieTitleLabel.setText(selectedMovie.getHebtitle());
            moviePosterImageView.setImage(new Image(new ByteArrayInputStream(selectedMovie.getImageData())));
            movieDescriptionTextArea.setText(selectedMovie.getDescription());
            directorLabel.setText("Director : " + selectedMovie.getDirector());
            actorsLabel.setText("Actors : " + selectedMovie.getMainActors());
            yearLabel.setText("Year : " + selectedMovie.getYear());
            genreLabel.setText("Genre : " + selectedMovie.getGenre());
            lengthLabel.setText("Length : " + selectedMovie.getLength());
            hebtitle.setText(selectedMovie.getHebtitle());
            engtitle.setText(selectedMovie.getEngtitle());

            requestScreeningTimesFromServer(); // ××§×©× ××©×¨×ª ××§×× ××ª ××× × ×××§×¨× ×

            chooseDatePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (date.isBefore(LocalDate.now())) {
                        setDisable(true);
                    }
                }
            });

            if (selectedMovie instanceof HomeMovie) {
                cinemaComboBox.setVisible(false);
                chooseDatePicker.setVisible(true);
                timeComboBox.setVisible(true);

                chooseDatePicker.setLayoutX(230.0);
                timeComboBox.setLayoutX(430.0);

                //×× ×××¨× × ×¡×¨× ×××ª ×× ×× ×× × ×¢×××¨×× ××¢××× ××ª ××ª××¨×××× ×××©×¢××ª ×× ××¡×¨×× ××××ª ××× ××ª× ×§×× ××¢
                chooseDatePicker.setOnAction(event -> updateAvailableTimes(selectedMovie.getScreenings()));

            }
            else if(selectedMovie instanceof SoonMovie) {
                cinemaComboBox.setVisible(false);
                chooseDatePicker.setVisible(false);
                timeComboBox.setVisible(false);
                bookNowLabel.setVisible(false);
                lengthLabel.setVisible(false);
            }
            else {
                cinemaComboBox.setVisible(true);
                chooseDatePicker.setVisible(true);
                timeComboBox.setVisible(true);

                cinemaComboBox.setLayoutX(230.0);
                chooseDatePicker.setLayoutX(430.0);
                timeComboBox.setLayoutX(630.0);

                //×× ×××¨× × ×¡×¨× ×©×××¦× ××××ª ×§×× ××¢ ×× ×× ×× × ×¢×××¨×× ××¢××× ××××§×¡ ××××× ×¡× ××¤×× ××× × ××¦××××××ª×× ××¢××× ×× ××ª ××ª×××¨××× ×××©×¢××ª
                cinemaComboBox.setOnAction(event -> updateAvailableDays(null));
                chooseDatePicker.setOnAction(event -> updateAvailableTimes(null));
            }
        }
    }

    private void requestScreeningTimesFromServer() {
        try {
            NewMessage message = new NewMessage("screeningTimesRequest");
            message.setMovie(selectedMovie);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onUpdateScreeningTimes(UpdateScreeningTimesEvent event) {
        List<Screening> screenings = event.getScreenings();
        updateUIWithScreeningTimes(screenings);
        updateAvailableDays(screenings); // ×¨×¢× ×× ××××× ××××× ××
        updateAvailableTimes(screenings); // ×¨×¢× ×× ××©×¢××ª ××××× ××ª
    }

    private void updateUIWithScreeningTimes(List<Screening> screenings) {
        cinemaComboBox.getItems().clear();
        timeComboBox.getItems().clear();
        chooseDatePicker.setValue(null);

        if (selectedMovie instanceof HomeMovie) {
            updateAvailableDays(screenings);  //  ×× ×××××¨ ××¡×¨× ×××ª×, ××× ×¦××¨× ××¢××× ××ª  ××ª× ××§××× ××¢ × ×¢×××¨ ××¢××× ××ª  ××¢×××× ×××××
        } else {
            // ×§×××ª ×¨×©××× ×©× ××ª× ×§××× ××¢ ××ª×× ×¨×©×××ª ×××§×¨× ××ª
            Set<String> availableCinemas = screenings.stream()
                    .filter(screening -> screening.getBranch() != null)  // ××××× ×©-Branch ××× × null
                    .map(screening -> screening.getBranch().getName())
                    .collect(Collectors.toSet());

            // ×××¡×¤×ª ××ª× ××§××× ××¢ ×-ComboBox
            cinemaComboBox.getItems().addAll(availableCinemas);

            cinemaComboBox.setOnAction(event -> updateAvailableDays(screenings));
        }
    }


    private void updateAvailableDays(List<Screening> screenings) {
        timeComboBox.getItems().clear(); ///////////////////////////////////////////////
        chooseDatePicker.setValue(null); ///////////////////////////////////////////////

        if (screenings == null || screenings.isEmpty()) {
            screenings = selectedMovie.getScreenings();
        }

        if (selectedMovie instanceof HomeMovie) {
            // ×¢×××× ××××× ××× ××© ××§×¨× ××ª ××¡×¨×× ××××ª
            Set<LocalDate> availableDays = screenings.stream()
                    .filter(screening -> screening.getBranch() == null)
                    .map(screening -> screening.getScreeningTime().toLocalDate())
                    .collect(Collectors.toSet());

            chooseDatePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (!availableDays.contains(date) || date.isBefore(LocalDate.of(2024, 9, 24))) {
                        setDisable(true);
                    }
                }
            });

        } else {
            String selectedCinema = cinemaComboBox.getValue();
            if (selectedCinema != null) {
                Set<LocalDate> availableDays = screenings.stream()
                        .filter(screening -> screening.getBranch() != null)
                        .filter(screening -> screening.getBranch().getName().equals(selectedCinema))
                        .map(screening -> screening.getScreeningTime().toLocalDate())
                        .collect(Collectors.toSet());

                chooseDatePicker.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (!availableDays.contains(date) || date.isBefore(LocalDate.of(2024, 9, 24))) {
                            setDisable(true);
                        }
                    }
                });
            }
        }

        // ×¢××××× ××× × ×××§×¨× × ×¢× ×¤× ×××× ×©× ×××¨
        List<Screening> finalScreenings = screenings;
        chooseDatePicker.setOnAction(event -> updateAvailableTimes(finalScreenings));
    }

    private void updateAvailableTimes(List<Screening> screenings) {
        timeComboBox.getItems().clear();
        timeComboBox.setPromptText("Select Time");

        LocalDate selectedDay = chooseDatePicker.getValue();

        if (screenings == null || screenings.isEmpty()) {
            screenings = selectedMovie.getScreenings();
        }

        if (selectedMovie instanceof HomeMovie) {
            // Handle HomeMovie screenings
            if (selectedDay != null) {
                List<LocalTime> availableTimes = screenings.stream()
                        .filter(screening -> screening.getBranch() == null) // Filter for home movies
                        .filter(screening -> screening.getScreeningTime().toLocalDate().equals(selectedDay))
                        .map(screening -> screening.getScreeningTime().toLocalTime())
                        .collect(Collectors.toList());

                timeComboBox.getItems().addAll(
                        availableTimes.stream().map(LocalTime::toString).collect(Collectors.toList())
                );
            }
        } else {
            // Handle Cinema screenings
            String selectedCinema = cinemaComboBox.getValue();
            if (selectedCinema != null && selectedDay != null) {
                List<LocalTime> availableTimes = screenings.stream()
                        .filter(screening -> screening.getBranch() != null)
                        .filter(screening -> screening.getBranch().getName().equals(selectedCinema))
                        .filter(screening -> screening.getScreeningTime().toLocalDate().equals(selectedDay))
                        .map(screening -> screening.getScreeningTime().toLocalTime())
                        .collect(Collectors.toList());

                timeComboBox.getItems().addAll(
                        availableTimes.stream().map(LocalTime::toString).collect(Collectors.toList())
                );
            }
        }
    }

    static int movieDetailsPage;

    @FXML
    private void switchToHomePage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("HostPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("CardsPage");
    }

    @FXML
    private void switchToPurchaseProductsPage() throws IOException {

        if (selectedMovie instanceof HomeMovie) {
            //System.out.println("Navigating to PurchaseLink");
            App.switchScreen("PurchaseLink");
        } else {
            // System.out.println("Navigating to PurchaseProductsPage");
            App.switchScreen("PurchaseProductsPage");
        }
        //App.switchScreen("PurchaseProductsPage");
        //System.out.println("Selected movie type: " + selectedMovie.getClass().getSimpleName());

       /* if (selectedMovie instanceof HomeMovie){
            App.switchScreen("PurchaseLink");
            System.out.println("PurchaseLink");

        }
        else {
        App.switchScreen("PurchaseProductsPage");}}


}
}
        */
    }}