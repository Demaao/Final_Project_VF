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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditScreeningPage {
    @FXML private DatePicker addDateText;
    @FXML private TextField addHoursText;
    @FXML private Button addTimeBtn;
    @FXML private Button removeTimeBtn;
    @FXML private TableView<Movie> complaintTable;
    @FXML private TableColumn<Movie, String> nameEngColumn;
    @FXML private TableColumn<Movie, String> TypeColumn;
    @FXML private TableColumn<Movie, String> ScreeningTimes;
    @FXML private ComboBox<String> chooseMovieBox;
    @FXML private ComboBox<String> chooseCinemaBox;
    @FXML private ComboBox<String> removeComboBox;
    @FXML private ComboBox<String> editComboBox;
    @FXML private TextField editNewHoursText;
    @FXML private DatePicker editNewDateText;
    @FXML private Button editTimeBtn;


    private List<Movie> allMovies;
    private String savedMovieSelection;
    private String savedCinemaSelection;


    @FXML public void initialize() {
        EventBus.getDefault().register(this);
        requestMoviesFromServer();
        setupComboBoxes();
    }

    private void setupComboBoxes() {

        chooseMovieBox.setOnAction(event -> {
            updateCinemaOptions();
            updateTableBasedOnSelection();
            updateRemoveComboBox();
            resetDateTimeInputs();
            resetDateTimeInputs();
        });


        chooseCinemaBox.setOnAction(event -> {
            updateTableBasedOnCinemaSelection();
            updateEditComboBox();
        });
    }

    private void resetDateTimeInputs() {
        addDateText.setValue(null);
        addHoursText.clear();
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
        updateRemoveComboBox();
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
        saveSelections();


        String cinemaName = chooseCinemaBox.getValue();

        try {
            Movie selectedMovie = getSelectedMovie();
            if (selectedMovie == null) {
                showAlert("Please select a movie.");
                return;
            }


            LocalDateTime dateTime;
            try {
                dateTime = LocalDateTime.of(addDateText.getValue(), LocalTime.parse(addHoursText.getText()));
            } catch (DateTimeParseException e) {
                showAlert("Invalid date or time format. Please use the correct format.");
                return;
            }

            Branch selectedBranch = cinemaName != null ? getSelectedBranch(selectedMovie, cinemaName) : null;


            boolean screeningExists = selectedMovie.getScreenings().stream()
                    .anyMatch(screening -> screening.getScreeningTime().equals(dateTime) && screening.getBranch().equals(selectedBranch));

            if (screeningExists) {
                showAlert("This screening time already exists. Choose a different time.");
                return;
            }

            if(!(selectedMovie instanceof HomeMovie) && cinemaName == null){
                showAlert("Please choose a cinema.");
                return;
            }


            ScreeningData data = new ScreeningData(selectedMovie.getId(), selectedBranch != null ? selectedBranch.getId() : null, dateTime);
            NewMessage addMessage = new NewMessage(data, "addScreening");


            SimpleClient.getClient().sendToServer(addMessage);


            selectedMovie.addScreening(dateTime, selectedBranch);
            updateTable(selectedMovie, cinemaName);
            updateRemoveComboBox();

        } catch (Exception e) {
            showAlert("An unexpected error occurred. Please check your inputs and try again.");
        } finally {
            restoreSelections();
        }
    }

    private Branch getSelectedBranch(Movie movie, String branchName) {
        if (movie != null && branchName != null) {

            return movie.getBranches().stream()
                    .filter(branch -> branch.getName().equals(branchName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
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
        saveSelections();
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

            if(!(selectedMovie instanceof HomeMovie) && cinemaName == null){
                showAlert("Please choose a cinema.");
                return;
            }


            if(selectedMovie instanceof HomeMovie){
                ScreeningData data = new ScreeningData(selectedMovie.getId(), null, selectedTime);
                NewMessage removeMessage = new NewMessage(data, "removeScreening");
                SimpleClient.getClient().sendToServer(removeMessage);
            } else {
                ScreeningData data = new ScreeningData(selectedMovie.getId(), selectedBranch != null ? selectedBranch.getId() : null, selectedTime);
                NewMessage removeMessage = new NewMessage(data, "removeScreening");
                SimpleClient.getClient().sendToServer(removeMessage);
            }

            selectedMovie.getScreenings().removeIf(screening ->
                    screening.getScreeningTime().equals(selectedTime) && (screening.getBranch() == selectedBranch));
            updateTable(selectedMovie, cinemaName);

            updateRemoveComboBox();
            removeComboBox.setValue(null);

        } catch (Exception e) {
            showAlert("Invalid time format or selection.");
        } finally {
            restoreSelections();
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


    private void resetEditFields() {
        editNewDateText.setValue(null);
        editNewHoursText.clear();
        editComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    void editTime(ActionEvent event) {
        saveSelections();

        String cinemaName = chooseCinemaBox.getValue();

        try {
            Movie selectedMovie = getSelectedMovie();
            if (selectedMovie == null) {
                showAlert("Please select a movie to edit its screening time.");
                return;
            }


            LocalDateTime newDateTime;
            try {
                newDateTime = LocalDateTime.of(editNewDateText.getValue(), LocalTime.parse(editNewHoursText.getText()));
            } catch (DateTimeParseException e) {
                showAlert("Invalid new date or time format. Please use the correct format.");
                return;
            }


            String selectedTimeStr = editComboBox.getValue();
            LocalDateTime selectedTime = LocalDateTime.parse(selectedTimeStr);


            boolean screeningExists = selectedMovie.getScreenings().stream()
                    .anyMatch(screening -> screening.getScreeningTime().equals(newDateTime));

            if (screeningExists) {
                showAlert("This new screening time already exists. Choose a different time.");
                return;
            }

            Branch selectedBranch = cinemaName != null ? getSelectedBranch(selectedMovie, cinemaName) : null;


            Screening screeningToUpdate = selectedMovie.getScreenings().stream()
                    .filter(s -> s.getScreeningTime().equals(selectedTime) && s.getBranch().equals(selectedBranch))
                    .findFirst().orElse(null);

            if (screeningToUpdate == null) {
                showAlert("Selected screening time does not exist. Please refresh and try again.");
                return;
            }


            screeningToUpdate.setScreeningTime(newDateTime);


            ScreeningData updateData = new ScreeningData(selectedMovie.getId(), selectedBranch != null ? selectedBranch.getId() : null, newDateTime);
            NewMessage updateMessage = new NewMessage(updateData, "editScreening");
            SimpleClient.getClient().sendToServer(updateMessage);


            updateTable(selectedMovie, cinemaName);
            updateRemoveComboBox();
            resetEditFields();

            showAlert("Screening time updated successfully.");

        } catch (Exception e) {
            showAlert("An unexpected error occurred while editing the screening time. Please check your inputs and try again.");
        } finally {
            restoreSelections();
        }
    }



    private void updateEditComboBox() {
        Movie selectedMovie = getSelectedMovie();
        String cinemaName = chooseCinemaBox.isVisible() ? chooseCinemaBox.getValue() : null;
        if (selectedMovie != null) {
            List<Screening> screenings = new ArrayList<>(selectedMovie.getScreenings());
            if (cinemaName != null) {
                screenings = screenings.stream()
                        .filter(s -> s.getBranch() != null && s.getBranch().getName().equals(cinemaName))
                        .collect(Collectors.toList());
            }
            editComboBox.getItems().clear();
            editComboBox.getItems().addAll(screenings.stream()
                    .map(screening -> screening.getScreeningTime().toString())
                    .collect(Collectors.toList()));
        }
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
        try {
            NewMessage message = new NewMessage("logOut", LoginPage.employee1);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onUpdateHomeMoviesEvent(UpdateHomeMoviesEvent event) {}

    @Subscribe
    public void onUpdateScreeningEvent(UpdateScreeningTimesEvent event) {}
}