package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class App extends Application {

    private static Scene scene;
    static SimpleClient client;
    private static Stage appStage;

    public static AbstractClient getClient() {
        return client;
    }

    @Override
    public void start(Stage stage) throws IOException {
    	EventBus.getDefault().register(this);
    	client = SimpleClient.getClient();
    	client.openConnection();
        scene = new Scene(loadFXML("HostPage"), 900, 650);
        appStage = stage;
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
    	EventBus.getDefault().unregister(this);
		super.stop();
	}

   public static void setContent(String pageName) throws IOException {
        Parent root = loadFXML(pageName);
        scene = new Scene(root);
        appStage.setScene(scene);
        appStage.show();
    }

    public static void switchScreen(String screenName) {
        switch (screenName) {
            case "MoviesList":
                Platform.runLater(() -> {
                    try {
                        setContent("MoviesList");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "secondary":
                Platform.runLater(() -> {
                    try {
                        setContent("secondary");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "updateList":
                Platform.runLater(() -> {
                    try {
                        setContent("updateList");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "HostPage":
                Platform.runLater(() -> {
                    try {
                        setContent("HostPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "HomePage":
                Platform.runLater(() -> {
                    try {
                        setContent("HomePage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "LoginPage":
                Platform.runLater(() -> {
                    try {
                        setContent("LoginPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ComplaintPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ComplaintPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ChargebackPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ChargebackPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ChargebackPolicyPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ChargebackPolicyPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "CardsPage":
                Platform.runLater(() -> {
                    try {
                        setContent("CardsPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "CheckTicketsInCardPage":
                Platform.runLater(() -> {
                    try {
                        setContent("CheckTicketsInCardPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PurchaseProductsPage":
                Platform.runLater(() -> {
                    try {
                        setContent("PurchaseProductsPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PaymentPage":
                Platform.runLater(() -> {
                    try {
                        setContent("PaymentPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "MoviesPage":
                Platform.runLater(() -> {
                    try {
                        setContent("MoviesPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "FiledComplaintsPage":
                Platform.runLater(() -> {
                    try {
                        setContent("FiledComplaintsPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "CustomerServiceWorkerPage":
                Platform.runLater(() -> {
                    try {
                        setContent("CustomerServiceWorkerPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "HandleComplaintPage":
                Platform.runLater(() -> {
                    try {
                        setContent("HandleComplaintPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ContentManagerPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ContentManagerPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "AddMoviePage":
                Platform.runLater(() -> {
                    try {
                        setContent("AddMoviePage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "RemoveMoviePage":
                Platform.runLater(() -> {
                    try {
                        setContent("RemoveMoviePage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "EditPricesPage":
                Platform.runLater(() -> {
                    try {
                        setContent("EditPricesPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "EditScreeningPage":
                Platform.runLater(() -> {
                    try {
                        setContent("EditScreeningPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "HeadManagerPage":
                Platform.runLater(() -> {
                    try {
                        setContent("HeadManagerPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "TicketsReportPage":
                Platform.runLater(() -> {
                    try {
                        setContent("TicketsReportPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "CardsAndLinksReportPage":
                Platform.runLater(() -> {
                    try {
                        setContent("CardsAndLinksReportPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ComplaintsReportPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ComplaintsReportPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ConfirmPricesUpdatesPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ConfirmPricesUpdatesPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "BranchManagerPage":
                Platform.runLater(() -> {
                    try {
                        setContent("BranchManagerPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
        }
    }

    @Subscribe
    public void onWarningEvent(WarningEvent event) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    String.format("Message: %s\nTimestamp: %s\n",
                            event.getWarning().getMessage(),
                            event.getWarning().getTime().toString())
            );
            alert.show();
        });
    }

	public static void main(String[] args) {
        launch();
    }
}