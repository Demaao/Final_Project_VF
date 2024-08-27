package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

public class PaymentPage {

    @FXML
    private TextField IDNumText;

    @FXML
    private TextField creditCardTxt;

    @FXML
    private TextField emailText;

    @FXML
    private TextField fullNameText;

    @FXML
    private Button payBtn;

    @FXML
    private TextField phoneText;

    @FXML
    private Label totalPriceLabel;

    @FXML
    void payForProduct(ActionEvent event) {
        if (fullNameText.getText().isEmpty() || IDNumText.getText().isEmpty() ||
                phoneText.getText().isEmpty() || emailText.getText().isEmpty() ||
                creditCardTxt.getText().isEmpty()) {

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "All fields must be filled out to complete the payment.");
                alert.show();
            });
            return;
        }

        // Validate each field individually
        if (!validateFullName() || !validateIDNumber() || !validatePhoneNumber() ||
                !validateEmail() || !validateCreditCard()) {
            return; // Stop processing if validation fails
        }

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Payment completed successfully!");
            alert.show();
        });
    }

    private boolean validateFullName() {
        String fullName = fullNameText.getText().trim();
        if (!Pattern.matches("[a-zA-Z\\s]+", fullName)) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Invalid full name. Please use only letters and spaces.");
                alert.show();
            });
            return false;
        }
        return true;
    }

    private boolean validateIDNumber() {
        String idNumber = IDNumText.getText().trim();
        if (!Pattern.matches("\\d{9}", idNumber)) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Invalid ID number. Please enter a 9-digit number.");
                alert.show();
            });
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = phoneText.getText().trim();
        if (!Pattern.matches("\\d{10}", phoneNumber)) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Invalid phone number. Please enter a 10-digit number.");
                alert.show();
            });
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String email = emailText.getText().trim();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(emailRegex, email)) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Invalid email format.");
                alert.show();
            });
            return false;
        }
        return true;
    }

    private boolean validateCreditCard() {
        String creditCard = creditCardTxt.getText().trim();
        if (!Pattern.matches("\\d{16}", creditCard)) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Invalid credit card number. Please enter a 16-digit number.");
                alert.show();
            });
            return false;
        }
        return true;
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        App.switchScreen("CardsPage");
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
    private void switchToChargebackPage() throws IOException {
        App.switchScreen("ChargebackPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        App.switchScreen("MoviesPage");
    }
}
