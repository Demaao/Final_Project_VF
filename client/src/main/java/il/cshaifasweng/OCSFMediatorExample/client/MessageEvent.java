package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import java.util.List;

public class MessageEvent {
    private String message;
    private List<Movie> movieList;

    public MessageEvent(String message, List<Movie> movieList) {
        this.message = message;
        this.movieList = movieList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public MessageEvent(String message) {}
}