package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditScreeningPage {
    @FXML private DatePicker addDateText;
    @FXML private TextField addHoursText;
    @FXML private Button addTimeBtn;
    @FXML private TableView<Movie> complaintTable;
    @FXML private TableColumn<Movie, String> nameEngColumn;
    @FXML private TableColumn<Movie, String> TypeColumn;
    @FXML private TableColumn<Movie, String> ScreeningTimes;
    @FXML private ComboBox<String> chooseMovieBox;
    @FXML private ComboBox<String> chooseCinemaBox;
    @FXML private ComboBox<String> removeComboBox;

    private List<Movie> allMovies;
    private String savedMovieSelection;
    private String savedCinemaSelection;


    @FXML public void initialize() {
        EventBus.getDefault().register(this);
        requestMoviesFromServer();
        setupComboBoxes();
    }

    private void setupComboBoxes() {
        // EventHandler לבחירת סרט, מעדכן גם את רשימת בתי הקולנוע ואת ה-ComboBox להסרת זמנים
        chooseMovieBox.setOnAction(event -> {
            updateCinemaOptions();
            updateTableBasedOnSelection();
            updateRemoveComboBox();  // עדכון ה-ComboBox להסרת זמנים בכל בחירת סרט
            resetDateTimeInputs();  // איפוס התאריך והשעה
        });

        // EventHandler לבחירת בית קולנוע, מעדכן את הטבלה על פי בחירת בית הקולנוע
        chooseCinemaBox.setOnAction(event -> {
            updateTableBasedOnCinemaSelection();
        });
    }


    private void resetDateTimeInputs() {
        addDateText.setValue(null);  // איפוס שדה התאריך
        addHoursText.clear();  // ניקוי שדה השעה
    }

    @Subscribe
    public void onUpdateMoviesEvent(UpdateMoviesEvent event) {
        saveSelections();
        Platform.runLater(() -> {
            allMovies = event.getMovies().stream().filter(movie -> !(movie instanceof SoonMovie)).collect(Collectors.toList());
            chooseMovieBox.getItems().clear();
            chooseMovieBox.getItems().addAll(allMovies.stream().map(Movie::getEngtitle).collect(Collectors.toList()));
            restoreSelections();
            updateRemoveComboBox();
        });
    }


    private void updateCinemaOptions() {
        Movie selectedMovie = getSelectedMovie();
        if (selectedMovie != null && !(selectedMovie instanceof HomeMovie)) {
            chooseCinemaBox.setVisible(true);
            chooseCinemaBox.getItems().setAll(selectedMovie.getBranches().stream().map(Branch::getName).collect(Collectors.toList()));
        } else {
            chooseCinemaBox.setVisible(false);
        }
    }


    private void updateTableBasedOnCinemaSelection() {
        updateTable(getSelectedMovie(), chooseCinemaBox.getValue());
        updateRemoveComboBox();  // עדכון ה-ComboBox להסרת זמנים
    }

    private void updateTable(Movie movie, String cinemaName) {
        Platform.runLater(() -> {
            complaintTable.getItems().clear();
            if (movie != null) {
                List<Screening> screenings = new ArrayList<>(movie.getScreenings());
                if (cinemaName != null) {
                    screenings = screenings.stream().filter(s -> s.getBranch() != null && s.getBranch().getName().equals(cinemaName)).collect(Collectors.toList());
                }
                if (!screenings.isEmpty()) {
                    final List<Screening> finalScreenings = screenings;
                    complaintTable.getItems().add(movie);
                    nameEngColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEngtitle()));
                    TypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(movie instanceof HomeMovie ? "HOME MOVIE" : "CINEMA MOVIE"));
                    ScreeningTimes.setCellValueFactory(cellData -> new SimpleStringProperty(finalScreenings.stream().map(s -> s.getScreeningTime().toString()).collect(Collectors.joining("\n"))));
                    complaintTable.refresh();
                }
            }
        });
    }


    private void updateTableBasedOnSelection() {
        Movie selectedMovie = getSelectedMovie();
        String cinemaName = chooseCinemaBox.isVisible() ? chooseCinemaBox.getValue() : null;
        updateTable(selectedMovie, cinemaName);
    }



    @FXML
    void addTime(ActionEvent event) {
        saveSelections(); // שמירה לפני הפעולה

        // קבלת ערך בית הקולנוע מה-ComboBox
        String cinemaName = chooseCinemaBox.getValue();

        try {
            Movie selectedMovie = getSelectedMovie();
            if (selectedMovie == null) {
                showAlert("No movie selected.");
                return;
            }

            LocalDateTime dateTime = LocalDateTime.of(addDateText.getValue(), LocalTime.parse(addHoursText.getText()));

            Branch selectedBranch = cinemaName != null ? getSelectedBranch(selectedMovie, cinemaName) : null;

            // בדיקה אם ההקרנה כבר קיימת
            boolean screeningExists = selectedMovie.getScreenings().stream()
                    .anyMatch(screening -> screening.getScreeningTime().equals(dateTime) && (screening.getBranch() == selectedBranch));

            if (screeningExists) {
                showAlert("This screening time already exists.");
                return;
            }

            if (selectedBranch != null || selectedMovie instanceof HomeMovie) {
                selectedMovie.addScreening(dateTime, selectedBranch);

                if (updateScreeningInDatabase(selectedMovie)) {
                    updateTable(selectedMovie, cinemaName);
                    updateRemoveComboBox();
                } else {
                    showAlert("Failed to update the database.");
                }
            } else {
                showAlert("Cinema not found or invalid.");
            }
        } catch (Exception e) {
            showAlert("Invalid date or time format.");
        } finally {
            restoreSelections(); // שחזור לאחר הפעולה
        }
    }



    private Branch getSelectedBranch(Movie movie, String branchName) {
        if (movie != null && branchName != null) {
            // חיפוש הסניף ברשימת הסניפים של הסרט הנבחר
            return movie.getBranches().stream()
                    .filter(branch -> branch.getName().equals(branchName))
                    .findFirst()
                    .orElse(null);
        }
        return null;  // מחזירים null אם הסרט או שם הסניף לא קיימים
    }

    private Movie getSelectedMovie() {
        String title = chooseMovieBox.getValue();
        return allMovies.stream().filter(m -> m.getEngtitle().equals(title)).findFirst().orElse(null);
    }
    private void saveSelections() {
        savedMovieSelection = chooseMovieBox.getValue();
        savedCinemaSelection = chooseCinemaBox.getValue();
    }

    private void restoreSelections() {
        chooseMovieBox.setValue(savedMovieSelection);
        if (savedCinemaSelection != null && chooseCinemaBox.getItems().contains(savedCinemaSelection)) {
            chooseCinemaBox.setValue(savedCinemaSelection);
        }
    }


    private boolean updateScreeningInDatabase(Movie movie) {
        try {
            NewMessage message = new NewMessage("updateScreening", movie);
            SimpleClient.getClient().sendToServer(message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
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


    @FXML
    void removeTime(ActionEvent event) {
        saveSelections();  // שמירה לפני הפעולה
        String cinemaName = chooseCinemaBox.getValue();
        try {
            Movie selectedMovie = getSelectedMovie();
            if (selectedMovie == null) {
                showAlert("No movie selected.");
                return;
            }
            String selectedTimeStr = removeComboBox.getValue();
            LocalDateTime selectedTime = LocalDateTime.parse(selectedTimeStr);
            Branch selectedBranch = cinemaName != null ? getSelectedBranch(selectedMovie, cinemaName) : null;

            ScreeningData data = new ScreeningData(selectedMovie.getId(), selectedBranch != null ? selectedBranch.getId() : null, selectedTime); // ID אינו בשימוש
            NewMessage removeMessage = new NewMessage(data, "removeScreening");
            SimpleClient.getClient().sendToServer(removeMessage);

            // לאחר השליחה, רענן את הטבלה מקומית
            selectedMovie.getScreenings().removeIf(screening ->
                    screening.getScreeningTime().equals(selectedTime) && (screening.getBranch() == selectedBranch));
            updateTable(selectedMovie, cinemaName);


            updateRemoveComboBox();
            removeComboBox.setValue(null); // איפוס  הבחירה לאחר המחיקה

        } catch (Exception e) {
            showAlert("Invalid time format or selection.");
        } finally {
            restoreSelections();  // שחזור
        }
    }


    private void updateRemoveComboBox() {
        Platform.runLater(() -> {
            Movie selectedMovie = getSelectedMovie();
            String cinemaName = chooseCinemaBox.isVisible() ? chooseCinemaBox.getValue() : null;
            if (selectedMovie != null) {
                List<Screening> screenings = new ArrayList<>(selectedMovie.getScreenings());
                if (cinemaName != null) {
                    screenings = screenings.stream()
                            .filter(s -> s.getBranch() != null && s.getBranch().getName().equals(cinemaName))
                            .collect(Collectors.toList());
                }
                removeComboBox.getItems().clear();
                removeComboBox.getItems().addAll(screenings.stream()
                        .map(screening -> screening.getScreeningTime().toString())
                        .collect(Collectors.toList()));
            }
        });
    }






    @FXML
    void editTime(ActionEvent event) {}




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
        try {
            NewMessage message = new NewMessage("logOut", LoginPage.employee1);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
