package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.time.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChargebackPage {

    public TableColumn productNOColumn;
    public TableColumn typeColumn;
    public TableColumn dateColumn;
    public TableColumn priceColumn;
    public TableColumn methodColumn;
    public TableColumn paymentDetailsColumn;
    @FXML
    private TextField IDNumText;

    @FXML
    private Menu cardsMenue;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Menu chargebackMenue;

    @FXML
    private Menu comlaintsMenue;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Menu moviesMenue;

    @FXML
    private TableView<Purchase> purchasesTable;

    @FXML
    private Button returnProductBtn;

    @FXML
    private Label selectProductText;

    @FXML
    private Button signUpBtn;

    private ObservableList<Purchase> purchaseList = FXCollections.observableArrayList();
    private List<Purchase> purchases = new ArrayList<>();
    private List<Purchase> allPurchases = new ArrayList<>();

    public void initialize() {
        EventBus.getDefault().register(this);
        requestPurchasesFromServer();
        purchasesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                returnProductBtn.setDisable(false);
            }});
    }

    @FXML
    void showPurchasesTable(ActionEvent event) {
        Platform.runLater(() -> {
        returnProductBtn.setDisable(true);
        if(IDNumText.getText().isEmpty() || IDNumText.getText().length() != 9 || !IDNumText.getText().matches("\\d+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid ID number");
            alert.show();
            return;
        } else {
            purchaseList.clear(); // Clear existing items
            ObservableList<Purchase> items = purchasesTable.getItems();
            items.clear();
            purchases.clear();
            if (!IDNumText.getText().isEmpty()) {
                for(Purchase purchase : allPurchases) {
                    if(purchase.getCustomer().getId() == Integer.parseInt(IDNumText.getText())){
                        if (purchase instanceof Card || purchase instanceof HomeMoviePurchase) { // || purchase instanceof MovieTicket){
                            purchases.add(purchase);
                        }}
                }
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
                purchaseList.addAll(purchases);
                purchasesTable.setItems(purchaseList);
            }}
            if (purchasesTable.getItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No matching customer found!");
                alert.showAndWait();
            } else {
                purchasesTable.setVisible(true);
                selectProductText.setVisible(true);
                returnProductBtn.setVisible(true);
            }
        });
    }

    @Subscribe
    public void handleUpdatePurchasesEvent(UpdatePurchasesEvent event) {
        Platform.runLater(() -> {
            purchaseList.clear(); // Clear existing items
            ObservableList<Purchase> items = purchasesTable.getItems();
            items.clear();
            purchases.clear();
            allPurchases = event.getPurchases();
            if(!IDNumText.getText().isEmpty()){
                allPurchases.removeIf(purchase -> purchase.getCustomer().getId() != Integer.parseInt(IDNumText.getText()));
                for(Purchase purchase : allPurchases) {
                    if(purchase instanceof Card || purchase instanceof HomeMoviePurchase) { // || purchase instanceof MovieTicket){
                        purchases.add(purchase);
                    }
                } dateColumn.setCellFactory(new Callback<TableColumn<Complaint, LocalDateTime>, TableCell<Complaint, LocalDateTime>>() {
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
                purchaseList.addAll(purchases);
                purchasesTable.setItems(purchaseList);
            }});
    }

    public void returnProduct(ActionEvent actionEvent) {
        Purchase selectedItem = purchasesTable.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Card) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Cards cannot be returned!");
            alert.showAndWait();
        }
        else {
            double refundPercentage = 0;
            if(selectedItem instanceof HomeMoviePurchase){
               LocalDateTime screeningTime = ((HomeMoviePurchase) selectedItem).getScreening().getScreeningTime();
                LocalDateTime currentTime = LocalDateTime.now();
                Duration duration = Duration.between(currentTime, screeningTime);
                // Get the difference in hours
                long hoursDifference = duration.toHours();

                // Determine refund based on hours difference
                if (hoursDifference >= 1) {
                    refundPercentage = 50.0;
                } else {
                    refundPercentage = 0.0;
                }
            }/*
            if(selectedItem instanceof MovieTicket){
               LocalDateTime screeningTime = ((MovieTicket) selectedItem).getScreening().getScreeningTime();
                LocalDateTime currentTime = LocalDateTime.now();
                Duration duration = Duration.between(currentTime, screeningTime);
                double refundPercentage;
                // Get the difference in hours
                long hoursDifference = duration.toHours();

                // Determine refund based on hours difference
                if (hoursDifference >= 3) {
                    refundPercentage = 100.0;
                } else if(hoursDifference >= 1){
                    refundPercentage = 50.0;
                } else{
                refundPercentage = 0.0;
         }
            }*/
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Refund");
            if (refundPercentage > 0) {
                alert.setContentText("you are eligible for a " + refundPercentage + "% refund.\nThe refund will be processed to the original payment method used for the purchase.\nAre you sure you want to return this product?");
            } else {
                alert.setContentText("No refund is available.");
            }
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType cancelButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, cancelButton);
            alert.showAndWait().ifPresent(response -> {
                if (response == yesButton) {
                    try {
                        NewMessage msg = new NewMessage(selectedItem, "returnProduct");
                        SimpleClient.getClient().sendToServer(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }}});}
    }

    @FXML
    private void requestPurchasesFromServer() {
        try {
            NewMessage message = new NewMessage("fetchPurchases");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void switchToChargebackPolicyPage() throws IOException {
        App.switchScreen("ChargebackPolicyPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        App.switchScreen("ChargebackPage");
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
    private void switchToComplaintPage() throws IOException {
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        App.switchScreen("CardsPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        App.switchScreen("MoviesPage");
    }
    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        App.switchScreen("PersonalAreaPage");
    }
}
