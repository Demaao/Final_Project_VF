package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceWorkerPage {
    public TableView<Complaint> complaintTable;
    public TableColumn complaintNOColumn;
    public TableColumn subjectColumn;
    public TableColumn idColumn;
    public TableColumn dateColumn;
    public TableColumn statusColumn;
    public TableColumn complaintColumn;
    private ObservableList<Complaint> complaintObservableList = FXCollections. observableArrayList();
    private List<Complaint> complaints = new ArrayList<>();

    @FXML
    private Label customerServiceWorkerNameLabel;

    @FXML
    private void switchToCustomerServiceWorkerPage() throws IOException {
        App.switchScreen("CustomerServiceWorkerPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    void switchToHostPage(ActionEvent event) {
        App.switchScreen("HostPage");
    }

    @FXML
    void switchToHandleComplaintPage(ActionEvent event) {
        Complaint complaint = complaintTable.getSelectionModel().getSelectedItem();
        HandleComplaintPage.setSelectedComplaint(complaint);
        App.switchScreen("HandleComplaintPage");
    }

    public void initialize() {
        EventBus.getDefault().register(this);
        customerServiceWorkerNameLabel.setText(LoginPage.employee1.getFullName());
        requestComplaintFromServer();
    }

    @Subscribe
    public void onUpdateComplaintsEvent(UpdateComplaintsEvent event) {
        Platform.runLater(() -> {
            complaints.clear();
            ObservableList<Complaint> items = complaintTable.getItems();
            items.clear();
            complaints = event.getComplaints();
            complaintObservableList.addAll(complaints);
            statusColumn.setCellFactory(new Callback<TableColumn<Complaint, Boolean>, TableCell<Complaint, Boolean>>() {
                @Override
                public TableCell<Complaint, Boolean> call(TableColumn<Complaint, Boolean> col) {
                    return new TableCell<Complaint, Boolean>() {
                        @Override
                        protected void updateItem(Boolean item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                                setText(null);
                            } else {
                                Complaint complaint = (Complaint) getTableRow().getItem();
                                if (!complaint.getStatus()) {
                                    setText("Received");
                                } else {
                                    setText("Closed");
                                }}}};}});
            dateColumn.setCellFactory(new Callback<TableColumn<Complaint, LocalDateTime>, TableCell<Complaint, LocalDateTime>>() {
                @Override
                public TableCell<Complaint, LocalDateTime> call(TableColumn<Complaint, LocalDateTime> col) {
                    return new TableCell<Complaint, LocalDateTime>() {
                        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.format(formatter));
                            }}};}});
            complaintTable.setItems(complaintObservableList);
            for(Complaint complaint: complaints) {
                if (!complaint.getStatus()) { // Assuming getStatus() returns false if not responded
                    LocalDateTime submissionTime = complaint.getSubmissionDate();
                    LocalDateTime deadline = submissionTime.plusHours(24);
                    LocalDateTime currentTime = LocalDateTime.now();

                    long hoursLeft = java.time.Duration.between(currentTime, deadline).toHours();
                    long minutesLeft = java.time.Duration.between(currentTime, deadline).toMinutesPart();

                    if (hoursLeft < 2) {
                        String timeLeftMessage = String.format(
                                "Time remaining for Complaint NO. %d: %d hours and %d minutes.\nImmediate action is needed.",
                                complaint.getId(),
                                hoursLeft,
                                minutesLeft);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Complaint Response Time");
                        alert.setContentText(timeLeftMessage);
                        alert.showAndWait();
                    }}}});
    }

    private void requestComplaintFromServer() {
        try {
            NewMessage message = new NewMessage("complaintsList");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void requestLogoutFromServer() {
        try {
            NewMessage message = new NewMessage("logOut", LoginPage.employee1);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }}
}
