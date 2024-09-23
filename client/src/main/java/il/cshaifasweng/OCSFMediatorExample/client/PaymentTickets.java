package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PaymentTickets {
    private static BookingSeatsReq request;
    public static Screening screening; //////////////////////////////////////////////////////////////////
    private static String temp;
    private static  String temp1;
    @FXML
    private TextField IDNumText;

    @FXML
    private TextField creditCardTxt;

    @FXML
    private TextField emailText;

    @FXML
    private TextField fullNameText;

    @FXML
    private TextField phoneText;


    @FXML
    private RadioButton creditCardRadioBtn;

    @FXML
    private RadioButton ticketTabRadioBtn;

    @FXML
    private AnchorPane creditCardPane;

    @FXML
    private AnchorPane ticketTabPane;

    @FXML
    private ToggleGroup paymentMethodToggleGroup;

    @FXML
    private Label creditCardLabel;  // Updated label to switch text dynamically

    @FXML // fx:id="movieInfo"
    private Label movieInfo; // Value injected by FXMLLoader

    @FXML // fx:id="costInfo"
    private Label costInfo; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    private  double totalPrice;

    @FXML
    void initialize() {
        paymentMethodToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == creditCardRadioBtn) {
                creditCardLabel.setVisible(true);
                creditCardTxt.setVisible(true);
            } else if (newToggle == ticketTabRadioBtn) {
                creditCardLabel.setVisible(false);
                creditCardTxt.setVisible(false);
            }
        });
        screening = request.getScreening();

        temp = "Order Summary:\nMovie: " + screening.getMovie().getEngtitle()
                + "\nDate: " + screening.getScreeningTime().toLocalDate()
                + "\nTime: " + screening.getScreeningTime().toLocalTime()
                + "\nNumber of tickets: " + request.getArrSize() + "\nSeats IDs: ";
        for (int i = 0; i < request.getArrSize(); i++) {
            temp += request.getSeatIds()[i] + " ";
        }
        movieInfo.setText(temp);
        String cost   = "Cost:\n" + request.getArrSize()+ " X " + MovieDetailsPage.ticketValue  + "\nTotal: " + MovieDetailsPage.ticketValue*request.getArrSize() + "$";
        costInfo.setText(cost);
    }

    public static int paymentCanceledFlag = 0;

    @FXML
    void CancelOrder(ActionEvent event) {
        paymentCanceledFlag = 1;
        MovieDetailsPage.setTicketPrice = 0;
        try {
            SimpleClient.getClient().sendToServer(new NewMessage( request,"UndoSaveSeatsInHall"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        App.switchScreen("MoviesPage");
    }

    @FXML
    void ConfirmOrder(ActionEvent event) {
        List<String> errorMessages = new ArrayList<>();

        resetFieldStyles();

        if (creditCardRadioBtn.isSelected()) {
            if (fullNameText.getText().isEmpty() || !validateFullName()) {
                highlightFieldError(fullNameText);
                errorMessages.add("Invalid full name. Please use only letters and spaces.");
            }

            if (IDNumText.getText().isEmpty() || !validateIDNumber()) {
                highlightFieldError(IDNumText);
                errorMessages.add("Invalid ID number. Please enter a 9-digit number.");
            }

            if (phoneText.getText().isEmpty() || !validatePhoneNumber()) {
                highlightFieldError(phoneText);
                errorMessages.add("Invalid phone number. Please enter a 10-digit number.");
            }

            if (emailText.getText().isEmpty() || !validateEmail()) {
                highlightFieldError(emailText);
                errorMessages.add("Invalid email format.");
            }

            if (creditCardTxt.getText().isEmpty() || !validateCreditCard()) {
                highlightFieldError(creditCardTxt);
                errorMessages.add("Invalid credit card number. Please enter a 16-digit number.");
            }
        } else if (ticketTabRadioBtn.isSelected()) {
            if (IDNumText.getText().isEmpty() || !validateIDNumber()) {
                highlightFieldError(IDNumText);
                errorMessages.add("Invalid ID number. Please enter a 9-digit number.");
            }

            if (creditCardTxt.getText().isEmpty()) {
                highlightFieldError(creditCardTxt);
                errorMessages.add("Invalid ticket tab number. Please enter a valid number.");
            }
        }
        if (!errorMessages.isEmpty()) {
            String alertMessage;
            if (errorMessages.size() == 1) {
                alertMessage = errorMessages.get(0);
            } else {
                alertMessage = "Multiple errors detected. Please review the highlighted fields and correct the issues.";
            }

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Errors");
                alert.setHeaderText(null);
                alert.setContentText(alertMessage);
                alert.show();
            });
            return; // Stop processing if validation fails
        }
/*
        if (!errorMessages.isEmpty()) {
            String alertMessage = errorMessages.size() == 1 ? errorMessages.get(0) :
                    "Multiple errors detected. Please review the highlighted fields and correct the issues.";
            showAlert(Alert.AlertType.WARNING, "Validation Errors", alertMessage);
            return;
        }*/


        // Gather data from input fields
        int customerId = Integer.parseInt(IDNumText.getText());
        String fullName = fullNameText.getText();
        String email = emailText.getText();
        String phone = phoneText.getText();
        String creditCard = creditCardTxt.getText();


        Customer customer = new Customer(customerId, fullName, email, phone, new ArrayList<>(), false);
        LocalDateTime time = LocalDateTime.now();
        List<Purchase> purchases = new ArrayList<>();


        Purchase purchase = new Purchase("Movie Tickets", time, "Credit Card",
                MovieDetailsPage.ticketValue*request.getArrSize(), customer, screening.getBranch().getName(), request.getArrSize(), "Movie Tickets Details: " + temp+"\nBranch: "+ screening.getBranch().getName());

        purchases.add(purchase);

        for(int i = 0; i< request.getArrSize(); i++){
            temp1 = "\nMovie: " + screening.getMovie().getEngtitle()
                    + "\nDate: " + screening.getScreeningTime().toLocalDate()
                    + "\nTime: " + screening.getScreeningTime().toLocalTime()
                    + "\nSeat ID: " + request.getSeatIds()[i];
            Purchase ticket = new Purchase("Movie Ticket", time, "Credit Card", MovieDetailsPage.ticketValue, customer, screening.getBranch().getName(), 1,
                    "Movie Ticket Details: " + temp1 +"\nBranch: "+ screening.getBranch().getName(), ChooseSeating.arr1[i]);
            purchases.add(ticket);
        }

        // Send purchase and customer data to the server
        NewMessage message = new NewMessage(purchases,"processPaymentForTickets");
        try {
            SimpleClient.getClient().sendToServer(message);  // Send to the server
        } catch (IOException e) {
            e.printStackTrace();
        }

        temp = "Order Summary:\nMovie: " + screening.getMovie().getEngtitle() + " - " + screening.getMovie().getHebtitle()
                + "\nScreening day is: " + screening.getScreeningTime().toLocalDate()
                + "\nScreening time is: " + screening.getScreeningTime().toLocalTime()
                + "\nNumber of tickets: " + request.getArrSize() + "\nSeats IDs: ";
        for (int i = 0; i < request.getArrSize(); i++) {
            temp += request.getSeatIds()[i] + " ";
        }
        temp+="\nScreening hall: "+ screening.getHall().getHallName()+ "\nBranch: "+ screening.getBranch().getName(); //+ "\n\n Order summary will be sent to your E-mail right away!";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Purchase Receipt");
        alert.setHeaderText("Purchase completed successfully!");
        alert.setContentText(temp + "\n\nNote: You can see your purchase details in the personal area.");
        alert.show();

        MovieDetailsPage.movieDetailsPage = 0;
        MovieDetailsPage.setTicketPrice = 0;
        App.switchScreen("MoviesPage");
        // showAlert(Alert.AlertType.INFORMATION, "Payment Completed", "Payment completed successfully!\n"+temp) ;
    }

    private void resetFieldStyles() {
        fullNameText.getStyleClass().remove("error");
        IDNumText.getStyleClass().remove("error");
        phoneText.getStyleClass().remove("error");
        emailText.getStyleClass().remove("error");
        creditCardTxt.getStyleClass().remove("error");
    }

    private boolean validateFullName() {
        return Pattern.matches("[a-zA-Z\\s]+", fullNameText.getText().trim());
    }

    private boolean validateIDNumber() {
        return Pattern.matches("\\d{9}", IDNumText.getText().trim());
    }

    //private boolean validatePhoneNumber() {
//        return Pattern.matches("\\d{10}", phoneText.getText().trim());
//    }

    private boolean validatePhoneNumber() {
        String phoneNumber = phoneText.getText().trim();
        return phoneNumber.length() == 10 && Pattern.matches("\\d{10}", phoneNumber);
    }


    private boolean validateEmail() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, emailText.getText().trim());
    }

    private boolean validateCreditCard() {
        return Pattern.matches("\\d{16}", creditCardTxt.getText().trim());
    }

    private void highlightFieldError(TextField field) {
        field.getStyleClass().add("error");
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);

            // Add a handler for both "OK" and "X" (alert close)
            alert.showAndWait().ifPresentOrElse(response -> {
                if (response == ButtonType.OK) {
                    // User pressed "OK" button
                    App.switchScreen("MoviesPage");  // Replace with your actual navigation logic
                }
            }, () -> {
                // User closed the alert (clicked "X")
                App.switchScreen("MoviesPage");  // Navigate to the next page as well
            });
        });
    }

    public static void setRequest(BookingSeatsReq request) {
        PaymentTickets.request = request;
    }

    public static BookingSeatsReq getRequest() {
        return request;
    }


    @FXML
    public void switchToMoviesPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        MovieDetailsPage.setTicketPrice = 0;
        try { SimpleClient.getClient().sendToServer(new NewMessage( request,"UndoSaveSeatsInHall"));
        } catch (IOException e) {
            e.printStackTrace();}
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        MovieDetailsPage.setTicketPrice = 0;
        try { SimpleClient.getClient().sendToServer(new NewMessage( request,"UndoSaveSeatsInHall"));
        } catch (IOException e) {
            e.printStackTrace();}
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        MovieDetailsPage.setTicketPrice = 0;
        try { SimpleClient.getClient().sendToServer(new NewMessage( request,"UndoSaveSeatsInHall"));
        } catch (IOException e) {
            e.printStackTrace();}
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        MovieDetailsPage.setTicketPrice = 0;
        try { SimpleClient.getClient().sendToServer(new NewMessage( request,"UndoSaveSeatsInHall"));
        } catch (IOException e) {
            e.printStackTrace();}
        App.switchScreen("CardsPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        MovieDetailsPage.setTicketPrice = 0;
        try { SimpleClient.getClient().sendToServer(new NewMessage( request,"UndoSaveSeatsInHall"));
        } catch (IOException e) {
            e.printStackTrace();}
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        MovieDetailsPage.setTicketPrice = 0;
        try { SimpleClient.getClient().sendToServer(new NewMessage(request,"UndoSaveSeatsInHall"));
        } catch (IOException e) {
            e.printStackTrace();}
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        MovieDetailsPage.setTicketPrice = 0;
        try { SimpleClient.getClient().sendToServer(new NewMessage( request,"UndoSaveSeatsInHall"));
        } catch (IOException e) {
            e.printStackTrace();}
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        MovieDetailsPage.setTicketPrice = 0;
        try { SimpleClient.getClient().sendToServer(new NewMessage( request,"UndoSaveSeatsInHall"));
        } catch (IOException e) {
            e.printStackTrace();}
        App.switchScreen("PersonalAreaPage");
    }

}

