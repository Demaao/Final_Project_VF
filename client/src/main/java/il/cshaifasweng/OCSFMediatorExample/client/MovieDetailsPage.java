package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.HomeMovie;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.Screening;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MovieDetailsPage {

    private List<Screening> selectedScreening;
    private static Movie selectedMovie;

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
    private Button SBtn;

    public static void setSelectedMovie(Movie movie) {
        selectedMovie = movie;
    }

    @FXML
    public void initialize() {
        System.out.println("this shit is being invoked");//TODO
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

            requestScreeningTimesFromServer(); // בקשה לשרת לקבל את זמני ההקרנה

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

                //אם בחרנו סרט בית אז אנחנו עוברים לעדכן את התאריכים והשעות כי לסרטי הבית אין בתי קלנוע
                chooseDatePicker.setOnAction(event -> updateAvailableTimes(selectedMovie.getScreenings()));
                System.out.println("screenings size1: " +  selectedMovie.getScreenings().size());

            } else {
                cinemaComboBox.setVisible(true);
                chooseDatePicker.setVisible(true);
                timeComboBox.setVisible(true);

                cinemaComboBox.setLayoutX(230.0);
                chooseDatePicker.setLayoutX(430.0);
                timeComboBox.setLayoutX(630.0);

                //אם בחרנו סרט שמוצג בבית קלנוע אז אנחנו עוברים לעדכן בבוקס באיזה סניפים הוא נמצאיובהתאם מעדכנים את התאירכים והשעות
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
    }

    @Subscribe
    public void onUpdateScreeningEvent(UpdateScreeningEvent event) {
        System.out.println("onUpdateScreeningEvent screenings size: " +  event.getScreenings().size());
        selectedScreening = event.getScreenings();

    }

    private void updateUIWithScreeningTimes(List<Screening> screenings) {
        cinemaComboBox.getItems().clear();

        if (selectedMovie instanceof HomeMovie) {
            updateAvailableDays(screenings);  //  אם מדובר בסרט ביתי, אין צורך לעדכן את  בתי הקולנוע נעבור לעדכן את  לעדכון הימים
        } else {
            // קבלת רשימה של בתי קולנוע מתוך רשימת ההקרנות
            Set<String> availableCinemas = screenings.stream()
                    .filter(screening -> screening.getBranch() != null)  // לוודא ש-Branch אינו null
                    .map(screening -> screening.getBranch().getName())
                    .collect(Collectors.toSet());

            // הוספת בתי הקולנוע ל-ComboBox
            cinemaComboBox.getItems().addAll(availableCinemas);

            cinemaComboBox.setOnAction(event -> updateAvailableDays(screenings));

        }
    }

    void setCelltoField() {
        System.out.println("my sister 1  me1");
        LocalDate localDate = chooseDatePicker.getValue();
        System.out.println("localDate: " + localDate);
        String time = timeComboBox.getValue();
        LocalTime time1 = LocalTime.parse(time);
        System.out.println("time1: " + time1);
        LocalDateTime screeningTime = localDate.atTime(time1.minusHours(3));
        System.out.println("screeningTime: " + screeningTime);
        requestScreeningFromServer();
//        try {
//            wait(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        System.out.println("selectedMovie " + selectedMovie.getEngtitle());
        System.out.println("my sister 1  me6");
//        System.out.println("onUpdateScreeningEvent screenings size: " +  selectedScreening.size());
//        selectedScreening.forEach(e -> {if(e.getScreeningTime().equals(screeningTime) && e.getBranch().getName().equals(cinemaComboBox.getValue())){
//            System.out.println("selected screening branch: " + e.getBranch().getName() + " screening time: " + e.getScreeningTime() + " hall: " + e.getHall());
//            ChooseSeating.setScreening(e);
//        }});
        System.out.println("screenings size: " +  selectedMovie.getScreenings().size());
        selectedMovie.getScreenings().forEach(e -> {if(e.getScreeningTime().equals(timeComboBox.getValue()) && e.getBranch().getName().equals(cinemaComboBox.getValue())){
            System.out.println("selected screening branch: " + e.getBranch().getName() + " screening time: " + e.getScreeningTime() + " hall: " + e.getHall().getId());
           // ChooseSeating.setScreening(e);
        }
        else {System.out.println("lolol");}
        });


        System.out.println("my sister 1  me7");
    }


    private void requestScreeningFromServer() {
        System.out.println("requestHallFromServer1 ");
        try {
            System.out.println("requestHallFromServer 2");
            NewMessage message = new NewMessage("screeningHallsRequest");
            message.setMovie(selectedMovie);
            System.out.println("requestHallFromServer 3");
            SimpleClient.getClient().sendToServer(message);
            System.out.println("requestHallFromServer 4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void updateAvailableDays(List<Screening> screenings) {
        System.out.println("updateAvailableDays called for movie: " + selectedMovie.getEngtitle());

        if (screenings == null || screenings.isEmpty()) {
            screenings = selectedMovie.getScreenings();
        }

        if (selectedMovie instanceof HomeMovie) {
            // עדכון הימים בהם יש הקרנות לסרטי הבית
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

        // עידכון זמני ההקרנה על פי היום שנבחר
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
    private void switchToChooseSeating() throws IOException, InterruptedException {
        System.out.println("my sister 1  me");
        setCelltoField();
        System.out.println("my sister 2  me");
        App.switchScreen("ChooseSeating");
    }



}