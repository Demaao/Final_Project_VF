package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Purchase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class PersonalDetailsPage {

    @FXML
    private Button ChargebackBtn , cardsBtn,changeHostBtn,complaintsBtn,enterBtn, homePageBtn,loginBtn, moviesBtn,OKBtn;

    @FXML
    private TableView<Purchase> purchasesTable;

    @FXML
    private TableColumn<Purchase, String> orderNumberColumn;

    @FXML
    private TableColumn<Purchase, String> orderPriceColumn;

    @FXML
    private TableColumn<Purchase, String> orderDateColumn;

    @FXML
    private TableColumn<Purchase, Double> orderDetailsColumn;




     //we have to add all the functions about the orders history


    @FXML
    private void switchToHomePage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("CardsPage");
    }

    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        App.switchScreen("PersonalAreaPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("MoviesPage");
    }
    @FXML
    private void switchToPersonalArea() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("PersonalAreaPage");
    }

}
