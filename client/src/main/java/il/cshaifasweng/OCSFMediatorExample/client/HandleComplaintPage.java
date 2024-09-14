package il.cshaifasweng.OCSFMediatorExample.client;

import com.sun.javafx.menu.MenuItemBase;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandleComplaintPage {
    public TableColumn statusColumn;
    @FXML
    private TextArea answerText;

    @FXML
    private Button chancelPurchaseBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    public TableColumn<?, ?> productNOColumn;

    @FXML
    public TableColumn<?,?> priceColumn;

    @FXML
    public TableColumn<?,?> methodColumn;

    @FXML
    public TableColumn<?,?> paymentDetailsColumn;

    @FXML
    public TableView<Complaint> complaintTable;

    @FXML
    public TableColumn<?,?> typeColumn;

    @FXML
    public TableColumn<?,?> dateColumn;

    @FXML
    private Button hidePurchases;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    public TableView<?> purchaseHistoryTable;

    @FXML
    private AnchorPane purchaseHistory;

    @FXML
    private Button seeCustomerPurchasesBtn;

    @FXML
    private Button submitAnswerBtn;

    @FXML
    private Button cancelPurchaseBtn;

    public static Complaint selectedComplaint;

    public static void setSelectedComplaint(Complaint complaint) {
        selectedComplaint = complaint;
    }

    private ObservableList<Complaint> complaintObservableList = FXCollections.observableArrayList();

    public void initialize() {
        EventBus.getDefault().register(this);
        ObservableList<Complaint> items = complaintTable.getItems();
        items.clear();
        complaintObservableList.add(selectedComplaint);
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
        complaintTable.setItems(complaintObservableList);
        purchaseHistoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cancelPurchaseBtn.setDisable(false);
            }});
    }

    @Subscribe
    public void onUpdateComplaintsEvent(UpdateComplaintsEvent event) {
        Platform.runLater(() -> {
            for(Complaint complaint : event.getComplaints()) {
                if (Objects.equals(complaint.getId(), selectedComplaint.getId())) {
                   setSelectedComplaint(complaint);
                }
            }
            ObservableList<Complaint> items = complaintTable.getItems();
            items.clear();
            complaintObservableList.add(selectedComplaint);
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
            complaintTable.setItems(complaintObservableList);
        });
    }

    @FXML
    void cancelPurchase(ActionEvent event) {

    }

    @FXML
    void hidePurchases(ActionEvent event) {
        purchaseHistory.setVisible(false);
    }

    @FXML
    void showPurchaseHistory(ActionEvent event) {
        purchaseHistory.setVisible(true);
    }

    @FXML
    void submitAnswer(ActionEvent event) {
        if (selectedComplaint.getStatus()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The complaint has been resolved and closed!");
            alert.show();
        } else {
            if (answerText.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Response is empty!");
                alert.show();
            } else {
                selectedComplaint.setResponse(answerText.getText());
                try {
                    NewMessage msg = new NewMessage(selectedComplaint, "answerComplaint");
                    SimpleClient.getClient().sendToServer(msg);
                    answerText.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToCustomerServiceWorkerPage() throws IOException {
        App.switchScreen("CustomerServiceWorkerPage");
    }


    @FXML
    private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
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
