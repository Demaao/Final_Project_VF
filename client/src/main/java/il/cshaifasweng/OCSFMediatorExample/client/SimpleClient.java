package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.HomeMovie;
import il.cshaifasweng.OCSFMediatorExample.entities.SoonMovie;
import javafx.application.Platform;
import org.greenrobot.eventbus.EventBus;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import java.util.List;

public class SimpleClient extends AbstractClient {

	public static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof NewMessage) {
			NewMessage message = (NewMessage) msg;
			Platform.runLater(() -> {
				if (message.getMessage().equals("movies")) {
					List<Movie> movies = (List<Movie>) message.getObject();
					EventBus.getDefault().post(new UpdateMoviesEvent(movies));
				} else if (message.getMessage().equals("soonMovies")) {
					List<SoonMovie> soonMovies = (List<SoonMovie>) message.getObject();
					EventBus.getDefault().post(new UpdateSoonMoviesEvent(soonMovies));
				} else if (message.getMessage().equals("homeMovies")) {  // טיפול בהודעת homeMovies
					List<HomeMovie> homeMovies = (List<HomeMovie>) message.getObject();
					EventBus.getDefault().post(new UpdateHomeMoviesEvent(homeMovies));
				}
			});
		}
	}


	// Singleton pattern to get the client instance
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
	public static void setClient(SimpleClient client) {
		SimpleClient.client= client;
	}
}





