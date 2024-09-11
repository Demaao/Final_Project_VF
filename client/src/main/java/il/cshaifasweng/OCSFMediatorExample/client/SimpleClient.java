package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import org.greenrobot.eventbus.EventBus;
import java.io.IOException;
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
					App.loginDeniedCounter = 0;
					EventBus.getDefault().post(new WarningEvent(new Warning("User is already logged in!")));
				} else if (message.getMessage().equals("logOut")) {
					EventBus.getDefault().post(new MessageEvent("Log out"));
					EventBus.getDefault().post(new WarningEvent(new Warning("Logged out successfully!")));
				} else if (message.getMessage().equals("screeningTimes")) {
					List<Screening> screenings = (List<Screening>) message.getObject();
					EventBus.getDefault().post(new UpdateScreeningTimesEvent(screenings));
				}
				else if (message.getMessage().equals("movieRemoved")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Movie removed successfully!")));
				}
				else if(message.getMessage().equals("movieAdded")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Movie added successfully!")));
				}  else if (message.getMessage().equals("screeningAdded")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Screening added successfully!")));
			} else if (message.getMessage().equals("screeningRemoved")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Screening removed successfully!")));
			} else if (message.getMessage().equals("movieNotAvailable")) {
					EventBus.getDefault().post(new NewMessage(message.getObject() ,"movieNotAvailable"));
			} else if (message.getMessage().equals("screeningUpdated")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Screening time updated successfully!")));
			} else if (message.getMessage().equals("screeningUpdateFailed")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Failed to update screening time.")));
			} else if(message.getMessage().equals("complaintSubmitted")) {
					EventBus.getDefault().post(new WarningEvent(new Warning("Complaint submitted successfully!")));
			} else 	if (message.getMessage().equals("complaints")) {
					List<Complaint> complaints = (List<Complaint>) message.getObject();
					EventBus.getDefault().post(new UpdateComplaintsEvent(complaints));
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

