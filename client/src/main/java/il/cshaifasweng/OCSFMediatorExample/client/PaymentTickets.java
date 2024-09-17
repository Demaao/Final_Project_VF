package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PaymentTickets {

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
    private Label totalPriceLabel;

    @FXML
    private RadioButton creditCardRadioBtn;

    @FXML
    private RadioButton ticketTabRadioBtn;

    @FXML
    private Button payBtn;

    @FXML
    private AnchorPane creditCardPane;

    @FXML
    private AnchorPane ticketTabPane;

    @FXML
    private ToggleGroup paymentMethodToggleGroup;

    @FXML
    private Label creditCardLabel;  // Updated label to switch text dynamically

    @FXML
    void initialize() {
        paymentMethodToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == creditCardRadioBtn) {
                creditCardPane.setVisible(true);
                ticketTabPane.setVisible(false);
                creditCardLabel.setText("Credit Card");  // Set label to "Credit Card"
                creditCardTxt.setPromptText("Credit Card Number");  // Set prompt text
            } else if (newToggle == ticketTabRadioBtn) {
                creditCardPane.setVisible(true);
                ticketTabPane.setVisible(false);
                creditCardLabel.setText("Ticket Tab Number");  // Set label to "Ticket Tab Number"
                creditCardTxt.setPromptText("Ticket Tab Number");  // Set prompt text
            }
        });
    }

    @FXML
    void payForProduct(ActionEvent event) {
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
            String alertMessage = errorMessages.size() == 1 ? errorMessages.get(0) :
                    "Multiple errors detected. Please review the highlighted fields and correct the issues.";
            showAlert(Alert.AlertType.WARNING, "Validation Errors", alertMessage);
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Payment Completed", "Payment completed successfully!");
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

    private boolean validatePhoneNumber() {
        return Pattern.matches("\\d{10}", phoneText.getText().trim());
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
            alert.show();
        });
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
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
    private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        App.switchScreen("ChargebackPage");
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

