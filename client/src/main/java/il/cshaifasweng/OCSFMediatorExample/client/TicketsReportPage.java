package il.cshaifasweng.OCSFMediatorExample.client;


import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

import javafx.scene.text.Text;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.EventBus;
import java.util.concurrent.CountDownLatch;
import il.cshaifasweng.OCSFMediatorExample.entities.Card;
import il.cshaifasweng.OCSFMediatorExample.entities.HomeMoviePurchase;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.Purchase;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.EventBus;
import java.io.IOException;
import java.util.List;
import java.time.YearMonth;

public class TicketsReportPage {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<TicketReportModelClass, String> Cinema;

    @FXML
    private TableColumn<TicketReportModelClass, String> Date;

    @FXML
    private TableColumn<TicketReportModelClass, String> TicketPrice;

    @FXML
    private TableColumn<TicketReportModelClass, String> TicketsSold;

    @FXML
    private TableColumn<TicketReportModelClass, String> TotalSales;

    @FXML
    private Button cardAndLinkReportBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Button complaintsReportBtn;

    @FXML
    private Button confirmPricesBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private ComboBox<Integer> monthComboBox;

    @FXML
    private TableView<TicketReportModelClass> ticketsReportTable;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    void requestLogoutFromServer(ActionEvent event) {

    }

    @FXML
    void switchToCardsAndLinksReportPage(ActionEvent event) {

    }

    @FXML
    void switchToComplaintsReportPage(ActionEvent event) {

    }

    @FXML
    void switchToConfirmPricesUpdatesPage(ActionEvent event) {

    }

    @FXML
    void switchToHeadManagerPage(ActionEvent event) {

    }

    @FXML
    void switchToHostPage(ActionEvent event) {

    }

    @FXML
    void switchToTicketsReportPage(ActionEvent event) {

    }


    //SET DATE TO CURRENT
    LocalDateTime currentDate = LocalDateTime.now();
    int year = currentDate.getYear();
    int month = currentDate.getMonthValue();
    //CONSTRUCT AN OBSERVABLELIST
    ObservableList<TicketReportModelClass> data = FXCollections.observableArrayList();
    List<Purchase> purchaseList;
    //A WAY TO ITERATE ON ALL BRANCHES*
    String[] cinemas = {"Haifa Cinema", "Tel Aviv Cinema", "Eilat Cinema",
            "Karmiel Cinema", "Jerusalem Cinema"};



    @FXML
    void initialize() {
        EventBus.getDefault().register(this);

        //CREATE A MAPPING BETWEEN COLUMNS AND THE PERAMETERS WE WILL USE
        Cinema.setCellValueFactory(new PropertyValueFactory<>("cinema"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        TicketsSold.setCellValueFactory(new PropertyValueFactory<>("ticketsSold"));
        TotalSales.setCellValueFactory(new PropertyValueFactory<>("totalSales"));

        monthComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        int currentYear = LocalDate.now().getYear(); // Get the current year
        yearComboBox.setItems(FXCollections.observableArrayList(
                currentYear, currentYear - 1, currentYear - 2, currentYear - 3
        ));
        monthComboBox.setValue(month);
        yearComboBox.setValue(year);

        requestPurchasesFromServer();

        // Optional: Set the default value to the current year
        //yearComboBox.setValue(currentYear);


    }





    @FXML
    private void requestPurchasesFromServer() {
        try {
            NewMessage message = new NewMessage("getAllPurchasesForReport");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    @Subscribe
    public void handleUpdatePurchasesEvent(UpdatePurchasesEvent event) {
        ObservableList<TicketReportModelClass> items = ticketsReportTable.getItems();
        items.clear();
        purchaseList = event.getPurchases();
        showTable();
    }


    private void showTable() {
        Platform.runLater(() -> {
            data.clear(); // Clear the list before adding new data
        year = yearComboBox.getValue();
        month = monthComboBox.getValue();

            // Determine the number of days in the given month and year
            YearMonth yearMonth = YearMonth.of(year, month);
            int daysInMonth = yearMonth.lengthOfMonth(); // Get the number of days in the month

            for (int day = 1; day <= daysInMonth; day++) {

                for (String cinema : cinemas) {

                    int ticketsSold = 0;
                    int totalSales = 0;
                    LocalDate date = LocalDate.of(year, month, day);

                    for (Purchase purchase : purchaseList) {

                        if (purchase.getPurchaseDate() != null
                                && purchase.getBranchName() != null
                                && purchase.getPurchaseDate().getMonthValue() == month
                                && purchase.getPurchaseDate().getDayOfMonth() == day
                                && purchase.getPurchaseDate().getYear() == year
                                && purchase.getBranchName().equals(cinema)
                        && purchase.getProductType().equals("Movie Ticket")) {
                            ticketsSold += purchase.getQuantity();
                            totalSales += purchase.getPricePaid();
                        }


                    }
                    //each row in table represent a day(date + month) and a branch
                    TicketReportModelClass report =
                            new TicketReportModelClass(date, cinema,
                                    ticketsSold, totalSales);
                    //ticket price i will delete the column - not defined
                    data.add(report);
                }
            }
            ticketsReportTable.setItems(data);

        });


    }


    @FXML
    void userSettedMonth(ActionEvent event) {
       // month = monthComboBox.getValue();

        showTable();

      //  EventBus.getDefault().register(this);
        //requestPurchasesFromServer();

    }

    @FXML
    void userSettedYear(ActionEvent event) {

      //  year = yearComboBox.getValue();
        //CONSTRUCT AN OBSERVABLELIST
showTable();
        //EventBus.getDefault().register(this);
        //requestPurchasesFromServer();
     //HERE
    }

}