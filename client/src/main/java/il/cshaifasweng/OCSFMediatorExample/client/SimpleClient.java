package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import org.greenrobot.eventbus.EventBus;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;

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
				} else if (message.getMessage().equals("login")) {  ///////////////////////////////////////////////////////
					App.loginDeniedCounter = 0;
					Employee employee = (Employee) message.getObject();
					EventBus.getDefault().post(new UpdateLoginEvent(employee));
				} else if (message.getMessage().equals("loginDenied")) {
					App.loginDeniedCounter++;
					EventBus.getDefault().post(new WarningEvent(new Warning("User name or Password is incorrect!")));
				} else if (message.getMessage().equals("Alreadylogin")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("User is already logged in!")));
				} else if (message.getMessage().equals("logOut")) {
					EventBus.getDefault().post(new MessageEvent("Log out"));
					EventBus.getDefault().post(new WarningEvent(new Warning("Logged out successfully!")));
				} else if (message.getMessage().equals("screeningTimes")) {
					List<Screening> screenings = (List<Screening>) message.getObject();
					EventBus.getDefault().post(new UpdateScreeningTimesEvent(screenings));
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
}