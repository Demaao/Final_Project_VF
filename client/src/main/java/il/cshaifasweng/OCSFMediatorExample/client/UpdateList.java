//package il.cshaifasweng.OCSFMediatorExample.client;

//import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
//import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
//import javafx.application.Platform;
////import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TextField;
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//
//public class UpdateList  {

   // private String selectedItemUpdate;
  //  private String selectedItemTitle;

   // @FXML // fx:id="backBtn"
   // private Button backBtn; // Value injected by FXMLLoader

   // @FXML // fx:id="confirmBtn"
    //private Button confirmBtn; // Value injected by FXMLLoader

   // @FXML // fx:id="movieList"
   // private ListView<String> movieListViewUpdate;

   // @FXML // fx:id="screenTimeText"
   // private TextField screenTimeText; // Value injected by FXMLLoader

   // @FXML // fx:id="showUpdatedList"
   // private Button showUpdatedList; // Value injected by FXMLLoader
    //private ObservableList<String> movieListUpdate;

   // static List<Movie> movieL1 = new ArrayList<Movie>();

    ///@FXML
    ////public void initialize() {
       // EventBus.getDefault().register(this);
       // movieListUpdate = FXCollections.observableArrayList();
       // movieListUpdate.clear();
       // for(Movie movie : movieL1) {
       //     movieListUpdate.add(movie.toString());
       // }
        //movieListViewUpdate.setItems(movieListUpdate);
      //  movieListViewUpdate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
       //     @Override
          //  public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
              //  selectedItemUpdate = movieListViewUpdate.getSelectionModel().getSelectedItem();
              //  String[] parts = selectedItemUpdate.split("-");
               // selectedItemUpdate = parts[1].strip();
               /// screenTimeText.setText(selectedItemUpdate);
               // selectedItemTitle = parts[0].strip();
        //    }
    //    });
  //  }

  // @Subscribe
    //public void onMessageUpdate(MessageEvent event) {
       // Platform.runLater(() -> {
          //  if (event.getMessage().equals("moviesUpdated")) {
             //   List<Movie> movies = event.getMovieList();
               // movieListUpdate.clear();
                //for (Movie movie : movies) {
                //    movieListUpdate.add(movie.toString());
               // }
                //movieListViewUpdate.setItems(movieListUpdate);
          //  }
      //  });
  //  }

 //@Subscribe
 //public void onUpdatedEvent(MessageEvent event) {
   //  Platform.runLater(() -> {
     //if (event.getMessage().equals("changed")) {
      //       Alert alert = new Alert(Alert.AlertType.INFORMATION,
           //          String.format("The List has been updated successfully\n")
          //   );
            // alert.showAndWait();
     //    }
   // });
// }

  //  @FXML
   // void sentAlert(ActionEvent event) {
       // if(screenTimeText.getText() != null) {
         //   try {
          //      SimpleClient.getClient().sendToServer(new NewMessage(selectedItemTitle + "-" + screenTimeText.getText()));
          //  } catch (IOException e) {
             //   e.printStackTrace();
           // }
           // String movieDetails = selectedItemTitle + "        -      " + screenTimeText.getText();
            //int selectedIndex = movieListViewUpdate.getSelectionModel().getSelectedIndex();
            //if (selectedIndex >= 0) {
              //  movieListUpdate.set(selectedIndex, movieDetails);
         //   }
          //  movieListViewUpdate.refresh();
      //  }
   // }

  //  @FXML
  //  private void switchToSecondary() throws IOException {
    //    App.switchScreen("secondary");
    //}

   // @FXML
    //private void switchToUpdatedList() throws IOException {
    // try {
       //     SimpleClient.getClient().sendToServer(new NewMessage("movieList"));
       // } catch (IOException e) {
       //     e.printStackTrace();
       // }
       // App.switchScreen("movieList");
  //  }
 //}